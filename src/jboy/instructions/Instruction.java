package jboy.instructions;

/**
 * Represents a CPU instruction.
 */
public class Instruction {
    private byte opCode;
    private byte opSize;
    private byte opCycles;
    private InstructionType opType;

    public Instruction(byte code, byte size, byte cycles, InstructionType type) {
        this.opCode = code;
        this.opSize = size;
        this.opCycles = cycles;
        this.opType = type;
    }

    public byte getOpCode() {
        return opCode;
    }

    public byte getOpSize() {
        return opSize;
    }

    public byte getOpCycles() {
        return opCycles;
    }

    public InstructionType getOpType() {
        return opType;
    }
}