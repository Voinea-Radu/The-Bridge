package dev.lightdream.bridge.Exceptions;

import dev.lightdream.bridge.Utils.Language;

public class ArenaIsFull extends Exception {

    public ArenaIsFull() {
        super(Language.arena_is_full);
    }

}
