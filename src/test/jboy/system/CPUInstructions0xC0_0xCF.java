package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUInstructions0xC0_0xCF {
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

    // op code 0xC0
    @Test
    void ret_nz_test() {
        memory.setByteAt(0xFFFD, 0x01);
        memory.setByteAt(0xFFFC, 0x03);

        cpu.setFlags(CPU.FLAG_ZERO);
        cpu.setSP(0xFFFC);
        cpu.setPC(0x1234);

        rom[0x1234] = 0xC0; // ret nz

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xFFFC, cpu.getSP(), "The SP should equal 0xFFFC");
        assertEquals(0x1235, cpu.getPC(), "The PC should equal 0x1235");

        cpu.resetFlags(CPU.FLAG_ZERO);
        cpu.setPC(0x1234);
        cpu.tick();
        assertEquals(0xFFFE, cpu.getSP(), "The SP should equal 0xFFFE");
        assertEquals(0x103, cpu.getPC(), "The PC should equal 0x103");
    }

    // op code 0xC1
    @Test
    void pop_bc_test() {
        memory.setByteAt(0xFFFD, 0xBE);
        memory.setByteAt(0xFFFC, 0xEF);
        cpu.setSP(0xFFFC);

        rom[0x100] = 0xC1; // pop bc

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xBEEF, cpu.getBC(), "The BC register should equal 0xBEEF.");
        assertEquals(0xFFFE, cpu.getSP(), "The SP should equal 0xFFFE.");
        assertEquals(0x101, cpu.getPC(), "The PC should equal 0x101");
    }

    // op code 0xC2
    @Test
    void jp_nz_xx_test() {
        cpu.resetFlags(CPU.FLAG_ZERO);
        rom[0x100] = 0xC2; // jp nz,0x8000
        rom[0x101] = 0x00;
        rom[0x102] = 0x80;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x8000, cpu.getPC(), "The PC should equal 0x8000.");

        cpu.setFlags(CPU.FLAG_ZERO);
        cpu.setPC(0x100);
        cpu.tick();
        assertEquals(0x103, cpu.getPC(), "The PC should equal 0x103.");
    }

    // op code 0xC3
    @Test
    void jp_xx_test() {
        rom[0x100] = 0xC3; // jp 0x8000
        rom[0x101] = 0x00;
        rom[0x102] = 0x80;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x8000, cpu.getPC(), "The PC should equal 0x8000.");
    }

    // op code 0xC4
    @Test
    void call_nz_xx_test() {
        cpu.setFlags(CPU.FLAG_ZERO);
        rom[0x100] = 0xC4; // call nz,0x1234
        rom[0x101] = 0x34;
        rom[0x102] = 0x12;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xFFFE, cpu.getSP(), "The SP should equal 0xFFFE");
        assertEquals(0x103, cpu.getPC(), "The PC should equal 0x103");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO);
        cpu.tick();
        assertEquals(0xFFFC, cpu.getSP(), "The SP should equal 0xFFFC");
        assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01");
        assertEquals(0x03, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x03");
        assertEquals(0x1234, cpu.getPC(), "The PC should equal 0x1234");
    }

    // op code 0xC5
    @Test
    void push_bc_test() {
        rom[0x100] = 0x01; // ld bc,0xBEEF
        rom[0x101] = 0xEF;
        rom[0x102] = 0xBE;
        rom[0x103] = 0xC5; // push bc

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFFFC, cpu.getSP(), "The SP should equal 0xFFFC");
        assertEquals(0xBE, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0xEF");
        assertEquals(0xEF, memory.getByteAt(0xFFFC), "The value at address 0xFFFC should equal 0xBE");
        assertEquals(0x104, cpu.getPC(), "The PC should equal 0x104.");
    }

    // op code 0xC6
    @Test
    void add_a_x_test() {
        rom[0x100] = 0x3E; // ld a,0x3A
        rom[0x101] = 0x3A;
        rom[0x102] = 0xC6; // add a,0xC6
        rom[0x103] = 0xC6;

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xC7
    @Test
    void rst_00_test() {
        rom[0x100] = 0xC7; // rst 0x00

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xFFFC, cpu.getSP(), "The SP should equal 0xFFFC.");
        assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01.");
        assertEquals(0x01, memory.getByteAt(0xFFFC), "The value at address 0xFFFC should equal 0x01.");
        assertEquals(0x00, cpu.getPC(), "The PC should equal 0x00.");
    }

    // op code 0xC8
    @Test
    void ret_z_test() {
        memory.setByteAt(0xFFFD, 0x01);
        memory.setByteAt(0xFFFC, 0x03);

        cpu.setFlags(CPU.FLAG_ZERO);
        cpu.setSP(0xFFFC);
        cpu.setPC(0x1234);

        rom[0x1234] = 0xC8; // ret z

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xFFFE, cpu.getSP(), "The SP should equal 0xFFFE");
        assertEquals(0x103, cpu.getPC(), "The PC should equal 0x103");

        cpu.resetFlags(CPU.FLAG_ZERO);
        cpu.setSP(0xFFFC);
        cpu.setPC(0x1234);
        cpu.tick();
        assertEquals(0xFFFC, cpu.getSP(), "The SP should equal 0xFFFE");
        assertEquals(0x1235, cpu.getPC(), "The PC should equal 0x1235");
    }

    // op code 0xC9
    @Test
    void ret_test() {
        memory.setByteAt(0xFFFD, 0x01);
        memory.setByteAt(0xFFFC, 0x03);

        cpu.setSP(0xFFFC);
        cpu.setPC(0x1234);

        rom[0x1234] = 0xC9; // ret

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xFFFE, cpu.getSP(), "The SP should equal 0xFFFE");
        assertEquals(0x103, cpu.getPC(), "The PC should equal 0x103");
    }

    // op code 0xCA
    @Test
    void jp_z_xx_test() {
        cpu.resetFlags(CPU.FLAG_ZERO);
        rom[0x100] = 0xCA; // jp z,0x8000
        rom[0x101] = 0x00;
        rom[0x102] = 0x80;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x103, cpu.getPC(), "The PC should equal 0x103.");

        cpu.setFlags(CPU.FLAG_ZERO);
        cpu.setPC(0x100);
        cpu.tick();
        assertEquals(0x8000, cpu.getPC(), "The PC should equal 0x8000.");
    }

    // op code 0xCC
    @Test
    void call_z_xx_test() {
        cpu.resetFlags(CPU.FLAG_ZERO);
        rom[0x100] = 0xCC; // call z,0x1234
        rom[0x101] = 0x34;
        rom[0x102] = 0x12;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xFFFE, cpu.getSP(), "The SP should equal 0xFFFE");
        assertEquals(0x103, cpu.getPC(), "The PC should equal 0x103");

        cpu.setPC(0x100);
        cpu.setFlags(CPU.FLAG_ZERO);
        cpu.tick();
        assertEquals(0xFFFC, cpu.getSP(), "The SP should equal 0xFFFC");
        assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01");
        assertEquals(0x03, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x03");
        assertEquals(0x1234, cpu.getPC(), "The PC should equal 0x1234");
    }

    // op code 0xCD
    @Test
    void call_xx_test() {
        rom[0x100] = 0xCD; // call 0xC000
        rom[0x101] = 0x00;
        rom[0x102] = 0xC0;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xFFFC, cpu.getSP(), "The SP should equal 0xFFFC");
        assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01");
        assertEquals(0x03, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x03");
        assertEquals(0xC000, cpu.getPC(), "The PC should equal 0xC000");
    }

    // op code 0xCE
    @Test
    void adc_a_x_test() {
        cpu.setFlags(CPU.FLAG_CARRY);

        rom[0x100] = 0x3E; // ld a,0xE1
        rom[0x101] = 0xE1;
        rom[0x102] = 0xCE; // adc a,0x1E
        rom[0x103] = 0x1E;

        memory.loadROM(rom);
        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getA(), "The A register should equal 0x00.");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF | CPU.FLAG_CARRY, cpu.getF(), "The ZERO, HALF_CARRY and CARRY flags should be set.");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x104.");
    }

    // op code 0xCF
    @Test
    void rst_08_test() {
        rom[0x100] = 0xCF; // rst 0x08

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xFFFC, cpu.getSP(), "The SP should equal 0xFFFC.");
        assertEquals(0x01, memory.getByteAt(0xFFFD), "The value at address 0xFFFD should equal 0x01.");
        assertEquals(0x01, memory.getByteAt(0xFFFC), "The value at address 0xFFFD should equal 0x00.");
        assertEquals(0x08, cpu.getPC(), "The PC should equal 0x08.");
    }
}
