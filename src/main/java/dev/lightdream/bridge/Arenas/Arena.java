package dev.lightdream.bridge.Arenas;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import dev.lightdream.bridge.Bridge;
import dev.lightdream.bridge.Enums.LoadFileType;
import dev.lightdream.bridge.Exceptions.ArenaIsFull;
import dev.lightdream.bridge.Utils.API;
import dev.lightdream.bridge.Utils.Language;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unused", "FieldCanBeLocal", "deprecation", "unchecked", "FieldMayBeFinal"})
public class Arena {

    //Generic
    private final Bridge INSTANCE;

    //Game Settings
    private final int maxPlayerNumber;
    private final int minPlayerNumber;
    private final Location waitingLocation;
    private final List<Location> spawnLocations;
    private final String gameName;
    private final int winPoints;
    private final String schematicName;
    private final Clipboard schematic;

    //Game Specific
    private List<Player> players = new ArrayList<>();

    //Internal Variables
    private BukkitScheduler startTimer;
    public List<Integer> score = new ArrayList<>();

    public Arena(Bridge instance, int maxPlayerNumber, int minPlayerNumber, Location waitingLocation, List<Location> spawnLocations, String gameName, int winPoints, String schematicName) {
        this.INSTANCE = instance;
        this.maxPlayerNumber = maxPlayerNumber;
        this.minPlayerNumber = minPlayerNumber;
        this.waitingLocation = waitingLocation;
        this.spawnLocations = spawnLocations;
        this.gameName = gameName;
        this.winPoints = winPoints;
        this.schematicName = schematicName;
        schematic = API.loadArenaSchematic(schematicName);
    }

    public void join(Player player) throws ArenaIsFull{
        if(players.size()>maxPlayerNumber)
            throw new ArenaIsFull();

        player.teleport(waitingLocation);
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

    private void startCounter(){
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

    private void stopCounter(){
        startTimer.cancelTask(0);
        broadcastMessage(Language.not_enough_players);
    }

    private void broadcastMessage(String message){
        API.sendColoredMessage(players, message);
    }

    private void start(){
        giveInventory(players);
        tpToSpawn(players);
    }

    private void end(){
        loadSchem();
    }

    public void onDeath(Player player){
        tpToSpawn(Collections.singletonList(player));
        giveInventory(Collections.singletonList(player));
    }

    public void onScore(Player player){
        int index = players.indexOf(player);
        score.set(index, score.get(index) + 1);
        giveInventory(players);
        tpToSpawn(players);
    }

    private void loadSchem(){
        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(BukkitAdapter.adapt(waitingLocation.getWorld()), -1)) {
            Operation operation = new ClipboardHolder(schematic)
                    .createPaste(editSession)
                    .to(BlockVector3.at(waitingLocation.getBlockX(), waitingLocation.getBlockY(), waitingLocation.getBlockZ()))
                    .ignoreAirBlocks(false)
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }

    private void tpToSpawn(List<Player> players){
        for(int i=0;i<players.size();i++)
            players.get(i).teleport(spawnLocations.get(i));
    }

    private void giveInventory(List<Player> players){
        for(Player player : players){
            FileConfiguration inventory = API.loadFile("inventory.yml", LoadFileType.DEFAULT);
            FileConfiguration armour = API.loadFile("armour.yml", LoadFileType.DEFAULT);

            player.getInventory().setContents(inventory.getList("inventory").toArray(new ItemStack[0]));
            player.getInventory().setArmorContents(inventory.getList("armour").toArray(new ItemStack[0]));
        }
    }


    public Bridge getINSTANCE() {
        return INSTANCE;
    }

    public int getMaxPlayerNumber() {
        return maxPlayerNumber;
    }

    public int getMinPlayerNumber() {
        return minPlayerNumber;
    }

    public Location getWaitingLocation() {
        return waitingLocation;
    }

    public List<Location> getSpawnLocations() {
        return spawnLocations;
    }

    public String getGameName() {
        return gameName;
    }

    public int getWinPoints() {
        return winPoints;
    }

    public String getSchematicName() {
        return schematicName;
    }

    public Clipboard getSchematic() {
        return schematic;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
