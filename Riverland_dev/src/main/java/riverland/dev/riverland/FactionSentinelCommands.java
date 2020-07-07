package riverland.dev.riverland;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mcmonkey.sentinel.SentinelPlugin;
import org.mcmonkey.sentinel.SentinelTrait;

public class FactionSentinelCommands  implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        NPC selected = CitizensAPI.getDefaultNPCSelector().getSelected(sender);


        if (selected == null)
        {
            sender.sendMessage("Please select an NPC");
            return false;
        }
        // check if npc is valid ..
        if (selected.getTrait(SentinelTrait.class) == null || selected.getTrait(RiverlandSentinel.class) == null)
        {
            sender.sendMessage("Please select a valid NPC");
            return false;
        }
        // check validity of selection..
        Player player = (Player)sender;
        FPlayer factionPlayer = FPlayers.getInstance().getByPlayer(player);
        RiverlandSentinel riverlandSentinel = selected.getTrait(RiverlandSentinel.class);
        if (factionPlayer.getFaction() != Factions.getInstance().getByTag(riverlandSentinel.ownerFaction))
        {
            sender.sendMessage("This NPC Doesn't belong to your faction!");
            return false;
        }
        SentinelTrait sentinel =  selected.getTrait(SentinelTrait.class);


        if (args[0].equalsIgnoreCase("equip"))
        {
            // remove from player hand
            riverlandSentinel.EquipItem(player, player.getInventory().getItemInMainHand());
        }
        else if (args[0].equalsIgnoreCase("strip"))
        {
            // remove all items, drop onto ground
            riverlandSentinel.Strip();
        }
        else if (args[0].equalsIgnoreCase("Follow"))
        {
            if (args.length > 1)
            {
                Player other = Riverland._Instance.getServer().getPlayer(args[1]);
                if (other != null)
                {
                    FPlayer otherFactionPlayer = FPlayers.getInstance().getByPlayer(other);
                    if (factionPlayer.getFaction() == otherFactionPlayer.getFaction())
                    {
                        sentinel.setGuarding(other.getUniqueId());
                        sentinel.sayTo(player, "I am now following " + other.getDisplayName());
                        sentinel.sayTo(other, player.getDisplayName() + " has commanded me to follow you");
                    }else {
                        sentinel.sayTo(player, "I won't follow anyone who isn't in our faction.");
                    }
                }else
                {
                    sentinel.setGuarding(player.getUniqueId());
                    sentinel.sayTo(player, "I don't know who that is, I'll follow you instead.");
                }

            }else {
                sentinel.setGuarding(player.getUniqueId());
                sentinel.sayTo(player, "Lead the way.");
            }

        }
        else if (args[0].equalsIgnoreCase("Wait"))
        {
            sentinel.sayTo(player, "I will wait here.");
            sentinel.pathTo(selected.getStoredLocation());
        }


        return true;
    }
}
