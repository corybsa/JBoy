package jboy.instructions;

import java.util.function.Function;

/**
 * Represents a CPU instruction.
 */
public class Instruction {
    private byte opCode;
    private byte opSize;
    private byte opCycles;
    private Function<Short, Void> opType;

    public Instruction(byte code, byte size, byte cycles, Function<Short, Void> type) {
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

    public Function<Short, Void> getOpType() {
        return opType;
    }
}