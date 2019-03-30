package sshcomm;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Slf4j
public class Utils {
    // TODO: migrate config to build dir on build
    @Getter private static final Map<String, String> configFilePathMap;
    static {
        Map<String, String> aMap = new HashMap<>();
        aMap.put("client", "configuration/PropertiesClient");
        aMap.put("client-test", "configuration/PropertiesClient-Test");
        aMap.put("server", "configuration/PropertiesServer");
        aMap.put("server-test", "configuration/PropertiesServer-Test");
        configFilePathMap = Collections.unmodifiableMap(aMap);
    }
    public static Properties getProp(String configPath) {
        Properties prop = new Properties();
        FileInputStream input = null;
        try {
            input = new FileInputStream(configPath);
            prop.load(input);
        } catch (IOException e) {
            log.error("Can't read properties file " + e.getStackTrace());
        }

        return prop;
    }

    private Utils(){}
}
