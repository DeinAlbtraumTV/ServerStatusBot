package de.albtraum.config;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.*;

public final class ConfigController {


    public static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final File config_file = new File("./bot_config.json");
    public static Config cfg;

    /**
     * (Re)loads the de.albtraum.config file
     */
    public static void loadConfig() {
        try {
            if (!config_file.exists()) {
                cfg = new Config();
                Writer w = new FileWriter(config_file);
                gson.toJson(cfg, w);
                w.close();
            }
            final JsonReader r = new JsonReader(new FileReader(config_file));
            cfg = gson.fromJson(r, Config.class);
            if (Config.CURRENT_CFG_VERSION != cfg.cfgVersion) {
                saveConfig();
            }
            r.close();
        } catch (IOException e) {
            System.err.println("FAILED TO SAVE/LOAD CONFIG");
            e.printStackTrace();
        }
    }

    /**
     * Saves changed entries to de.albtraum.config file
     */
    public static void saveConfig() throws IOException {
        cfg.cfgVersion = Config.CURRENT_CFG_VERSION;
        Writer w = new FileWriter(config_file);
        gson.toJson(cfg, w);
        w.close();
    }
}

