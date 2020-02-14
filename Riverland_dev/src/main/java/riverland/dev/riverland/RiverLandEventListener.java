package riverland.dev.riverland;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.awt.*;


public class RiverLandEventListener implements Listener
{


    // This method checks for incoming players and sends them a message
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.isOp())
        {
            // check if tickting is running..
            if (Riverland._InstanceRiverLandTicket._IsTaskRunning)
            {
                int size  =  Riverland._InstanceRiverLandTicket.storedTicketIssues.size();
                if (size > 0) {

                    net.md_5.bungee.api.chat.TextComponent msg = new net.md_5.bungee.api.chat.TextComponent("There are " + Riverland._InstanceRiverLandTicket.storedTicketIssues.size() + " Tickets Awaiting Completion.");
                    msg.setColor(net.md_5.bungee.api.ChatColor.GOLD);

                    net.md_5.bungee.api.chat.TextComponent msg2 = new net.md_5.bungee.api.chat.TextComponent("  [Click to View]");
                    msg2.setColor(net.md_5.bungee.api.ChatColor.AQUA);
                    msg2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/opadminhelp display")); // if the user clicks the message, tp them to the coords

                    msg.addExtra(msg2);


                    player.sendMessage(msg);
                }
                else
                {
                    net.md_5.bungee.api.chat.TextComponent msg = new net.md_5.bungee.api.chat.TextComponent("There are " + Riverland._InstanceRiverLandTicket.storedTicketIssues.size() + " Tickets Awaiting Completion.");
                    msg.setColor(net.md_5.bungee.api.ChatColor.GREEN);

                  //  msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/opadminhelp display")); // if the user clicks the message, tp them to the coords
                    player.sendMessage(msg);
                }
            }
        }
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Block pos = event.getEntity().getLocation().getBlock();
        String message = ChatColor.RED + "Oops, you've died at: " + ChatColor.GOLD.toString() + "(X = " + pos.getX() + "," + " Y = " + pos.getY() + ", Z = " + pos.getZ() + ")";
        event.getEntity().sendMessage(message);


        // loop players on server..
        for(Player player : Bukkit.getOnlinePlayers())
        {
            if (player.isOp()) // check if player is operator
            {
                net.md_5.bungee.api.chat.TextComponent message2 = new net.md_5.bungee.api.chat.TextComponent( "Player " + event.getEntity().getName()  + " Has Died  " );
                message2.setColor( net.md_5.bungee.api.ChatColor.GOLD );

                net.md_5.bungee.api.chat.TextComponent click = new net.md_5.bungee.api.chat.TextComponent( " [Click to Teleport]" );


                String worldName = "overworld";

                if (event.getEntity().getPlayer().getWorld().getEnvironment().equals(World.Environment.NORMAL))
                {
                    worldName = "overworld";
                }else if (event.getEntity().getPlayer().getWorld().getEnvironment().equals(World.Environment.THE_END))
                {
                    worldName = "the_end";
                }else if (event.getEntity().getPlayer().getWorld().getEnvironment().equals(World.Environment.NETHER))
                {
                    worldName = "the_nether";
                }


                click.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/execute in " + worldName + " run tp " + player.getName() + " " + pos.getX() + " " + pos.getY() + " " + pos.getZ())); // if the user clicks the message, tp them to the coords
                //click.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/teleport " + player.getName() + " " +  pos.getX() + " " + pos.getY() + " " + pos.getZ())); // if the user clicks the message, tp them to the coords
                click.setColor( net.md_5.bungee.api.ChatColor.AQUA );
                message2.addExtra( click );

                player.sendMessage(message2);

            }
        }
    }
}
