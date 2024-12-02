import java.io.*;

public record ConstantInvokeDynamic(int bootstrapMethodAttrIndex, ConstantPoolIndex nameAndTypeIndex) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantInvokeDynamic(classFile.getInput().readShort(), classFile.readIndex());
	}

	public ConstantTag getTag() {
		return ConstantTag.INVOKEDYNAMIC;
	}
}
