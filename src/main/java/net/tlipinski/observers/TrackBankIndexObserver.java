package net.tlipinski.observers;

import net.tlipinski.Controller;
import net.tlipinski.SysexSend;
import net.tlipinski.observers.callbacks.DisplayTrackBankRangeCallback;

public class TrackBankIndexObserver {
    public TrackBankIndexObserver(Controller controller, SysexSend sysexSend) {
        // track.scrollPosition().get() was showing stale values
        controller.getTracks().getTrackBank().scrollPosition().addValueObserver(
                new DisplayTrackBankRangeCallback(sysexSend)
        );
    }
}
