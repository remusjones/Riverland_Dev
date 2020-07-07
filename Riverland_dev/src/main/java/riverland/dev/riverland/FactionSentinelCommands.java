package riverland.dev.riverland;

import com.massivecraft.factions.*;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.factions.perms.Role;
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
        // basic thrall checks..
        if (!(sender instanceof Player))
            return true;

        Player player = (Player)sender;
        if (args[0].equalsIgnoreCase("Unused"))
        {
            player.sendMessage("Unused Thralls: " + Riverland._Instance.getNPCFaction(player).storedNPCs);
            return true;
        }

        else if (args[0].equalsIgnoreCase("Used"))
        {
            NPCFaction npcFaction = Riverland._Instance.getNPCFaction(player);
            player.sendMessage("Used Thralls: " + (npcFaction.NPCCount - npcFaction.storedNPCs));
            return true;
        }else if (args[0].equalsIgnoreCase("Buy"))
        {
            NPCFaction npcFaction = Riverland._Instance.getNPCFaction(player);
            FPlayer tmpPlayer = FPlayers.getInstance().getByPlayer(player);
            if (tmpPlayer.getRole().isAtLeast(Role.MODERATOR)) {
                if (!npcFaction.Purchase(player)) {
                    player.sendMessage("Your Faction cannot afford that.");
                }
            }
            else
                player.sendMessage("You cannot do this for your faction, only Faction Moderator and above have access to this command.");
            return true;
        }
        else if (args[0].equalsIgnoreCase("Spawn"))
        {
            NPCFaction npcFaction = Riverland._Instance.getNPCFaction(player);
            FPlayer tmpPlayer = FPlayers.getInstance().getByPlayer(player);
            FLocation flocation = new FLocation(player.getLocation());
            Faction faction = Board.getInstance().getFactionAt(flocation);
            if (faction == tmpPlayer.getFaction() || faction.isWilderness())
            {
                boolean success = npcFaction.SpawnSentinel(player.getLocation());
                if (success)
                    player.sendMessage("Thrall Spawned");
                else
                    player.sendMessage("No Thralls in reserve");
            }
            return true;
        }


        NPC selected = CitizensAPI.getDefaultNPCSelector().getSelected(sender);
        if (selected == null)
        {
            sender.sendMessage("Please select an NPC");
            return true;
        }
        // check if npc is valid ..
        if (selected.getTrait(SentinelTrait.class) == null || selected.getTrait(RiverlandSentinel.class) == null)
        {
            sender.sendMessage("Please select a valid NPC");
            return true;
        }
        // check validity of selection..

        FPlayer factionPlayer = FPlayers.getInstance().getByPlayer(player);
        RiverlandSentinel riverlandSentinel = selected.getTrait(RiverlandSentinel.class);
        if (factionPlayer.getFaction() != Factions.getInstance().getByTag(riverlandSentinel.ownerFaction))
        {
            sender.sendMessage("This NPC Doesn't belong to your faction!");
            return true;
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
