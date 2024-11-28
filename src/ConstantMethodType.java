import java.io.*;

public record ConstantMethodType(ConstantPoolIndex descriptorIndex) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantMethodType(classFile.readIndex());
	}

	public ConstantTag getTag() {
		return ConstantTag.METHODTYPE;
	}
}
