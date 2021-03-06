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

    public static void loadLang(String language){

        lang = API.loadFile(language + ".yml", LoadFileType.DEFAULT);

        must_be_ingame              = lang.getString("must_be_ingame");
        no_permission_command       = lang.getString("no_permission_command");
        wrong_usage_command         = lang.getString("wrong_usage_command");
        error_creating_file         = lang.getString("error_creating_file");
        arena_name_already_exists   = lang.getString("arena_name_already_exists");

    }

}
