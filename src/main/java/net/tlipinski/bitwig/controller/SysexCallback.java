package net.tlipinski.bitwig.controller;

import com.bitwig.extension.callback.SysexMidiDataReceivedCallback;
import com.bitwig.extension.controller.api.ControllerHost;
import net.tlipinski.bitwig.controller.observers.ImpulseModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SysexCallback implements SysexMidiDataReceivedCallback {

    public SysexCallback(ControllerHost host, Controller controller, Preferences prefs) {
        this.host = host;
        this.controller = controller;
        this.prefs = prefs;
    }

    @Override
    public void sysexDataReceived(String data) {
        if (prefs.debug()) {
            host.println("Received sysex: " + data);
        }

        Matcher matcher = impulseWelcome.matcher(data);
        if (matcher.matches()) {
            int model = Integer.valueOf(matcher.group(1), 16);
            controller.setModel(ImpulseModel.get(model));
            host.showPopupNotification("Novation Impulse " + model + " Initialized");
        }
    }

    private Pattern impulseWelcome = Pattern.compile("f00020296707(..)f7");
    private final ControllerHost host;
    private final Controller controller;
    private final Preferences prefs;
}
