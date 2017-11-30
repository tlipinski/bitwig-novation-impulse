package net.tlipinski.bitwig.controller;

import com.bitwig.extension.controller.api.ClipLauncherSlot;
import com.bitwig.extension.controller.api.ClipLauncherSlotBank;
import com.bitwig.extension.controller.api.Track;

public class ClipLauncher {
    public ClipLauncher(Controller controller) {
        controller.getTracks().getSceneBank().scrollPosition().markInterested();

        for (int t = 0; t < 8; t++) {
            Track channel = controller.getTracks().getTrackBank().getChannel(t);
            ClipLauncherSlotBank clips = channel.clipLauncherSlotBank();

            for (int i = 0; i < clips.getSizeOfBank(); i++) {
                final ClipLauncherSlot clip = clips.getItemAt(i);
                clip.hasContent().markInterested();

                final int idx = t;
                clip.isPlaybackQueued().addValueObserver((boolean is) -> {
                    controller.getHost().println("PlaybackQueued " + idx + " " + is);
                    if (is) {
                        controller.getMidiSend().clipLight(idx, ClipLight.PLAYBACK_QUEUED);
                    } else {
                        controller.getMidiSend().clipLight(idx, ClipLight.PLAYING);
                    }
                });

                clip.isPlaying().addValueObserver((boolean is) -> {
                    controller.getHost().println("Playing " + idx + " " + is);
                    if (is) {
                        controller.getMidiSend().clipLight(idx, ClipLight.PLAYING);
                    } else {
                        if (clip.hasContent().get()) {
                            controller.getMidiSend().clipLight(idx, ClipLight.STOPPED);
                        } else {
                            controller.getMidiSend().clipLight(idx, ClipLight.EMPTY);
                        }
                    }
                });

                clip.hasContent().addValueObserver((boolean is) -> {
                    controller.getHost().println("Content " + idx + " " + is);
                    if (is) {

                    } else {

                    }
                });

                clip.isStopQueued().addValueObserver((boolean is) -> {
                    controller.getHost().println("StopQueued " + idx + " " + is);
                    if (is) {
                        controller.getMidiSend().clipLight(idx, ClipLight.STOP_QUEUED);
                    }
                });

                clip.isSelected().addValueObserver((boolean is) -> {
                    controller.getHost().println("Selected " + idx + " " + is);
                });
            }
        }
    }
}
