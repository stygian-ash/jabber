import java.io.*;
import java.util.*;

public class AttributeCode extends Attribute {
	private int maxStack, maxLocals;
	private byte[] code;
	private List<Instruction> instructions;
	private ExceptionTableEntry[] exceptionTable;
	private Attribute[] attributes;

	public AttributeCode(ConstantPoolIndex nameIndex, int size) {
		super(nameIndex, size);
	}

	public void readInfo(ClassFile classFile) throws IOException, ClassFileFormatException {
		maxStack = classFile.getInput().readShort();
		maxLocals = classFile.getInput().readShort();
		code = classFile.getInput().readNBytes(classFile.getInput().readInt());
		instructions = parseInstructions(classFile);
		exceptionTable = classFile.readTable(new ExceptionTableEntry[0], ExceptionTableEntry::read);
		attributes = classFile.readTable(new Attribute[0], Attribute::read);
	}

	@Override
	public String infoString() {
		return String.format("maxStack=%d, maxLocals=%d, code=byte[%d], exceptionTable=%s, attributes=%s",
				maxStack, maxLocals, code.length, Arrays.deepToString(exceptionTable), Arrays.deepToString(attributes));
	}

	public String disassemble() {
		var assembly = new StringBuffer();
		assembly.append(".maxstack " + maxStack + "\n");
		assembly.append(".maxlocals " + maxLocals + "\n");
		var labeled = new HashSet<Long>();
		var tryStart = new HashSet<Long>();
		var tryEnd = new HashMap<Long, Set<Pair<ConstantPoolIndex, Long>>>();
		for (var instruction: instructions)
			labeled.addAll(instruction.xrefs());
		for (var exception: exceptionTable) {
			labeled.add(exception.handlerPc());
			tryStart.add(exception.startPc());
			if (!tryEnd.containsKey(exception.endPc()))
				tryEnd.put(exception.endPc(), new HashSet<>());
			tryEnd.get(exception.endPc()).add(new Pair<>(exception.catchType(), exception.handlerPc()));
		}
		for (var instruction: instructions) {
			if (labeled.contains(instruction.offset()))
				assembly.append(Instruction.getLabel(instruction.offset()) + ":\n");
			if (tryStart.contains(instruction.offset()))
				assembly.append(".try\n");
			if (tryEnd.containsKey(instruction.offset()))
				for (var pair: tryEnd.get(instruction.offset())) {
					if (pair.getKey() == null)
						assembly.append(String.format(".finally %s\n", Instruction.getLabel(pair.getValue())));
					else
						assembly.append(String.format(".catch %s %s\n",
									pair.getKey().resolve().disassemble(),
									Instruction.getLabel(pair.getValue())));
				}
			assembly.append(instruction.disassemble().indent(4));
		}
		return assembly.toString();
	}

	public static Instruction readInstruction(BinaryInputStream stream, ClassFile classFile, boolean isWide) throws IOException, ClassFileFormatException {
		var offset = stream.getPosition();
		var opcodeByte = stream.readByte();
		var opcode = Opcode.lookupOpcode(opcodeByte);
		var xrefs = new ArrayList<Long>();
		Object[] operands;
		if (opcode == null)
			throw new ClassFileFormatException("Unsupported opcode 0x%02X", opcodeByte);
		Logger.debug("Read opcode %s (0x%02X)", opcode, opcode.opcode);
		operands = new Object[opcode.operands.length];
		for (int i = 0; i < opcode.operands.length; i++)
			operands[i] = switch (isWide ? opcode.operands[i].widen() : opcode.operands[i]) {
				case PAD_ALIGN_4 -> stream.align(4);
				case BYTE_IMMEDIATE, ZERO_BYTE -> stream.readByte();
				case VARIABLE, WIDE_VARIABLE, COUNT -> Byte.toUnsignedInt(stream.readByte());
				case SHORT_IMMEDIATE -> stream.readShort();
				case BRANCH_OFFSET -> {
					short branchOffset = stream.readShort();
					xrefs.add(offset + (long) branchOffset & 0xffffL);
					yield branchOffset;
				}
				case INT_IMMEDIATE -> stream.readInt();
				case WIDE_BRANCH_OFFSET -> {
					int branchOffset = stream.readInt();
					xrefs.add(offset + Integer.toUnsignedLong(branchOffset));
					yield branchOffset;
				}
				case CONSTANT_INDEX -> new ConstantPoolIndex(classFile.getConstantPool(),
						Byte.toUnsignedInt(stream.readByte()));
				case TYPE, FIELD, DYNAMIC, METHODREF, INTERFACEMETHODREF, WIDE_CONSTANT_INDEX ->
					new ConstantPoolIndex(classFile.getConstantPool(), stream.readShort());
				case LOOKUPSWITCH_PAIRS -> {
					var pairs = new Pair[(int) operands[i - 1]];
					for (int j = 0; j < pairs.length; j++) {
						int key = stream.readInt();
						int value = stream.readInt();
						pairs[j] = new Pair<>(key, value);
						xrefs.add(offset + Integer.toUnsignedLong(value));
					}
					yield pairs;
				}
				case TABLESWITCH_OFFSETS -> {
					var offsets = new int[(int) operands[i - 1] - (int) operands[i - 2] + 1];
					for (int j = 0; j < offsets.length; j++) {
						int branchOffset = stream.readInt();
						offsets[j] = branchOffset;
						xrefs.add(offset + Integer.toUnsignedLong(branchOffset));
					}
					yield offsets;
				}
				case INSTRUCTION -> readInstruction(stream, classFile, true);
				default -> throw new UnsupportedOperationException("Parsing operand of type " + opcode.operands[i] + " is not implemented.");
			};
		var instruction = new Instruction(offset, opcode, operands, xrefs);
		Logger.debug("Parsed instruction %s", instruction);
		return instruction;
	}

	public List<Instruction> parseInstructions(ClassFile classFile) throws IOException, ClassFileFormatException {
		var instructions = new ArrayList<Instruction>();
		var stream = new BinaryInputStream(new ByteArrayInputStream(code));
		while (stream.available() > 0)
			instructions.add(readInstruction(stream, classFile, false));
		return instructions;
	}
}
