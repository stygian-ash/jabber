import java.io.*;
import java.util.*;

public record ConstantDouble(double value) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantDouble(classFile.getInput().readDouble());
	}

	public String disassemble() {
		return value + "d";
	}

	public ConstantTag getTag() {
		return ConstantTag.DOUBLE;
	}
}
