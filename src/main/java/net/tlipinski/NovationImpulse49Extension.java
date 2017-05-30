package net.tlipinski;

import com.bitwig.extension.controller.ControllerExtension;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.NoteInput;
import net.tlipinski.commands.*;

import java.util.Arrays;
import java.util.List;

public class NovationImpulse49Extension extends ControllerExtension {
    protected NovationImpulse49Extension(final NovationImpulse49ExtensionDefinition definition, final ControllerHost host) {
        super(definition, host);
    }

    @Override
    public void init() {
        final ControllerHost host = getHost();

        MidiSend midiSend = new MidiSend(host);
        SysexSend sysexSend = new SysexSend(host);

        Tracks tracks = new Tracks(host, sysexSend);

        List<MidiCommand> midiCommands = Arrays.asList(
                new FaderCommand(tracks, sysexSend),
                new ButtonsModeCommand(tracks, midiSend, sysexSend),
                new MuteSoloCommand(tracks, midiSend, sysexSend),
                new RotaryCommand(tracks, sysexSend),
                new TrackBankUpCommand(tracks, sysexSend),
                new TrackBankDownCommand(tracks, sysexSend),
                new RotaryBankUpCommand(tracks, sysexSend),
                new RotaryBankDownCommand(tracks, sysexSend),
                new TransportCommand(host.createTransport()),
                new RotaryPluginModeCommand(tracks, midiSend, sysexSend),
                new RotaryMixerModeCommand(tracks, midiSend, sysexSend)
        );

        new MuteObserver(tracks, midiSend);
        new SoloObserver(tracks, midiSend);
        new ChannelCountObserver(tracks, midiSend);

        host.getMidiInPort(0).setMidiCallback(new MidiCallback(host, midiCommands));
        host.getMidiInPort(1).setMidiCallback(new MidiCallback(host, midiCommands));

        host.getMidiInPort(0).setSysexCallback(new SysexCallback(host));
        host.getMidiInPort(1).setSysexCallback(new SysexCallback(host));

        NoteInput noteInputs0 = createNoteInputs(host, 0);
        NoteInput noteInputs1 = createNoteInputs(host, 1);

        host.showPopupNotification("Novation Impulse 49 Initialized");

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

}
