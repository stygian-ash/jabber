import java.io.*;
import java.util.*;

public record ConstantLong(long value) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantLong(classFile.getInput().readLong());
	}

	public String disassemble() {
		return value + "L";
	}

	public ConstantTag getTag() {
		return ConstantTag.LONG;
	}
}
