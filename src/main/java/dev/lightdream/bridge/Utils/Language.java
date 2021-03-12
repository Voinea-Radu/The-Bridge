package dev.lightdream.bridge.Utils;

import dev.lightdream.bridge.Enums.LoadFileType;
import org.bukkit.configuration.file.FileConfiguration;

@SuppressWarnings("unused")
public class Language {

    public static FileConfiguration lang;

    public static String must_be_ingame;
    public static String no_permission_command;
    public static String wrong_usage_command;
    public static String error_creating_file;
    public static String arena_name_already_exists;
    public static String arena_is_full;
    public static String already_in_game;
    public static String not_enough_players;
    public static String default_kit_set;
    public static String set_waiting_location;
    public static String add_player_spawn_location;
    public static String setup_done;
    public static String setup_done_command;
    public static String not_the_creator;
    public static String arena_does_not_exist;
    public static String joined_arena;

    public static void loadLang(String language){

        lang = API.loadFile(language + ".yml", LoadFileType.DEFAULT);

        must_be_ingame              = lang.getString("must_be_ingame");
        no_permission_command       = lang.getString("no_permission_command");
        wrong_usage_command         = lang.getString("wrong_usage_command");
        error_creating_file         = lang.getString("error_creating_file");
        arena_name_already_exists   = lang.getString("arena_name_already_exists");
        arena_is_full               = lang.getString("arena_is_full");
        already_in_game             = lang.getString("already_in_game");
        not_enough_players          = lang.getString("not_enough_players");
        default_kit_set             = lang.getString("default_kit_set");
        set_waiting_location        = lang.getString("set_waiting_location")     .replace("{command}", "/bridge setup set WaitingLocation");
        add_player_spawn_location   = lang.getString("add_player_spawn_location").replace("{command}", "/bridge setup add PlayerSpawnLocation");
        setup_done                  = lang.getString("setup_done");
        setup_done_command          = lang.getString("setup_done_command");
        not_the_creator             = lang.getString("not_the_creator");
        arena_does_not_exist        = lang.getString("arena_does_not_exist");
        joined_arena                = lang.getString("joined_arena");

    }

    public static String getArenaStartingMessage(int time){
        return lang.getString(String.valueOf(time)).replace("{time}", String.valueOf(time));
    }

}
