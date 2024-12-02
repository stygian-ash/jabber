import java.io.*;

public record ConstantMethodHandle(int referenceKind, ConstantPoolIndex referenceIndex) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantMethodHandle(classFile.getInput().readByte(), classFile.readIndex());
	}

	public ConstantTag getTag() {
		return ConstantTag.METHODHANDLE;
	}
}
