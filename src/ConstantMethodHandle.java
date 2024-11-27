import java.io.*;

public record ConstantMethodHandle(int referenceKind, ConstantPoolIndex referenceIndex) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantMethodHandle(Binary.readU1(classFile.getInput()), classFile.readIndex());
	}

	public ConstantTag getTag() {
		return ConstantTag.CONSTANT_METHODHANDLE;
	}
}
