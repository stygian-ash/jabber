import java.io.*;
import java.util.*;

public record ConstantFieldref(ConstantPoolIndex classIndex, ConstantPoolIndex nameAndTypeIndex) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantFieldref(classFile.readIndex(), classFile.readIndex());
	}

	public String disassemble() {
		return classIndex.resolve().disassemble() + "." + nameAndTypeIndex.resolve().disassemble();
	}

	public ConstantTag getTag() {
		return ConstantTag.FIELDREF;
	}
}
