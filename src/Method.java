import java.io.*;
import java.util.*;

public record Method(Set<AccessFlag> accessFlags, ConstantPoolIndex nameIndex, ConstantPoolIndex descriptorIndex, Attribute[] attributes) {
	public static Method read(ClassFile classFile) throws IOException, ClassFileFormatException {
		var method = new Method(
				AccessFlag.parse(classFile.readU2()),
				classFile.readIndex(), classFile.readIndex(),
				classFile.readTable(new Attribute[0], Attribute::read));
		Logger.debug("Read method %s", method);
		return method;
	}
}
