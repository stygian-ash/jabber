import java.io.*;
import java.util.*;

public record ConstantNameAndType(ConstantPoolIndex nameIndex, ConstantPoolIndex descriptorIndex) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantNameAndType(classFile.readIndex(), classFile.readIndex());
	}

	public ConstantTag getTag() {
		return ConstantTag.NAMEANDTYPE;
	}
}
