package dev.lightdream.bridge.Listener;

import dev.lightdream.bridge.Arenas.Arena;
import dev.lightdream.bridge.Bridge;
import dev.lightdream.bridge.Exceptions.NotInArena;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPortalEnterEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

public class EventListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){
        Player player = event.getEntity().getPlayer();
        try {
            Bridge.arenaManager.getArenaOfPlayer(player).onDeath(player);
        } catch (NotInArena ignored) { }
    }

    @EventHandler
    public void onPlayerEnterPortal(EntityPortalEnterEvent event){
        Entity entity = event.getEntity();

        if(entity instanceof Player){
            Player player = (Player) entity;

            try {
                Bridge.arenaManager.getArenaOfPlayer(player).onScore(player);
            } catch (NotInArena ignored) { }
        }

    }

}
