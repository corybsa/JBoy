package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xD0_0xDF {
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

    // op code 0xD0
    @Test
    void ret_nc_test() {
        memory.setByteAt(0xFFFD, 0x01);
        memory.setByteAt(0xFFFC, 0x03);

        cpu.setFlags(CPU.FLAG_CARRY);
        cpu.setPC(0x1234);

        rom[0x1234] = 0xD0; // ret nc

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xFFFC, cpu.getSP(), "The SP should equal 0xFFFE");
        assertEquals(0x1235, cpu.getPC(), "The PC should equal 0x1235");

        cpu.resetFlags(CPU.FLAG_CARRY);
        cpu.setPC(0x1234);
        cpu.tick();
        assertEquals(0xFFFE, cpu.getSP(), "The SP should equal 0xFFFE");
        assertEquals(0x103, cpu.getPC(), "The PC should equal 0x103");
    }

    // op code 0xD1
    @Test
    void pop_de_test() {
        memory.setByteAt(0xFFFD, 0xEF);
        memory.setByteAt(0xFFFC, 0xBE);
        cpu.setSP(0xFFFC);

        rom[0x100] = 0xD1; // pop de

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xBEEF, cpu.getDE(), "The DE register should equal 0xBEEF.");
        assertEquals(0xFFFE, cpu.getSP(), "The SP should equal 0xFFFE.");
        assertEquals(0x101, cpu.getPC(), "The PC should equal 0x101");
    }

    // op code 0xD2
    @Test
    void jp_nc_xx_test() {
        cpu.resetFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0xD2; // jp nc,0x8000
        rom[0x101] = 0x00;
        rom[0x102] = 0x80;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x103, cpu.getPC(), "The PC should equal 0x103.");

        cpu.setFlags(CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        cpu.tick();
        assertEquals(0x8000, cpu.getPC(), "The PC should equal 0x8000.");
    }

    // op code 0xD4
    @Test
    void call_nc_xx_test() {
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0xD4; // call nc,0x1234
        rom[0x101] = 0x34;
        rom[0x102] = 0x12;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xFFFE, cpu.getSP(), "The SP should equal 0xFFFE");
        assertEquals(0x103, cpu.getPC(), "The PC should equal 0x103");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_CARRY);
        cpu.tick();
        assertEquals(0xFFFC, cpu.getSP(), "The SP should equal 0xFFFC");
        assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01");
        assertEquals(0x03, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x03");
        assertEquals(0x1234, cpu.getPC(), "The PC should equal 0x1234");
    }

    // op code 0xD5
    @Test
    void push_de_test() {
        rom[0x100] = 0x11; // ld de,0xBEEF
        rom[0x101] = 0xEF;
        rom[0x102] = 0xBE;
        rom[0x103] = 0xD5; // push de

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFFFC, cpu.getSP(), "The SP should equal 0xFFFC");
        assertEquals(0xEF, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0xEF");
        assertEquals(0xBE, memory.getByteAt(0xFFFC), "The value at address 0xFFFC should equal 0xBE");
        assertEquals(0x104, cpu.getPC(), "The PC should equal 0x104.");
    }

    // op code 0xD6
    @Test
    void sub_x_test() {
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0xD6; // sub 0x3E
        rom[0x103] = 0x3E;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0xD6; // sub 0x0F
        rom[0x103] = 0x0F;

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x2F, cpu.getA(), "The A register should equal 0x2F.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        rom[0x100] = 0x3E; // ld a,0x3E
        rom[0x101] = 0x3E;
        rom[0x102] = 0xD6; // sub 0x40
        rom[0x103] = 0x40;

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, cpu.getA(), "The A register should equal 0xFE.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xD7
    @Test
    void rst_10_test() {
        rom[0x100] = 0xD7; // rst 0x10

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xFFFC, cpu.getSP(), "The SP should equal 0xFFFC.");
        assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01.");
        assertEquals(0x00, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x00.");
        assertEquals(0x10, cpu.getPC(), "The PC should equal 0x10.");
    }

    // op code 0xD8
    @Test
    void ret_c_test() {
        memory.setByteAt(0xFFFD, 0x01);
        memory.setByteAt(0xFFFC, 0x03);

        cpu.setFlags(CPU.FLAG_CARRY);
        cpu.setSP(0xFFFC);
        cpu.setPC(0x1234);

        rom[0x1234] = 0xD8; // ret c

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xFFFE, cpu.getSP(), "The SP should equal 0xFFFE");
        assertEquals(0x103, cpu.getPC(), "The PC should equal 0x103");

        cpu.resetFlags(CPU.FLAG_CARRY);
        cpu.setSP(0xFFFC);
        cpu.setPC(0x1234);
        cpu.tick();
        assertEquals(0xFFFC, cpu.getSP(), "The SP should equal 0xFFFE");
        assertEquals(0x1235, cpu.getPC(), "The PC should equal 0x1235");
    }

    // op code 0xD9
    @Test
    void reti_test() {
        // TODO: don't know how to test this.
    }

    // op code 0xDA
    @Test
    void jp_c_xx_test() {
        cpu.resetFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0xDA; // jp c,0x8000
        rom[0x101] = 0x00;
        rom[0x102] = 0x80;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x8000, cpu.getPC(), "The PC should equal 0x8000.");

        cpu.setFlags(CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        cpu.tick();
        assertEquals(0x103, cpu.getPC(), "The PC should equal 0x103.");
    }

    // op code 0xDC
    @Test
    void call_c_xx_test() {
        cpu.resetFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0xDC; // call c,0x1234
        rom[0x101] = 0x34;
        rom[0x102] = 0x12;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xFFFC, cpu.getSP(), "The SP should equal 0xFFFC");
        assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01");
        assertEquals(0x03, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x03");
        assertEquals(0x1234, cpu.getPC(), "The PC should equal 0x1234");

        cpu.setPC(0x100);
        cpu.setFlags(CPU.FLAG_CARRY);
        cpu.tick();
        assertEquals(0xFFFE, cpu.getSP(), "The SP should equal 0xFFFE");
        assertEquals(0x103, cpu.getPC(), "The PC should equal 0x103");
    }

    // op code 0xDE
    @Test
    void sbc_a_x_test() {
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0xDE; // sbc a,0x2A
        rom[0x103] = 0x2A;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.getA(), "The A register should equal 0x10.");
        assertEquals(CPU.FLAG_SUB, cpu.getF(), "The SUB flag should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0xDE; // sbc a,0x3A
        rom[0x103] = 0x3A;

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "The ZERO and SUB flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        cpu.setPC(0x100);
        cpu.setFlags(CPU.FLAG_CARRY);
        rom[0x100] = 0x3E; // ld a,0x3B
        rom[0x101] = 0x3B;
        rom[0x102] = 0xDE; // sbc a,0x4F
        rom[0x103] = 0x4F;

        cpu.tick();
        cpu.tick();
        cpu.tick();
        assertEquals(0xEB, cpu.getA(), "The A register should equal 0xEB.");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The SUB, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xDF
    @Test
    void rst_18_test() {
        rom[0x100] = 0xDF; // rst 0x18

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xFFFC, cpu.getSP(), "The SP should equal 0xFFFC.");
        assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01.");
        assertEquals(0x00, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x00.");
        assertEquals(0x18, cpu.getPC(), "The PC should equal 0x18.");
    }
}
