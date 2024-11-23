import java.io.*;
import java.util.*;

public class ClassFile {
	private int magic;
	private ClassFileVersion version;
	private Constant[] constantPool;

	private InputStream input;

	public static final int MAGIC = 0xCAFEBABE;

	public ClassFile() {}

	public ClassFile(InputStream input) throws IOException, ClassFileFormatException {
		this.input = input;
		readClassFile();
	}

	public void setInput(InputStream input) {
		this.input = input;
	}

	public ClassFile readClassFile() throws IOException, ClassFileFormatException {
		readMagic();
		version = readVersion();
		constantPool = readConstantPool();
		return this;
	}

	private int readMagic() throws IOException, ClassFileFormatException {
		int magic = Binary.readU4(input);
		Logger.debug("Read magic bytes 0x%08X", magic);
		if (magic != MAGIC) {
			Logger.error("Invalid magic bytes 0x%08X", magic);
			throw new ClassFileFormatException("Invalid magic bytes 0x%08X", magic);
		}
		return magic;
	}

	private ClassFileVersion readVersion() throws IOException, ClassFileFormatException {
		int minorVersion = Binary.readU2(input),
			majorVersion = Binary.readU2(input);

		Logger.debug("Read class file version %d.%d", majorVersion, minorVersion);
		try {
			var version = new ClassFileVersion(majorVersion, minorVersion);
			Logger.debug("Parsed class file version %s", version);
			return version;
		} catch (IllegalArgumentException exception) {
			Logger.error("%s", exception.getMessage());
			throw new ClassFileFormatException(exception.getMessage());
		}
	}

	private Constant[] readConstantPool() throws IOException, ClassFileFormatException {
		int constantPoolCount = Binary.readU2(input);
		Logger.debug("Allocating constant pool of size %d", constantPoolCount);
		var constantPool = new Constant[constantPoolCount - 1];
		for (int i = 0; i < constantPool.length; i++) {
			var constant = Constant.readConstant(constantPool, input);
			constantPool[i] = constant;
			if (constant.getTag() == ConstantTag.CONSTANT_LONG || constant.getTag() == ConstantTag.CONSTANT_DOUBLE)
				constantPool[++i] = Constant.EMPTY;
		}
		return constantPool;
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
	}
}
