import java.io.*;
import java.util.*;

public enum AccessFlag {
	ACC_PUBLIC(0x0001, "public"),
	ACC_PRIVATE(0x0002, "private"),
	ACC_PROTECTED(0x0004, "protected"),
	ACC_STATIC(0x0008, "static"),
	ACC_FINAL(0x0010, "final"),
	ACC_SUPER(0x0020, "super"),
	ACC_SYNCHRONIZED(0x0020, "synchronized"),
	ACC_BRIDGE(0x0040, "bridge"),
	ACC_VARARGS(0x0080, "varargs"),
	ACC_NATIVE(0x0100, "native"),
	ACC_INTERFACE(0x0200, "interface"),
	ACC_ABSTRACT(0x0400, "abstract"),
	ACC_STRICT(0x0800, "strictfp"),
	ACC_SYNTHETIC(0x1000, "synthetic"),
	ACC_ANNOTATION(0x2000, "annotation"),
	ACC_ENUM(0x4000, "enum"),
	ACC_MODULE(0x8000, "module");

	public final int value;
	public final String string;
	AccessFlag(int value, String string) {
		this.value = value;
		this.string = string;
	}

	public static Set<AccessFlag> parse(short flags) {
		return parse(Short.toUnsignedInt(flags));
	}

	/* TODO: check if flag is valid for clasfile/field */
	/* TODO: check if flag is valid for Java version */
	public static Set<AccessFlag> parse(int flags) {
		var seenFlags = new ArrayList<AccessFlag>();
		for (var flag: AccessFlag.class.getEnumConstants())
			if ((flags & flag.value) != 0)
				seenFlags.add(flag);
		EnumSet<AccessFlag> ret;
		switch (seenFlags.size()) {
			case 0:
				return EnumSet.noneOf(AccessFlag.class);
			case 1:
				return EnumSet.of(seenFlags.get(0));
			default: {
				var first = seenFlags.remove(0);
				var rest = seenFlags.toArray(new AccessFlag[0]);
				return EnumSet.of(first, rest);
			}
		}
	}

	public static String disassemble(Set<AccessFlag> flags) {
		return String.join(" ", flags.stream().filter(i -> i.string.length() > 0) .map(i -> i.string).toArray(String[]::new));
	}
}
