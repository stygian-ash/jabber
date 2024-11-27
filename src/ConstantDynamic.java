import java.io.*;

public record ConstantDynamic(int bootstrapMethodAttrIndex, ConstantPoolIndex nameAndTypeIndex) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantDynamic(Binary.readU2(classFile.getInput()), classFile.readIndex());
	}

	public ConstantTag getTag() {
		return ConstantTag.CONSTANT_DYNAMIC;
	}
}
