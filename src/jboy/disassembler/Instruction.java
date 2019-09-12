package jboy.disassembler;

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

    /**
     * Create an Instruction with
     * @param code A {@link Byte} or a {@link Short}.
     * @param name The definition of the instruction.
     * @param size How many bytes the instruction "takes up".
     * @param cycles How many CPU cycles the instruction uses.
     */
    public Instruction(T code, String name, byte size, byte cycles) {
        this.opCode = code;
        this.opName = name;
        this.opSize = size;
        this.opCycles = cycles;
    }

    /**
     * Get the decimal value of the operation.
     * @return The decimal value.
     */
    public T getOpCode() {
        return this.opCode;
    }

    /**
     * Get the operation name.
     * @return The operation name.
     */
    public String getOpName() {
        return this.opName;
    }

    /**
     * Get how many bytes the operation takes.
     * @return The amount of bytes.
     */
    public byte getOpSize() {
        return this.opSize;
    }

    /**
     * Get how many CPU cycles the operation takes.
     * @return The amount of CPU cycles.
     */
    public byte getOpCycles() {
        return this.opCycles;
    }

    /**
     * Get the parsed instruction.
     * @return Returns the parsed instruction, or just the {@code opCode} for operations that don't accept any arguments.
     */
    public String getInstruction() {
        if(this.instruction == null || this.instruction.isEmpty()) {
            return this.opName;
        }

        return this.instruction;
    }

    /**
     * Parse the instruction and put the actual values in place.
     * @param op1 The first operand.
     * @param op2 The second operand.
     * @return Returns the current instance.
     */
    public Instruction<T> parseInstruction(byte op1, byte op2) {
        // ignore 0xD3
        if(op1 != (byte)0xD3) {
            this.instruction = this.opName.replaceFirst("\\*", this.byteToHex(op1));
        }

        // ignore 0xD3
        if(op2 != (byte)0xD3) {
            this.instruction = this.instruction.replaceFirst("\\*", this.byteToHex(op2));
        }

        return this;
    }

    /**
     * Converts the {@code opCode} to hexadecimal.
     * @return The hexadecimal representation of {@code opCode}
     */
    public String getOpHex() {
        if(this.opCode instanceof Byte) {
            return this.byteToHex((Byte)this.opCode);
        } else {
            return this.shortToHex((Short)this.opCode);
        }
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
