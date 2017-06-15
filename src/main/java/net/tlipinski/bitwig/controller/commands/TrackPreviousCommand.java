package net.tlipinski.bitwig.controller.commands;

import com.bitwig.extension.controller.api.CursorTrack;
import com.bitwig.extension.controller.api.Track;
import net.tlipinski.bitwig.controller.Controller;
import net.tlipinski.bitwig.controller.MidiCommand;
import net.tlipinski.bitwig.controller.SysexSend;

import java.util.stream.Stream;

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
    public Stream<Boolean> triggersWhen(int statusByte, int data1, int data2) {
        return Stream.of(
                statusByte == 0xB0,
                data1 == 38,
                data2 == 1
        );
    }

    @Override
    public void handle(int data1, int data2) {
        CursorTrack cursorTrack = controller.getTracks().getCursorTrack();
        cursorTrack.selectPrevious();
        controller.getHost().scheduleTask(() -> {
            sysexSend.displayText(cursorTrack.name().get());
        }, 200);
    }

    private final Controller controller;
    private final SysexSend sysexSend;
}
