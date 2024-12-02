import java.util.*;

public enum AccessFlag {
	ACC_PUBLIC(0x0001),
	ACC_PRIVATE(0x0002),
	ACC_PROTECTED(0x0004),
	ACC_STATIC(0x0008),
	ACC_FINAL(0x0010),
	ACC_SUPER(0x0020),
	ACC_SYNCHRONIZED(0x0020),
	ACC_BRIDGE(0x0040),
	ACC_VARARGS(0x0080),
	ACC_NATIVE(0x0100),
	ACC_INTERFACE(0x0200),
	ACC_ABSTRACT(0x0400),
	ACC_STRICT(0x0800),
	ACC_SYNTHETIC(0x1000),
	ACC_ANNOTATION(0x2000),
	ACC_ENUM(0x4000),
	ACC_MODULE(0x8000);

	public final int value;
	AccessFlag(int value) {
		this.value = value;
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
}
