import java.io.*;

public record ConstantMethodType(ConstantPoolIndex descriptorIndex) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantMethodType(classFile.readIndex());
	}

	public String disassemble() {
		return descriptorIndex.resolve().disassemble();
	}

	public ConstantTag getTag() {
		return ConstantTag.METHODTYPE;
	}
}
