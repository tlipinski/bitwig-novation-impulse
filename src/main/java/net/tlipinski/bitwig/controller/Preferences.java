package net.tlipinski.bitwig.controller;

import com.bitwig.extension.callback.NoArgsCallback;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.SettableEnumValue;
import com.bitwig.extension.controller.api.Signal;

public class Preferences {

    public Preferences(ControllerHost host, SysexSend sysexSend) {
        SettableEnumValue debugLogging = host.getPreferences().getEnumSetting("Debug logging", "Debug", new String[]{"on", "off"}, "off");
        debugLogging.addValueObserver((String value) -> {
            this.debug = value.equals("on");
            host.println("Debug logging: " + value);
        });

        Signal sendTemplate = host.getPreferences().getSignalSetting("Template", "MIDI", "Send template");
        sendTemplate.addSignalObserver(new SendTemplateCallback(host, sysexSend));
    }

    public boolean debug() {
        return debug;
    }

    private boolean debug = false;
}
