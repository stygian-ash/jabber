import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public enum ConstantTag {
	CONSTANT_EMPTY              (-1,   new ClassFileVersion(45, 0), false, null                           ),
	CONSTANT_UTF8               ( 1,   new ClassFileVersion(45, 3), false, ConstantUtf8::read             ),
	CONSTANT_INTEGER            ( 3,   new ClassFileVersion(45, 3), true,  ConstantInteger::read          ),
	CONSTANT_FLOAT              ( 4,   new ClassFileVersion(45, 3), true,  ConstantFloat::read            ),
	CONSTANT_LONG               ( 5,   new ClassFileVersion(45, 3), true,  ConstantLong::read             ),
	CONSTANT_DOUBLE             ( 6,   new ClassFileVersion(45, 3), true,  ConstantDouble::read           ),
	CONSTANT_CLASS              ( 7,   new ClassFileVersion(45, 3), true,  ConstantClass::read            ) {
		@Override
		public boolean isLoadableIn(ClassFileVersion version) {
			return isAvailableIn(version) && version.compareTo(new ClassFileVersion(49, 0)) >= 0;
		}
	},
	CONSTANT_STRING             ( 8,   new ClassFileVersion(45, 3), true,  ConstantString::read            ),
	CONSTANT_FIELDREF           ( 9,   new ClassFileVersion(45, 3), false, ConstantFieldref::read          ),
	CONSTANT_METHODREF          ( 10,  new ClassFileVersion(45, 3), false, ConstantMethodref::read         ),
	CONSTANT_INTERFACEMETHODREF ( 11,  new ClassFileVersion(45, 3), false, ConstantInterfaceMethodref::read),
	CONSTANT_NAMEANDTYPE        ( 12,  new ClassFileVersion(45, 3), false, ConstantNameAndType::read       ),
	CONSTANT_METHODHANDLE       ( 15,  new ClassFileVersion(51, 0), true,  ConstantMethodHandle::read      ),
	CONSTANT_METHODTYPE         ( 16,  new ClassFileVersion(51, 0), true,  ConstantMethodType::read        ),
	CONSTANT_DYNAMIC            ( 17,  new ClassFileVersion(55, 0), true,  ConstantDynamic::read           ),
	CONSTANT_INVOKEDYNAMIC      ( 18,  new ClassFileVersion(51, 0), false, ConstantInvokeDynamic::read     ),
	CONSTANT_MODULE             ( 19,  new ClassFileVersion(53, 0), false, ConstantModule::read            ),
	CONSTANT_PACKAGE            ( 20,  new ClassFileVersion(53, 0), false, ConstantPackage::read           );

	public final int tag;
	public final ClassFileVersion version;
	private final boolean isLoadable;
	public final Reader<Constant> reader;
	private static Map<Integer, ConstantTag> tagLookupMap = new HashMap<>(){{
		for (var constantTag: ConstantTag.class.getEnumConstants())
			put(constantTag.tag, constantTag);
	}};

	ConstantTag(int tag, ClassFileVersion version, boolean isLoadable, Reader<Constant> reader) {
		this.tag = tag;
		this.version = version;
		this.isLoadable = isLoadable;
		this.reader = reader;
	}

	public boolean isAvailableIn(ClassFileVersion version) {
		return version.compareTo(this.version) >= 0;
	}

	public boolean isLoadableIn(ClassFileVersion version) {
		return isAvailableIn(version) && isLoadable;
	}

	public static ConstantTag lookupTag(int tag) {
		return tagLookupMap.get(tag);
	}
}
