package dev.lightdream.bridge.Exceptions;

import dev.lightdream.bridge.Utils.Language;

public class AlreadyInGame extends Exception {

    public AlreadyInGame() {
        super(Language.arena_name_already_exists);
    }

}
