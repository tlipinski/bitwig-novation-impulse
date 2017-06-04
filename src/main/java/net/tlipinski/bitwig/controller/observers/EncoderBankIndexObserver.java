package net.tlipinski.bitwig.controller.observers;

import com.bitwig.extension.controller.api.CursorRemoteControlsPage;
import net.tlipinski.bitwig.controller.SysexSend;
import net.tlipinski.bitwig.controller.Controller;
import net.tlipinski.bitwig.controller.observers.callbacks.DisplayEncoderBankNameCallback;

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
