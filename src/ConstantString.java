import java.io.*;
import java.util.*;

public record ConstantString(ConstantPoolIndex stringIndex) implements Constant {
	@SuppressWarnings("preview")
	public static Constant read(Constant[] constantPool, InputStream input) throws IOException, ClassFileFormatException {
		ConstantPoolIndex stringIndex = ConstantPoolIndex.read(constantPool, input);
		return new ConstantString(stringIndex);
	}

	public ConstantTag getTag() {
		return ConstantTag.CONSTANT_STRING;
	}
}
