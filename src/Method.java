import java.io.*;
import java.util.*;

public record Method(Set<AccessFlag> accessFlags, ConstantPoolIndex nameIndex, ConstantPoolIndex descriptorIndex, Attribute[] attributes) {
	public static Method read(ClassFile classFile) throws IOException, ClassFileFormatException {
		var accessFlags = AccessFlag.parse(classFile.getInput().readShort());
		var nameIndex = classFile.readIndex();
		var descriptorIndex = classFile.readIndex();
		Logger.debug("Parsing method %s(%s)", nameIndex, descriptorIndex);
		var attributes = classFile.readTable(new Attribute[0], Attribute::read);
		var method = new Method(accessFlags, nameIndex, descriptorIndex, attributes);
		Logger.debug("Read method %s", method);
		return method;
	}
}
