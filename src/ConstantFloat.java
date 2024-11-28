import java.io.*;
import java.util.*;

public record ConstantFloat(float value) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		float value = classFile.readFloat();
		return new ConstantFloat(value);
	}

	public ConstantTag getTag() {
		return ConstantTag.CONSTANT_FLOAT;
	}
}
