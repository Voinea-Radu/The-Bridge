package dev.lightdream.bridge.Commands;

import dev.lightdream.bridge.Enums.LoadFileType;
import dev.lightdream.bridge.Utils.API;
import dev.lightdream.bridge.Utils.Language;
import org.bukkit.configuration.file.FileConfiguration;

@SuppressWarnings("unused")
public class SetDefaultKitCommand extends BaseCommand{

    public SetDefaultKitCommand() {
        forcePlayer = true;
        commandName = "setDefaultKit";
        argLength = 0;
    }

    @Override
    public boolean run(){

        FileConfiguration inventory = API.loadFile("inventory.yml", LoadFileType.DEFAULT);
        inventory.set("inventory", player.getInventory().getContents());
        API.sendColoredMessage(player, Language.default_kit_set);

        return false;
    }

}
