import java.util.*;

public record Instruction(long offset, Opcode opcode, Object[] operands, List<Long> xrefs) {
	@Override
	public String toString() {
		return String.format("Instruction[offset=0x%02X, opcode=%s, operands=%s",
				offset, opcode, Arrays.deepToString(operands));
	}

	@SuppressWarnings("unchecked")
	public String disassemble() {
		var assembly = new StringBuffer();
		assembly.append(opcode.mnemonic);
		for (int i = 0; i < opcode.operands.length; i++) {
			switch (opcode.operands[i]) {
				case BYTE_IMMEDIATE:
					assembly.append(" " + Byte.toUnsignedInt((byte) operands[i]));
					break;
				case SHORT_IMMEDIATE:
					assembly.append(" " + Short.toUnsignedInt((short) operands[i]));
					break;
				case BRANCH_OFFSET:
					assembly.append(" " + getLabel(offset + (long) (short) operands[i]));
					break;
				case WIDE_BRANCH_OFFSET:
					assembly.append(" " + getLabel(offset + (long) (int) operands[i]));
					break;
				case VARIABLE:
				case WIDE_VARIABLE:
				case COUNT:
				case INT_IMMEDIATE:
					assembly.append(" " + getLabel((int) operands[i]));
					break;
				case CONSTANT_INDEX:
				case WIDE_CONSTANT_INDEX:
				case TYPE:
				case FIELD:
				case METHODREF:
				case INTERFACEMETHODREF:
					assembly.append(" " + ((ConstantPoolIndex) operands[i]).resolve().disassemble());
					break;
				case INSTRUCTION:
					assembly.append(" " + ((Instruction) operands[i]).disassemble());
					break;
				case LOOKUPSWITCH_PAIRS:
					assembly.append(" {\n");
					for (var pair: ((Pair<Integer, Integer>[]) operands[i]))
						assembly.append(String.format("%s: %s\n",
									pair.getKey(),
									getLabel(pair.getValue())).indent(4));
					assembly.append("}");
					break;
				case TABLESWITCH_OFFSETS:
					assembly.append(" {"
							+ String.join(" ", Arrays.stream((int[]) operands[i])
								.mapToObj(Instruction::getLabel)
								.toArray(String[]::new))
							+ "}");
					break;
			}
		}
		return assembly.toString();
	}

	public static String getLabel(long offset) {
		return "L" + Long.toUnsignedString(offset);
	}
}
