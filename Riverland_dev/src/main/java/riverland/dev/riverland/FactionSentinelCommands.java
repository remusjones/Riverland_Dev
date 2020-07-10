package riverland.dev.riverland;

import com.massivecraft.factions.*;
import com.massivecraft.factions.integration.Econ;
import com.massivecraft.factions.perms.Role;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.waypoint.LinearWaypointProvider;
import net.citizensnpcs.trait.waypoint.Waypoint;
import net.citizensnpcs.trait.waypoint.WaypointProvider;
import net.citizensnpcs.trait.waypoint.Waypoints;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.mcmonkey.sentinel.SentinelPlugin;
import org.mcmonkey.sentinel.SentinelTrait;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

public class FactionSentinelCommands  implements CommandExecutor
{
    public static double powerCostPerSentinel = 20;
    /**Handles "Merc" On Commands*/
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        String prefix = ChatColor.DARK_BLUE + "[" + ChatColor.GOLD+"Mercenaries"+ChatColor.DARK_BLUE+"] " +ChatColor.YELLOW;

        if (args.length == 0)
        {
            sender.sendMessage(prefix + "Incorrect Usage");
            return true;

        }
        if (!(sender instanceof Player))
            return true;

        Player player = (Player)sender;
        FPlayer tmpPlayer = FPlayers.getInstance().getByPlayer(player);
        if (tmpPlayer.getFaction().isWilderness() || !tmpPlayer.getRole().isAtLeast(Role.MODERATOR))
        {
            player.sendMessage(prefix + "You cannot do this for your faction, only Faction Moderator and above have access to this command.");
            return true;
        }
        if (args[0].equalsIgnoreCase("Unused"))
        {
            player.sendMessage(prefix + "Unused Mercenaries: " + ChatColor.GOLD + Riverland._Instance.getNPCFaction(player).storedNPCs);
            return true;
        }
        else if (args[0].equalsIgnoreCase("tphere"))
        {
            NPCFaction fac = Riverland._Instance.getNPCFaction(player);
            for (int npcuuid : fac.NpcUUID)
            {
                NPC npc = CitizensAPI.getNPCRegistry().getById(npcuuid);
                npc.teleport(player.getLocation(), PlayerTeleportEvent.TeleportCause.COMMAND);
            }
        }else if (args[0].equalsIgnoreCase("Forgive"))
        {
            NPCFaction fac = Riverland._Instance.getNPCFaction(player);
            for(Integer i : fac.NpcUUID)
            {
                SentinelTrait tmpSentinel = CitizensAPI.getNPCRegistry().getById(i).getTrait(SentinelTrait.class);
                tmpSentinel.targetingHelper.currentTargets.clear();
                tmpSentinel.chasing = null;
            }
            return true;
        }
        else if (args[0].equalsIgnoreCase("Remove"))
        {
            NPCFaction fac = Riverland._Instance.getNPCFaction(player);
            if (args.length > 1)
            {

                // translate index 1 to int
                try {
                    int amount = Integer.parseUnsignedInt(args[1]);
                    if (amount > fac.NPCCount) {
                        sender.sendMessage(prefix + "You do not own that many Mercenaries.");
                        return true;
                    }
                    if (amount > 0)
                    {
                        for (int i = 0; i < amount; i++)
                        {
                            fac.DespawnLast();
                        }
                        if (amount == 1) sender.sendMessage(prefix + "Removed " +ChatColor.GOLD+ amount + ChatColor.YELLOW +" Mercenary");
                        else  sender.sendMessage(prefix + "Removed " +ChatColor.GOLD+ amount +ChatColor.YELLOW +" Mercenaries");

                        return true;
                    }

                }catch (Exception exc)
                {
                    sender.sendMessage(prefix +"Invalid Input");
                    exc.printStackTrace();

                    return true;
                }
            }else
            {
                if (fac.NPCCount > 0) {
                    fac.DespawnLast();
                    sender.sendMessage(prefix + "Removed " + ChatColor.GOLD + "1" + ChatColor.YELLOW + " Mercenary");
                }
                return true;
            }
        }

