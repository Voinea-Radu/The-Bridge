package dev.lightdream.bridge.Arenas;

import dev.lightdream.bridge.Exceptions.ArenaAlreadyExists;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ArenaManager {

    private HashMap<String, Arena> arenas = new HashMap<>();

    public void registerArena(String name, Arena arena) throws Exception {
        if(arenas.containsKey(name))
            throw new ArenaAlreadyExists();
        else
            arenas.put(name, arena);
    }

    public void joinArena (Player player, Arena arena){
        
    }

    public Arena getArenaByName(String name){
        return null;
    }

}
