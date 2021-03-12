package dev.lightdream.bridge.Arenas;

import dev.lightdream.bridge.Exceptions.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ArenaManager {

    private HashMap<String, Arena> arenas = new HashMap<>();
    private HashMap<Player, Arena> playerMap = new HashMap<>();

    public HashMap<String, Arena> getArenasHashMap() {
        return arenas;
    }

    public List<Arena> getArenasList() {
        List<Arena> output = new ArrayList<>();

        for(String key : arenas.keySet())
            output.add(arenas.get(key));

        return output;
    }

    public HashMap<Player, Arena> getPlayerMap() {
        return playerMap;
    }

    public void registerArena(String name, Arena arena) throws ArenaAlreadyExists {
        if(arenas.containsKey(name))
            throw new ArenaAlreadyExists();
        else
            arenas.put(name, arena);
    }

    public void joinArena (Player player, String name) throws ArenaIsFull, AlreadyInGame, ArenaDoesNotExist {
        if(isInGame(player))
            throw new AlreadyInGame();

        if(!arenas.containsKey(name))
            throw new ArenaDoesNotExist();
        arenas.get(name).join(player);
        playerMap.put(player, getArenaByName(name));
    }

    public Arena getArenaByName(String name){
        return arenas.get(name);
    }

    public void leaveArena(Player player) throws NotInArena {
        if(!isInGame(player))
            throw new NotInArena();

        playerMap.get(player).leave(player);
        playerMap.remove(player);
    }

    public void registerArena(List<String> namesList, List<Arena> arenasList) throws ArenaAlreadyExists {
        if(namesList == null || arenasList == null)
            return;
        if(namesList.size()!=arenasList.size())
            return;

        for(int i=0;i<namesList.size();i++){

            String name = namesList.get(i);
            Arena arena = arenasList.get(i);

            if(arenas.containsKey(name))
                throw new ArenaAlreadyExists();
            else
                arenas.put(name, arena);

        }

    }
    
    public boolean isInGame(Player player){
        return playerMap.containsKey(player);
    }

    public Arena getArenaOfPlayer(Player player) throws NotInArena {
        if(isInGame(player))
            return playerMap.get(player);
        throw new NotInArena();
    }

}
