package net.tlipinski;

import com.bitwig.extension.controller.api.*;

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

    public void changeEncoderMode(EncoderMode mode) {
        this.encoderMode = mode;
    }

    public EncoderMode getEncoderMode() {
        return encoderMode;
    }

    public Preferences getPreferences() {
        return preferences;
    }

    public void shiftPressed() {
        this.shiftPressed = true;
    }

    public void shiftReleased() {
        this.shiftPressed = false;
    }

    public boolean isShiftPressed() {
        return shiftPressed;
    }

    public EncoderMixerModePage getEncoderMixerPage() {
        return encoderMixerPage;
    }

    private final ControllerHost host;
    private final Tracks tracks;
    private final SysexSend sysexSend;
    private final Preferences preferences;
    private ButtonsMode buttonsMode = ButtonsMode.MUTE;
    private EncoderMode encoderMode = EncoderMode.PLUGIN;
    private boolean shiftPressed = false;
    private EncoderMixerModePage encoderMixerPage = new EncoderMixerModePage();

}
