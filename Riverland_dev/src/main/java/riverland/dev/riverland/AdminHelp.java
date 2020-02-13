package riverland.dev.riverland;

import org.bukkit.Location;
import org.bukkit.Material;
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
            String msgString = "";
            String pattern = "[^a-zA-Z0-9]"; // pattern for regex..
            for (String str : args)
            {
                msgString += " " + str.replaceAll(pattern,""); // add args as words..
            }

            float x = player.getLocation().getBlock().getX();
            float y = player.getLocation().getBlock().getY();
            float z = player.getLocation().getBlock().getZ();

            msgString += " Reported at BlockPos: (" + x +"," + y + "," + z + ")"; // store blockpos
            Riverland._InstanceRiverLandTicket.WriteTicketToServer(player, msgString); // store ticket..
            player.sendMessage("Help is on it's way, message details: " + msgString); // write message to player..

        }

        return true;
    }
}

