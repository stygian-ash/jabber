import java.io.*;
import java.util.*;

public record Field(Set<AccessFlag> accessFlags, ConstantPoolIndex nameIndex, ConstantPoolIndex descriptorIndex, Attribute[] attributes) {
	public static Field read(ClassFile classFile) throws IOException, ClassFileFormatException {
		var field = new Field(
				AccessFlag.parse(classFile.getInput().readShort()),
				classFile.readIndex(), classFile.readIndex(),
				classFile.readTable(new Attribute[0], Attribute::read));
		Logger.debug("Read field %s", field);
		return field;
	}
}
