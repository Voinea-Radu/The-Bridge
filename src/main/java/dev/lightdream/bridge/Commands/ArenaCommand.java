package dev.lightdream.bridge.Commands;

import dev.lightdream.bridge.Bridge;
import dev.lightdream.bridge.Utils.API;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public class ArenaCommand extends BaseCommand{

    public ArenaCommand() {
        forcePlayer = true;
        commandName = "arena";
        argLength = 1;
        usage = "[list]";
    }

    @Override
    public boolean run(){

        if(args[1].equalsIgnoreCase("list")){
            API.sendColoredMessage(player, Arrays.asList(Bridge.INSTANCE.arenaManager.getArenasHashMap().keySet().toArray()));
        }

        return false;
    }

}
