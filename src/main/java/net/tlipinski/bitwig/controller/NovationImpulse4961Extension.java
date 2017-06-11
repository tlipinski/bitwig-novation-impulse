package net.tlipinski.bitwig.controller;

import com.bitwig.extension.controller.ControllerExtension;
import com.bitwig.extension.controller.api.ControllerHost;
import com.bitwig.extension.controller.api.NoteInput;
import net.tlipinski.bitwig.controller.commands.*;
import net.tlipinski.bitwig.controller.observers.*;

import java.util.Arrays;
import java.util.List;

public class NovationImpulse4961Extension extends ControllerExtension {
    protected NovationImpulse4961Extension(final NovationImpulse4961ExtensionDefinition definition, final ControllerHost host) {
        super(definition, host);
    }

    @Override
    public void init() {
        final ControllerHost host = getHost();
        host.println(Version.info(host));

        MidiSend midiSend = new MidiSend(host);
        SysexSend sysexSend = new SysexSend(host);

        Preferences prefs = new Preferences(host, sysexSend);

        Tracks tracks = new Tracks(host);

        Controller controller = new Controller(host, tracks, sysexSend, prefs);

        List<MidiCommand> midiCommands = Arrays.asList(
                new SceneUpCommand(controller),
                new SceneDownCommand(controller),
                new SceneLaunchCommand(controller),
                new FaderCommand(controller, midiSend, sysexSend),
                new ButtonsModeCommand(controller, midiSend, sysexSend),
                new SingleMuteCommand(controller, midiSend, sysexSend),
                new SingleSoloCommand(controller, midiSend, sysexSend),
                new MuteCommand(controller, midiSend, sysexSend),
                new SoloCommand(controller, midiSend, sysexSend),
                new EncoderPluginCommand(controller, midiSend, sysexSend),
                new EncoderMixerCommand(controller, midiSend, sysexSend),
                new TrackBankUpCommand(controller),
                new TrackBankDownCommand(controller),
                new EncoderMixerPageUpCommand(controller, sysexSend),
                new EncoderMixerPageDownCommand(controller, sysexSend),
                new EncoderPageUpCommand(controller),
                new EncoderPageDownCommand(controller),
                new TransportRewindCommand(host.createTransport()),
                new TransportForwardCommand(host.createTransport()),
                new TransportStopCommand(host.createTransport()),
                new TransportPlayCommand(host.createTransport()),
                new TransportArrangerLoopCommand(host.createTransport()),
                new TransportRecordCommand(host.createTransport()),
                new ShiftPressedCommand(controller),
                new ShiftReleasedCommand(controller),
                new NoOpShiftCommand(),
                new TrackPreviousCommand(controller, sysexSend),
                new TrackNextCommand(controller, sysexSend),
                new EncoderPluginModeCommand(controller, midiSend, sysexSend),
                new EncoderMixerModeCommand(controller, midiSend, sysexSend)
        );

        new MuteObserver(controller, midiSend);
        new SoloObserver(controller, midiSend);
        new ChannelCountObserver(controller, midiSend);
        new EncoderBankIndexObserver(controller, sysexSend);
        new TrackBankIndexObserver(controller, sysexSend);

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
        String padNote = "99????";
        String padVelocity = "D9????";
        return host.getMidiInPort(index).createNoteInput("Impulse Keyboard",
            notePress,
            noteRelease,
            aftertouch,
            pitchWheel,
            padNote,
            padVelocity,
            modWheel);
    }

}
