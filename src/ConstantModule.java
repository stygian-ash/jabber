import java.io.*;

public record ConstantModule(ConstantPoolIndex nameIndex) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantModule(classFile.readIndex());
	}

	public String disassemble() {
		return nameIndex.resolve().disassemble();
	}

	public ConstantTag getTag() {
		return ConstantTag.MODULE;
	}
}
