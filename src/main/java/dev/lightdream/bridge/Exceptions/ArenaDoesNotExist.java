package dev.lightdream.bridge.Exceptions;

import dev.lightdream.bridge.Utils.Language;

@SuppressWarnings("unused")
public class ArenaDoesNotExist extends Exception {

    public ArenaDoesNotExist() {
        super(Language.arena_does_not_exist);
    }

}
