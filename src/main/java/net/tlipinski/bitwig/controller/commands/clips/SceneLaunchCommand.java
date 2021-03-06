package net.tlipinski.bitwig.controller.commands.clips;

import com.bitwig.extension.controller.api.Application;
import net.tlipinski.bitwig.controller.Controller;
import net.tlipinski.bitwig.controller.MidiCommand;

import java.util.stream.Stream;

public class SceneLaunchCommand implements MidiCommand {

    private final Controller controller;
    private final Application application;

    public SceneLaunchCommand(Controller controller) {
        this.controller = controller;
        this.application = controller.getHost().createApplication();

        application.panelLayout().markInterested();
    }

    @Override
    public Stream<Boolean> triggersWhen(int statusByte, int data1, int data2) {
        return Stream.of(
                application.panelLayout().get().equals(Application.PANEL_LAYOUT_MIX),
                statusByte == 0xB0,
                data1 == 31
        );
    }

    @Override
    public void handle(int data1, int data2) {
        if (data2 == 1) {
            // read comment in SceneUpCommand
            controller.getTracks().getSceneBank().getItemAt(0).launch();
        }
    }

}
