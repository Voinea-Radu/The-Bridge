package dev.lightdream.bridge;

import dev.lightdream.bridge.Arenas.Arena;
import dev.lightdream.bridge.Arenas.ArenaManager;
import dev.lightdream.bridge.Commands.CommandHandler;
import dev.lightdream.bridge.Enums.LoadFileType;
import dev.lightdream.bridge.Exceptions.ArenaAlreadyExists;
import dev.lightdream.bridge.Utils.API;
import dev.lightdream.bridge.Utils.Language;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    public static ArenaManager arenaManager;

    @Override
    public void onEnable() {
        INSTANCE = this;

        commandHandler = new CommandHandler(this);
        logger = super.getLogger();
        config = API.loadFile("config.yml", LoadFileType.DEFAULT);
        lang = config.getString("lang");
        Language.loadLang(lang);

        arenaManager = new ArenaManager();

        try {
            arenaManager.registerArena((List<String>) API.loadFile("arenas.yml", LoadFileType.DEFAULT).getList("arenas-names"), API.getArenaFromFile());
        } catch (ArenaAlreadyExists arenaAlreadyExists) {
            arenaAlreadyExists.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        List<HashMap<String, String>> data = new ArrayList<>();
        List<String> arenasName = new ArrayList<>();

        for(Arena arena : arenaManager.getArenasList()){
            data.add(API.getArenaFileReady(arena));
            arenasName.add(arena.getGameName());
        }

        FileConfiguration arenas = API.loadFile("arenas.yml", LoadFileType.DEFAULT);
        arenas.set("arenas", data);
        arenas.set("arenas-names", arenasName);
        API.saveFile(arenas, "arenas.yml");
    }

    public void onReload(){
        //Disable Functionality
        List<HashMap<String, String>> data = new ArrayList<>();
        List<String> arenasName = new ArrayList<>();

        for(Arena arena : arenaManager.getArenasList()){
            data.add(API.getArenaFileReady(arena));
            arenasName.add(arena.getGameName());
        }

        FileConfiguration arenas = API.loadFile("arenas.yml", LoadFileType.DEFAULT);
        arenas.set("arenas", data);
        arenas.set("arenas-name", arenasName);


        //Enable Functionality
        INSTANCE = this;

        config = API.loadFile("config.yml", LoadFileType.DEFAULT);
        lang = config.getString("lang");
        Language.loadLang("lang");
    }
}
