public final class ConstantEmpty implements Constant {
	public static final ConstantEmpty INSTANCE = new ConstantEmpty();

	private ConstantEmpty() {}

	public ConstantTag getTag() {
		return ConstantTag.CONSTANT_EMPTY;
	}

	public String toString() {
		return "EMPTY";
	}
}
