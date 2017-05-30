package net.tlipinski;

import com.bitwig.extension.callback.SysexMidiDataReceivedCallback;
import com.bitwig.extension.controller.api.ControllerHost;

public class SysexCallback implements SysexMidiDataReceivedCallback {

    public SysexCallback(ControllerHost host, Preferences prefs) {
        this.host = host;
        this.prefs = prefs;
    }

    @Override
    public void sysexDataReceived(String data) {
        if (prefs.debug()) {
            host.println("Received sysex: " + data);
        }
    }

    private final ControllerHost host;
    private final Preferences prefs;
}
