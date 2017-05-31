package net.tlipinski.observers;

import com.bitwig.extension.controller.api.CursorRemoteControlsPage;
import net.tlipinski.SysexSend;
import net.tlipinski.Tracks;
import net.tlipinski.observers.callbacks.DisplayRotaryBankNameCallback;

public class RotaryBankIndexObserver {
    public RotaryBankIndexObserver(Tracks tracks, SysexSend sysexSend) {
        CursorRemoteControlsPage cursorRemoteControlsPage = tracks.getCursorRemoteControlsPage();

        cursorRemoteControlsPage.pageNames().markInterested();
        cursorRemoteControlsPage.selectedPageIndex().addValueObserver(
            new DisplayRotaryBankNameCallback(tracks, cursorRemoteControlsPage, sysexSend),
            0
        );
    }
}
