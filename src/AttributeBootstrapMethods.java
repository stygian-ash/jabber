import java.io.*;
import java.util.*;

public class AttributeBootstrapMethods extends Attribute {
	private BootstrapMethod[] bootstrapMethods;

	public AttributeBootstrapMethods(ConstantPoolIndex nameIndex, int size) {
		super(nameIndex, size);
	}

	public void readInfo(ClassFile classFile) throws IOException, ClassFileFormatException {
		bootstrapMethods = classFile.readTable(new BootstrapMethod[0], BootstrapMethod::read);
	}

	@Override
	public String infoString() {
		return String.format("bootstrap_methods=%s", Arrays.deepToString(bootstrapMethods));
	}
}
