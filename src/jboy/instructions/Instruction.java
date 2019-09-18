package jboy.instructions;

import java.util.function.Function;

/**
 * Represents a CPU instruction.
 */
public class Instruction {
    private byte opCode;
    private byte opSize;
    private byte opCycles;
    private Function<byte[], Void> operation;

    public Instruction(byte code, byte size, byte cycles, Function<byte[], Void> operation) {
        this.opCode = code;
        this.opSize = size;
        this.opCycles = cycles;
        this.operation = operation;
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

    public Function<byte[], Void> getOperation() {
        return operation;
    }
}