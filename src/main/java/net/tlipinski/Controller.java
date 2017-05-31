package net.tlipinski;

import com.bitwig.extension.controller.api.*;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    public Controller(ControllerHost host, Tracks tracks, SysexSend sysexSend, Preferences preferences) {
        this.host = host;
        this.tracks = tracks;
        this.sysexSend = sysexSend;
        this.preferences = preferences;
    }

    public Tracks getTracks() {
        return tracks;
    }

    public void changeButtonsMode(ButtonsMode buttonsMode) {
        this.buttonsMode = buttonsMode;
    }

    public ButtonsMode getButtonsMode() {
        return buttonsMode;
    }

    public void printTracks() {
        for (int i = 0; i < tracks.getTrackBank().getSizeOfBank(); i++) {
            Track channel = tracks.getTrackBank().getChannel(i);
            host.println("" + i + ": " + channel.name().get());
        }
    }

    public ControllerHost getHost() {
        return host;
    }

    public void changeRotaryMode(RotaryMode mode) {
        this.rotaryMode = mode;
    }

    public RotaryMode getRotaryMode() {
        return rotaryMode;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    private final ControllerHost host;
    private final Tracks tracks;
    private final SysexSend sysexSend;
    private final Preferences preferences;
    private ButtonsMode buttonsMode = ButtonsMode.MUTE;
    private RotaryMode rotaryMode = RotaryMode.PLUGIN;

}
