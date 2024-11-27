import java.io.*;
import java.util.*;
import java.util.function.*;

@FunctionalInterface
public interface Reader<T> {
	public T apply(ClassFile classFile) throws IOException, ClassFileFormatException;
}
