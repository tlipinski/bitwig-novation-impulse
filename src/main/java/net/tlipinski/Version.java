package net.tlipinski;

import com.bitwig.extension.controller.api.ControllerHost;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Properties;

public class Version {
    public static String get() {
        try {
            String pomProperties = "/META-INF/maven/net.tlipinski/bitwig-novation-impulse-49/pom.properties";
            Properties props = new Properties();
            props.load(new InputStreamReader(Version.class.getResourceAsStream(pomProperties)));
            return props.getProperty("version");
        } catch (IOException e) {
            return "???";
        }
    }

    public static String info(ControllerHost host) {
        return "Version: " + String.join(",", Arrays.asList(
            "script=" + Version.get(),
            "host=" + host.getHostVersion(),
            "api=" + host.getHostApiVersion(),
            "platform=" + host.getPlatformType().name()
        ));
    }
}
