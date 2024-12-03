import java.io.*;
import java.util.*;

public class AttributeStackMapTable extends Attribute {
	private StackMapFrame[] entries;

	public AttributeStackMapTable(ConstantPoolIndex nameIndex, int size) {
		super(nameIndex, size);
	}

	public void readInfo(ClassFile classFile) throws IOException, ClassFileFormatException {
		entries = classFile.readTable(new StackMapFrame[0], StackMapFrame::read);
	}

	@Override
	public String infoString() {
		return String.format("entries=%s", Arrays.deepToString(entries));
	}
}
