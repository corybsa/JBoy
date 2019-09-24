package test.jboy.system;

import jboy.system.CPU;
import jboy.system.Memory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CPUTest {
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
        rom = new int[0x7FFF];
    }

    @AfterEach
    void tearDown() {
        rom = null;
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
    }

    @Test
    void setFlagsTest() {
        cpu.setFlags(CPU.FLAG_ZERO);
        assertEquals(0b10000000, cpu.getF(), "Only ZERO flag should be set.");

        cpu.setFlags(CPU.FLAG_SUB);
        assertEquals(0b11000000, cpu.getF(), "ZERO and SUB flag should be set");

        cpu.setFlags(CPU.FLAG_HALF);
        assertEquals(0b11100000, cpu.getF(), "ZERO, SUB and HALF_CARRY flags should be set.");

        cpu.setFlags(CPU.FLAG_CARRY);
        assertEquals(0b11110000, cpu.getF(), "All flags should be set.");
    }

    @Test
    void resetFlagsTest() {
        cpu.setFlags(0b11110000);
        assertEquals(0b11110000, cpu.getF(), "All flags should be set.");

        cpu.resetFlags(CPU.FLAG_ZERO);
        assertEquals(0b01110000, cpu.getF(), "SUB, HALF_CARRY and CARRY flags should be set.");

        cpu.resetFlags(CPU.FLAG_SUB);
        assertEquals(0b00110000, cpu.getF(), "HALF_CARRY and CARRY flags should be set.");

        cpu.resetFlags(CPU.FLAG_HALF);
        assertEquals(0b00010000, cpu.getF(), "Only HALF_CARRY flag should be set.");

        cpu.resetFlags(CPU.FLAG_CARRY);
        assertEquals(0b00000000, cpu.getF(), "No flags should be set.");
    }

    @Test
    void complexFlagLogicTest() {
        cpu.setFlags(CPU.FLAG_ZERO | CPU.FLAG_CARRY);
        assertEquals(0b10010000, cpu.getF(), "ZERO and CARRY flags should be set.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_CARRY);
        assertEquals(0b00000000, cpu.getF(), "No flags should be set.");

        cpu.setFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_CARRY);
        assertEquals(0b11010000, cpu.getF(), "ZERO, SUB and CARRY flags should be set.");

        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_CARRY);
        assertEquals(0b01000000, cpu.getF(), "Only SUB should be set.");
    }

    // op code 0x00
    @Test
    void nop_test() {
        rom[0x100] = 0x00; // nop

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x101, cpu.getPC(), "PC should equal 0x101");
    }

    // op code 0x01
    @Test
    void ld_bc_xx_test() {
        rom[0x100] = 0x01; // ld bc,0x895F
        rom[0x101] = 0x5F;
        rom[0x102] = 0x89;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x895F, cpu.getBC(), "The BC register should equal 0x895F");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x103");
    }

    // op code 0x02
    @Test
    void ld_bc_a_test() {
        rom[0x100] = 0x01; // ld bc,0x7F50
        rom[0x101] = 0x50;
        rom[0x102] = 0x7F;
        rom[0x103] = 0x3E; // ld a,0x50
        rom[0x104] = 0x50;
        rom[0x105] = 0x02; // ld (bc),a

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x7F50, cpu.getBC(), "The BC register should equal 0x7F50.");

        cpu.tick();
        assertEquals(0x50, cpu.getA(), "The A register should equal 0x50.");

        cpu.tick();
        assertEquals(0x50, memory.getByteAt(cpu.getBC()), "The BC register should equal 0x50");
        assertEquals(0x106, cpu.getPC(), "PC should equal 0x106");
    }

    // op code 0x03
    @Test
    void inc_bc_test() {
        rom[0x100] = 0x03;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0x01, cpu.getBC(), "The BC register should equal 0x01.");
    }

    // op code 0x04
    @Test
    void inc_b_test() {
        rom[0x100] = 0x06; // ld b,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0x04; // inc b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x01, cpu.getB(), "The B register should equal 0x01");
        assertEquals(0x00, cpu.getF(), "No flags should be set.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x06; // ld b,0x0F
        rom[0x101] = 0x0F;
        rom[0x102] = 0x04; // inc b

        cpu.tick();
        cpu.tick();
        assertEquals(0x10, cpu.getB(), "The B register should equal 0x10");
        assertEquals(CPU.FLAG_HALF, cpu.getF(), "The HALF_CARRY flag should be set.");

        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);

        rom[0x100] = 0x06; // ld b,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x04; // inc b

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getB(), "The B register should equal 0x00");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_HALF, cpu.getF(), "The ZERO and HALF_CARRY flags should be set.");
    }

    // op code 0x05
    @Test
    void dec_b_test() {
        // Test a subtraction.
        rom[0x100] = 0x06; // ld b,0xFF
        rom[0x101] = 0xFF;
        rom[0x102] = 0x05; // dec b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFE, cpu.getB(), "The B register should equal 0xFE");
        assertEquals(CPU.FLAG_SUB, cpu.getF(), "The SUB flag should be set.");

        // Test a subtraction that results in zero.
        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        rom[0x100] = 0x06; // ld b,0x01
        rom[0x101] = 0x01;
        rom[0x102] = 0x05; // dec b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x00, cpu.getB(), "The B register should equal 0x00");
        assertEquals(CPU.FLAG_ZERO | CPU.FLAG_SUB, cpu.getF(), "ZERO and SUB flags should be set.");

        // Test a half carry.
        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        rom[0x100] = 0x06; // ld b,0x10
        rom[0x101] = 0x10;
        rom[0x102] = 0x05; // dec b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x0F, cpu.getB(), "The B register should equal 0x0F");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The SUB and HALF_CARRY flags should be set.");

        // Test decrementing zero.
        cpu.setPC(0x100);
        cpu.resetFlags(CPU.FLAG_ZERO | CPU.FLAG_SUB | CPU.FLAG_HALF | CPU.FLAG_CARRY);
        rom[0x100] = 0x06; // ld b,0x00
        rom[0x101] = 0x00;
        rom[0x102] = 0x05; // dec b

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0xFF, cpu.getB(), "The B register should equal 0xFF");
        assertEquals(CPU.FLAG_SUB | CPU.FLAG_HALF, cpu.getF(), "The SUB and HALF_CARRY flags should be set.");
    }

    // op code 0x06
    @Test
    void ld_b_x_test() {
        rom[0x100] = 0x06; // ld b,0xF3
        rom[0x101] = 0xF3;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xF3, cpu.getB(), "The B register should equal 0xF3");
        assertEquals(0x102, cpu.getPC(), "PC should equal 0x102");
    }

    // op code 0x07
    @Test
    void rlca_test() {
        rom[0x100] = 0x3E; // ld a,0xAA
        rom[0x101] = 0xAA;
        rom[0x102] = 0x07; // rcla

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0b01010101, cpu.getA(), "The A register should equal AA shifted left 1 bit.");
        assertEquals(CPU.FLAG_CARRY, cpu.getF(), "The carry flag should be set.");
        assertEquals(0x103, cpu.getPC(), "PC should equal 0x104");
    }

    // op code 0x08
    @Test
    void ld_xxp_sp_test() {}

    // op code 0x09
    @Test
    void add_hl_bc_test() {}

    // op code 0x0A
    @Test
    void ld_a_bc_test() {
        // this is the value that BC will point to.
        rom[0x7F00] = 0x12;

        rom[0x100] = 0x01; // ld bc,0x7F00
        rom[0x101] = 0x00;
        rom[0x102] = 0x7F;
        rom[0x103] = 0x0A; // ld a,(bc)

        memory.loadROM(rom);

        cpu.tick();
        cpu.tick();
        assertEquals(0x12, cpu.getA(), "The A register should equal 0x12");
        assertEquals(0x104, cpu.getPC(), "PC should equal 0x103");
    }

    // op code 0x0B
    @Test
    void dec_bc_test() {}

    // op code 0x0C
    @Test
    void inc_c_test() {}

    // op code 0x0D
    @Test
    void dec_c_test() {}

    // op code 0x0E
    @Test
    void ld_c_x_test() {}

    // op code 0x0F
    @Test
    void rrca_test() {}

    // op code 0x10
    @Test
    void stop_test() {}

    // op code 0x11
    @Test
    void ld_de_xx_test() {}

    // op code 0x12
    @Test
    void ld_dep_a_test() {}

    // op code 0x13
    @Test
    void inc_de_test() {}

    // op code 0x14
    @Test
    void inc_d_test() {}

    // op code 0x15
    @Test
    void dec_d_test() {}

    // op code 0x16
    @Test
    void ld_d_x_test() {}

    // op code 0x17
    @Test
    void rla_test() {}

    // op code 0x18
    @Test
    void jr_x_test() {}

    // op code 0x19
    @Test
    void add_hl_de_test() {}

    // op code 0x1A
    @Test
    void ld_a_dep_test() {}

    // op code 0x1B
    @Test
    void dec_de_test() {}

    // op code 0x1C
    @Test
    void inc_e_test() {}

    // op code 0x1D
    @Test
    void dec_e_test() {}

    // op code 0x1E
    @Test
    void ld_e_n_test() {}

    // op code 0x1F
    @Test
    void rra_test() {}

    // op code 0x20
    @Test
    void jr_nz_x_test() {}

    // op code 0x21
    @Test
    void ld_hl_xx_test() {}

    // op code 0x22
    @Test
    void ldi_hlp_a_test() {}

    // op code 0x23
    @Test
    void inc_hl_test() {}

    // op code 0x24
    @Test
    void inc_h_test() {}

    // op code 0x25
    @Test
    void dec_h_test() {}

    // op code 0x26
    @Test
    void ld_h_x_test() {}

    // op code 0x27
    @Test
    void daa_test() {}

    // op code 0x28
    @Test
    void jr_z_x_test() {}

    // op code 0x29
    @Test
    void add_hl_hl_test() {}

    // op code 0x2A
    @Test
    void ldi_a_hlp_test() {}

    // op code 0x2B
    @Test
    void dec_hl_test() {}

    // op code 0x2C
    @Test
    void inc_l_test() {}

    // op code 0x2D
    @Test
    void dec_l_test() {}

    // op code 0x2E
    @Test
    void ld_l_x_test() {}

    // op code 0x2F
    @Test
    void cpl_test() {}

    // op code 0x30
    @Test
    void jr_nc_x_test() {}

    // op code 0x31
    @Test
    void ld_sp_xx_test() {}

    // op code 0x32
    @Test
    void ldd_hlp_a_test() {}

    // op code 0x33
    @Test
    void inc_sp_test() {}

    // op code 0x34
    @Test
    void inc_hlp_test() {}

    // op code 0x35
    @Test
    void dec_hlp_test() {}

    // op code 0x36
    @Test
    void ld_hlp_x_test() {}

    // op code 0x37
    @Test
    void scf_test() {}

    // op code 0x38
    @Test
    void jr_c_x_test() {}

    // op code 0x39
    @Test
    void add_hl_sp_test() {}

    // op code 0x3A
    @Test
    void ldd_a_hlp_test() {}

    // op code 0x3B
    @Test
    void dec_sp_test() {}

    // op code 0x3C
    @Test
    void inc_a_test() {}

    // op code 0x3D
    @Test
    void dec_a_test() {}

    // op code 0x3E
    @Test
    void ld_a_x_test() {
        rom[0x100] = 0x3E; // ld a,0xA9
        rom[0x101] = 0xA9;

        memory.loadROM(rom);

        cpu.tick();
        assertEquals(0xA9, cpu.getA(), "The A register should equal 0xA9");
        assertEquals(0x102, cpu.getPC(), "PC should equal 0x102");
    }

    // op code 0x3F
    @Test
    void ccf_test() {}

    // op code 0x40
    @Test
    void ld_b_b_test() {}

    // op code 0x41
    @Test
    void ld_b_c_test() {}

    // op code 0x42
    @Test
    void ld_b_d_test() {}

    // op code 0x43
    @Test
    void ld_b_e_test() {}

    // op code 0x44
    @Test
    void ld_b_h_test() {}

    // op code 0x45
    @Test
    void ld_b_l_test() {}

    // op code 0x46
    @Test
    void ld_b_hlp_test() {}

    // op code 0x47
    @Test
    void ld_b_a_test() {}

    // op code 0x48
    @Test
    void ld_c_b_test() {}

    // op code 0x49
    @Test
    void ld_c_c_test() {}

    // op code 0x4A
    @Test
    void ld_c_d_test() {}

    // op code 0x4B
    @Test
    void ld_c_e_test() {}

    // op code 0x4C
    @Test
    void ld_c_h_test() {}

    // op code 0x4D
    @Test
    void ld_c_l_test() {}

    // op code 0x4E
    @Test
    void ld_c_hlp_test() {}

    // op code 0x4F
    @Test
    void ld_c_a_test() {}

    // op code 0x50
    @Test
    void ld_d_b_test() {}

    // op code 0x51
    @Test
    void ld_d_c_test() {}

    // op code 0x52
    @Test
    void ld_d_d_test() {}

    // op code 0x53
    @Test
    void ld_d_e_test() {}

    // op code 0x54
    @Test
    void ld_d_h_test() {}

    // op code 0x55
    @Test
    void ld_d_l_test() {}

    // op code 0x56
    @Test
    void ld_d_hlp_test() {}

    // op code 0x57
    @Test
    void ld_d_a_test() {}

    // op code 0x58
    @Test
    void ld_e_b_test() {}

    // op code 0x59
    @Test
    void ld_e_c_test() {}

    // op code 0x5A
    @Test
    void ld_e_d_test() {}

    // op code 0x5B
    @Test
    void ld_e_e_test() {}

    // op code 0x5C
    @Test
    void ld_e_h_test() {}

    // op code 0x5D
    @Test
    void ld_e_l_test() {}

    // op code 0x5E
    @Test
    void ld_e_hlp_test() {}

    // op code 0x5F
    @Test
    void ld_e_a_test() {}

    // op code 0x60
    @Test
    void ld_h_b_test() {}

    // op code 0x61
    @Test
    void ld_h_c_test() {}

    // op code 0x62
    @Test
    void ld_h_d_test() {}

    // op code 0x63
    @Test
    void ld_h_e_test() {}

    // op code 0x64
    @Test
    void ld_h_h_test() {}

    // op code 0x65
    @Test
    void ld_h_l_test() {}

    // op code 0x66
    @Test
    void ld_h_hlp_test() {}

    // op code 0x67
    @Test
    void ld_h_a_test() {}

    // op code 0x68
    @Test
    void ld_l_b_test() {}

    // op code 0x69
    @Test
    void ld_l_c_test() {}

    // op code 0x6A
    @Test
    void ld_l_d_test() {}

    // op code 0x6B
    @Test
    void ld_l_e_test() {}

    // op code 0x6C
    @Test
    void ld_l_h_test() {}

    // op code 0x6D
    @Test
    void ld_l_l_test() {}

    // op code 0x6E
    @Test
    void ld_l_hlp_test() {}

    // op code 0x6F
    @Test
    void ld_l_a_test() {}

    // op code 0x70
    @Test
    void ld_hlp_b_test() {}

    // op code 0x71
    @Test
    void ld_hlp_c_test() {}

    // op code 0x72
    @Test
    void ld_hlp_d_test() {}

    // op code 0x73
    @Test
    void ld_hlp_e_test() {}

    // op code 0x74
    @Test
    void ld_hlp_h_test() {}

    // op code 0x75
    @Test
    void ld_hlp_l_test() {}

    // op code 0x76
    @Test
    void ld_hlp_hlp_test() {}

    // op code 0x77
    @Test
    void ld_hlp_a_test() {}

    // op code 0x78
    @Test
    void ld_a_b_test() {}

    // op code 0x79
    @Test
    void ld_a_c_test() {}

    // op code 0x7A
    @Test
    void ld_a_d_test() {}

    // op code 0x7B
    @Test
    void ld_a_e_test() {}

    // op code 0x7C
    @Test
    void ld_a_h_test() {}

    // op code 0x7D
    @Test
    void ld_a_l_test() {}

    // op code 0x7E
    @Test
    void ld_a_hlp_test() {}

    // op code 0x7F
    @Test
    void ld_a_a_test() {}

    // op code 0x80
    @Test
    void add_a_b_test() {}

    // op code 0x81
    @Test
    void add_a_c_test() {}

    // op code 0x82
    @Test
    void add_a_d_test() {}

    // op code 0x83
    @Test
    void add_a_e_test() {}

    // op code 0x84
    @Test
    void add_a_h_test() {}

    // op code 0x85
    @Test
    void add_a_l_test() {}

    // op code 0x86
    @Test
    void add_a_hlp_test() {}

    // op code 0x87
    @Test
    void add_a_a_test() {}

    // op code 0x88
    @Test
    void adc_a_b_test() {}

    // op code 0x89
    @Test
    void adc_a_c_test() {}

    // op code 0x8A
    @Test
    void adc_a_d_test() {}

    // op code 0x8B
    @Test
    void adc_a_e_test() {}

    // op code 0x8C
    @Test
    void adc_a_h_test() {}

    // op code 0x8D
    @Test
    void adc_a_l_test() {}

    // op code 0x8E
    @Test
    void adc_a_hlp_test() {}

    // op code 0x8F
    @Test
    void adc_a_a_test() {}

    // op code 0x90
    @Test
    void sub_b_test() {}

    // op code 0x91
    @Test
    void sub_c_test() {}

    // op code 0x92
    @Test
    void sub_d_test() {}

    // op code 0x93
    @Test
    void sub_e_test() {}

    // op code 0x94
    @Test
    void sub_h_test() {}

    // op code 0x95
    @Test
    void sub_l_test() {}

    // op code 0x96
    @Test
    void sub_hlp_test() {}

    // op code 0x97
    @Test
    void sub_a_test() {}

    // op code 0x98
    @Test
    void sbc_a_b_test() {}

    // op code 0x99
    @Test
    void sbc_a_c_test() {}

    // op code 0x9A
    @Test
    void sbc_a_d_test() {}

    // op code 0x9B
    @Test
    void sbc_a_e_test() {}

    // op code 0x9C
    @Test
    void sbc_a_h_test() {}

    // op code 0x9D
    @Test
    void sbc_a_l_test() {}

    // op code 0x9E
    @Test
    void sbc_a_hlp_test() {}

    // op code 0x9F
    @Test
    void sbc_a_a_test() {}

    // op code 0xA0
    @Test
    void and_b_test() {}

    // op code 0xA1
    @Test
    void and_c_test() {}

    // op code 0xA2
    @Test
    void and_d_test() {}

    // op code 0xA3
    @Test
    void and_e_test() {}

    // op code 0xA4
    @Test
    void and_h_test() {}

    // op code 0xA5
    @Test
    void and_l_test() {}

    // op code 0xA6
    @Test
    void and_hlp_test() {}

    // op code 0xA7
    @Test
    void and_a_test() {}

    // op code 0xA8
    @Test
    void xor_b_test() {}

    // op code 0xA9
    @Test
    void xor_c_test() {}

    // op code 0xAA
    @Test
    void xor_d_test() {}

    // op code 0xAB
    @Test
    void xor_e_test() {}

    // op code 0xAC
    @Test
    void xor_h_test() {}

    // op code 0xAD
    @Test
    void xor_l_test() {}

    // op code 0xAE
    @Test
    void xor_hlp_test() {}

    // op code 0xAF
    @Test
    void xor_a_test() {}

    // op code 0xB0
    @Test
    void or_b_test() {}

    // op code 0xB1
    @Test
    void or_c_test() {}

    // op code 0xB2
    @Test
    void or_d_test() {}

    // op code 0xB3
    @Test
    void or_e_test() {}

    // op code 0xB4
    @Test
    void or_h_test() {}

    // op code 0xB5
    @Test
    void or_l_test() {}

    // op code 0xB6
    @Test
    void or_hlp_test() {}

    // op code 0xB7
    @Test
    void or_a_test() {}

    // op code 0xB8
    @Test
    void cp_b_test() {}

    // op code 0xB9
    @Test
    void cp_c_test() {}

    // op code 0xBA
    @Test
    void cp_d_test() {}

    // op code 0xBB
    @Test
    void cp_e_test() {}

    // op code 0xBC
    @Test
    void cp_h_test() {}

    // op code 0xBD
    @Test
    void cp_l_test() {}

    // op code 0xBE
    @Test
    void cp_hlp_test() {}

    // op code 0xBF
    @Test
    void cp_a_test() {}

    // op code 0xC0
    @Test
    void ret_nz_test() {}

    // op code 0xC1
    @Test
    void pop_bc_test() {}

    // op code 0xC2
    @Test
    void jp_nz_xx_test() {}

    // op code 0xC3
    @Test
    void jp_xx_test() {}

    // op code 0xC4
    @Test
    void call_nz_xx_test() {}

    // op code 0xC5
    @Test
    void push_bc_test() {}

    // op code 0xC6
    @Test
    void add_a_x_test() {}

    // op code 0xC7
    @Test
    void rst_00_test() {}

    // op code 0xC8
    @Test
    void ret_z_test() {}

    // op code 0xC9
    @Test
    void ret_test() {}

    // op code 0xCA
    @Test
    void jp_z_xx_test() {}

    // op code 0xCC
    @Test
    void call_z_xx_test() {}

    // op code 0xCD
    @Test
    void call_xx_test() {}

    // op code 0xCE
    @Test
    void adc_a_n_test() {}

    // op code 0xCF
    @Test
    void rst_08_test() {}

    // op code 0xD0
    @Test
    void ret_nc_test() {}

    // op code 0xD1
    @Test
    void pop_de_test() {}

    // op code 0xD2
    @Test
    void jp_nc_xx_test() {}

    // op code 0xD4
    @Test
    void call_nc_xx_test() {}

    // op code 0xD5
    @Test
    void push_de_test() {}

    // op code 0xD6
    @Test
    void sub_x_test() {}

    // op code 0xD7
    @Test
    void rst_10_test() {}

    // op code 0xD8
    @Test
    void ret_c_test() {}

    // op code 0xD9
    @Test
    void reti_test() {}

    // op code 0xDA
    @Test
    void jp_c_xx_test() {}

    // op code 0xDC
    @Test
    void call_c_xx_test() {}

    // op code 0xDE
    @Test
    void sbc_a_x_test() {}

    // op code 0xDF
    @Test
    void rst_18_test() {}

    // op code 0xE0
    @Test
    void ldh_xp_a_test() {}

    // op code 0xE1
    @Test
    void pop_hl_test() {}

    // op code 0xE2
    @Test
    void ld_cp_a_test() {}

    // op code 0xE5
    @Test
    void push_hl_test() {}

    // op code 0xE6
    @Test
    void and_x_test() {}

    // op code 0xE7
    @Test
    void rst_20_test() {}

    // op code 0xE8
    @Test
    void add_sp_x_test() {}

    // op code 0xE9
    @Test
    void jp_hlp_test() {}

    // op code 0xEA
    @Test
    void ld_xxp_a_test() {}

    // op code 0xEE
    @Test
    void xor_x_test() {}

    // op code 0xEF
    @Test
    void rst_28_test() {}

    // op code 0xF0
    @Test
    void ldh_a_xp_test() {}

    // op code 0xF1
    @Test
    void pop_af_test() {}

    // op code 0xF2
    @Test
    void ld_a_cp_test() {}

    // op code 0xF3
    @Test
    void di_test() {}

    // op code 0xF5
    @Test
    void push_af_test() {}

    // op code 0xF6
    @Test
    void or_x_test() {}

    // op code 0xF7
    @Test
    void rst_30_test() {}

    // op code 0xF8
    @Test
    void ld_hl_sp_x_test() {}

    // op code 0xF9
    @Test
    void ld_sp_hl_test() {}

    // op code 0xFA
    @Test
    void ld_a_xx_test() {}

    // op code 0xFB
    @Test
    void ei_test() {}

    // op code 0xFE
    @Test
    void cp_x_test() {}

    // op code 0xFF
    @Test
    void rst_38_test() {}
}