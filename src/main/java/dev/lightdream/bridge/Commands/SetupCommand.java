package dev.lightdream.bridge.Commands;

import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import dev.lightdream.bridge.Arenas.Arena;
import dev.lightdream.bridge.Bridge;
import dev.lightdream.bridge.Exceptions.ArenaAlreadyExists;
import dev.lightdream.bridge.Utils.API;
import dev.lightdream.bridge.Utils.Language;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SetupCommand extends BaseCommand{

    private String name;
    private int winPoints;
    private Player creator;
    private Location waitingLocation;
    private List<Location> spawnLocation = new ArrayList<>();
    private int maxPlayers = 2;
    private String schematicName;

    public SetupCommand() {
        forcePlayer = true;
        commandName = "setup";
        argLength = 1;
        usage = "<Name> <WinPoints> <SchematicName>";
    }

    @Override
    public boolean run(){

        if(creator == null){
            if(args.length <= 3){
                sendHelpLine();
                return true;
            }
            creator = player;
            name = args[1];
            winPoints = Integer.parseInt(args[2]);
            schematicName = args[3];
        }

        if(creator == player) {

            if(args[1].equalsIgnoreCase("cancel")){
                creator = null;
                return true;
            }
            if(args[1].equalsIgnoreCase("finish")){
                Arena arena = new Arena(Bridge.INSTANCE, maxPlayers, maxPlayers, waitingLocation, spawnLocation, name, winPoints, schematicName);
                try {
                    Bridge.arenaManager.registerArena(name, arena);
                } catch (ArenaAlreadyExists arenaAlreadyExists) {
                    API.sendColoredMessage(player, arenaAlreadyExists.getMessage());
                    return true;
                }
                API.sendColoredMessage(player, Language.setup_done);
                return true;
            }
            if(args.length > 2){
                if(args[1].equalsIgnoreCase("set") && args[2].equalsIgnoreCase("WaitingLocation"))
                    waitingLocation = player.getLocation();
                if(args[1].equalsIgnoreCase("add") && args[2].equalsIgnoreCase("PlayerSpawnLocation"))
                    spawnLocation.add(player.getLocation());
            }

            boolean commandsNotExecuted = false;
            if(waitingLocation == null){
                commandsNotExecuted = true;
                API.sendColoredMessage(player, Language.set_waiting_location);
            }
            if(spawnLocation.size() != maxPlayers){
                if(spawnLocation.size() == maxPlayers-1)
                    commandsNotExecuted = true;
                API.sendColoredMessage(player, Language.add_player_spawn_location.replace("{times}", String.valueOf(maxPlayers - spawnLocation.size())));
            }
            if(!commandsNotExecuted){
                API.sendColoredMessage(player, Language.setup_done_command);
            }
        }
        else {
            API.sendColoredMessage(player, Language.not_the_creator);
        }



        return false;
    }
}
