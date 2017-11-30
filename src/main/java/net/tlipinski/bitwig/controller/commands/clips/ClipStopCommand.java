package net.tlipinski.bitwig.controller.commands.clips;

import com.bitwig.extension.controller.api.ClipLauncherSlot;
import com.bitwig.extension.controller.api.ClipLauncherSlotBank;
import com.bitwig.extension.controller.api.Track;
import net.tlipinski.bitwig.controller.Controller;
import net.tlipinski.bitwig.controller.MidiCommand;

import java.util.stream.Stream;

public class ClipStopCommand implements MidiCommand {

    public ClipStopCommand(Controller controller) {
        this.controller = controller;

        for (int i = 0; i < 8; i++) {
            controller.getMidiSend().send(0xB0, 60 + i, 0);
        }
    }

    @Override
    public Stream<Boolean> triggersWhen(int statusByte, int data1, int data2) {
        return Stream.of(
                controller.isShiftPressed(),
                statusByte == 0xB0,
                data1 >= 60,
                data1 <= 67
        );
    }

    @Override
    public void handle(int data1, int data2) {
        int index = data1 - 60;
        Track channel = controller.getTracks().getTrackBank().getChannel(index);
        channel.stop();
    }

    private Controller controller;
}
