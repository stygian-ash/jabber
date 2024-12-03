import java.io.*;
import java.util.*;

/* Adapted from https://stackoverflow.com/a/240431 */
public class BinaryInputStream extends FilterInputStream {
	private long position = 0;
	private long mark = 0;

	public BinaryInputStream(InputStream stream) {
		super(stream);
	}

	public BinaryInputStream(byte[] backingArray) {
		super(new ByteArrayInputStream(backingArray));
	}

	public long getPosition() {
		return position;
	}

	@Override
	public int read() throws IOException {
		int value = super.read();
		if (value >= 0)
			position++;
		return value;
	}

	@Override
	public int read(byte[] destination, int offset, int length) throws IOException {
		int bytesRead = super.read(destination, offset, length);
		if (bytesRead >= 0)
			position += bytesRead;
		return bytesRead;
	}

	@Override
	public long skip(long amount) throws IOException {
		long bytesSkipped = super.skip(amount);
		if (bytesSkipped >= 0)
			position += bytesSkipped;
		return bytesSkipped;
	}

	@Override
	public void mark(int readAheadLimit) {
		super.mark(readAheadLimit);
		mark = position;
	}

	@Override
	public void reset() throws IOException {
		if (!markSupported())
			throw new IOException("Mark not supported.");
		super.reset();
		position = mark;
	}

	public byte readByte() throws IOException {
		return (byte) read();
	}

	public short readShort() throws IOException {
		return (short)(read() << 8 | read());
	}

	public int readInt() throws IOException {
		return read() << 24
			| read() << 16
			| read() << 8
			| read();
	}

	public long readLong() throws IOException {
		return (long) readInt() << 32 | readInt();
	}

	public float readFloat() throws IOException {
		return Float.intBitsToFloat(readInt());
	}

	public double readDouble() throws IOException {
		return Double.longBitsToDouble(readLong());
	}

	public byte[] align(int boundary) throws IOException {
		var padding = new byte[boundary];
		int index = 0;
		while (position % boundary != 0)
			padding[index++] = readByte();
		return Arrays.copyOf(padding, index);
	}
}
