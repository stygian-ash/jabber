import java.io.*;
import java.util.*;

public record ConstantClass(ConstantPoolIndex nameIndex) implements Constant {
	public static Constant read(Constant[] constantPool, InputStream input) throws IOException, ClassFileFormatException {
		ConstantPoolIndex nameIndex = new ConstantPoolIndex(constantPool, Binary.readU2(input));
		return new ConstantClass(nameIndex);
	}

	public ConstantTag getTag() {
		return ConstantTag.CONSTANT_CLASS;
	}
}
