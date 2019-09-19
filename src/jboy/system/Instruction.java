package jboy.system;

import java.util.function.Function;

/**
 * Represents a CPU instruction.
 */
public class Instruction {
    private int opCode;
    private int opSize;
    private int opCycles;
    private Function<int[], Void> operation;

    public Instruction(int code, int size, int cycles, Function<int[], Void> operation) {
        this.opCode = code;
        this.opSize = size;
        this.opCycles = cycles;
        this.operation = operation;
    }

    public int getOpCode() {
        return opCode;
    }

    public int getOpSize() {
        return opSize;
    }

    public int getOpCycles() {
        return opCycles;
    }

    public Function<int[], Void> getOperation() {
        return operation;
    }
}