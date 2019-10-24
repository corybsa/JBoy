package jboy.system;

public interface Interrupts {
    int VBLANK = 0x01;
    int LCD_STAT = 0x02;
    int TIMER = 0x04;
    int SERIAL = 0x08;
    int JOYPAD = 0x10;
}
