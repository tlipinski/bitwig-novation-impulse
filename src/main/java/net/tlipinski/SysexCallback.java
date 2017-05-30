package net.tlipinski;

import com.bitwig.extension.callback.SysexMidiDataReceivedCallback;
import com.bitwig.extension.controller.api.ControllerHost;

public class SysexCallback implements SysexMidiDataReceivedCallback {

    public SysexCallback(ControllerHost host) {
        this.host = host;
    }

    @Override
    public void sysexDataReceived(String data) {
        host.println("Received sysex: " + data);
    }

    private final ControllerHost host;
}
