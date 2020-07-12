package riverland.dev.riverland;
import com.massivecraft.factions.event.FactionRenameEvent;
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
            if (npcFac.factionID.equalsIgnoreCase(e.getFactionTag()))
            {
                npcFac.factionID = e.getFactionTag();
                npcFac.ownerFaction = e.getFaction();
            }
        }

    }

}