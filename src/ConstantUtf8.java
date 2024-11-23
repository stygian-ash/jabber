import java.io.*;
import java.util.*;

public record ConstantUtf8(int length, String value) implements Constant {
	@SuppressWarnings("preview")
	public static Constant read(Constant[] constantPool, InputStream input) throws IOException, ClassFileFormatException {
		int length = Binary.readU2(input);
		byte[] bytes = input.readNBytes(length);
		String value = new String(bytes, "UTF-8");
		return new ConstantUtf8(length, value);
	}

	public ConstantTag getTag() {
		return ConstantTag.CONSTANT_UTF8;
	}
}
