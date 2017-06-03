package net.tlipinski.commands;

import com.bitwig.extension.controller.api.CursorTrack;
import com.bitwig.extension.controller.api.Track;
import net.tlipinski.Controller;
import net.tlipinski.MidiCommand;
import net.tlipinski.SysexSend;

public class TrackPreviousCommand implements MidiCommand {

    public TrackPreviousCommand(Controller controller, SysexSend sysexSend) {
        this.controller = controller;
        this.sysexSend = sysexSend;

        controller.getTracks().getCursorTrack().hasPrevious().markInterested();
        controller.getTracks().getCursorTrack().position().markInterested();
        for (Track track : controller.getTracks().getAll()) {
            track.position().markInterested();
        }
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return (statusByte == 0xB0) && (data1 == 38) && (data2 == 1);
    }

    @Override
    public void handle(int data1, int data2) {
        CursorTrack cursorTrack = controller.getTracks().getCursorTrack();
        if (cursorTrack.hasPrevious().get()) {
            int position = cursorTrack.position().get();
            Track t = controller.getTracks().get(position - 1);
            cursorTrack.selectChannel(t);
            sysexSend.displayText(t.name().get());
        }
    }

    private final Controller controller;
    private final SysexSend sysexSend;
}
