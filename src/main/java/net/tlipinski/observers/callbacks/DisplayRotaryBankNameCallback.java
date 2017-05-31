package net.tlipinski.observers.callbacks;

import com.bitwig.extension.callback.IntegerValueChangedCallback;
import com.bitwig.extension.controller.api.CursorRemoteControlsPage;
import net.tlipinski.SysexSend;
import net.tlipinski.Controller;

public class DisplayRotaryBankNameCallback implements IntegerValueChangedCallback {
    private final Controller controller;
    private final CursorRemoteControlsPage cursorRemoteControlsPage;
    private final SysexSend sysexSend;

    public DisplayRotaryBankNameCallback(Controller controller, CursorRemoteControlsPage cursorRemoteControlsPage, SysexSend sysexSend) {
        this.controller = controller;
        this.cursorRemoteControlsPage = cursorRemoteControlsPage;
        this.sysexSend = sysexSend;
    }

    @Override
    public void valueChanged(int page) {
        // selectedPageIndex().get() was showing stale values
        String[] pages = cursorRemoteControlsPage.pageNames().get();
        if (pages.length > 0) {
            if (page < pages.length) {
                sysexSend.displayText(pages[page]);
            } else {
                controller.getHost().errorln("Rotary bank page index bigger than pages size: " + page + ", pages: "+ String.join(", ", pages));
            }
        }
    }
}
