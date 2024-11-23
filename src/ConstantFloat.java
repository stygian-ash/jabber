import java.io.*;
import java.util.*;

public record ConstantFloat(float value) implements Constant {
	public static Constant read(Constant[] constantPool, InputStream input) throws IOException, ClassFileFormatException {
		int bits = Binary.readU4(input);
		float value = Float.intBitsToFloat(bits);
		return new ConstantFloat(value);
	}

	public ConstantTag getTag() {
		return ConstantTag.CONSTANT_FLOAT;
	}
}
