import java.io.*;
import java.util.*;

public class AttributeClassList extends Attribute {
	private ConstantPoolIndex[] classes;

	public AttributeClassList(ConstantPoolIndex nameIndex, int size) {
		super(nameIndex, size);
	}

	public void readInfo(ClassFile classFile) throws IOException, ClassFileFormatException {
		classes = classFile.readTable(new ConstantPoolIndex[0], ClassFile::readIndex);
	}

	@Override
	public String infoString() {
		return String.format("classes=%s", Arrays.deepToString(classes));
	}
}
