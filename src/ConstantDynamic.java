import java.io.*;

public record ConstantDynamic(int bootstrapMethodAttrIndex, ConstantPoolIndex nameAndTypeIndex) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantDynamic(classFile.getInput().readShort(), classFile.readIndex());
	}

	public String disassemble() {
		throw new UnsupportedOperationException("Method disassemble()L[java/lang/String; not implemented on class " + this.getClass());
	}

	public ConstantTag getTag() {
		return ConstantTag.DYNAMIC;
	}
}
