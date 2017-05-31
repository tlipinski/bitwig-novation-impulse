package net.tlipinski.observers.callbacks;

import com.bitwig.extension.callback.IntegerValueChangedCallback;
import net.tlipinski.SysexSend;

public class DisplayTrackBankRangeCallback implements IntegerValueChangedCallback {
    private final SysexSend sysexSend;

    public DisplayTrackBankRangeCallback(SysexSend sysexSend) {
        this.sysexSend = sysexSend;
    }

    @Override
    public void valueChanged(int pos) {
        sysexSend.displayText("[" + (pos + 1) + "-" + (pos + 8) + "]");
    }
}
