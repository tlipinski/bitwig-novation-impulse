package net.tlipinski.bitwig.controller;

import java.lang.reflect.Field;
import java.util.stream.Stream;

public class Template {
    private String  header     = "f0002029430000";

    private String  name       = "4269747769672020";
    private String  channel    = "00";
    private String  port       = "03";
    private String  velocity   = "02";
    private String  aftertouch = "01";
    private String  octave     = "40";
    private String  transpose  = "0b";
    private String  zones      = "00";

    private String  zone1      = "243b400000";
    private String  zone2      = "3c60400100";
    private String  zone3      = "2454401004";
    private String  zone4      = "2454401004";

    private String  encoder1   = "09357f00100801";
    private String  encoder2   = "09367f00100801";
    private String  encoder3   = "09377f00100801";
    private String  encoder4   = "09387f00100801";
    private String  encoder5   = "09397f00100801";
    private String  encoder6   = "093a7f00100801";
    private String  encoder7   = "093b7f00100801";
    private String  encoder8   = "093c7f00100801";

    private String  drumPad1   = "08437f00100801";
    private String  drumPad2   = "08457f00100801";
    private String  drumPad3   = "08477f00100801";
    private String  drumPad4   = "08487f00100801";
    private String  drumPad5   = "083c7f00100801";
    private String  drumPad6   = "083e7f00100801";
    private String  drumPad7   = "08407f00100801";
    private String  drumPad8   = "08417f00100801";

    private String  fader1     = "09297f00100801";
    private String  fader2     = "092a7f00100801";
    private String  fader3     = "092b7f00100801";
    private String  fader4     = "092c7f00100801";
    private String  fader5     = "092d7f00100801";
    private String  fader6     = "092e7f00100801";
    private String  fader7     = "092f7f00100801";
    private String  fader8     = "09307f00100801";
    private String  faderM     = "09317f00100801";

    private String  button1    = "11337f00100801";
    private String  button2    = "11347f00100801";
    private String  button3    = "11357f00100801";
    private String  button4    = "11367f00100801";
    private String  button5    = "11377f00100801";
    private String  button6    = "11387f00100801";
    private String  button7    = "11397f00100801";
    private String  button8    = "113a7f00100801";
    private String  buttonM    = "113b7f00100801";

    private String  bank       = "09017f00100801";

    private String  end        = "f7";

    String toSysexString() {
        Field[] declaredFields = Template.class.getDeclaredFields();
        Stream<String> objectStream = Stream.of(declaredFields).map(field -> {
            try {
                return field.get(this).toString();
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        return objectStream.reduce("", String::concat);
    }
}
