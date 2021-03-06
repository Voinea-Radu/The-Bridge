package dev.lightdream.bridge.Utils;

import dev.lightdream.bridge.Bridge;
import dev.lightdream.bridge.Enums.LoadFileType;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

@SuppressWarnings("unused")
public class API {

    public static String color(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static ArrayList<String> color(ArrayList<String> str) {

        ArrayList<String> output = new ArrayList<>();

        for(String var1 : str){
            output.add(ChatColor.translateAlternateColorCodes('&', var1));
        }

        return output;
    }

    public static ArrayList<String> color(List<String> str) {

        ArrayList<String> output = new ArrayList<>();

        for(String var1 : str){
            output.add(ChatColor.translateAlternateColorCodes('&', var1));
        }

        return output;
    }

    public static void createFile(String path, LoadFileType type) {

        try {
            if(type == LoadFileType.DEFAULT)
                FileUtils.copyInputStreamToFile(Bridge.INSTANCE.getResource(path), new File(Bridge.INSTANCE.getDataFolder(), path));
            else if(type == LoadFileType.PLAYER_DATA){
                FileUtils.copyInputStreamToFile(Bridge.INSTANCE.getResource("playerData.yml"), new File(Bridge.INSTANCE.getDataFolder(), path));
                FileConfiguration data = loadFile(path, type);
                saveFile(data, path);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveFile(FileConfiguration config, String path){

        File file = new File(Bukkit.getServer().getPluginManager().getPlugin(Bridge.PLUGIN_NAME).getDataFolder(), path);
        try {
            if(!file.exists())
                if(!file.createNewFile())
                    Bridge.logger.severe(Language.error_creating_file);
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static FileConfiguration loadFile(String path, LoadFileType type) {

        File file = new File(Bukkit.getServer().getPluginManager().getPlugin(Bridge.PLUGIN_NAME).getDataFolder(), path);
        if (file.exists()) {
            return YamlConfiguration.loadConfiguration(file);
        } else {
            createFile(path, type);
            return loadFile(path, type);
        }
    }

    public static void sendColoredMessage(CommandSender sender, String text){
        //ArrayList<String> texts = new ArrayList<>();
        for(String var1 : text.split("%newline%"))
            sender.sendMessage(color(var1));
    }

    public static FileConfiguration loadPlayerDataFile(UUID uuid){
        return API.loadFile("PlayerData/" + uuid + ".yml", LoadFileType.PLAYER_DATA);
    }

    public static void savePlayerDataFile(UUID uuid, FileConfiguration data){
        API.saveFile(data, "PlayerData/" + uuid + ".yml");
    }

    public static void sendCommands(CommandSender sender) {
        API.sendColoredMessage(sender, "&4*&c&m                         &7*( &3&l " + Bridge.PLUGIN_NAME + " &7)*&c&m                          &4*");
        Bridge.commandHandler.getCommands().forEach(command -> {
            if (command.hasPermission(sender)) {
                API.sendColoredMessage(sender, "  &7&l- " + command.sendHelpLine());
            }
        });
        API.sendColoredMessage(sender, "&4*&c&m                                                                             &4*");
    }


}
