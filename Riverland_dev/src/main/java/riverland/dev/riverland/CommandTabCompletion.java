package riverland.dev.riverland;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class CommandTabCompletion implements TabCompleter
{

    String pickup = "pickup";
    String Reload = "reload";
    String TNTSmall = "tntsmall";
    String TNTMedium = "tntmedium";
    String TNTLarge = "tntlarge";
    String TNTBuster = "tntbuster";
    String CustomTNT1 = "customtnt1";
    String CustomTNT2 = "customtnt2";
    String CustomTNT3 = "customtnt3";
    String CustomTNT4 = "customtnt4";
    String ObsidianBreakChance = "obsidianbreakchance";
    String TNTIgnoreWater = "tntignorewater";
    String Display = "display";
    String Remove = "remove";
    String RemoveAll = "removeall";
    String ThumbusSpawn = "setthumbusspawn";
    String ThumbusBoss = "setthumbusboss";

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        List<String> l = new ArrayList<>();
        // riverland commands..
        if(command.getName().equalsIgnoreCase("riverland") && args.length >= 0)
        {
            if (args.length == 1)
            {
                if (ThumbusSpawn.contains(args[0].toLowerCase()))
                {
                    l.add("setThumbusSpawn");
                }
                if (ThumbusBoss.contains(args[0].toLowerCase()))
                {
                    l.add("setThumbusBoss");
                }
                if (pickup.contains(args[0].toLowerCase()))
                    l.add("Pickup");
                if (TNTIgnoreWater.contains(args[0].toLowerCase()))
                {
                    l.add("TNTIgnoreWater");
                }
                if (Reload.contains(args[0].toLowerCase()))
                    l.add("Reload");

                if (TNTSmall.contains(args[0].toLowerCase()))
                    l.add("TNTSmall");

                if (TNTMedium.contains(args[0].toLowerCase()))
                    l.add("TNTMedium");

                if (TNTLarge.contains(args[0].toLowerCase()))
                    l.add("TNTLarge");

                if ( TNTBuster.contains(args[0].toLowerCase()))
                    l.add("TNTBuster");

                if (CustomTNT1.contains(args[0].toLowerCase()))
                    l.add("CustomTNT1");

                if (CustomTNT2.contains(args[0].toLowerCase()))
                    l.add("CustomTNT2");

                if (CustomTNT3.contains(args[0].toLowerCase()))
                    l.add("CustomTNT3");

                if (CustomTNT4.contains(args[0].toLowerCase()))
                    l.add("CustomTNT4");

                if (ObsidianBreakChance.contains(args[0].toLowerCase()))
                    l.add("ObsidianBreakChance");
            }
            if (args.length == 2)
            {
                if (args[0].equalsIgnoreCase("TNTSmall"))
                {
                    l.add("Size");
                }
                else if (args[0].equalsIgnoreCase("TNTMedium"))
                {
                    l.add("Size");
                }
                else if (args[0].equalsIgnoreCase("TNTLarge"))
                {
                    l.add("Size");
                }
                else if (args[0].equalsIgnoreCase("TNTBuster"))
                {
                    l.add("Size");
                } else if (args[0].equalsIgnoreCase("ObsidianBreakChance"))
                {
                    l.add("chance [float]");
                }
                if (args[0].equalsIgnoreCase("TNTIgnoreWater"))
                {
                    l.add("True|False");
                }

            }
        }else if (command.getName().equalsIgnoreCase("opadminhelp")&& args.length >= 0)
        {
            if (args.length == 1)
            {


                if (Display.contains(args[0].toLowerCase()))
                    l.add("Display");
                if (Remove.contains(args[0].toLowerCase()))
                    l.add("Remove");
                if (RemoveAll.contains(args[0].toLowerCase()))
                    l.add("RemoveAll");

            }else if (args.length == 2 && args[0].equalsIgnoreCase("remove"))
            {
                l.add("ID");
            }
        }else if (command.getName().equalsIgnoreCase("adminhelp")&& args.length >= 0)
        {
            if (args.length == 1)
            {
                l.add("Message");
            }
        }

        return l;
    }
}
