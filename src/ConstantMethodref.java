import java.io.*;
import java.util.*;

public record ConstantMethodref(ConstantPoolIndex classIndex, ConstantPoolIndex nameAndTypeIndex) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantMethodref(classFile.readIndex(), classFile.readIndex());
	}

	public String disassemble() {
		return classIndex.resolve().disassemble() + "." + nameAndTypeIndex.resolve().disassemble();
	}

	public ConstantTag getTag() {
		return ConstantTag.METHODREF;
	}
}
