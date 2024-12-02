import java.io.*;
import java.util.*;

public class Attribute {
	private ConstantPoolIndex nameIndex;
	private byte[] info;

	public Attribute(ConstantPoolIndex nameIndex) {
		this(nameIndex, null);
	}

	public Attribute(ConstantPoolIndex nameIndex, byte[] info) {
		this.nameIndex = nameIndex;
		this.info = info;
	}

	public static Attribute read(ClassFile classFile) throws IOException, ClassFileFormatException {
		var nameIndex = classFile.readIndex();
		int size = classFile.getInput().readInt();
		var nameConstant = nameIndex.resolve();
		var name = ((ConstantUtf8) nameConstant).value();
		if (nameConstant == null || nameConstant.getTag() != ConstantTag.UTF8)
			throw new ClassFileFormatException("Invalid attribute_name_index: %s", nameConstant);

		Attribute attribute;
		switch (name) {
			case "ConstantValue":
				attribute = AttributeConstantValue.read(nameIndex, classFile);
				break;
			default:
				Logger.warn("Unknown attribute type %s", name);
				attribute = new Attribute(nameIndex, classFile.getInput().readNBytes(size));
		}
		Logger.debug("Read attribute %s", attribute);
		return attribute;
	}

	public String infoString() {
		return String.format("info=byte[%s]", info.length);
	}

	public String toString() {
		return String.format("Attribute[%s, %s]", nameIndex, infoString());
	}
}
