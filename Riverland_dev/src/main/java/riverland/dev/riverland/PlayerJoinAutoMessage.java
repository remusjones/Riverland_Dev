package riverland.dev.riverland;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class PlayerJoinAutoMessage implements Listener
{
    private String welcome;
    Riverland pluginReference;
    public PlayerJoinAutoMessage(String welcomeMessage, Riverland pluginRef)
    {
        welcome = welcomeMessage;
        pluginReference = pluginRef;
    }

    // This method checks for incoming players and sends them a message
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(welcome);
        pluginReference.getLogger().log(Level.INFO, "Sending Player: " + player.getDisplayName() + " A welcome message..");

    }
}
