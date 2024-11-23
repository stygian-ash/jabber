import java.io.*;
import java.util.*;
import java.lang.*;
import java.lang.reflect.*;

public interface Constant {
	public ConstantTag getTag();
	public static final Constant EMPTY = ConstantEmpty.INSTANCE;

	public static Constant readConstant(Constant[] constantPool, InputStream input) throws IOException, ClassFileFormatException {
		int tagNo = Binary.readU1(input);
		var tag = ConstantTag.lookupTag(tagNo);
		if (tag == null) {
			Logger.error("Invalid constant tag %d", tagNo);
			throw new ClassFileFormatException("Invalid constant tag %d", tagNo);
		}
		var constant = tag.reader.apply(constantPool, input);
		Logger.debug("Read constant %s", constant);
		return constant;
	}
}
