import java.io.*;
import java.util.*;

public record ConstantNameAndType(ConstantPoolIndex nameIndex, ConstantPoolIndex descriptorIndex) implements Constant {
	public static Constant read(Constant[] constantPool, InputStream input) throws IOException, ClassFileFormatException {
		ConstantPoolIndex nameIndex = ConstantPoolIndex.read(constantPool, input),
						  descriptorIndex = ConstantPoolIndex.read(constantPool, input);
		return new ConstantNameAndType(nameIndex, descriptorIndex);
	}

	public ConstantTag getTag() {
		return ConstantTag.CONSTANT_NAMEANDTYPE;
	}
}
