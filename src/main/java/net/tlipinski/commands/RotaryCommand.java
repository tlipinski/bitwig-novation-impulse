package net.tlipinski.commands;

import com.bitwig.extension.controller.api.CursorRemoteControlsPage;
import com.bitwig.extension.controller.api.RemoteControl;
import com.bitwig.extension.controller.api.Track;
import net.tlipinski.MidiCommand;
import net.tlipinski.RotaryMode;
import net.tlipinski.SysexSend;
import net.tlipinski.Tracks;

public class RotaryCommand implements MidiCommand {

    public RotaryCommand(Tracks tracks, SysexSend sysexSend) {
        this.tracks = tracks;
        this.sysexSend = sysexSend;

        CursorRemoteControlsPage cursorRemoteControlsPage = tracks.getCursorRemoteControlsPage();

        for (int i = 0; i < 8; i++) {
            cursorRemoteControlsPage.getParameter(i).name().markInterested();
            cursorRemoteControlsPage.getParameter(i).markInterested();
        }

        for (int i = 0; i < 8; i++) {
            tracks.getTrackBank().getChannel(i).getPan().markInterested();
        }
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return statusByte == 0xB1 && (0 <= data1 && data1 <= 7);
    }

    @Override
    public void handle(int data1, int data2) {
        int rotaryIndex = data1;

        if (tracks.getRotaryMode() == RotaryMode.PLUGIN) {
            RemoteControl parameter = tracks.getCursorRemoteControlsPage().getParameter(rotaryIndex);

            double mod = (data2 - 64) * 0.03;
            parameter.inc(mod);

            sysexSend.displayText(parameter.name().get());
        }

        if (tracks.getRotaryMode() == RotaryMode.MIXER) {
            Track track = tracks.get(rotaryIndex);
            double mod = (data2 - 64) * 0.03;
            track.getPan().inc(mod);

            sysexSend.displayText("Pan");
        }
    }

    private final Tracks tracks;
    private final SysexSend sysexSend;

}
