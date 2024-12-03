import java.io.*;

public record ExceptionTableEntry(long startPc, long endPc, long handlerPc, ConstantPoolIndex catchType) {
	public ExceptionTableEntry(int startPc, int endPc, int handlerPc, ConstantPoolIndex catchType) {
		this(Integer.toUnsignedLong(startPc),
				Integer.toUnsignedLong(endPc),
				Integer.toUnsignedLong(handlerPc),
				catchType);
	}

	public static ExceptionTableEntry read(ClassFile classFile) throws IOException, ClassFileFormatException {
		var startPc = classFile.getInput().readShort();
		var endPc = classFile.getInput().readShort();
		var handlerPc = classFile.getInput().readShort();
		ConstantPoolIndex catchType;
		try {
			catchType = classFile.readIndex();
		} catch (ClassFileFormatException _) {
			catchType = null;
		}
		return new ExceptionTableEntry(startPc, endPc, handlerPc, catchType);
	}
}
