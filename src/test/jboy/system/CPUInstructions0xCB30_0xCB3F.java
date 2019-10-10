package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xCB30_0xCB3F {
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
    }

    @AfterEach
    void tearDown() {
        rom = null;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
    }

    // op code 0xCB30
    @Test
    void swap_b_test() {
        rom[0x100] = 0x06; // ld b,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // swap b
        rom[0x103] = 0x30;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getB(), "The B register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x06; // ld b,0xF0
        rom[0x101] = 0xF0;
        rom[0x102] = 0xCB; // swap b
        rom[0x103] = 0x30;

        cpu.tick();
        cpu.tick();
        assertEquals(0x0F, cpu.getB(), "The B register should equal 0x0F.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB31
    @Test
    void swap_c_test() {
        rom[0x100] = 0x0E; // ld c,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // swap c
        rom[0x103] = 0x31;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getC(), "The C register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x0E; // ld c,0xF0
        rom[0x101] = 0xF0;
        rom[0x102] = 0xCB; // swap c
        rom[0x103] = 0x31;

        cpu.tick();
        cpu.tick();
        assertEquals(0x0F, cpu.getC(), "The C register should equal 0x0F.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB32
    @Test
    void swap_d_test() {
        rom[0x100] = 0x16; // ld d,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // swap d
        rom[0x103] = 0x32;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getD(), "The D register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x16; // ld d,0xF0
        rom[0x101] = 0xF0;
        rom[0x102] = 0xCB; // swap d
        rom[0x103] = 0x32;

        cpu.tick();
        cpu.tick();
        assertEquals(0x0F, cpu.getD(), "The D register should equal 0x0F.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB33
    @Test
    void swap_e_test() {
        rom[0x100] = 0x1E; // ld e,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // swap e
        rom[0x103] = 0x33;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getE(), "The E register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x1E; // ld e,0xF0
        rom[0x101] = 0xF0;
        rom[0x102] = 0xCB; // swap e
        rom[0x103] = 0x33;

        cpu.tick();
        cpu.tick();
        assertEquals(0x0F, cpu.getE(), "The E register should equal 0x0F.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB34
    @Test
    void swap_h_test() {
        rom[0x100] = 0x26; // ld h,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // swap h
        rom[0x103] = 0x34;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getH(), "The H register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x26; // ld h,0xF0
        rom[0x101] = 0xF0;
        rom[0x102] = 0xCB; // swap h
        rom[0x103] = 0x34;

        cpu.tick();
        cpu.tick();
        assertEquals(0x0F, cpu.getH(), "The H register should equal 0x0F.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB35
    @Test
    void swap_l_test() {
        rom[0x100] = 0x2E; // ld l,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // swap l
        rom[0x103] = 0x35;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getL(), "The L register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x2E; // ld l,0xF0
        rom[0x101] = 0xF0;
        rom[0x102] = 0xCB; // swap l
        rom[0x103] = 0x35;

        cpu.tick();
        cpu.tick();
        assertEquals(0x0F, cpu.getL(), "The L register should equal 0x0F.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB36
    @Test
    void swap_hlp_test() {
        memory.setByteAt(0xC000, 0x00); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // swap (hl)
        rom[0x104] = 0x36;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, memory.getByteAt(cpu.getHL()), "The value pointed to by HL should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        memory.setByteAt(0xC000, 0xF0); // This is the value that HL will point to.

        cpu.tick();
        cpu.tick();
        assertEquals(0x0F, memory.getByteAt(cpu.getHL()), "The value pointed to by HL should equal 0x0F.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCB37
    @Test
    void swap_a_test() {
        rom[0x100] = 0x3E; // ld a,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0xCB; // swap a
        rom[0x103] = 0x37;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO, cpu.getF(), "The ZERO flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0xF0
        rom[0x101] = 0xF0;
        rom[0x102] = 0xCB; // swap a
        rom[0x103] = 0x37;

        cpu.tick();
        cpu.tick();
        assertEquals(0x0F, cpu.getA(), "The A register should equal 0x0F.");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB38
    @Test
    void srl_b_test() {
        rom[0x100] = 0x06; // ld b,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // srl b
        rom[0x103] = 0x38;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getB(), "The B register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x06; // ld b,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // srl b
        rom[0x103] = 0x38;

        cpu.tick();
        cpu.tick();
        assertEquals(0x7F, cpu.getB(), "The B register should equal 0x00.");
        assertEquals(CPU.FLAG_CARRY, cpu.getF(), "The CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB39
    @Test
    void srl_c_test() {
        rom[0x100] = 0x0E; // ld c,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // srl c
        rom[0x103] = 0x39;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getC(), "The C register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x0E; // ld c,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // srl c
        rom[0x103] = 0x39;

        cpu.tick();
        cpu.tick();
        assertEquals(0x7F, cpu.getC(), "The C register should equal 0x00.");
        assertEquals(CPU.FLAG_CARRY, cpu.getF(), "The CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB3A
    @Test
    void srl_d_test() {
        rom[0x100] = 0x16; // ld d,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // srl d
        rom[0x103] = 0x3A;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getD(), "The D register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x16; // ld d,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // srl d
        rom[0x103] = 0x3A;

        cpu.tick();
        cpu.tick();
        assertEquals(0x7F, cpu.getD(), "The D register should equal 0x00.");
        assertEquals(CPU.FLAG_CARRY, cpu.getF(), "The CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB3B
    @Test
    void srl_e_test() {
        rom[0x100] = 0x1E; // ld e,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // srl e
        rom[0x103] = 0x3B;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getE(), "The E register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x1E; // ld e,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // srl e
        rom[0x103] = 0x3B;

        cpu.tick();
        cpu.tick();
        assertEquals(0x7F, cpu.getE(), "The E register should equal 0x00.");
        assertEquals(CPU.FLAG_CARRY, cpu.getF(), "The CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB3C
    @Test
    void srl_h_test() {
        rom[0x100] = 0x26; // ld h,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // srl h
        rom[0x103] = 0x3C;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getH(), "The H register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x26; // ld h,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // srl h
        rom[0x103] = 0x3C;

        cpu.tick();
        cpu.tick();
        assertEquals(0x7F, cpu.getH(), "The H register should equal 0x00.");
        assertEquals(CPU.FLAG_CARRY, cpu.getF(), "The CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB3D
    @Test
    void srl_l_test() {
        rom[0x100] = 0x2E; // ld l,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // srl l
        rom[0x103] = 0x3D;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getL(), "The L register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x2E; // ld l,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // srl l
        rom[0x103] = 0x3D;

        cpu.tick();
        cpu.tick();
        assertEquals(0x7F, cpu.getL(), "The L register should equal 0x00.");
        assertEquals(CPU.FLAG_CARRY, cpu.getF(), "The CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCB3E
    @Test
    void srl_hlp_test() {
        memory.setByteAt(0xC000, 0x01); // This is the value that HL will point to.

        rom[0x100] = 0x21; // ld hl,0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;
        rom[0x103] = 0xCB; // srl (hl)
        rom[0x104] = 0x3E;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, memory.getByteAt(cpu.getHL()), "The value pointed to by HL should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        memory.setByteAt(0xC000, 0xFF); // This is the value that HL will point to.

        cpu.tick();
        cpu.tick();
        assertEquals(0x7F, memory.getByteAt(cpu.getHL()), "The value pointed to by HL should equal 0xFE.");
        assertEquals(CPU.FLAG_CARRY, cpu.getF(), "The CARRY flag should be set.");
        assertEquals(0x105, cpu.getPC(), "PC should equal 0x105.");
    }

    // op code 0xCB3F
    @Test
    void srl_a_test() {
        rom[0x100] = 0x3E; // ld a,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0xCB; // srl a
        rom[0x103] = 0x3F;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_CARRY, cpu.getF(), "The ZERO and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0xCB; // srl a
        rom[0x103] = 0x3F;

        cpu.tick();
        cpu.tick();
        assertEquals(0x7F, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_CARRY, cpu.getF(), "The CARRY flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }
}
