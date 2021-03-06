package net.tlipinski.bitwig.controller.commands.encoders;

import com.bitwig.extension.controller.api.Parameter;
import com.bitwig.extension.controller.api.Track;
import net.tlipinski.bitwig.controller.*;

import java.util.stream.Stream;

public class EncoderMixerCommand implements MidiCommand {

    public EncoderMixerCommand(Controller controller, MidiSend midiSend, SysexSend sysexSend) {
        this.controller = controller;
        this.midiSend = midiSend;
        this.sysexSend = sysexSend;

        for (int i = 0; i < 8; i++) {
            controller.getTracks().getTrackBank().getChannel(i).getPan().markInterested();
            controller.getTracks().getTrackBank().getChannel(i).sendBank().getItemAt(0).markInterested();
            controller.getTracks().getTrackBank().getChannel(i).sendBank().getItemAt(1).markInterested();
        }
    }

    @Override
    public Stream<Boolean> triggersWhen(int statusByte, int data1, int data2) {
        return Stream.of(
                controller.getEncoderMode() == EncoderMode.MIXER,
                statusByte == 0xB1,
                data1 >= 0,
                data1 <= 7
        );
    }

    @Override
    public void handle(int data1, int data2) {
        int encoderIndex = data1;
        Track track = controller.getTracks().get(encoderIndex);
        if (track != null) {
            Parameter param = getParameter(track);

            double mod = (data2 - 64) * 0.01;
            param.inc(mod);

            sysexSend.displayText(track.name().get());
            int val = (int) (param.get() * 100);
            midiSend.send(0xB1, data1, val);
        } else {
            sysexSend.displayText("<empty>");
        }
    }

    private Parameter getParameter(Track track) {
        switch (controller.getEncoderMixerPage().getPage()) {
            case PAN: return track.getPan();
            case SEND1: return track.sendBank().getItemAt(0);
            case SEND2: return track.sendBank().getItemAt(1);
        }
        throw new IllegalStateException("Invalid page " + controller.getEncoderMixerPage().getPage());
    }

    private final Controller controller;
    private final MidiSend midiSend;
    private final SysexSend sysexSend;

}
