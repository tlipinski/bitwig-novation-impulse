package net.tlipinski;

import com.bitwig.extension.controller.api.CursorRemoteControlsPage;

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
