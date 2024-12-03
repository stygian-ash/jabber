import java.io.*;
import java.util.*;

public record ConstantFloat(float value) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		float value = classFile.getInput().readFloat();
		return new ConstantFloat(value);
	}

	public String disassemble() {
		return value + "f";
	}

	public ConstantTag getTag() {
		return ConstantTag.FLOAT;
	}
}
