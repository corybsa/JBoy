package jboy.disassembler;

/**
 * Represents a CPU instruction.
 */
public class Instruction<T> {
    private T opCode;
    private String opName;
    private byte opSize;
    private byte opCycles;

    public Instruction(T code, String name, byte size, byte cycles) {

        this.opCode = code;
        this.opName = name;
        this.opSize = size;
        this.opCycles = cycles;
    }

    public T getOpCode() {
        return opCode;
    }

    public String getOpName() {
        return opName;
    }

    public byte getOpSize() {
        return opSize;
    }

    public byte getOpCycles() {
        return opCycles;
    }
}
