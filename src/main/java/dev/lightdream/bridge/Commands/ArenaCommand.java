package dev.lightdream.bridge.Commands;

@SuppressWarnings("unused")
public class ArenaCommand extends BaseCommand{

    public ArenaCommand() {
        forcePlayer = true;
        commandName = "arena";
        argLength = 0;
    }

    @Override
    public boolean run(){

        return false;
    }

}
