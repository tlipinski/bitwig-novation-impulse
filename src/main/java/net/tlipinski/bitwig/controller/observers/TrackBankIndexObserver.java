package net.tlipinski.bitwig.controller.observers;

import net.tlipinski.bitwig.controller.Controller;
import net.tlipinski.bitwig.controller.SysexSend;
import net.tlipinski.bitwig.controller.observers.callbacks.DisplayTrackBankRangeCallback;

public class TrackBankIndexObserver {
    public TrackBankIndexObserver(Controller controller, SysexSend sysexSend) {
        // track.scrollPosition().get() was showing stale values
        controller.getTracks().getTrackBank().scrollPosition().addValueObserver(
                new DisplayTrackBankRangeCallback(sysexSend)
        );
    }
}
