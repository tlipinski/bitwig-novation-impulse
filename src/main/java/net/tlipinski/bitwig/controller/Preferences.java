package net.tlipinski.bitwig.controller;

import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.SettableEnumValue;

public class Preferences {

    public Preferences(ControllerHost host) {
        SettableEnumValue debugLogging = host.getPreferences().getEnumSetting("Debug logging", "Debug", new String[]{"on", "off"}, "off");

        debugLogging.addValueObserver((String value) -> {
            this.debug = value.equals("on");
            host.println("Debug logging: " + value);
        });
    }

    public boolean debug() {
        return debug;
    }

    private boolean debug = false;
}
