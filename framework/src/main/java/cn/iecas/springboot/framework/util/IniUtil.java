package cn.iecas.springboot.framework.util;

import org.ini4j.Config;
import org.ini4j.Ini;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

/**
 * @author ch
 * @date 2021-10-18
 */
public class IniUtil {
    public static Map<String, String> parseIni(String iniString) {
        Config config = new Config();
        config.setGlobalSection(true);
        config.setGlobalSectionName("");
        Ini ini = new Ini();
        ini.setConfig(config);
        try {
            ini.load(new StringReader(iniString));
            return ini.get("");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Map<String,String> parseIni(String sectionName,String string) {
        Ini ini = new Ini();
        try {
            ini.load(new StringReader(string));
            return ini.get(sectionName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
