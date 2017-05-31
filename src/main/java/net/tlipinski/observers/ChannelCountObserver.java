package net.tlipinski.observers;

import com.bitwig.extension.controller.api.Track;
import net.tlipinski.MidiSend;
import net.tlipinski.Controller;
import net.tlipinski.observers.callbacks.RefreshAllLightsCallback;

public class ChannelCountObserver {

    public ChannelCountObserver(Controller controller, MidiSend midiSend) {
        for (Track t : controller.getTracks().getAll()) {
            t.getMute().markInterested();
            t.getSolo().markInterested();
        }

        controller.getTracks().getTrackBank().channelCount().addValueObserver((int count) -> {
            new RefreshAllLightsCallback(controller, midiSend).valueChanged(controller.getButtonsMode());
        });
    }
}
