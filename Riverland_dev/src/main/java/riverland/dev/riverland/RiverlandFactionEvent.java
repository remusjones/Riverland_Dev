package riverland.dev.riverland;
import com.massivecraft.factions.event.FactionRenameEvent;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.logging.Level;

public class RiverlandFactionEvent  implements Listener{



    @EventHandler
    public void onRename(FactionRenameEvent e)
    {
        // find old
        for (NPCFaction npcFac: Riverland._Instance.npcFactions)
        {
            if (npcFac.factionID.equalsIgnoreCase(e.getOldFactionTag()))
            {
                npcFac.factionID = e.getFactionTag();
                npcFac.ownerFaction = e.getFaction();
                for (Integer npc:
                     npcFac.NpcUUID)
                {

                    NPC npc1 = CitizensAPI.getNPCRegistry().getById(npc);
                    if (npc1 != null)
                    {
                        RiverlandSentinel sentinel = npc1.getTrait(RiverlandSentinel.class);
                        if (sentinel != null)
                        {
                            sentinel.ownerFaction = e.getFactionTag();
                            Riverland._Instance.getLogger().log(Level.WARNING, "test");
                        }
                    }
                }
                npcFac.UpdateSentinels();
            }
        }

    }

}