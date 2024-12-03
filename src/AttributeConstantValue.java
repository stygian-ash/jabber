import java.io.*;
import java.util.*;

public class AttributeConstantValue extends Attribute {
	private ConstantPoolIndex valueIndex;

	public AttributeConstantValue(ConstantPoolIndex nameIndex, int size) {
		super(nameIndex, size);
	}

	public void readInfo(ClassFile classFile) throws IOException, ClassFileFormatException {
		valueIndex = classFile.readIndex();
	}

	@Override
	public String infoString() {
		return String.format("valueIndex=%s", valueIndex);
	}
}
