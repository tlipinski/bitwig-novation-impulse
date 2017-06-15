package net.tlipinski.bitwig.controller.commands;

import com.bitwig.extension.controller.api.*;
import net.tlipinski.bitwig.controller.Controller;
import net.tlipinski.bitwig.controller.MidiCommand;
import net.tlipinski.bitwig.controller.observers.callbacks.RefreshClipLauncherCallback;

import java.util.stream.Stream;

public class SceneDownCommand implements MidiCommand {

    private final Controller controller;
    private final Application application;

    public SceneDownCommand(Controller controller) {
        this.controller = controller;
        this.application = controller.getHost().createApplication();

        application.panelLayout().markInterested();
    }

    @Override
    public Stream<Boolean> triggersWhen(int statusByte, int data1, int data2) {
        return Stream.of(
                application.panelLayout().get().equals(Application.PANEL_LAYOUT_MIX),
                statusByte == 0xB0,
                data1 == 28
        );
    }

    @Override
    public void handle(int data1, int data2) {
        if (data2 == 1) {
            controller.getTracks().getSceneBank().scrollForwards();

            // read comment in SceneUpCommand
            controller.getTracks().getSceneBank().getScene(0).selectInEditor();

            new RefreshClipLauncherCallback(controller).refresh();
        }
    }

}