        else if (args[0].equalsIgnoreCase("Used"))
        {
            NPCFaction npcFaction = Riverland._Instance.getNPCFaction(player);
            player.sendMessage(prefix + "Used Mercenaries: " + ChatColor.GOLD +(npcFaction.NPCCount - npcFaction.storedNPCs));
            return true;
        }else if (args[0].equalsIgnoreCase("Buy"))
        {
            NPCFaction npcFaction = Riverland._Instance.getNPCFaction(player);
            if (tmpPlayer.getRole().isAtLeast(Role.MODERATOR)) {
                double wouldUsePower = (npcFaction.NPCCount + 1) *  powerCostPerSentinel;
                if (wouldUsePower <= tmpPlayer.getFaction().getPowerMax()) {
                    if (!npcFaction.Purchase(player)) {
                        player.sendMessage(prefix + "Your Faction cannot afford that.");
                    }else
                    {
                        player.sendMessage(prefix + "Your faction now has " + ChatColor.GOLD + npcFaction.storedNPCs + ChatColor.YELLOW + " Stored.");
                    }
                }else player.sendMessage(prefix +"Not enough available power. Cost per Mercenary: " + ChatColor.RED + (int)powerCostPerSentinel);
            }else
                player.sendMessage(prefix +"You cannot do this for your faction, only Faction Moderator and above have access to this command.");
            return true;
        }
        else if (args[0].equalsIgnoreCase("Spawn"))
        {
            NPCFaction npcFaction = Riverland._Instance.getNPCFaction(player);

            FLocation flocation = new FLocation(player.getLocation());
            Faction faction = Board.getInstance().getFactionAt(flocation);
            if (faction == tmpPlayer.getFaction() || faction.isWilderness())
            {
                boolean success = npcFaction.SpawnSentinel(player.getLocation(), player);
                if (success)
                    player.sendMessage(prefix + "Mercenary Spawned");
                else
                    player.sendMessage(prefix +"No Mercenaries in reserve");
            }else
            {
                player.sendMessage(prefix + "You can only place Mercs in the wilderness or in your own faction land.");
            }
            return true;
        }else if (args[0].equalsIgnoreCase("Select"))
        {

            ((Player)sender).performCommand("npc select");
        }


        NPC selected = CitizensAPI.getDefaultNPCSelector().getSelected(sender);

        // check validity of selection..

        FPlayer factionPlayer = null;
        RiverlandSentinel riverlandSentinel = null;
        SentinelTrait sentinel =  null;
        if (selected != null)
        {
            factionPlayer= FPlayers.getInstance().getByPlayer(player);
            riverlandSentinel = selected.getTrait(RiverlandSentinel.class);
            sentinel = selected.getTrait(SentinelTrait.class);
        }
        if (sender.isOp())
        {
            if (args[0].equalsIgnoreCase("Info"))
            {

                // add additional detail here..
                if (selected == null)
                {
                    sender.sendMessage(prefix + "Please select an NPC");
                    return true;
                }else {
                    if (selected.getTrait(SentinelTrait.class) == null || selected.getTrait(RiverlandSentinel.class) == null)
                    {
                        sender.sendMessage(prefix +"Please select a valid NPC");
                        return true;
                    }else
                    {
                        sender.sendMessage(prefix +"Owner: " + riverlandSentinel.ownerFaction);
                        return true;
                    }
                }
                // check if npc is valid ..

            }else if (args[0].equalsIgnoreCase("SetFaction"))
            {
                if (selected == null)
                {
                    sender.sendMessage(prefix + "Please select an NPC");
                    return true;
                }else
                {
                    if (selected.getTrait(SentinelTrait.class) == null || selected.getTrait(RiverlandSentinel.class) == null)
                    {
                        sender.sendMessage(prefix +"Please select a valid NPC");
                        return true;
                    }else
                    {
                        if (args.length > 1)
                        {
                            riverlandSentinel.ownerFaction = args[1];
                        }
                        return true;
                    }
                }
            }
        }

        if (factionPlayer != null &&factionPlayer.getFaction() != Factions.getInstance().getByTag(riverlandSentinel.ownerFaction))
        {
            sender.sendMessage(prefix +"This NPC Doesn't belong to your faction!");
            return true;
        }

        if (args[0].equalsIgnoreCase("Delete"))
        {

            if (selected == null)
            {
                sender.sendMessage(prefix + "Please select an NPC");
                return true;
            }
            // check if npc is valid ..
            if (selected.getTrait(SentinelTrait.class) == null || selected.getTrait(RiverlandSentinel.class) == null)
            {
                sender.sendMessage(prefix +"Please select a valid NPC");
                return true;
            }
            riverlandSentinel.ForceRemove(Riverland._Instance.getNPCFaction(player));
        }
        else if (args[0].equalsIgnoreCase("Store"))
        {
            if (selected == null)
            {
                sender.sendMessage(prefix + "Please select an NPC");
                return true;
            }
            // check if npc is valid ..
            if (selected.getTrait(SentinelTrait.class) == null || selected.getTrait(RiverlandSentinel.class) == null)
            {
                sender.sendMessage(prefix +"Please select a valid NPC");
                return true;
            }
            NPCFaction fac = Riverland._Instance.getNPCFaction(player);
            riverlandSentinel.ForceRemove(fac);
            fac.storedNPCs++;
            sender.sendMessage(prefix + "Merc Stored");
        }

