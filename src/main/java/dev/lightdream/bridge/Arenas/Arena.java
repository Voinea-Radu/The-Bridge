package dev.lightdream.bridge.Arenas;

import dev.lightdream.bridge.Bridge;

import java.util.List;
import java.util.UUID;

@SuppressWarnings("unused")
public class Arena {

    //Generic
    private final Bridge INSTANCE;
    private final String language;

    //Game Specific
    private final String gameName;
    private List<UUID> players;




    public Arena(Bridge instance, String language, String gameName) {
        this.INSTANCE = instance;
        this.language = language;
        this.gameName = gameName;
    }


}
