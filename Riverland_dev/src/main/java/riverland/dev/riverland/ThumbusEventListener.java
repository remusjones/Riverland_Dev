package riverland.dev.riverland;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ThumbusEventListener implements Listener
{

    ThumbusEvent thumbusEvent = null;

    ThumbusEventListener(ThumbusEvent event)
    {
        thumbusEvent = event;
    }
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent player)
    {
        if (thumbusEvent.activePlayers.contains(player.getPlayer()))
        {
            thumbusEvent.blackListedPlayers.add(player.getPlayer());
            thumbusEvent.activePlayers.remove(player.getPlayer());
            thumbusEvent.joinedPlayers.remove(player.getPlayer());
        }
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent player)
    {
        if (thumbusEvent.hasBegunRun == true && (thumbusEvent.activePlayers.contains(player.getEntity()) || thumbusEvent.joinedPlayers.containsKey(player.getEntity()))) {


            thumbusEvent.blackListedPlayers.add(player.getEntity());
            thumbusEvent.activePlayers.remove(player.getEntity());
            thumbusEvent.joinedPlayers.remove(player.getEntity());

            for (Player p : thumbusEvent.activePlayers) {
                net.md_5.bungee.api.chat.TextComponent message2 = new net.md_5.bungee.api.chat.TextComponent("Player " + player.getEntity().getName() + " Has been defeated");
                message2.setColor(net.md_5.bungee.api.ChatColor.DARK_RED);
                p.sendMessage(message2);

                if (thumbusEvent.activePlayers.size() <= 5) {
                    net.md_5.bungee.api.chat.TextComponent message3 = new net.md_5.bungee.api.chat.TextComponent((thumbusEvent.activePlayers.size()) + " Players Remain");
                    message3.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
                    p.sendMessage(message3);

                }
            }
        }
    }
}
