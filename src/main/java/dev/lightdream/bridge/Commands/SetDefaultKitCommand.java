package dev.lightdream.bridge.Commands;

import dev.lightdream.bridge.Enums.LoadFileType;
import dev.lightdream.bridge.Utils.API;
import dev.lightdream.bridge.Utils.Language;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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
        FileConfiguration armour = API.loadFile("armour.yml", LoadFileType.DEFAULT);


        // [!] DEBUGGING [!]
        //player.getInventory().setContents(inventory.getList("inventory").toArray(new ItemStack[0]));
        //player.getInventory().setArmorContents(inventory.getList("armour").toArray(new ItemStack[0]));
        // [!] DEBUGGING [!]


        inventory.set("inventory", player.getInventory().getContents());
        armour.set("armour", player.getInventory().getArmorContents());
        API.saveFile(inventory, "inventory.yml");
        API.saveFile(inventory, "armour.yml");
        API.sendColoredMessage(player, Language.default_kit_set);

        return false;
    }

}
