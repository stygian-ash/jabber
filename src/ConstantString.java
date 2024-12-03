import java.io.*;
import java.util.*;
import java.util.regex.*;

public record ConstantString(ConstantPoolIndex stringIndex) implements Constant {
	public static Constant read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new ConstantString(classFile.readIndex());
	}

	public static String escapeString(String string) {
		/* TODO: implement octal escape sequences */
		return string
			.replaceAll("\\\\", Matcher.quoteReplacement("\\\\"))
			.replaceAll("\n", Matcher.quoteReplacement("\\n"))
			.replaceAll("\b", Matcher.quoteReplacement("\\b"))
			.replaceAll("\r", Matcher.quoteReplacement("\\r"))
			.replaceAll("\t", Matcher.quoteReplacement("\\t"))
			.replaceAll("\f", Matcher.quoteReplacement("\\f"))
			.replaceAll("\"", Matcher.quoteReplacement("\\\""));
	}

	public String disassemble() {
		return "\"" + escapeString(stringIndex.resolve().disassemble()) + "\"";
	}

	public ConstantTag getTag() {
		return ConstantTag.STRING;
	}
}
