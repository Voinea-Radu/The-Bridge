package dev.lightdream.bridge.Utils;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import dev.lightdream.bridge.Arenas.Arena;
import dev.lightdream.bridge.Bridge;
import dev.lightdream.bridge.Enums.LoadFileType;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        for(String var1 : text.split("%newline%"))
            sender.sendMessage(color(var1));
    }

    public static void sendColoredMessage(CommandSender sender, List<Object> texts){
        for(Object textObj : texts){
            String text = String.valueOf(textObj);
            for(String var1 : text.split("%newline%"))
                sender.sendMessage(color(var1));

        }
    }

    public static void sendColoredMessage(List<?> senders, String text){
        for(Object sender : senders)
            for(String var1 : text.split("%newline%"))
                ((CommandSender) sender).sendMessage(color(var1));
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

    public static Clipboard loadArenaSchematic(String name){
        File file = new File(Bridge.INSTANCE.getDataFolder().getAbsolutePath() + "/ArenaSchematic/" + name + ".schematic");
        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            return reader.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static HashMap<String, String> getArenaFileReady(Arena arena){
        HashMap<String, String> output = new HashMap<>();

        output.put("name", arena.getGameName());
        output.put("minPlayers", String.valueOf(arena.getMinPlayerNumber()));
        output.put("maxPlayers", String.valueOf(arena.getMaxPlayerNumber()));
        output.put("waiting-world", arena.getWaitingLocation().getWorld().getName());
        output.put("waiting-x", String.valueOf(arena.getWaitingLocation().getBlockX()));
        output.put("waiting-y", String.valueOf(arena.getWaitingLocation().getBlockY()));
        output.put("waiting-z", String.valueOf(arena.getWaitingLocation().getBlockZ()));
        output.put("spawn-world", arena.getSpawnLocations().get(0).getWorld().getName());

        StringBuilder payloadX = new StringBuilder();
        StringBuilder payloadY = new StringBuilder();
        StringBuilder payloadZ = new StringBuilder();

        for(Location l : arena.getSpawnLocations()){
            payloadX.append(l.getBlockX()).append(" ");
            payloadY.append(l.getBlockY()).append(" ");
            payloadZ.append(l.getBlockZ()).append(" ");
        }
        output.put("spawn-x", payloadX.toString());
        output.put("spawn-y", payloadY.toString());
        output.put("spawn-z", payloadZ.toString());
        output.put("winPoints", String.valueOf(arena.getWinPoints()));
        output.put("schematicName", arena.getSchematicName());
        output.put("name", arena.getGameName());

        return output;
    }

    public static List<Arena> getArenaFromFile(){

        String path = "arenas.%s.%s";

        FileConfiguration dataFile = loadFile("arenas.yml", LoadFileType.DEFAULT);
        List<String> arenasNames = (List<String>) dataFile.getList("arenas-names");
        List<Map<?, ?>> dataList = dataFile.getMapList("arenas");
        if(arenasNames == null)
            return null;

        List<Arena> arenas = new ArrayList<>();

        for(int i=0;i<arenasNames.size();i++){
            HashMap<String, String> data = (HashMap<String, String>) dataList.get(i);
            String name = arenasNames.get(i);

            Location l1 = new Location(Bukkit.getWorld(data.get("waiting-world")),
                    Integer.parseInt(data.get("waiting-x")),
                    Integer.parseInt(data.get("waiting-y")),
                    Integer.parseInt(data.get("waiting-z")));
            List<Location> l2 = new ArrayList<>();

            World spawnWorld = Bukkit.getWorld(data.get("spawn-world"));

            String[] xs = data.get("spawn-x").split(" ");
            String[] ys = data.get("spawn-y").split(" ");
            String[] zs = data.get("spawn-z").split(" ");

            for(int j=0;j<xs.length;j++)
                l2.add(new Location(spawnWorld,
                        Integer.parseInt(xs[j]),
                        Integer.parseInt(ys[j]),
                        Integer.parseInt(zs[j])));

            arenas.add(new Arena(Bridge.INSTANCE,
                    Integer.parseInt(data.get("maxPlayers")),
                    Integer.parseInt(data.get("minPlayers")),
                    l1, l2, name,
                    Integer.parseInt(data.get("winPoints")),
                    data.get("schematicName")
            ));
        }
        return arenas;
    }


}
