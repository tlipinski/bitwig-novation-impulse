package net.tlipinski.bitwig.controller.observers.callbacks;

import com.bitwig.extension.controller.api.ClipLauncherSlot;
import com.bitwig.extension.controller.api.ClipLauncherSlotBank;
import com.bitwig.extension.controller.api.Track;
import net.tlipinski.bitwig.controller.Controller;
import net.tlipinski.bitwig.controller.ClipLight;

public class RefreshClipLauncherCallback {

    public RefreshClipLauncherCallback(Controller controller) {
        this.controller = controller;
    }

    public void refresh() {
        controller.getHost().scheduleTask(() -> {
            int position = controller.getTracks().getSceneBank().scrollPosition().get();

            for (int t = 0; t < 8; t++) {
                Track channel = controller.getTracks().getTrackBank().getChannel(t);
                ClipLauncherSlotBank clips = channel.clipLauncherSlotBank();

                ClipLauncherSlot clip = clips.getItemAt(position);
                if (clip.isPlaying().get()) {
                    controller.getMidiSend().clipLight(t, ClipLight.PLAYING);
                } else
                if (clip.isPlaybackQueued().get()) {
                    controller.getMidiSend().clipLight(t, ClipLight.PLAYBACK_QUEUED);
                } else
                if (clip.isStopQueued().get()) {
                    controller.getMidiSend().clipLight(t, ClipLight.STOP_QUEUED);
                } else
                if (clip.hasContent().get()) {
                    controller.getMidiSend().clipLight(t, ClipLight.STOPPED);
                } else {
                    controller.getMidiSend().clipLight(t, ClipLight.EMPTY);
                }
            }
        }, 200);
    }

    private final Controller controller;
}
