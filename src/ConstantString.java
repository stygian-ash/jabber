import java.io.*;
import java.util.*;

public record ConstantString(ConstantPoolIndex stringIndex) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantString(classFile.readIndex());
	}

	public ConstantTag getTag() {
		return ConstantTag.STRING;
	}
}
