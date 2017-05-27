package net.tlipinski;

import com.bitwig.extension.api.util.midi.ShortMidiMessage;
import com.bitwig.extension.controller.ControllerExtension;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.NoteInput;
import com.bitwig.extension.controller.api.Transport;

public class NovationImpulse49Extension extends ControllerExtension {
    protected NovationImpulse49Extension(final NovationImpulse49ExtensionDefinition definition, final ControllerHost host) {
        super(definition, host);
    }

    @Override
    public void init() {
        final ControllerHost host = getHost();

        mTransport = host.createTransport();

        MidiSend midiSend = new MidiSend(host);
        SysexSend sysexSend = new SysexSend(host);

        Tracks tracks = new Tracks(host, sysexSend);

        host.getMidiInPort(0).setMidiCallback(new MidiCallback(host, tracks, midiSend, sysexSend));
        host.getMidiInPort(1).setMidiCallback(new MidiCallback(host, tracks, midiSend, sysexSend));

        host.getMidiInPort(0).setSysexCallback(new SysexCallback());
        host.getMidiInPort(1).setSysexCallback(new SysexCallback());

        NoteInput noteInputs0 = createNoteInputs(host, 0);
        NoteInput noteInputs1 = createNoteInputs(host, 1);

        host.showPopupNotification("Novation Impulse 49 Initialized");

        Lights trackBankControl = new Lights(host, tracks, midiSend);

        sysexSend.initController();
    }

    @Override
    public void exit() {
        getHost().showPopupNotification("Novation Impulse 49 Exited");
    }

    @Override
    public void flush() {

    }

    private NoteInput createNoteInputs(ControllerHost host, int index) {
        String notePress = "90????";
        String noteRelease = "80????";
        String aftertouch = "D0????";
        String pitchWheel = "E0????";
        String modWheel = "B201??";
        return host.getMidiInPort(index).createNoteInput("Impulse Keyboard", notePress, noteRelease, aftertouch, pitchWheel, modWheel);
    }

    private void onSysex0(final String data) {
        getHost().println(data);
        // MMC Transport Controls:
        switch (data) {
            case "f07f7f0605f7":
                mTransport.rewind();
                break;
            case "f07f7f0604f7":
                mTransport.fastForward();
                break;
            case "f07f7f0601f7":
                mTransport.stop();
                break;
            case "f07f7f0602f7":
                mTransport.play();
                break;
            case "f07f7f0606f7":
                mTransport.record();
                break;
        }
    }

    private void onMidi1(ShortMidiMessage msg) {
    }

    private void onSysex1(final String data) {

    }

    private Transport mTransport;
}
