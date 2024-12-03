import java.io.*;

public record ConstantPackage(ConstantPoolIndex nameIndex) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantPackage(classFile.readIndex());
	}

	public String disassemble() {
		return nameIndex.resolve().disassemble();
	}

	public ConstantTag getTag() {
		return ConstantTag.PACKAGE;
	}
}
