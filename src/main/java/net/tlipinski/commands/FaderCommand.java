package net.tlipinski.commands;

import com.bitwig.extension.controller.api.Track;
import net.tlipinski.MidiCommand;
import net.tlipinski.SysexSend;
import net.tlipinski.Controller;

public class FaderCommand implements MidiCommand {

    public FaderCommand(Controller controller, SysexSend sysexSend) {
        this.controller = controller;
        this.sysexSend = sysexSend;
    }

    @Override
    public boolean triggersFor(int statusByte, int data1, int data2) {
        return statusByte == 0xB0 && (0 <= data1 && data1 <= 8);
    }

    @Override
    public void handle(int data1, int data2) {
        Track t = this.controller.getTracks().get(data1);
        if (t != null) {
            t.getVolume().set(data2, 128);
            this.sysexSend.displayText(t.name().get());
        } else {
            this.sysexSend.displayText("<empty>");
        }
    }

    private Controller controller;
    private SysexSend sysexSend;

}
