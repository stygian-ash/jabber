import java.io.*;
import java.util.*;

public record ConstantInteger(int value) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantInteger(Binary.readU4(classFile.getInput()));
	}

	public ConstantTag getTag() {
		return ConstantTag.CONSTANT_INTEGER;
	}
}
