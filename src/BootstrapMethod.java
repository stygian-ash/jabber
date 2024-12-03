import java.io.*;
public record BootstrapMethod(ConstantPoolIndex methodref, ConstantPoolIndex[] arguments) {
	public static BootstrapMethod read(ClassFile classFile) throws IOException, ClassFileFormatException {
		return new BootstrapMethod(classFile.readIndex(),
				classFile.readTable(new ConstantPoolIndex[0], ClassFile::readIndex));
	}
}
