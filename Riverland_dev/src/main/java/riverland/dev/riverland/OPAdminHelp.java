package riverland.dev.riverland;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.Set;

public class OPAdminHelp implements CommandExecutor
{
    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        // check args..
        if (args.length > 0)
        {
            // get first arg
            //if (args.length > 1)
            {
                if (args[0].equalsIgnoreCase("Display"))
                {
                    if (sender instanceof Player)
                    {
                        Player player = (Player)sender;
                        player.sendMessage("Displaying latest Ticket Dump..");
                        // dump ticket info here..
                        Set<Map.Entry<Integer, String>> map = Riverland._InstanceRiverLandTicket.storedTicketIssues.entrySet();
                        for (Map.Entry<Integer, String> pair : map)
                        {
                            player.sendMessage(pair.getKey() + " | " + pair.getValue());
                        }
                        // end ticket dump..
                    }
                }else if (args[0].equalsIgnoreCase("Remove"))
                {
                    if (args.length > 1)
                    {
                        if (tryParseInt(args[1]))
                        {
                            Player player = (Player)sender;
                            Integer target = Integer.parseInt(args[1]);
                            Riverland._InstanceRiverLandTicket.removeDataBaseIssueID.add(target);
                            player.sendMessage("Removing Ticket Issue: " + target);
                        }
                    }
                }
            }

        }





        return true;
    }
}
