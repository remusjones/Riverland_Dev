package riverland.dev.riverland;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.entity.Player;

public class NPCSkinData
{
    NPC npc = null;
    String url = null;
    Player player = null;
    /**Constructs skin data*/
    public NPCSkinData(String _url, NPC _npc, Player _player)
    {
        npc = _npc;
        url = _url;
        player = _player;
    }
    /**Constructs skin data*/
    public NPCSkinData(NPCSkinData npcSkinData) {
        npc = npcSkinData.npc;
        url = npcSkinData.url;
        player = npcSkinData.player;
    }
}
