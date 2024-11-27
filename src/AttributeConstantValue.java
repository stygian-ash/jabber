import java.io.*;
import java.util.*;

public class AttributeConstantValue extends Attribute {
	private ConstantPoolIndex value;

	public AttributeConstantValue(ConstantPoolIndex nameIndex, ConstantPoolIndex value) {
		super(nameIndex);
		this.value = value;
	}

	public static AttributeConstantValue read(ConstantPoolIndex nameIndex, ClassFile classFile) throws IOException, ClassFileFormatException {
		return new AttributeConstantValue(nameIndex, classFile.readIndex());
	}

	@Override
	public String infoString() {
		return String.format("value=%s", value);
	}
}
