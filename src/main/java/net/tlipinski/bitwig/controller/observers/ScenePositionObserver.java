package net.tlipinski.bitwig.controller.observers;

import net.tlipinski.bitwig.controller.Controller;
import net.tlipinski.bitwig.controller.SysexSend;
import net.tlipinski.bitwig.controller.observers.callbacks.DisplayTrackBankRangeCallback;
import net.tlipinski.bitwig.controller.observers.callbacks.RefreshClipLauncherCallback;

public class ScenePositionObserver {
    public ScenePositionObserver(Controller controller, SysexSend sysexSend) {
        controller.getHost().createSceneBank(1).scrollPosition().subscribe();

        controller.getTracks().getTrackBank().sceneBank().scrollPosition().addValueObserver((int pos) -> {
                controller.getHost().println("" + pos);
            }
        );

        controller.getTracks().getTrackBank().sceneBank().scrollPosition().addValueObserver((int pos) ->
                new RefreshClipLauncherCallback(controller)
        );
    }
}
