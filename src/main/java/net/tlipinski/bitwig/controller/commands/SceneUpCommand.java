package net.tlipinski.bitwig.controller.commands;

import com.bitwig.extension.controller.api.Application;
import com.bitwig.extension.controller.api.SceneBank;
import net.tlipinski.bitwig.controller.Controller;
import net.tlipinski.bitwig.controller.MidiCommand;

import java.util.stream.Stream;

public class SceneUpCommand implements MidiCommand {

    private final Controller controller;
    private final Application application;

    public SceneUpCommand(Controller controller) {
        this.controller = controller;
        this.application = controller.getHost().createApplication();

        application.panelLayout().markInterested();
    }

    @Override
    public Stream<Boolean> conditions(int statusByte, int data1, int data2) {
        return Stream.of(
                application.panelLayout().get().equals(Application.PANEL_LAYOUT_MIX),
                statusByte == 0xB0,
                data1 == 27
        );
    }

    @Override
    public void handle(int data1, int data2) {
        if (data2 == 1) {
            controller.getTracks().getSceneBank().scrollBackwards();
            // scene bank is created with .createSceneBank(1) so index 0
            // corresponds to first scene in current single element SceneBank page
            controller.getTracks().getSceneBank().getScene(0).selectInEditor();
        }
    }

}
