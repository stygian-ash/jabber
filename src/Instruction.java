import java.util.*;

public record Instruction(long offset, Opcode opcode, Object[] operands) {
	@Override
	public String toString() {
		return String.format("Instruction[offset=0x%02X, opcode=%s, operands=%s",
				offset, opcode, Arrays.deepToString(operands));
	}
}
