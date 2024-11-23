import java.io.*;
import java.util.*;

public record ConstantLong(long value) implements Constant {
	public static Constant read(Constant[] constantPool, InputStream input) throws IOException, ClassFileFormatException {
		long highBytes = Binary.readU4(input),
			lowBytes = Binary.readU4(input);
		long value = highBytes << 32 | lowBytes;
		return new ConstantLong(value);
	}

	public ConstantTag getTag() {
		return ConstantTag.CONSTANT_LONG;
	}
}
