import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.stream.*;
import java.util.function.*;

public enum ConstantTag {
	EMPTY(-1, false, null),
	UTF8(1, false, ConstantUtf8::read),
	INTEGER(3, true, ConstantInteger::read),
	FLOAT(4, true, ConstantFloat::read),
	LONG(5, true, ConstantLong::read),
	DOUBLE(6, true, ConstantDouble::read),
	CLASS(7, true, ConstantClass::read),
	STRING(8, true, ConstantString::read),
	FIELDREF(9, false, ConstantFieldref::read),
	METHODREF(10, false, ConstantMethodref::read),
	INTERFACEMETHODREF(11, false, ConstantInterfaceMethodref::read),
	NAMEANDTYPE(12, false, ConstantNameAndType::read),
	METHODHANDLE(15, true, ConstantMethodHandle::read),
	METHODTYPE(16, true, ConstantMethodType::read),
	DYNAMIC(17, true, ConstantDynamic::read),
	INVOKEDYNAMIC(18, false, ConstantInvokeDynamic::read),
	MODULE(19, false, ConstantModule::read),
	PACKAGE(20, false, ConstantPackage::read);

	public final int tag;
	public final boolean isLoadable;
	public final Reader<Constant> reader;

	private static Map<Integer, ConstantTag> tagLookupMap = Collections.unmodifiableMap(
			new HashMap<>(){{
				for (var constantTag: ConstantTag.class.getEnumConstants())
					if (constantTag != EMPTY)
						put(constantTag.tag, constantTag);
			}});

	ConstantTag(int tag, boolean isLoadable, Reader<Constant> reader) {
		this.tag = tag;
		this.isLoadable = isLoadable;
		this.reader = reader;
	}

	public static ConstantTag lookupTag(int tag) {
		return tagLookupMap.get(tag);
	}
}
