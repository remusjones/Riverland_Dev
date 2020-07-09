package riverland.dev.riverland;

import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.perms.Relation;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.mcmonkey.sentinel.SentinelIntegration;

import java.util.logging.Level;


public class RiverlandSentinelIntegration extends SentinelIntegration
{
    @Override
    public String getTargetHelp()
    {
        return "factions:FACTION_NAME, factionsenemy:NAME, factionsally:NAME";
    }

    @Override
    public String[] getTargetPrefixes() {
        return new String[] { "factions", "factionsenemy", "factionsally" };
    }


    @Override
    public boolean isTarget(LivingEntity ent, String prefix, String value)
    {

        if (prefix.contains("factions"))
        {
            try {
                boolean isValid = CitizensAPI.getNPCRegistry().isNPC(ent);
                if (isValid) {
                    // try get clazz

                    NPC currNpc = CitizensAPI.getNPCRegistry().getNPC(ent);

                    RiverlandSentinel sentinel = currNpc.getTrait(RiverlandSentinel.class);

                    if (sentinel != null)
                    {
                        Faction faction = Factions.getInstance().getBestTagMatch(value);
                        Faction otherFaction = Factions.getInstance().getBestTagMatch(sentinel.ownerFaction);

                        Relation rel = faction.getRelationWish(otherFaction);
                        Relation rel2 = otherFaction.getRelationWish(faction);

                        if (rel.isEnemy() || rel2.isEnemy())
                        {
                            return true;
                        }
                    }
                }
                else if (ent instanceof Player)
                {
                    // player is our target ..

                    // get our faction tag..
                    Faction fac = Factions.getInstance().getBestTagMatch(value);
                    // get player faction
                    Faction other = FPlayers.getInstance().getByPlayer((Player)ent).getFaction();

                    Relation rel = fac.getRelationWish(other);
                    Relation rel2 = other.getRelationWish(fac);

                    if (rel.isEnemy() || rel2.isEnemy())
                    {
                        return true;
                    }
                    if (rel.isAlly() || rel2.isAlly())
                    {
                        return false;
                    }
                    if (rel.isMember() || rel2.isMember())
                    {
                        return false;
                    }

                }
            }
            catch (Exception exc)
            {
                exc.printStackTrace();
            }

        }

        return false;
    }
}
