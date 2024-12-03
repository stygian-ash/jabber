import java.io.*;
import java.util.*;

public class Attribute {
	protected ConstantPoolIndex nameIndex;
	protected int size;
	private byte[] info;

	public Attribute(ConstantPoolIndex nameIndex, int size) {
		this.nameIndex = nameIndex;
		this.size = size;
	}

	public static Attribute read(ClassFile classFile) throws IOException, ClassFileFormatException {
		var nameIndex = classFile.readIndex();
		int size = classFile.getInput().readInt();
		var nameConstant = nameIndex.resolve();
		var name = ((ConstantUtf8) nameConstant).value();
		if (nameConstant == null || nameConstant.getTag() != ConstantTag.UTF8)
			throw new ClassFileFormatException("Invalid attribute_name_index: %s", nameConstant);

		Attribute attribute = switch (name) {
			case "ConstantValue" -> new AttributeConstantValue(nameIndex, size);
			case "Code" -> new AttributeCode(nameIndex, size);
			default -> {
				Logger.warn("Unknown attribute type %s", name);
				yield new Attribute(nameIndex, size);
			}
		};
		attribute.readInfo(classFile);
		Logger.debug("Read attribute %s", attribute);
		return attribute;
	}

	public void readInfo(ClassFile classFile) throws IOException, ClassFileFormatException {
		info = classFile.getInput().readNBytes(size);
	}

	public String infoString() {
		return "[?]";
	}

	public String toString() {
		return String.format("Attribute[%s, %s]", nameIndex, infoString());
	}
}
