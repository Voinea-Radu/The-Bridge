package dev.lightdream.bridge.Commands;

import dev.lightdream.bridge.Bridge;
import dev.lightdream.bridge.Exceptions.AlreadyInGame;
import dev.lightdream.bridge.Exceptions.ArenaDoesNotExist;
import dev.lightdream.bridge.Exceptions.ArenaIsFull;
import dev.lightdream.bridge.Utils.API;
import dev.lightdream.bridge.Utils.Language;

public class JoinCommand extends BaseCommand{

    public JoinCommand() {
        forcePlayer = true;
        commandName = "join";
        argLength = 1;
        usage = "<name>";
    }

    @Override
    public boolean run(){

        try {
            Bridge.arenaManager.joinArena(player, args[1]);
            API.sendColoredMessage(player, Language.joined_arena);
        } catch (ArenaIsFull | AlreadyInGame | ArenaDoesNotExist e) {
            API.sendColoredMessage(player, e.getMessage());
        }

        return false;
    }
}
