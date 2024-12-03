import java.io.*;

public record ExceptionTableEntry(int startPc, int endPc, int handlerPc, ConstantPoolIndex catchType) {
	public static ExceptionTableEntry read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ExceptionTableEntry(
				classFile.getInput().readShort(), classFile.getInput().readShort(),
				classFile.getInput().readShort(), classFile.readIndex());
	}
}
