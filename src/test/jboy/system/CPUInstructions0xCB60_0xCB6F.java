package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xCB60_0xCB6F {
    static private CPU cpu;
    static private Memory memory;
    static private int[] rom;

    @BeforeAll
    static void testBeforeAll() {
        memory = new Memory();
        cpu = new CPU(memory);
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

    // op code 0xCB60
    @Test
    void bit_4_b_test() {
        rom[0x100] = 0x06; // ld b,0x10
        rom[0x101] = 0x10;
        rom[0x102] = 0xCB; // bit 4,b
        rom[0x103] = 0x60;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x06; // ld b,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 4,b
        rom[0x103] = 0x60;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB61
    @Test
    void bit_4_c_test() {
        rom[0x100] = 0x0E; // ld c,0x10
        rom[0x101] = 0x10;
        rom[0x102] = 0xCB; // bit 4,c
        rom[0x103] = 0x61;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x0E; // ld c,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 4,c
        rom[0x103] = 0x61;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB62
    @Test
    void bit_4_d_test() {
        rom[0x100] = 0x16; // ld d,0x10
        rom[0x101] = 0x10;
        rom[0x102] = 0xCB; // bit 4,d
        rom[0x103] = 0x62;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x16; // ld d,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 4,d
        rom[0x103] = 0x62;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB63
    @Test
    void bit_4_e_test() {
        rom[0x100] = 0x1E; // ld e,0x10
        rom[0x101] = 0x10;
        rom[0x102] = 0xCB; // bit 4,e
        rom[0x103] = 0x63;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x1E; // ld e,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 4,e
        rom[0x103] = 0x63;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB64
    @Test
    void bit_4_h_test() {
        rom[0x100] = 0x26; // ld h,0x10
        rom[0x101] = 0x10;
        rom[0x102] = 0xCB; // bit 4,h
        rom[0x103] = 0x64;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x26; // ld h,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 4,h
        rom[0x103] = 0x64;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB65
    @Test
    void bit_4_l_test() {
        rom[0x100] = 0x2E; // ld l,0x10
        rom[0x101] = 0x10;
        rom[0x102] = 0xCB; // bit 4,l
        rom[0x103] = 0x65;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x2E; // ld l,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 4,l
        rom[0x103] = 0x65;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB66
    @Test
    void bit_4_hlp_test() {
        memory.setByteAt(0xC000, 0x10); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // bit 4,(hl)
        rom[0x104] = 0x66;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCB67
    @Test
    void bit_4_a_test() {
        rom[0x100] = 0x3E; // ld a,0x10
        rom[0x101] = 0x10;
        rom[0x102] = 0xCB; // bit 4,a
        rom[0x103] = 0x67;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 4,a
        rom[0x103] = 0x67;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB68
    @Test
    void bit_5_b_test() {
        rom[0x100] = 0x06; // ld b,0x20
        rom[0x101] = 0x20;
        rom[0x102] = 0xCB; // bit 5,b
        rom[0x103] = 0x68;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x06; // ld b,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 5,b
        rom[0x103] = 0x68;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB69
    @Test
    void bit_5_c_test() {
        rom[0x100] = 0x0E; // ld c,0x20
        rom[0x101] = 0x20;
        rom[0x102] = 0xCB; // bit 5,c
        rom[0x103] = 0x69;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x0E; // ld c,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 5,c
        rom[0x103] = 0x69;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB6A
    @Test
    void bit_5_d_test() {
        rom[0x100] = 0x16; // ld d,0x20
        rom[0x101] = 0x20;
        rom[0x102] = 0xCB; // bit 5,d
        rom[0x103] = 0x6A;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x16; // ld d,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 5,d
        rom[0x103] = 0x6A;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB6B
    @Test
    void bit_5_e_test() {
        rom[0x100] = 0x1E; // ld e,0x20
        rom[0x101] = 0x20;
        rom[0x102] = 0xCB; // bit 5,e
        rom[0x103] = 0x6B;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x1E; // ld e,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 5,e
        rom[0x103] = 0x6B;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB6C
    @Test
    void bit_5_h_test() {
        rom[0x100] = 0x26; // ld h,0x20
        rom[0x101] = 0x20;
        rom[0x102] = 0xCB; // bit 5,h
        rom[0x103] = 0x6C;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x26; // ld h,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 5,h
        rom[0x103] = 0x6C;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB6D
    @Test
    void bit_5_l_test() {
        rom[0x100] = 0x2E; // ld l,0x20
        rom[0x101] = 0x20;
        rom[0x102] = 0xCB; // bit 5,l
        rom[0x103] = 0x6D;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x2E; // ld l,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 5,l
        rom[0x103] = 0x6D;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB6E
    @Test
    void bit_5_hlp_test() {
        memory.setByteAt(0xC000, 0x20); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // bit 5,(hl)
        rom[0x104] = 0x6E;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCB6F
    @Test
    void bit_5_a_test() {
        rom[0x100] = 0x3E; // ld a,0x20
        rom[0x101] = 0x20;
        rom[0x102] = 0xCB; // bit 5,a
        rom[0x103] = 0x6F;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // bit 5,a
        rom[0x103] = 0x6F;

        cpu.tick();
        cpu.tick();
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }
}
