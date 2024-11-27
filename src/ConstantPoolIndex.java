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

	public Constant resolve() {
		if (constantPool == null)
			return null;
		return constantPool[index - 1];
	}

	public String toString() {
		var element = resolve();
		return String.format("[%d] = %s", index, element == null ? "TBD" : element);
	}
}
