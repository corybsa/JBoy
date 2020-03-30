package goodboy.system;

import goodboy.other.Metadata;

public class Cartridge {
    private int[] rom;
    private String title;
    private boolean isColor;
    private String license;
    private boolean isSuper;
    private String type;
    private String romSize;
    private String ramSize;
    private String destinationCode;
    private int romVersionNumber;
    private int headerChecksum;

    Cartridge(int[] rom) {
        this.rom = rom;
        this.getTitle();
        this.isColor = this.rom[0x143] == 0x80;
        this.getLicense();
        this.isSuper = this.rom[0x146] == 0x03;
        this.getType();
        this.getROMSize();
        this.getRAMSize();
        this.getDestinationCode();
        this.romVersionNumber = this.rom[0x014C];
    }

    private void getTitle() {
        StringBuilder title = new StringBuilder();

        for(int i = 0x0134; i <= 0x0142; i++) {
            if(this.rom[i] >= 65 && this.rom[i] <= 90) {
                title.append((char)this.rom[i]);
            }
        }

        this.title = title.toString();
    }

    private void getLicense() {
        if(rom[0x014B] == 0x33) {
            this.license = Metadata.licensee.get(0x33 + rom[0x014B]);
        } else {
            this.license = Metadata.licensee.get(rom[0x014B]);
        }
    }

    private void getType() {
        this.type = Metadata.cartridgeType.get(rom[0x0147]);
    }

    private void getROMSize() {
        this.romSize = Metadata.romSize.get(rom[0x0148]);
    }

    private void getRAMSize() {
        this.ramSize = Metadata.ramSize.get(rom[0x0149]);
    }

    private void getDestinationCode() {
        if(this.rom[0x014A] == 0x00) {
            this.destinationCode = "Japanese";
        } else {
            this.destinationCode = "Non-Japanese";
        }
    }

    private void getHeaderChecksum() {
        // x=0:FOR i=0134h TO 014Ch:x=x-MEM[i]-1:NEXT
        this.headerChecksum = 0;

        for(int i = 0x0134; i <= 0x014C; i++) {
            this.headerChecksum = this.headerChecksum - this.rom[i] - 1;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("Title: ").append(this.title).append("\n");
        sb.append("CGB Compatible: ").append(this.isColor ? "Yes" : "No").append("\n");
        sb.append("Licensee: ").append(this.license).append("\n");
        sb.append("SGB Compatible: ").append(this.isSuper ? "Yes" : "No").append("\n");
        sb.append("Cartridge Type: ").append(this.type).append("\n");
        sb.append("Rom Size: ").append(this.romSize).append("\n");
        sb.append("Ram Size: ").append(this.ramSize).append("\n");
        sb.append("Destination Code: ").append(this.destinationCode).append("\n");
        sb.append("ROM Version: ").append(this.romVersionNumber).append("\n");
        // TODO: is this right?
        sb.append("Header Checksum: ").append(this.headerChecksum).append("\n");
        // TODO: implement this.
        sb.append("Global Checksum: ").append("Not implemented.").append("\n");

        return sb.toString();
    }
}
