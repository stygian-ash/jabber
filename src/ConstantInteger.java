import java.io.*;
import java.util.*;

public record ConstantInteger(int value) implements Constant {
	public static Constant read(Constant[] constantPool, InputStream input) throws IOException, ClassFileFormatException {
		int value = Binary.readU4(input);
		return new ConstantInteger(value);
	}

	public ConstantTag getTag() {
		return ConstantTag.CONSTANT_INTEGER;
	}
}
