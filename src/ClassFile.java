import java.io.*;
import java.util.*;
import java.util.stream.*;
import java.lang.reflect.*;

public class ClassFile {
	private int magic;
	private ClassFileVersion version;
	private Constant[] constantPool;
	private Set<AccessFlag> accessFlags;
	private ConstantPoolIndex thisClass, superClass;
	private ConstantPoolIndex[] interfaces;
	private Field[] fields;
	private Method[] methods;
	private Attribute[] attributes;

	private BinaryInputStream input;

	public static final int MAGIC = 0xCAFEBABE;

	public ClassFile() {}

	public ClassFile(InputStream input) throws IOException, ClassFileFormatException {
		this(new BinaryInputStream(input));
	}

	public ClassFile(BinaryInputStream input) throws IOException, ClassFileFormatException {
		this.input = input;
		readClassFile();
	}

	public void setInput(BinaryInputStream input) {
		this.input = input;
	}

	public int getMagic() {
		return magic;
	}

	public ClassFileVersion getVersion() {
		return version;
	}

	public BinaryInputStream getInput() {
		return input;
	}

	public Constant[] getConstantPool() {
		return constantPool;
	}

	public Set<AccessFlag> getAccessFlags() {
		return accessFlags;
	}

	public ConstantPoolIndex getThisClass() {
		return thisClass;
	}

	public ConstantPoolIndex getSuperClass() {
		return superClass;
	}

	public <E> E[] readTable(E[] array, Reader<E> reader) throws IOException, ClassFileFormatException {
		int size = Short.toUnsignedInt(input.readShort());
		Logger.debug("Allocating %s table of size %d", array.getClass().getComponentType().getName(), size);
		@SuppressWarnings("unchecked")
		E[] table = (E[]) Array.newInstance(array.getClass().componentType(), size);
		for (int i = 0; i < size; i++)
			table[i] = reader.apply(this);
		return table;
	}

	public ClassFile readClassFile() throws IOException, ClassFileFormatException {
		readMagic();
		version = readVersion();
		constantPool = readConstantPool();
		accessFlags = AccessFlag.parse(input.readShort());
		Logger.debug("Access flags: %s", accessFlags);
		thisClass = readIndex();
		Logger.debug("This class: %s", thisClass);
		superClass = readIndex();
		Logger.debug("Super class: %s", superClass);
		interfaces = readTable(new ConstantPoolIndex[0], i -> i.readIndex());
		fields = readTable(new Field[0], Field::read);
		methods = readTable(new Method[0], Method::read);
		attributes = readTable(new Attribute[0], Attribute::read);
		return this;
	}

	private int readMagic() throws IOException, ClassFileFormatException {
		int magic = input.readInt();
		Logger.debug("Read magic bytes 0x%08X", magic);
		if (magic != MAGIC)
			throw new ClassFileFormatException("Invalid magic bytes 0x%08X", magic);
		return magic;
	}

	private ClassFileVersion readVersion() throws IOException, ClassFileFormatException {
		short minorVersion = input.readShort(),
			majorVersion = input.readShort();

		Logger.debug("Read class file version %d.%d", majorVersion, minorVersion);
		try {
			var version = new ClassFileVersion(majorVersion, minorVersion);
			Logger.debug("Parsed class file version %s", version);
			return version;
		} catch (IllegalArgumentException exception) {
			throw new ClassFileFormatException(exception.getMessage());
		}
	}

	private Constant[] readConstantPool() throws IOException, ClassFileFormatException {
		int constantPoolCount = Short.toUnsignedInt(input.readShort());
		Logger.debug("Allocating constant pool of size %d", constantPoolCount);
		constantPool = new Constant[constantPoolCount - 1];
		for (int i = 0; i < constantPool.length; i++) {
			var constant = Constant.readConstant(this);
			constantPool[i] = constant;
			if (constant.getTag() == ConstantTag.LONG || constant.getTag() == ConstantTag.DOUBLE)
				constantPool[++i] = Constant.EMPTY;
		}
		return constantPool;
	}

	public ConstantPoolIndex readIndex() throws IOException, ClassFileFormatException {
		return new ConstantPoolIndex(constantPool, input.readShort());
	}

	public void disassemble(PrintStream stream) throws IOException {
		stream.printf(".class %s %s extends %s",
				AccessFlag.disassemble(accessFlags),
				thisClass.resolve().disassemble(),
				superClass.resolve().disassemble());
		if (interfaces.length > 0)
			stream.printf(" implements %s\n", String.join(", ", Stream.of(interfaces).map(i -> i.resolve().disassemble().toString()).toArray(String[]::new)));
		stream.println();
		Stream.of(fields).map(Field::disassemble).forEach(stream::println);
		stream.println();
		Stream.of(methods).map(Method::disassemble).forEach(stream::println);
		stream.println(".endclass");
	}

	public static void main(String[] args) throws IOException, ClassFileFormatException {
		if (args.length < 1) {
			Logger.error("No class file provided!");
			System.exit(1);
		}
		var testFile = args[0];
		Logger.info("Using file %s", testFile);
		var stream = new FileInputStream(new File(testFile));
		var classFile = new ClassFile(stream);
		classFile.disassemble(System.out);
	}
}
