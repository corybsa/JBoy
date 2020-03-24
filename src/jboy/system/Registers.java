package jboy.system;

public class Registers {
    public int A;
    public int B;
    public int C;
    public int D;
    public int E;
    public int F;
    public int H;
    public int L;
    public int SP;
    public int PC;

    public int getAF() {
        return (this.A << 8) + (this.F & 0xF0);
    }

    public int getBC() {
        return (this.B << 8) + this.C;
    }

    public int getDE() {
        return (this.D << 8) + this.E;
    }

    public int getHL() {
        return (this.H << 8) + this.L;
    }

    public void setAF(int n) {
        this.A = (n & 0xFF00) >> 8;
        this.F = n & 0x00F0;
    }

    public void setBC(int n) {
        this.B = (n & 0xFF00) >> 8;
        this.C = n & 0x00FF;
    }

    public void setDE(int n) {
        this.D = (n & 0xFF00) >> 8;
        this.E = n & 0x00FF;
    }

    public void setHL(int n) {
        this.H = (n & 0xFF00) >> 8;
        this.L = n & 0x00FF;
    }

    public int get8BitRegister(int register) {
        switch(register) {
            case 0b000:
                return this.B;
            case 0b001:
                return this.C;
            case 0b010:
                return this.D;
            case 0b011:
                return this.E;
            case 0b100:
                return this.H;
            case 0b101:
                return this.L;
            case 0b111:
                return this.A;
        }

        return 0;
    }

    public int get16BitRegister(int register, boolean useAF) {
        switch(register) {
            case 0b00:
                return this.getBC();
            case 0b01:
                return this.getDE();
            case 0b10:
                return this.getHL();
            case 0b11:
                if(useAF) {
                    return this.getAF();
                } else {
                    return this.SP;
                }
        }

        return 0;
    }

    public void set8BitRegister(int register, int value) {
        switch(register) {
            case 0b000:
                this.B = value;
                break;
            case 0b001:
                this.C = value;
                break;
            case 0b010:
                this.D = value;
                break;
            case 0b011:
                this.E = value;
                break;
            case 0b100:
                this.H = value;
                break;
            case 0b101:
                this.L = value;
                break;
            case 0b111:
                this.A = value;
                break;
        }
    }

    public void set16BitRegister(int register, int value, boolean useAF) {
        switch(register) {
            case 0b00:
                this.B = value >> 8;
                this.C = value & 0xFF;
                break;
            case 0b01:
                this.D = value >> 8;
                this.E = value & 0xFF;
                break;
            case 0b10:
                this.H = value >> 8;
                this.L = value & 0xFF;
                break;
            case 0b11:
                if(useAF) {
                    this.A = value >> 8;
                    this.F = value & 0xFF;
                } else {
                    this.SP = value;
                }

                break;
        }
    }
}
