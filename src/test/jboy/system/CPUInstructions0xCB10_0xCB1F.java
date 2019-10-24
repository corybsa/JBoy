package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xCB10_0xCB1F {
    static private CPU cpu;
    static private Memory memory;
    static private int[] rom;

    @BeforeAll
    static void testBeforeAll() {
        memory = new Memory();
        cpu = new CPU(memory, null);
    }

    @BeforeEach
    void setUp() {
        cpu.setPC(0x100);
        cpu.setSP(0xFFFE);
        rom = new int[0x7FFF];
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
    }

    @AfterEach
    void tearDown() {
        rom = null;
    }

    // op code 0xCB10
    @Test
    void rl_b_test() {
        rom[0x100] = 0x06; // ld b,0x80
        rom[0x101] = 0x80;
        rom[0x102] = 0xCB; // rl b
        rom[0x103] = 0x10;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getB(), "The B register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x06; // ld b,0x11
        rom[0x101] = 0x11;
        rom[0x102] = 0xCB; // rl b
        rom[0x103] = 0x10;

        cpu.tick();
        cpu.tick();
        assertEquals(0x22, cpu.getB(), "The B register should equal 0x22.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB11
    @Test
    void rl_c_test() {
        rom[0x100] = 0x0E; // ld c,0x80
        rom[0x101] = 0x80;
        rom[0x102] = 0xCB; // rl c
        rom[0x103] = 0x11;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getC(), "The C register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x0E; // ld c,0x11
        rom[0x101] = 0x11;
        rom[0x102] = 0xCB; // rl c
        rom[0x103] = 0x11;

        cpu.tick();
        cpu.tick();
        assertEquals(0x22, cpu.getC(), "The C register should equal 0x22.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB12
    @Test
    void rl_d_test() {
        rom[0x100] = 0x16; // ld d,0x80
        rom[0x101] = 0x80;
        rom[0x102] = 0xCB; // rl d
        rom[0x103] = 0x12;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getD(), "The D register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x16; // ld d,0x11
        rom[0x101] = 0x11;
        rom[0x102] = 0xCB; // rl d
        rom[0x103] = 0x12;

        cpu.tick();
        cpu.tick();
        assertEquals(0x22, cpu.getD(), "The D register should equal 0x22.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB13
    @Test
    void rl_e_test() {
        rom[0x100] = 0x1E; // ld e,0x80
        rom[0x101] = 0x80;
        rom[0x102] = 0xCB; // rl e
        rom[0x103] = 0x13;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getE(), "The E register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x1E; // ld e,0x11
        rom[0x101] = 0x11;
        rom[0x102] = 0xCB; // rl e
        rom[0x103] = 0x13;

        cpu.tick();
        cpu.tick();
        assertEquals(0x22, cpu.getE(), "The E register should equal 0x22.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB14
    @Test
    void rl_h_test() {
        rom[0x100] = 0x26; // ld h,0x80
        rom[0x101] = 0x80;
        rom[0x102] = 0xCB; // rl h
        rom[0x103] = 0x14;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getH(), "The H register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x26; // ld h,0x11
        rom[0x101] = 0x11;
        rom[0x102] = 0xCB; // rl h
        rom[0x103] = 0x14;

        cpu.tick();
        cpu.tick();
        assertEquals(0x22, cpu.getH(), "The H register should equal 0x22.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB15
    @Test
    void rl_l_test() {
        rom[0x100] = 0x2E; // ld l,0x80
        rom[0x101] = 0x80;
        rom[0x102] = 0xCB; // rl l
        rom[0x103] = 0x15;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getL(), "The L register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x2E; // ld l,0x11
        rom[0x101] = 0x11;
        rom[0x102] = 0xCB; // rl l
        rom[0x103] = 0x15;

        cpu.tick();
        cpu.tick();
        assertEquals(0x22, cpu.getL(), "The L register should equal 0x22.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB16
    @Test
    void rl_hlp_test() {
        memory.setByteAt(0xC000, 0x80); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // rl (hl)
        rom[0x104] = 0x16;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, memory.getByteAt(cpu.getHL()), "The value pointed to by HL should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        memory.setByteAt(0xC000, 0x11); // This is the value that HL will point to.

        cpu.tick();
        cpu.tick();
        assertEquals(0x22, memory.getByteAt(cpu.getHL()), "The value pointed to by HL should equal 0x22.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCB17
    @Test
    void rl_a_test() {
        rom[0x100] = 0x3E; // ld a,0x80
        rom[0x101] = 0x80;
        rom[0x102] = 0xCB; // rl a
        rom[0x103] = 0x17;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0x11
        rom[0x101] = 0x11;
        rom[0x102] = 0xCB; // rl a
        rom[0x103] = 0x17;

        cpu.tick();
        cpu.tick();
        assertEquals(0x22, cpu.getA(), "The A register should equal 0x22.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB18
    @Test
    void rr_b_test() {
        rom[0x100] = 0x06; // ld b,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // rr b
        rom[0x103] = 0x18;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getB(), "The B register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x06; // ld b,0x8A
        rom[0x101] = 0x8A;
        rom[0x102] = 0xCB; // rr b
        rom[0x103] = 0x18;

        cpu.tick();
        cpu.tick();
        assertEquals(0x45, cpu.getB(), "The B register should equal 0x45.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB19
    @Test
    void rr_c_test() {
        rom[0x100] = 0x0E; // ld c,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // rr c
        rom[0x103] = 0x19;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getC(), "The C register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x0E; // ld c,0x8A
        rom[0x101] = 0x8A;
        rom[0x102] = 0xCB; // rr c
        rom[0x103] = 0x19;

        cpu.tick();
        cpu.tick();
        assertEquals(0x45, cpu.getC(), "The C register should equal 0x45.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB1A
    @Test
    void rr_d_test() {
        rom[0x100] = 0x16; // ld d,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // rr d
        rom[0x103] = 0x1A;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getD(), "The D register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x16; // ld d,0x8A
        rom[0x101] = 0x8A;
        rom[0x102] = 0xCB; // rr d
        rom[0x103] = 0x1A;

        cpu.tick();
        cpu.tick();
        assertEquals(0x45, cpu.getD(), "The D register should equal 0x45.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB1B
    @Test
    void rr_e_test() {
        rom[0x100] = 0x1E; // ld e,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // rr e
        rom[0x103] = 0x1B;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getE(), "The E register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x1E; // ld e,0x8A
        rom[0x101] = 0x8A;
        rom[0x102] = 0xCB; // rr e
        rom[0x103] = 0x1B;

        cpu.tick();
        cpu.tick();
        assertEquals(0x45, cpu.getE(), "The E register should equal 0x45.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB1C
    @Test
    void rr_h_test() {
        rom[0x100] = 0x26; // ld h,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // rr h
        rom[0x103] = 0x1C;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getH(), "The H register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x26; // ld h,0x8A
        rom[0x101] = 0x8A;
        rom[0x102] = 0xCB; // rr h
        rom[0x103] = 0x1C;

        cpu.tick();
        cpu.tick();
        assertEquals(0x45, cpu.getH(), "The H register should equal 0x45.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB1D
    @Test
    void rr_l_test() {
        rom[0x100] = 0x2E; // ld l,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // rr l
        rom[0x103] = 0x1D;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getL(), "The L register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x2E; // ld l,0x8A
        rom[0x101] = 0x8A;
        rom[0x102] = 0xCB; // rr l
        rom[0x103] = 0x1D;

        cpu.tick();
        cpu.tick();
        assertEquals(0x45, cpu.getL(), "The L register should equal 0x45.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB1E
    @Test
    void rr_hlp_test() {
        memory.setByteAt(0xC000, 0x01); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // rr (hl)
        rom[0x104] = 0x1E;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, memory.getByteAt(cpu.getHL()), "The value pointed to by HL should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        memory.setByteAt(0xC000, 0x8A); // This is the value that HL will point to.

        cpu.tick();
        cpu.tick();
        assertEquals(0x45, memory.getByteAt(cpu.getHL()), "The value pointed to by HL should equal 0x45.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCB1F
    @Test
    void rr_a_test() {
        rom[0x100] = 0x3E; // ld a,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // rr a
        rom[0x103] = 0x1F;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0x8A
        rom[0x101] = 0x8A;
        rom[0x102] = 0xCB; // rr a
        rom[0x103] = 0x1F;

        cpu.tick();
        cpu.tick();
        assertEquals(0x45, cpu.getA(), "The A register should equal 0x45.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }
}
