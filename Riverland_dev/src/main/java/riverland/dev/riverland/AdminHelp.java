package riverland.dev.riverland;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;
import java.util.regex.Pattern;

public class AdminHelp implements CommandExecutor
{


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player)
        {

            Player player = (Player) sender;
            if (Riverland._InstanceRiverLandTicket.playerSubmittedTicketIssues.containsKey(player.getName())) // check if this user already exists in stored DB..
            {
                // unpack value..
                Integer currValue = Riverland._InstanceRiverLandTicket.playerSubmittedTicketIssues.get(player.getName());
                if (currValue >= (Riverland._InstanceRiverLandTicket.maxIssuesPerPlayer)) // Compare our limit to whats already on the Server.. .
                {
                    // no..
                    player.sendMessage(ChatColor.GREEN + "You've reached your Ticket limit, please wait for an Admin to assist with the other tickets first."); // write message to player..
                    return true;
                }else{
                    Riverland._InstanceRiverLandTicket.playerSubmittedTicketIssues.merge(player.getName(), 1, (prev, one) -> prev + one);
                }
            }

            String msgString = "";
            String pattern = "[^a-zA-Z0-9]"; // pattern for regex..
            for (String str : args)
            {
                msgString += " " + str.replaceAll(pattern,""); // add args as words..
            }

            float x = player.getLocation().getBlock().getX();
            float y = player.getLocation().getBlock().getY();
            float z = player.getLocation().getBlock().getZ();


            msgString += " Reported at BlockPos: (" + x +"," + y + "," + z + ") in world:"+player.getWorld().getName(); // store blockpos
            // reported at blockpos: (0.0,0.0,0.0)
            // 24 to first bracket > 35 are positions
            Riverland._InstanceRiverLandTicket.WriteTicketToServer(player, msgString); // store ticket..

            player.sendMessage(ChatColor.GREEN + "Help is on it's way!"); // write message to player..

        }

        return true;
    }
}

