package net.tlipinski.observers;

import com.bitwig.extension.controller.api.CursorRemoteControlsPage;
import net.tlipinski.SysexSend;
import net.tlipinski.Controller;
import net.tlipinski.observers.callbacks.DisplayEncoderBankNameCallback;

public class EncoderBankIndexObserver {
    public EncoderBankIndexObserver(Controller controller, SysexSend sysexSend) {
        CursorRemoteControlsPage cursorRemoteControlsPage = controller.getTracks().getCursorRemoteControlsPage();

        cursorRemoteControlsPage.pageNames().markInterested();
        cursorRemoteControlsPage.selectedPageIndex().addValueObserver(
            new DisplayEncoderBankNameCallback(controller, cursorRemoteControlsPage, sysexSend),
            0
        );
    }
}
