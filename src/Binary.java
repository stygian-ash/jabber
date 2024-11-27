import java.io.*;
public final class Binary {
	private Binary() {}

	private static int readUnsigned(InputStream stream, int bytes) throws IOException {
		assert 0 <= bytes && bytes <= 4;
		int ret = 0;
		for (int i = 0; i < bytes; i++)
			ret = (ret << 8) | stream.read();
		return ret;
	}

	public static int readU1(InputStream stream) throws IOException {
		return readUnsigned(stream, 1);
	}
	
	public static int readU2(InputStream stream) throws IOException {
		return readUnsigned(stream, 2);
	}

	public static int readU4(InputStream stream) throws IOException {
		return readUnsigned(stream, 4);
	}

	public static long readU8(InputStream stream) throws IOException {
		return (long) readU4(stream) << 32 | (long) readU4(stream);
	}

	public static float readFloat(InputStream stream) throws IOException {
		return Float.intBitsToFloat(readU4(stream));
	}

	public static double readDouble(InputStream stream) throws IOException {
		return Double.longBitsToDouble(readU8(stream));
	}
}
