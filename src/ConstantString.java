import java.io.*;
import java.util.*;

public record ConstantString(ConstantPoolIndex stringIndex) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantString(classFile.readIndex());
	}

	public static String escapeString(String string) {
		return string
			.replaceAll("([\\\t\n\"])", "\\\\\\1");
	}

	public String disassemble() {
		return "\"" + escapeString(stringIndex.resolve().disassemble()) + "\"";
	}

	public ConstantTag getTag() {
		return ConstantTag.STRING;
	}
}
