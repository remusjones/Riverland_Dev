package riverland.dev.riverland;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import riverland.dev.riverland.Riverland;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
#
#
# MADE REDUNDANT KEEPING HERE FOR REFERENCE ONLY
#
#
 */
@Deprecated
public class DonatorEventCommands implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        if (args[0].equalsIgnoreCase("ThumbusEventDonator"))
        {
            if (sender.hasPermission("Riverland.ThumbusEventDonator"))
            {
                // check time..
                // fetch last timestamp from config..
                String lastTime = Riverland._Instance.config.getString("THUMBUS_LASTSTART");
                //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                try {
                    Date date1 = formatter.parse(lastTime);
                    // compare now to then
                    Date currDate = new Date();

                    //in milliseconds
                    long diff = currDate.getTime() - date1.getTime();

                    long diffSeconds = diff / 1000 % 60;
                    long diffMinutes = diff / (60 * 1000) % 60;
                    long diffHours = diff / (60 * 60 * 1000) % 24;
                    long diffDays = diff / (24 * 60 * 60 * 1000);


                    if (diffDays > 0)
                    {
                        Riverland._Instance.getServer().dispatchCommand(Riverland._Instance.getServer().getConsoleSender(), "eventmanager start thumbus");
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Riverland._Instance.config.set("THUMBUS_LASTSTART", dateFormat.format(currDate));
                        Riverland._Instance.saveConfig();
                    }else if (diffHours > Riverland._Instance.getConfig().getInt("THUMBUS_DONATION_COOLDOWN"))
                    {
                        Riverland._Instance.getServer().dispatchCommand(Riverland._Instance.getServer().getConsoleSender(), "eventmanager start thumbus");
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Riverland._Instance.config.set("THUMBUS_LASTSTART", dateFormat.format(currDate));
                        Riverland._Instance.saveConfig();

                    }else {
                        sender.sendMessage("You have to wait " + (Riverland._Instance.getConfig().getInt("THUMBUS_DONATION_COOLDOWN") - diffHours) + " more hours!");
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else
            {
                sender.sendMessage("You don't have access to this command!");
            }
        }else if (args[0].equalsIgnoreCase("PVPEventDonator"))
        {
            if (sender.hasPermission("Riverland.PVPEventDonator"))
            {
                // check time..
                // fetch last timestamp from config..
                String lastTime = Riverland._Instance.config.getString("PVP_LASTSTART");
                //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                try {
                    Date date1 = formatter.parse(lastTime);
                    // compare now to then
                    Date currDate = new Date();

                    //in milliseconds
                    long diff = currDate.getTime() - date1.getTime();

                    long diffSeconds = diff / 1000 % 60;
                    long diffMinutes = diff / (60 * 1000) % 60;
                    long diffHours = diff / (60 * 60 * 1000) % 24;
                    long diffDays = diff / (24 * 60 * 60 * 1000);


                    if (diffDays > 0)
                    {
                        Riverland._Instance.getServer().dispatchCommand(Riverland._Instance.getServer().getConsoleSender(), "PVPArena start");
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Riverland._Instance.config.set("PVP_LASTSTART", dateFormat.format(currDate));
                        Riverland._Instance.saveConfig();
                    }else if (diffHours > Riverland._Instance.getConfig().getInt("PVP_DONATION_COOLDOWN"))
                    {
                        Riverland._Instance.getServer().dispatchCommand(Riverland._Instance.getServer().getConsoleSender(), "PVPArena start");
                        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                        Riverland._Instance.config.set("PVP_LASTSTART", dateFormat.format(currDate));
                        Riverland._Instance.saveConfig();

                    }else {
                        sender.sendMessage("You have to wait " + (Riverland._Instance.getConfig().getInt("PVP_DONATION_COOLDOWN") - diffHours) + " more hours!");
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }else
            {
                sender.sendMessage("You don't have access to this command!");
            }
        }
        if (args.length > 0)
            sender.sendMessage("Unknown command: " + args[0]);
        return false;
    }
}
