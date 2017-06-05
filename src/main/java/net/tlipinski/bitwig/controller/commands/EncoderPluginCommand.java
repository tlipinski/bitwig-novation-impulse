package net.tlipinski.bitwig.controller.commands;

import com.bitwig.extension.controller.api.CursorRemoteControlsPage;
import com.bitwig.extension.controller.api.RemoteControl;
import net.tlipinski.bitwig.controller.*;

import java.util.stream.Stream;

public class EncoderPluginCommand implements MidiCommand {

    public EncoderPluginCommand(Controller controller, MidiSend midiSend, SysexSend sysexSend) {
        this.controller = controller;
        this.midiSend = midiSend;
        this.sysexSend = sysexSend;

        CursorRemoteControlsPage cursorRemoteControlsPage = controller.getTracks().getCursorRemoteControlsPage();

        for (int i = 0; i < 8; i++) {
            cursorRemoteControlsPage.getParameter(i).name().markInterested();
            cursorRemoteControlsPage.getParameter(i).markInterested();
        }
    }

    @Override
    public Stream<Boolean> conditions(int statusByte, int data1, int data2) {
        return Stream.of(
                controller.getEncoderMode() == EncoderMode.PLUGIN,
                statusByte == 0xB1,
                data1 >= 0,
                data1 <= 7
        );
    }

    @Override
    public void handle(int data1, int data2) {
        int encoderIndex = data1;
        RemoteControl parameter = controller.getTracks().getCursorRemoteControlsPage().getParameter(encoderIndex);

        double mod = (data2 - 64) * 0.01;
        parameter.inc(mod);

        sysexSend.displayText(parameter.name().get());
        int val = (int) (parameter.get() * 100);
        midiSend.send(0xB1, data1, val);
    }

    private final Controller controller;
    private final MidiSend midiSend;
    private final SysexSend sysexSend;

}
