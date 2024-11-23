import java.io.*;
import java.util.*;

public record ConstantMethodref(ConstantPoolIndex classIndex, ConstantPoolIndex nameAndTypeIndex) implements Constant {
	public static Constant read(Constant[] constantPool, InputStream input) throws IOException, ClassFileFormatException {
		ConstantPoolIndex classIndex = ConstantPoolIndex.read(constantPool, input),
						  nameAndTypeIndex = ConstantPoolIndex.read(constantPool, input);
		return new ConstantMethodref(classIndex, nameAndTypeIndex);
	}

	public ConstantTag getTag() {
		return ConstantTag.CONSTANT_METHODREF;
	}
}
