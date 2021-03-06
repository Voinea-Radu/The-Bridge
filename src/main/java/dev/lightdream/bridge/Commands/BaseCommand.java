package dev.lightdream.bridge.Commands;

import dev.lightdream.bridge.Bridge;
import dev.lightdream.bridge.Utils.API;
import dev.lightdream.bridge.Utils.Language;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;

import java.util.ArrayList;

@SuppressWarnings({"unused", "UnusedReturnValue"})
public abstract class BaseCommand {

    Bridge plugin;
    PermissionDefault permissionDefault = PermissionDefault.OP;

    public BaseCommand() {
        this.plugin = Bridge.INSTANCE;
    }

    public CommandSender sender;
    public String[] args;
    public String commandName;
    public int argLength = 0;
    public boolean forcePlayer = true;
    public String usage = "";
    public ArrayList<String> aliases = new ArrayList<>();
    public Player player;

    public boolean processCmd(Bridge plugin, CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;

        if (forcePlayer) {
            if (!(sender instanceof Player)) {
                API.sendColoredMessage(sender, Language.must_be_ingame);
                return false;
            }
            else player = (Player) sender;
        }
        if (!hasPermission(sender))
            API.sendColoredMessage(sender, Language.no_permission_command);
        else if (argLength >= args.length)
            API.sendColoredMessage(sender, Language.wrong_usage_command + " " + sendHelpLine());
        else{
            return run();
        }

        return true;
    }

    public abstract boolean run();

    public String sendHelpLine() {
        String usage = this.usage.replaceAll("<", "&7&l<&f").replaceAll(">", "&7&l>");
        return String.format("&3&l/bridge &b%s %s", commandName, usage);
    }

    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(Bridge.PLUGIN_PERMISSION + commandName);
    }

}
