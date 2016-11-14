/*******************************************************************************
 * Copyright (c) 2014~2016 HoryuSystems Ltd. All rights reserved.
 *
 * 본 저작물의 모든 저작권은 HoryuSystems 에 있습니다.
 *
 * 소스를 참고하여 다른 프로그램을 제작하는 것은 허용되지만,
 * 프로그램의 접두사, 기능등의 수정 및 배포는 불가능합니다.
 *
 * 기능을 거의 똑같이 하여 제작하는 행위등은 '참고하여 다른 프로그램을 제작한다는 것' 에 해당하지 않습니다.
 *
 * ============================================
 * 본 소스를 참고하여 프로그램을 제작할 시 해당 프로그램에 본 소스의 출처/라이센스를 공식적으로 안내를 해야 합니다.
 * 출처: https://github.com/horyu1234
 * 라이센스: Copyright (c) 2014~2016 HoryuSystems Ltd. All rights reserved.
 * ============================================
 *
 * 자세한 내용은 https://horyu1234.com/EULA 를 확인해주세요.
 ******************************************************************************/

package com.horyu1234.handgiveall;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.Charset;
import java.util.logging.Level;

/**
 * Created by horyu on 2016-07-21.
 */
public class HGAYamlConfiguration extends YamlConfiguration {
    @Override
    public String saveToString() {
        String data = "";
        boolean first = true;
        for (String s : super.saveToString().split("\\\\u")) {
            if (s.length() >= 4 && !first) {
                data += (char) Integer.parseInt(s.substring(0, 4), 16);
                if (s.length() >= 5) {
                    data += s.substring(4);
                }
            } else {
                data += s;
                first = false;
            }
        }
        return data;
    }

    public static HGAYamlConfiguration loadConfiguration(File file) {
        Validate.notNull(file, "File cannot be null");
        HGAYamlConfiguration config = new HGAYamlConfiguration();
        try {
            config.load(file);
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load " + file, e);
        }
        return config;
    }

    @Deprecated
    public static HGAYamlConfiguration loadConfiguration(InputStream stream) {
        Validate.notNull(stream, "Stream cannot be null");
        HGAYamlConfiguration config = new HGAYamlConfiguration();
        try {
            config.load(stream);
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load configuration from stream", e);
        }
        return config;
    }

    public static HGAYamlConfiguration loadConfiguration(Reader reader) {
        Validate.notNull(reader, "Stream cannot be null");
        HGAYamlConfiguration config = new HGAYamlConfiguration();
        try {
            config.load(reader);
        } catch (Exception e) {
            Bukkit.getLogger().log(Level.SEVERE, "Cannot load configuration from stream", e);
        }
        return config;
    }
}