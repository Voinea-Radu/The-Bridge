package dev.lightdream.bridge.Exceptions;

import dev.lightdream.bridge.Utils.Language;

@SuppressWarnings("unused")
public class ArenaAlreadyExists extends Exception {

    public ArenaAlreadyExists() {
        super(Language.arena_name_already_exists);
    }

}