        if (args[0].equalsIgnoreCase("equip"))
        {
            if (selected == null)
            {
                sender.sendMessage(prefix + "Please select an NPC");
                return true;
            }
            // check if npc is valid ..
            if (selected.getTrait(SentinelTrait.class) == null || selected.getTrait(RiverlandSentinel.class) == null)
            {
                sender.sendMessage(prefix +"Please select a valid NPC");
                return true;
            }
            // remove from player hand
            riverlandSentinel.EquipItem(player, player.getInventory().getItemInMainHand());
        }else if (args[0].equalsIgnoreCase("rename"))
        {
            if (selected == null)
            {
                sender.sendMessage(prefix + "Please select an NPC");
                return true;
            }
            // check if npc is valid ..
            if (selected.getTrait(SentinelTrait.class) == null || selected.getTrait(RiverlandSentinel.class) == null)
            {
                sender.sendMessage(prefix +"Please select a valid NPC");
                return true;
            }
            if (player.hasPermission("Riverland.NpcChangeName")) {
                if (args.length > 1) {
                    String finalName = "";
                    for (int i = 1; i < args.length; i++) {
                        finalName +=  " " + args[i];
                    }
                    finalName.trim();
                    if (finalName.length() > 16) {
                        player.sendMessage(prefix + "Name too long");
                        return true;
                    } else {
                        selected.setName(finalName);

                        Riverland._Instance.getLogger().log(Level.WARNING, "User: " + player.getDisplayName() + " set NPC name to: " + finalName);
                    }
                } else sender.sendMessage(ChatColor.YELLOW + "Invalid Name");
            }else player.sendMessage(prefix + "You don't have this donator privilege");
        }else if (args[0].equalsIgnoreCase("Skin"))
        {
            if (selected == null)
            {
                sender.sendMessage(prefix + "Please select an NPC");
                return true;
            }
            // check if npc is valid ..
            if (selected.getTrait(SentinelTrait.class) == null || selected.getTrait(RiverlandSentinel.class) == null)
            {
                sender.sendMessage(prefix +"Please select a valid NPC");
                return true;
            }
                if (player.hasPermission("Riverland.NpcChangeSkin")) {
                if (args.length > 1) {
                    NPCFaction fac = Riverland._Instance.getNPCFaction(player);
                    NPCFaction.SetTextureTryLoad(args[1], selected, player);
                } else
                    player.sendMessage(prefix + "Invalid Input");
            }
            else player.sendMessage(prefix + "You don't have this donator privilege");
        }
        else if (args[0].equalsIgnoreCase("strip"))
        {
            if (selected == null)
            {
                sender.sendMessage(prefix + "Please select an NPC");
                return true;
            }
            // check if npc is valid ..
            if (selected.getTrait(SentinelTrait.class) == null || selected.getTrait(RiverlandSentinel.class) == null)
            {
                sender.sendMessage(prefix +"Please select a valid NPC");
                return true;
            }
            // remove all items, drop onto ground
            riverlandSentinel.Strip();
            sentinel.sayTo(player, prefix +  "I have dropped my equipment");
        }
        else if (args[0].equalsIgnoreCase("Follow"))
        {
            if (selected == null)
            {
                sender.sendMessage(prefix + "Please select an NPC");
                return true;
            }
            // check if npc is valid ..
            if (selected.getTrait(SentinelTrait.class) == null || selected.getTrait(RiverlandSentinel.class) == null)
            {
                sender.sendMessage(prefix +"Please select a valid NPC");
                return true;
            }
            if (args.length > 1)
            {
                Player other = Riverland._Instance.getServer().getPlayer(args[1]);
                if (other != null)
                {
                    FPlayer otherFactionPlayer = FPlayers.getInstance().getByPlayer(other);
                    if (factionPlayer.getFaction() == otherFactionPlayer.getFaction())
                    {
                        sentinel.setGuarding(other.getUniqueId());
                        sentinel.sayTo(player, ChatColor.YELLOW + "I am now following " + other.getDisplayName());
                        sentinel.sayTo(other, player.getDisplayName() + " has commanded me to follow you");
                    }else {
                        sentinel.sayTo(player, ChatColor.YELLOW + "I won't follow anyone who isn't in our faction.");
                    }
                }else
                {
                    sentinel.setGuarding(player.getUniqueId());
                    sentinel.sayTo(player, ChatColor.YELLOW + "I don't know who that is, I'll follow you instead.");
                }

            }else {
                sentinel.setGuarding(player.getUniqueId());
                sentinel.sayTo(player, ChatColor.YELLOW + "Lead the way.");
            }

        }
        else if (args[0].equalsIgnoreCase("Wait"))
        {
            if (selected == null)
            {
                sender.sendMessage(prefix + "Please select an NPC");
                return true;
            }
            // check if npc is valid ..
            if (selected.getTrait(SentinelTrait.class) == null || selected.getTrait(RiverlandSentinel.class) == null)
            {
                sender.sendMessage(prefix +"Please select a valid NPC");
                return true;
            }
            WaypointProvider provider = selected.getTrait(Waypoints.class).getCurrentProvider();
            Location loc = selected.getStoredLocation();
            sentinel.setGuarding(-1);
            List<Waypoint> waypoints = (List<Waypoint>) ((LinearWaypointProvider) provider).waypoints();
            waypoints.clear();
            waypoints.add(0, new Waypoint(loc));
            sentinel.sayTo(player, ChatColor.YELLOW + "I will wait here.");


        }



        return true;
    }
}
