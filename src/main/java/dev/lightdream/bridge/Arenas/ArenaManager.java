package dev.lightdream.bridge.Arenas;

import dev.lightdream.bridge.Exceptions.AlreadyInGame;
import dev.lightdream.bridge.Exceptions.ArenaAlreadyExists;
import dev.lightdream.bridge.Exceptions.ArenaIsFull;
import dev.lightdream.bridge.Exceptions.NotInArena;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ArenaManager {

    private HashMap<String, Arena> arenas = new HashMap<>();
    private HashMap<Player, Arena> playerMap = new HashMap<>();

    public void registerArena(String name, Arena arena) throws ArenaAlreadyExists {
        if(arenas.containsKey(name))
            throw new ArenaAlreadyExists();
        else
            arenas.put(name, arena);
    }

    public void joinArena (Player player, String name) throws ArenaIsFull, AlreadyInGame {
        if(playerMap.containsKey(player))
            throw new AlreadyInGame();

        arenas.get(name).join(player);
        playerMap.put(player, getArenaByName(name));
    }

    public Arena getArenaByName(String name){
        return arenas.get(name);
    }

    public void leaveArena(Player player) throws NotInArena {
        if(!playerMap.containsKey(player))
            throw new NotInArena();

        playerMap.get(player).leave(player);
        playerMap.remove(player);
    }

}
