package net.tlipinski.bitwig.controller.commands;

import com.bitwig.extension.controller.api.ClipLauncherSlot;
import com.bitwig.extension.controller.api.ClipLauncherSlotBank;
import com.bitwig.extension.controller.api.Track;
import net.tlipinski.bitwig.controller.*;

import java.util.stream.Stream;

public class ClipLauncherCommand implements MidiCommand {

    public ClipLauncherCommand(Controller controller) {
        this.controller = controller;

        for (int i = 0; i < 8; i++) {
            controller.getMidiSend().send(0xB0, 60 + i, 0);
        }
    }

    @Override
    public Stream<Boolean> triggersWhen(int statusByte, int data1, int data2) {
        return Stream.of(
                statusByte == 0xB0,
                data1 >= 60,
                data1 <= 67
        );
    }

    @Override
    public void handle(int data1, int data2) {
        int index = data1 - 60;
        Track channel = controller.getTracks().getTrackBank().getChannel(index);
        int scene = controller.getTracks().getSceneBank().scrollPosition().get();
        ClipLauncherSlotBank clips = channel.clipLauncherSlotBank();

        ClipLauncherSlot slot = clips.getItemAt(scene);

        slot.launch();
    }

    private Controller controller;
}
