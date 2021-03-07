package dev.lightdream.bridge.Exceptions;

import dev.lightdream.bridge.Utils.Language;

public class NotInArena extends Exception {

    public NotInArena() {
        super(Language.arena_is_full);
    }

}
