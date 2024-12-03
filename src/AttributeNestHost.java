import java.io.*;
import java.util.*;

public class AttributeNestHost extends Attribute {
	private ConstantPoolIndex hostClass;

	public AttributeNestHost(ConstantPoolIndex nameIndex, int size) {
		super(nameIndex, size);
	}

	public void readInfo(ClassFile classFile) throws IOException, ClassFileFormatException {
		hostClass = classFile.readIndex();
	}

	@Override
	public String infoString() {
		return String.format("host_class=%s", hostClass);
	}
}
