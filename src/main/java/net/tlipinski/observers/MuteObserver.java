package net.tlipinski.observers;

import com.bitwig.extension.controller.api.Track;
import net.tlipinski.MidiSend;
import net.tlipinski.Controller;
import net.tlipinski.observers.callbacks.MuteCallback;

import java.util.List;

public class MuteObserver {

    public MuteObserver(Controller controller, MidiSend midiSend) {
        List<Track> all = controller.getTracks().getAll();
        for (int i = 0; i < all.size(); i++) {
            Track t = all.get(i);
            t.getMute().addValueObserver(new MuteCallback(i, controller, midiSend));
        }
    }

}
