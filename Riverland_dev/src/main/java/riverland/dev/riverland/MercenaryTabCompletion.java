package riverland.dev.riverland;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MercenaryTabCompletion implements TabCompleter
{
    String Unused = "Unused";
    String Used = "Used";
    String Store = "Store";
    String Tphere = "Tphere";

    String Buy = "Buy";
    String Spawn = "Spawn";
    String Select = "Select";
    String Delete = "Delete";
    String Equip = "Equip";

    String Strip = "Strip";
    String Follow = "Follow";
    String Wait = "Wait";

    String Rename = "Rename";
    String Remove = "Remove";

    String Skin = "Skin";

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        List<String> l = new ArrayList<>();
        if (command.getName().equalsIgnoreCase("merc"))
        {
            if (args.length == 1)
            {
                String converted = args[0].toLowerCase();
                if (Unused.toLowerCase().contains(converted))
                {
                    l.add(Unused);
                }
                if (Skin.toLowerCase().contains(converted))
                {
                    l.add(Skin);
                }
                if (Used.toLowerCase().contains(converted))
                {
                    l.add(Used);
                }
                if (Store.toLowerCase().contains(converted))
                {
                    l.add(Store);
                }
                if (Tphere.toLowerCase().contains(converted))
                {
                    l.add(Tphere);
                }
                if (Buy.toLowerCase().contains(converted))
                {
                    l.add(Buy);
                }
                if (Spawn.toLowerCase().contains(converted))
                {
                    l.add(Spawn);
                }
                if (Select.toLowerCase().contains(converted))
                {
                    l.add(Select);
                }
                if (Delete.toLowerCase().contains(converted))
                {
                    l.add(Delete);
                }
                if (Equip.toLowerCase().contains(converted))
                {
                    l.add(Equip);
                }
                if (Select.toLowerCase().contains(converted))
                {
                    l.add(Select);
                }
                if (Strip.toLowerCase().contains(converted))
                {
                    l.add(Strip);
                }
                if (Follow.toLowerCase().contains(converted))
                {
                    l.add(Follow);
                }
                if (Wait.toLowerCase().contains(converted))
                {
                    l.add(Wait);
                }
                if (Rename.toLowerCase().contains(converted))
                {
                   l.add(Rename);
                }
                if (Remove.toLowerCase().contains(converted))
                {
                    l.add(Remove);
                }
            }if (args.length == 2)
            {
                String converted = args[0].toLowerCase();
                if (Rename.toLowerCase().contains(converted))
                {
                    l.add("<Name>");
                }
                else if (Remove.toLowerCase().contains(converted))
                {
                    l.add("#");
                }
                else if (Skin.toLowerCase().contains(converted))
                {
                    l.add("URL");
                }
            }



        }
        return l;
    }
}
