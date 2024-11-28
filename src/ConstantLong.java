import java.io.*;
import java.util.*;

public record ConstantLong(long value) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantLong(classFile.readU8());
	}

	public ConstantTag getTag() {
		return ConstantTag.CONSTANT_LONG;
	}
}
