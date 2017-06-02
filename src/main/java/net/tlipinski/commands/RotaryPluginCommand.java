package net.tlipinski.commands;

import com.bitwig.extension.controller.api.CursorRemoteControlsPage;
import com.bitwig.extension.controller.api.RemoteControl;
import net.tlipinski.Controller;
import net.tlipinski.MidiCommand;
import net.tlipinski.RotaryMode;
import net.tlipinski.SysexSend;

public class RotaryPluginCommand implements MidiCommand {

    public RotaryPluginCommand(Controller controller, SysexSend sysexSend) {
        this.controller = controller;
        this.sysexSend = sysexSend;

        CursorRemoteControlsPage cursorRemoteControlsPage = controller.getTracks().getCursorRemoteControlsPage();

        for (int i = 0; i < 8; i++) {
            cursorRemoteControlsPage.getParameter(i).name().markInterested();
            cursorRemoteControlsPage.getParameter(i).markInterested();
        }
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return (controller.getRotaryMode() == RotaryMode.PLUGIN) &&
                (statusByte == 0xB1) && (0 <= data1 && data1 <= 7);
    }

    @Override
    public void handle(int data1, int data2) {
        int rotaryIndex = data1;
        RemoteControl parameter = controller.getTracks().getCursorRemoteControlsPage().getParameter(rotaryIndex);

        double mod = (data2 - 64) * 0.03;
        parameter.inc(mod);

        sysexSend.displayText(parameter.name().get());
    }

    private final Controller controller;
    private final SysexSend sysexSend;

}
