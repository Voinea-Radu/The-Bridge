package dev.lightdream.bridge.Commands;

import com.google.common.base.Preconditions;
import dev.lightdream.bridge.Bridge;
import dev.lightdream.bridge.Listener.CommandListener;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused", "deprecation", "FieldCanBeLocal"})
public class CommandHandler {

    private final Bridge plugin;
    private final Map<String, BaseCommand> COMMANDS;

    public CommandHandler(Bridge plugin) {
        this.plugin = plugin;
        this.COMMANDS = new HashMap<>();
        loadCommands();
        registerPermissions();
        PluginCommand command = plugin.getCommand(Bridge.PLUGIN_MAIN_COMMAND);
        if (command != null) {
            command.setExecutor(new CommandListener(plugin, this));
        }
    }

    private void loadCommands() {
        registerCommand(ArenaCommand.class);
        registerCommand(SetDefaultKitCommand.class);
        registerCommand(SetupCommand.class);
        registerCommand(JoinCommand.class);
    }

    private void registerCommand(Class<? extends BaseCommand> cmdClass) {
        try {
            BaseCommand command = cmdClass.newInstance();
            COMMANDS.put(command.commandName, command);
            for(String alias : command.aliases){
                COMMANDS.put(alias, command);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void registerPermissions() {
        PluginManager pm = Bukkit.getPluginManager();
        COMMANDS.forEach((command, baseCmd) -> {
            String perm = Bridge.PLUGIN_PERMISSION + command;
            if (pm.getPermission(perm) == null) {
                Permission permission = new Permission(perm);
                permission.setDescription(String.format("Grants user access to " + Bridge.PLUGIN_NAME + " Command command '/" + Bridge.PLUGIN_MAIN_COMMAND + " %s'", command));
                permission.setDefault(baseCmd.permissionDefault);
                pm.addPermission(permission);
            }
        });
    }

    public boolean commandExists(String command) {
        return COMMANDS.containsKey(command);
    }


    public List<BaseCommand> getCommands() {
        return new ArrayList<>(COMMANDS.values());
    }

    public BaseCommand getCommand(String command) {
        Preconditions.checkArgument(COMMANDS.containsKey(command), Bridge.PLUGIN_NAME + "command does not exist: '/" + Bridge.PLUGIN_MAIN_COMMAND + " %s'", command);
        return COMMANDS.get(command);
    }

}
