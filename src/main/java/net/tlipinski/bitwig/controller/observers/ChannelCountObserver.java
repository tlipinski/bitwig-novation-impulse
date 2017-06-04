package net.tlipinski.bitwig.controller.observers;

import com.bitwig.extension.controller.api.Track;
import net.tlipinski.bitwig.controller.MidiSend;
import net.tlipinski.bitwig.controller.Controller;
import net.tlipinski.bitwig.controller.observers.callbacks.RefreshAllLightsCallback;

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
