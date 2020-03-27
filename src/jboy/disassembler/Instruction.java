package jboy.disassembler;

/**
 * Represents a CPU instruction.
 */
public class Instruction {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    private int opCode;
    private String opName;
    private int opSize;
    private int opCycles;
    private String instruction;

    /**
     * Create an Instruction with
     * @param code The op code.
     * @param name The definition of the instruction.
     * @param size How many bytes the instruction "takes up".
     * @param cycles How many CPU cycles the instruction uses.
     */
    public Instruction(int code, String name, int size, int cycles) {
        this.opCode = code;
        this.opName = name;
        this.opSize = size;
        this.opCycles = cycles;
    }

    /**
     * Get the decimal value of the operation.
     * @return The decimal value.
     */
    public int getOpCode() {
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
    public int getOpSize() {
        return this.opSize;
    }

    /**
     * Get how many CPU cycles the operation takes.
     * @return The amount of CPU cycles.
     */
    public int getOpCycles() {
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
    public Instruction parseInstruction(int op1, int op2) {
        if(op1 != -1) {
            this.instruction = this.opName.replaceFirst("\\*", this.byteToHex(op1));
        }

        if(op2 != -1) {
            this.instruction = this.instruction.replaceFirst("\\*", this.byteToHex(op2));
        }

        return this;
    }

    /**
     * Converts the {@code opCode} to hexadecimal.
     * @return The hexadecimal representation of {@code opCode}
     */
    public String getOpHex() {
        if((this.opCode & 0xCB00) != 0xCB00) {
            return this.byteToHex(this.opCode);
        } else {
            return this.shortToHex(this.opCode);
        }
    }

    /**
     * Converts a {@code byte} to an 8-bit hex number.
     * @param b the {@code byte} to convert to hex.
     * @return The hexadecimal representation of the {@code byte}.
     */
    private String byteToHex(int b) {
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
    private String shortToHex(int s) {
        int h = s & 0xFFFF;
        return this.byteToHex((h >>> 8)) + this.byteToHex(h);
    }
}
