import java.io.*;
import java.util.*;

public record ConstantInterfaceMethodref(ConstantPoolIndex classIndex, ConstantPoolIndex nameAndTypeIndex) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantInterfaceMethodref(classFile.readIndex(), classFile.readIndex());
	}

	public ConstantTag getTag() {
		return ConstantTag.METHODREF;
	}
}
