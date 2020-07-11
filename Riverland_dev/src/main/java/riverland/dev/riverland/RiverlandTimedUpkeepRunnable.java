package riverland.dev.riverland;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.Faction;
import net.citizensnpcs.npc.ai.speech.Chat;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Set;
import java.util.logging.Level;

public class RiverlandTimedUpkeepRunnable extends BukkitRunnable
{
    String prefix = ChatColor.DARK_BLUE + "[" + ChatColor.GOLD+"Mercenaries"+ChatColor.DARK_BLUE+"] " +ChatColor.YELLOW;
    public void SendFailedMessageToFaction(Faction faction, short diff)
    {

        Set<FPlayer> offline = faction.getFPlayersWhereOnline(false);
        String message = "";
        if (diff == 1)
            message = prefix + ChatColor.GOLD + "Your faction wasn't able to " + ChatColor.GREEN + "upkeep" + ChatColor.GOLD+" the costs of some/all of your mercenaries, " + ChatColor.DARK_RED + diff + ChatColor.GOLD+ " mercenary has" + ChatColor.DARK_RED +" left " + ChatColor.GOLD+ "your faction.";
        else
            message = prefix + ChatColor.GOLD + "Your faction wasn't able to " + ChatColor.GREEN + "upkeep" + ChatColor.GOLD+" the costs of some/all of your mercenaries, " + ChatColor.DARK_RED + diff + ChatColor.GOLD+ " mercenaries have" + ChatColor.DARK_RED +" left " + ChatColor.GOLD+ "your faction.";

        for (FPlayer player : offline)
        {
            faction.addAnnouncement(player, message);
        }
        Set<FPlayer> online = faction.getFPlayersWhereOnline(true);
        for (FPlayer player : online)
        {
            player.sendMessage(message);
        }

    }

    public void SendSucessMessageToFaction(Faction faction, NPCFaction npcFaction)
    {
        Set<FPlayer> offline = faction.getFPlayersWhereOnline(false);
        String message = prefix + ChatColor.GOLD + "Your faction has been charged: " + ChatColor.GREEN + "$" + npcFaction.getCurrentUpkeepCost() + ChatColor.GOLD+" for the upkeep of " + ChatColor.GREEN + npcFaction.NPCCount + ChatColor.GOLD + " Mercenaries.";
        for (FPlayer player : offline)
        {
            faction.addAnnouncement(player, message);
        }
        Set<FPlayer> online = faction.getFPlayersWhereOnline(true);
        for (FPlayer player : online)
        {
            player.sendMessage(message);
        }

    }
    @Override
    public void run()
    {
        // loop over factions, compare timestamps and charge ..
        for (NPCFaction faction : Riverland._Instance.npcFactions)
        {
            if (faction.NPCCount <= 0) {
                faction.remainingHours = 0;
                continue;

            }
            short npcCountStore = faction.NPCCount;

            long diff = NPCFaction.getDifferenceDays(NPCFaction.UnpackLongData(System.currentTimeMillis()),NPCFaction.UnpackLongData(faction.lastPurchaseTime));
            faction.remainingHours = (24 -(int)diff);
            if (diff >= 24)
            {

                boolean success = faction.Upkeep();
                if (npcCountStore != faction.NPCCount)
                {
                    SendFailedMessageToFaction(faction.ownerFaction, (short)(npcCountStore - faction.NPCCount));
                }else
                {
                    // notify the fact

                    SendSucessMessageToFaction(faction.ownerFaction, faction);
                }
            }
        }
    }
}
