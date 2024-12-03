import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.reflect.*;

public interface Constant {
	public ConstantTag getTag();
	public static final Constant EMPTY = ConstantEmpty.INSTANCE;

	public static Constant readConstant(ClassFile classFile) throws IOException, ClassFileFormatException {
		int tagNo = classFile.getInput().readByte();
		var tag = ConstantTag.lookupTag(tagNo);
		if (tag == null)
			throw new ClassFileFormatException("Invalid constant tag %d", tagNo);
		var constant = tag.reader.apply(classFile);
		Logger.debug("Read constant %s", constant);
		return constant;
	}

	public String disassemble();
}
