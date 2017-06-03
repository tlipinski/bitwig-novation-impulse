package net.tlipinski;

import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.MidiOut;

import java.util.ArrayList;
import java.util.List;

public class SysexSend {

    public SysexSend(ControllerHost host) {
        this.midiOut = host.getMidiOutPort(0);
    }

    public void initController() {
        midiOut.sendSysex(sysexHeader + " 06 01 01 01 F7");
    }

    public void displayText(String text) {
        if (text.length() != 0) {
            String hex = toHexString(text.substring(0, Math.min(text.length(), maxLength)));
            midiOut.sendSysex(sysexHeader + "08 " + hex + " F7");
        }
    }

    public void displayNumber(int number) {
        String hex = toHexString(String.format("%3d", number));
        midiOut.sendSysex(sysexHeader + "09 " + hex + " F7");
    }

    private String toHexString(String sub) {
        byte[] bytes = sub.getBytes();

        List<String> hexes = new ArrayList<>();
        for (byte b : bytes) {
            hexes.add(String.format("%02X", b));
        }

        return String.join(" ", hexes);
    }

    private final MidiOut midiOut;

    private final static String sysexHeader = "F0 00 20 29 67";
    private final static int maxLength = 8;
}
