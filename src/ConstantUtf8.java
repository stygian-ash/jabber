import java.io.*;
import java.util.*;

public record ConstantUtf8(int length, String value) implements Constant {
	@SuppressWarnings("preview")
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		int length = Short.toUnsignedInt(classFile.getInput().readShort());
		byte[] bytes = classFile.getInput().readNBytes(length);
		String value = new String(bytes, "UTF-8");
		return new ConstantUtf8(length, value);
	}

	public String disassemble() {
		return value;
	}

	public ConstantTag getTag() {
		return ConstantTag.UTF8;
	}
}
