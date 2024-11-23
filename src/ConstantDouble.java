import java.io.*;
import java.util.*;

public record ConstantDouble(double value) implements Constant {
	public static Constant read(Constant[] constantPool, InputStream input) throws IOException, ClassFileFormatException {
		long highBytes = Binary.readU4(input),
			lowBytes = Binary.readU4(input);
		double value = Double.longBitsToDouble(highBytes << 32 | lowBytes);
		return new ConstantDouble(value);
	}

	public ConstantTag getTag() {
		return ConstantTag.CONSTANT_DOUBLE;
	}
}
