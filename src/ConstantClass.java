import java.io.*;
import java.util.*;

public record ConstantClass(ConstantPoolIndex nameIndex) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantClass(classFile.readIndex());
	}

	public ConstantTag getTag() {
		return ConstantTag.CLASS;
	}
}
