import java.io.*;
import java.util.*;

public record ConstantDouble(double value) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantDouble(Binary.readDouble(classFile.getInput()));
	}

	public ConstantTag getTag() {
		return ConstantTag.CONSTANT_DOUBLE;
	}
}
