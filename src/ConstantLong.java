import java.io.*;
import java.util.*;

public record ConstantLong(long value) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantLong(Binary.readU8(classFile.getInput()));
	}

	public ConstantTag getTag() {
		return ConstantTag.CONSTANT_LONG;
	}
}
