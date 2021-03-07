package dev.lightdream.bridge.Arenas;

import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sun.tools.javac.file.Locations;
import dev.lightdream.bridge.Bridge;
import dev.lightdream.bridge.Exceptions.ArenaIsFull;
import dev.lightdream.bridge.Utils.API;
import dev.lightdream.bridge.Utils.Language;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;

@SuppressWarnings({"unused", "FieldCanBeLocal", "deprecation", "unchecked", "FieldMayBeFinal"})
public class Arena {

    //Generic
    private final Bridge INSTANCE;
    private final String language;

    //Game Settings
    private final int maxPlayerNumber;
    private final int minPlayerNumber;
    private final int waitingLocation;
    private final List<Locations> spawnLocations;
    private final String gameName;
    private final ClipboardFormat schematic;

    //Game Specific
    private List<Player> players;

    //Internal Variables
    BukkitScheduler startTimer;

    public Arena(Bridge instance, String language, int maxPlayerNumber, int minPlayerNumber, int waitingLocation, List<Locations> spawnLocations, String gameName, ClipboardFormat schematic) {
        this.INSTANCE = instance;
        this.language = language;
        this.maxPlayerNumber = maxPlayerNumber;
        this.minPlayerNumber = minPlayerNumber;
        this.waitingLocation = waitingLocation;
        this.spawnLocations = spawnLocations;
        this.gameName = gameName;
        this.schematic = schematic;
    }

    public void join(Player player) throws ArenaIsFull{
        if(players.size()>maxPlayerNumber)
            throw new ArenaIsFull();

        //TODO: Tp to waiting room
        players.add(player);
        if(players.size()>minPlayerNumber)
            startCounter();
    }

    public void leave(Player player){
        players.remove(player);

        //TODO: Tp to spawn
        if(players.size()<minPlayerNumber)
            stopCounter();
    }

    public void startCounter(){
        startTimer = Bukkit.getServer().getScheduler();
        startTimer.runTaskTimer(Bridge.INSTANCE, new BukkitRunnable() {
            List<Integer> messageTimes = (List<Integer>) Bridge.config.getList("arena-start-message-times");
            private int counter = messageTimes.get(0);
            @Override
            public void run() {
                if(messageTimes.contains(counter))
                    broadcastMessage(Language.getArenaStartingMessage(counter));
                if(counter<=0)
                    start();
                counter--;
            }
        }, 0L, 20L);
    }

    public void stopCounter(){
        startTimer.cancelAllTasks();
        broadcastMessage(Language.not_enough_players);
    }

    public void broadcastMessage(String message){
        API.sendColoredMessage(players, message);
    }

    public void start(){
        //TODO: Load the schematic for that specific world (https://matthewmiller.dev/blog/how-to-load-and-save-schematics-with-the-worldedit-api/#register-a-custom-schematic-format)
        //TODO: Give inventories to players
        //TODO: TP players
        //TODO: Create the portal
    }


}
