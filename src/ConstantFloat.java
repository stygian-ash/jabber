import java.io.*;
import java.util.*;

public record ConstantFloat(float value) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		float value = Binary.readFloat(classFile.getInput());
		return new ConstantFloat(value);
	}

	public ConstantTag getTag() {
		return ConstantTag.CONSTANT_FLOAT;
	}
}
