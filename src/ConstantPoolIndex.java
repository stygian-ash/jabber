import java.io.*;

public class ConstantPoolIndex {
	public final Constant[] constantPool;
	public final int index;

	public ConstantPoolIndex(Constant[] constantPool, int index) throws ClassFileFormatException {
		this.constantPool = constantPool;
		if (!(0 < index && index < constantPool.length + 1)) {
			Logger.error("Invalid constant pool index %d ∉ (0, %d)", index, constantPool.length + 1);
			throw new ClassFileFormatException("Invalid constant pool index %d ∉ (0, %d)", index, constantPool.length + 1);
		}
		this.index = index;
	}

	public String toString() {
		var element = constantPool[index - 1];
		return String.format("[%d] = %s", index, element == null ? "TBD" : element);
	}

	public static ConstantPoolIndex read(Constant[] constantPool, InputStream input) throws IOException, ClassFileFormatException {
		return new ConstantPoolIndex(constantPool, Binary.readU2(input));
	}
}
