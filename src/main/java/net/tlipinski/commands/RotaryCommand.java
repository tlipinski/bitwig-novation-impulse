package net.tlipinski.commands;

import com.bitwig.extension.controller.api.CursorRemoteControlsPage;
import com.bitwig.extension.controller.api.RemoteControl;
import com.bitwig.extension.controller.api.Track;
import net.tlipinski.MidiCommand;
import net.tlipinski.RotaryMode;
import net.tlipinski.SysexSend;
import net.tlipinski.Controller;

public class RotaryCommand implements MidiCommand {

    public RotaryCommand(Controller controller, SysexSend sysexSend) {
        this.controller = controller;
        this.sysexSend = sysexSend;

        CursorRemoteControlsPage cursorRemoteControlsPage = controller.getTracks().getCursorRemoteControlsPage();

        for (int i = 0; i < 8; i++) {
            cursorRemoteControlsPage.getParameter(i).name().markInterested();
            cursorRemoteControlsPage.getParameter(i).markInterested();
        }

        for (int i = 0; i < 8; i++) {
            controller.getTracks().getTrackBank().getChannel(i).getPan().markInterested();
        }
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return statusByte == 0xB1 && (0 <= data1 && data1 <= 7);
    }

    @Override
    public void handle(int data1, int data2) {
        int rotaryIndex = data1;

        if (controller.getRotaryMode() == RotaryMode.PLUGIN) {
            RemoteControl parameter = controller.getTracks().getCursorRemoteControlsPage().getParameter(rotaryIndex);

            double mod = (data2 - 64) * 0.03;
            parameter.inc(mod);

            sysexSend.displayText(parameter.name().get());
        }

        if (controller.getRotaryMode() == RotaryMode.MIXER) {
            Track track = controller.getTracks().get(rotaryIndex);
            double mod = (data2 - 64) * 0.03;
            track.getPan().inc(mod);

            sysexSend.displayText("Pan");
        }
    }

    private final Controller controller;
    private final SysexSend sysexSend;

}
