package net.tlipinski;

import com.bitwig.extension.controller.ControllerExtension;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.NoteInput;
import net.tlipinski.commands.*;
import net.tlipinski.observers.ChannelCountObserver;
import net.tlipinski.observers.MuteObserver;
import net.tlipinski.observers.RotaryBankIndexObserver;
import net.tlipinski.observers.SoloObserver;

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

        Preferences prefs = new Preferences(host);

        Tracks tracks = new Tracks(host);

        Controller controller = new Controller(host, tracks, sysexSend, prefs);

        List<MidiCommand> midiCommands = Arrays.asList(
                new FaderCommand(controller, sysexSend),
                new ButtonsModeCommand(controller, midiSend, sysexSend),
                new MuteSoloCommand(controller, midiSend, sysexSend),
                new RotaryCommand(controller, sysexSend),
                new TrackBankUpCommand(controller, sysexSend),
                new TrackBankDownCommand(controller, sysexSend),
                new RotaryBankUpCommand(controller),
                new RotaryBankDownCommand(controller),
                new TransportCommand(host.createTransport()),
                new RotaryPluginModeCommand(controller, midiSend, sysexSend),
                new RotaryMixerModeCommand(controller, midiSend, sysexSend)
        );

        new MuteObserver(controller, midiSend);
        new SoloObserver(controller, midiSend);
        new ChannelCountObserver(controller, midiSend);
        new RotaryBankIndexObserver(controller, sysexSend);

        host.getMidiInPort(0).setMidiCallback(new MidiCallback(host, prefs, midiCommands));
        host.getMidiInPort(1).setMidiCallback(new MidiCallback(host, prefs, midiCommands));

        host.getMidiInPort(0).setSysexCallback(new SysexCallback(host, prefs));
        host.getMidiInPort(1).setSysexCallback(new SysexCallback(host, prefs));

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
