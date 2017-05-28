package net.tlipinski;

import com.bitwig.extension.callback.ShortMidiDataReceivedCallback;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.RemoteControl;
import com.bitwig.extension.controller.api.Track;

public class MidiCallback implements ShortMidiDataReceivedCallback {
    public MidiCallback(ControllerHost host, Tracks tracks, MidiSend midiSend, SysexSend sysexSend) {
        this.host = host;
        this.tracks = tracks;
        this.midiSend = midiSend;
        this.sysexSend = sysexSend;
    }

    @Override
    public void midiReceived(int statusByte, int data1, int data2) {
//        host.println("-- midi -- " + String.format("%2X", statusByte) + ":" + data1 + ":" + data2);

        if (statusByte == 0xB0) {
            if (0 <= data1 && data1 <= 8) {
                handleFader(data1, data2);
            } else if (9 <= data1 && data1 <= 17) {
                handleMute(data1, data2);
            } else if (data1 == 34) {
                handleButtonsMode(data2);
            } else if (data1 == 35) {
                handleTrackBankPageUp(data1);
            } else if (data1 == 36) {
                handleTrackBankPageDown(data1);
            }
        }
        if (statusByte == 0xB1) {
            if (0 <= data1 && data1 <= 8) {
                handleRotary(data1, data2);
            }
        }
    }

    private void handleRotary(int data1, int data2) {
        int rotaryIndex = data1;
        RemoteControl parameter = tracks.getCursorRemoteControlsPage().getParameter(rotaryIndex);

        double mod = (data2 - 64) * 0.03;
        parameter.inc(mod);

        sysexSend.displayText(parameter.name().get());
    }

    private void handleTrackBankPageUp(int data1) {
        tracks.getTrackBank().scrollChannelsPageUp();
    }

    private void handleTrackBankPageDown(int data1) {
        tracks.getTrackBank().scrollChannelsPageDown();
    }

    private void handleButtonsMode(int data2) {
        if (data2 == 1) {
            tracks.changeButtonsMode(ButtonsMode.MUTE);
            sysexSend.displayText("Mutes");
        } else {
            tracks.changeButtonsMode(ButtonsMode.SOLO);
            sysexSend.displayText("Solos");
        }
    }

    private void handleMute(int data1, int state) {
        int buttonIndex = data1 - 9;

        if (tracks.getButtonsMode() == ButtonsMode.MUTE) {
            if (state == 1) {
                Track t = tracks.get(buttonIndex);
                if (t != null) {
                    t.getMute().toggle();
                    boolean mute = t.getMute().get();
                    midiSend.light(buttonIndex, !mute);

                    sysexSend.displayText(t.name().get());
                } else {
                    sysexSend.displayText("<empty>");
                }
            }
        }
        if (tracks.getButtonsMode() == ButtonsMode.SOLO) {
            if (state == 1) {
                Track t = tracks.get(buttonIndex);
                if (t != null) {
                    t.getSolo().toggle();
                    boolean solo = t.getSolo().get();
                    midiSend.light(buttonIndex, solo);

                    sysexSend.displayText(t.name().get());
                } else {
                    sysexSend.displayText("<empty>");
                }
            }
        }
    }

    private void handleFader(int faderIndex, int volume) {
        Track t = tracks.get(faderIndex);
        if (t != null) {
            t.getVolume().set(volume, 128);
            sysexSend.displayText(t.name().get());
        } else {
            sysexSend.displayText("<empty>");
        }
    }

    private ControllerHost host;
    private final Tracks tracks;
    private final MidiSend midiSend;
    private final SysexSend sysexSend;
}
