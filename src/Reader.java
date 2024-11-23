import java.io.*;
import java.util.*;
import java.util.function.*;

@FunctionalInterface
public interface Reader {
	public Constant apply(Constant[] constantPool, InputStream stream) throws IOException, ClassFileFormatException;
}
