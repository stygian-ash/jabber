import java.io.*;

public record ConstantMethodHandle(int referenceKind, ConstantPoolIndex referenceIndex) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantMethodHandle(classFile.getInput().readByte(), classFile.readIndex());
	}

	public String disassemble() {
		throw new UnsupportedOperationException("Method disassemble()L[java/lang/String; not implemented on class " + this.getClass());
	}

	public ConstantTag getTag() {
		return ConstantTag.METHODHANDLE;
	}
}
