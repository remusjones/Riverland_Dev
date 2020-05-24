package riverland.dev.riverland;

import net.md_5.bungee.api.chat.ClickEvent;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;
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

            if (args[0].equalsIgnoreCase("Display")) // compare against /display command
            {
                if (sender instanceof Player) // get instance of player.
                {
                    Player player = (Player)sender; // cast player
                    if (Riverland._InstanceRiverLandTicket.storedTicketIssues.size() > 0) { // check if the server has updated the list from the DB..
                        player.sendMessage(ChatColor.GREEN + "Displaying latest Ticket Dump."); // if yes, display an init string
                        // dump ticket info here..
                        Set<Map.Entry<Integer, String>> map = Riverland._InstanceRiverLandTicket.storedTicketIssues.entrySet(); // get entry point for our stored map
                        player.sendMessage(ChatColor.RED.toString() + "______________________________________"); // add seperator string
                        for (Map.Entry<Integer, String> pair : map)
                        {
<<<<<<< HEAD
                            player.sendMessage("----------------------");
=======
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                            net.md_5.bungee.api.chat.TextComponent id = new net.md_5.bungee.api.chat.TextComponent(pair.getKey().toString()); // get id
                            id.setColor(net.md_5.bungee.api.ChatColor.RED); // set red..

                            net.md_5.bungee.api.chat.TextComponent strSeperator = new net.md_5.bungee.api.chat.TextComponent(" | "); // put seperator
                            strSeperator.setColor(net.md_5.bungee.api.ChatColor.GREEN); // set green

                            String targetString = pair.getValue();
                            Integer worldStart = pair.getValue().lastIndexOf("BlockPos:") + 9;
                            if (!((Player)sender).isOp())
                            {
                                targetString = StringUtils.left(targetString, worldStart);
                                targetString += "[Redacted]";
                            }


                            net.md_5.bungee.api.chat.TextComponent nameMessageCombo = new net.md_5.bungee.api.chat.TextComponent(targetString); // get pair message
                            nameMessageCombo.setColor(net.md_5.bungee.api.ChatColor.GOLD); // set Gold

                            net.md_5.bungee.api.chat.TextComponent clickable1 = new net.md_5.bungee.api.chat.TextComponent("  [Click to Remove]"); // add inline text button text
                            clickable1.setColor(net.md_5.bungee.api.ChatColor.AQUA); // set to aqua
                            clickable1.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/opadminhelp remove " + pair.getKey())); // hook event to the inline text button

                            net.md_5.bungee.api.chat.TextComponent clickable2 = new net.md_5.bungee.api.chat.TextComponent("  [Click to Teleport]"); // add inline text button text
                            clickable2.setColor(net.md_5.bungee.api.ChatColor.AQUA); // set color
                            // define tp positions
                            Double x = 0.0;
                            Double y = 0.0;
                            Double z = 0.0;
                            World targetWorld = null;

                            // try catch in-case it fails to parse positional coords and region.
                            try {

                                Integer start = pair.getValue().lastIndexOf('('); // find index of init bracket for pos
                                Integer end = pair.getValue().lastIndexOf(')'); // find end index of closing bracket for pos
                                worldStart = pair.getValue().lastIndexOf("world:"); // find index of the world: identifier
                                String split = pair.getValue().substring(start+1 , end-1); // offset the substr to ignore closing brackets.
                                String[] pos = split.split(","); // seperate positions by commas and store them as double if possible.
                                x = Double.parseDouble(pos[0]);
                                y = Double.parseDouble(pos[1]);
                                z = Double.parseDouble(pos[2]);

                               // targetWorld = Riverland._Instance.getServer().getWorld( pair.getValue().substring(worldStart+6 , pair.getValue().length())); // fetch world by name..

                            }
                            catch (Exception exc)
                            {
                                player.sendMessage(exc.toString());
                            }

                          //  String worldName = "overworld";

                            String substr = pair.getValue().substring(worldStart+6 , pair.getValue().length());

                            clickable2.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/riverland teleport" + " " + substr +" " +  x + " " + y + " " + z)); // set click event to text component
<<<<<<< HEAD
                            net.md_5.bungee.api.chat.TextComponent id2 = new net.md_5.bungee.api.chat.TextComponent();
=======

>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                            // compile json string
                            id.addExtra(strSeperator);
                            id.addExtra(nameMessageCombo);
                            if (((Player)sender).isOp())
                            {
<<<<<<< HEAD

                                id2.addExtra(clickable2);
                                id2.addExtra(clickable1);
                            }
                            player.sendMessage(id); // output json string
                            player.sendMessage(id2);
                            player.sendMessage("----------------------");
=======
                                id.addExtra(clickable2);
                                id.addExtra(clickable1);
                            }
                            player.sendMessage(id); // output json string

>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                        }
                        player.sendMessage(ChatColor.RED.toString() + "______________________________________");
                    }
                    else{
                        player.sendMessage(ChatColor.GREEN + "No Tickets Available.");
                    }
                    // end ticket dump..
                }
            }else if (args[0].equalsIgnoreCase("Remove") && ((Player)sender).isOp() )
            {
                if (args.length > 1)
                {
                    if (tryParseInt(args[1]))
                    {
                        Player player = (Player)sender;

                        Integer target = Integer.parseInt(args[1]);
                        Riverland._InstanceRiverLandTicket.removeDataBaseIssueID.add(target);
                        player.sendMessage(ChatColor.GREEN+"Removing Ticket Issue: " + ChatColor.DARK_RED+target);


                        for(Player plr : Bukkit.getServer().getOnlinePlayers())
                        {
                            if (plr != player)
                            {
<<<<<<< HEAD
                                if (plr.isOp())
                                    plr.sendMessage( ChatColor.AQUA + player.getName() + " Is "+ChatColor.GREEN+"Removing Ticket Issue: " + ChatColor.DARK_RED+target);
=======
                                plr.sendMessage( ChatColor.AQUA + player.getName() + " Is "+ChatColor.GREEN+"Removing Ticket Issue: " + ChatColor.DARK_RED+target);
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                            }
                        }
                    }
                }
            }else if (args[0].equalsIgnoreCase("RemoveALL") && ((Player)sender).isOp())
            {
                Player player = (Player)sender;
                // remove all entries..
                Set<Map.Entry<Integer, String>> map = Riverland._InstanceRiverLandTicket.storedTicketIssues.entrySet();
                for (Map.Entry<Integer, String> pair : map)
                {
                    Riverland._InstanceRiverLandTicket.removeDataBaseIssueID.add(pair.getKey());
                }
                player.sendMessage(ChatColor.GREEN.toString() + "Removed all Ticket entries.");


                for(Player plr : Bukkit.getServer().getOnlinePlayers())
                {
                    if (plr != player)
                    {
                        plr.sendMessage( ChatColor.AQUA + player.getName() + " Is "+ChatColor.GREEN+"Removing All Tickets.");
                    }
                }
            }


        }

        return true;
    }
}
