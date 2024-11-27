public class ClassFileFormatException extends Exception {
	public ClassFileFormatException(String format, Object... args) {
		super(String.format(format, args));
		Logger.error(format, args);
	}

	public ClassFileFormatException(String message) {
		super(message);
		Logger.error("%s", message);
	}
}
