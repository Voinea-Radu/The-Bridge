package dev.lightdream.bridge;

import dev.lightdream.bridge.Commands.CommandHandler;
import dev.lightdream.bridge.Enums.LoadFileType;
import dev.lightdream.bridge.Utils.API;
import dev.lightdream.bridge.Utils.Language;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

@SuppressWarnings("unused")
public final class Bridge extends JavaPlugin {

    public static Bridge INSTANCE;
    public static CommandHandler commandHandler;
    public static String PLUGIN_NAME = "Bridge";
    public static String PLUGIN_PERMISSION = "bridge.";
    public static String PLUGIN_MAIN_COMMAND = "bridge";
    public static Logger logger;
    public static FileConfiguration config;

    private static String lang;

    @Override
    public void onEnable() {
        INSTANCE = this;

        commandHandler = new CommandHandler(this);
        logger = super.getLogger();
        config = API.loadFile("config.yml", LoadFileType.DEFAULT);
        lang = config.getString("lang");
        Language.loadLang(lang);
    }

    @Override
    public void onDisable() {
        //TODO: Save the arenas
    }

    public void onReload(){
        //Disable Functionality


        //Enable Functionality
        INSTANCE = this;

        config = API.loadFile("config.yml", LoadFileType.DEFAULT);
        lang = config.getString("lang");
        Language.loadLang("lang");
    }
}
