package jboy.disassembler;

import static jboy.disassembler.Instructions.GB_8BIT_INSTRUCTIONS;

/**
 * Represents a CPU instruction.
 */
public class Instruction<T> {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private T opCode;
    private String opName;
    private byte opSize;
    private byte opCycles;
    private String instruction;

    public Instruction(T code, String name, byte size, byte cycles) {

        this.opCode = code;
        this.opName = name;
        this.opSize = size;
        this.opCycles = cycles;
    }

    public T getOpCode() {
        return this.opCode;
    }

    public String getOpName() {
        return this.opName;
    }

    public byte getOpSize() {
        return this.opSize;
    }

    public byte getOpCycles() {
        return this.opCycles;
    }

    public String getInstruction() {
        if(this.instruction == null || this.instruction.isEmpty()) {
            return this.opName;
        }

        return this.instruction;
    }

    public Instruction<T> parseInstruction(byte op1, byte op2) {
        if(op1 != (byte)0xD3) {
            this.instruction = this.opName.replaceFirst("\\*", this.byteToHex(op1));
        }

        if(op2 != (byte)0xD3) {
            this.instruction = this.instruction.replaceFirst("\\*", this.byteToHex(op2));
        }

        return this;
    }

    /**
     * Converts a {@code byte} to an 8-bit hex number.
     * @param b the {@code byte} to convert to hex.
     * @return The hexadecimal representation of the {@code byte}.
     */
    private String byteToHex(byte b) {
        char[] hexChars = new char[2];

        // Bytes are unsigned in Java, and we need the unsigned version. The bitwise-and will do that.
        int h = b & 0xFF;

        // Shift
        hexChars[0] = HEX_ARRAY[h >>> 4];
        hexChars[1] = HEX_ARRAY[h & 0x0F];

        return new String(hexChars);
    }

    /**
     * Converts a {@code short} to a 16-bit hex number.
     * @param s The {@code short} to convert to hex.
     * @return The hexadecimal representation of the {@code short}.
     */
    private String shortToHex(short s) {
        int h = s & 0xFFFF;
        return this.byteToHex((byte) (h >>> 8)) + this.byteToHex((byte) h);
    }
}
