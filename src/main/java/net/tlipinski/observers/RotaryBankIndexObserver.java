package net.tlipinski.observers;

import com.bitwig.extension.controller.api.CursorRemoteControlsPage;
import net.tlipinski.SysexSend;
import net.tlipinski.Controller;
import net.tlipinski.observers.callbacks.DisplayRotaryBankNameCallback;

public class RotaryBankIndexObserver {
    public RotaryBankIndexObserver(Controller controller, SysexSend sysexSend) {
        CursorRemoteControlsPage cursorRemoteControlsPage = controller.getTracks().getCursorRemoteControlsPage();

        cursorRemoteControlsPage.pageNames().markInterested();
        cursorRemoteControlsPage.selectedPageIndex().addValueObserver(
            new DisplayRotaryBankNameCallback(controller, cursorRemoteControlsPage, sysexSend),
            0
        );
    }
}
