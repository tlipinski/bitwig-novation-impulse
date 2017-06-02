package net.tlipinski.commands;

import com.bitwig.extension.controller.api.Track;
import net.tlipinski.Controller;
import net.tlipinski.MidiCommand;
import net.tlipinski.RotaryMode;
import net.tlipinski.SysexSend;

public class RotaryMixerCommand implements MidiCommand {

    public RotaryMixerCommand(Controller controller, SysexSend sysexSend) {
        this.controller = controller;
        this.sysexSend = sysexSend;

        for (int i = 0; i < 8; i++) {
            controller.getTracks().getTrackBank().getChannel(i).getPan().markInterested();
        }
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return (controller.getRotaryMode() == RotaryMode.MIXER) &&
                (statusByte == 0xB1) && (0 <= data1 && data1 <= 7);
    }

    @Override
    public void handle(int data1, int data2) {
        int rotaryIndex = data1;
        Track track = controller.getTracks().get(rotaryIndex);
        double mod = (data2 - 64) * 0.03;
        track.getPan().inc(mod);

        sysexSend.displayText("Pan");
    }

    private final Controller controller;
    private final SysexSend sysexSend;

}
