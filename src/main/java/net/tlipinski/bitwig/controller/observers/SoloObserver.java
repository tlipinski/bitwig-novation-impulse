package net.tlipinski.bitwig.controller.observers;

import com.bitwig.extension.controller.api.Track;
import net.tlipinski.bitwig.controller.MidiSend;
import net.tlipinski.bitwig.controller.Controller;
import net.tlipinski.bitwig.controller.observers.callbacks.SoloCallback;

import java.util.List;

public class SoloObserver {
    public SoloObserver(Controller controller, MidiSend midiSend) {
        List<Track> all = controller.getTracks().getAll();
        for (int i = 0; i < all.size(); i++) {
            Track t = all.get(i);
            t.getSolo().addValueObserver(new SoloCallback(i, controller, midiSend));
        }
    }
}
