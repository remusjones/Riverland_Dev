package riverland.dev.riverland;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.mcmonkey.sentinel.SentinelTrait;
import org.mcmonkey.sentinel.integration.SentinelFactions;
import org.mcmonkey.sentinel.targeting.SentinelTargetLabel;

import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

public class NPCFaction
{

    String factionID = "";
    Faction ownerFaction;
    //current faction money
    long currentFactionMoney = 0;
    //cost per day time
    long costPerNPC = 1000;
    //cost multiplier
    long costMultiplier = 3;
    //current npc count to multiply
    short NPCCount = 0;
    short storedNPCs = 0;

    ArrayList<SentinelTrait> activeSentinels = new ArrayList<>();

    ArrayList<UUID> NpcUUID = new ArrayList<>();

    public SerializedSentinelFaction savedData = new SerializedSentinelFaction();
    // load
    NPCFaction(String storedFaction)
    {
        factionID = storedFaction;
        ownerFaction = Factions.getInstance().getByTag(factionID);
    }
    public void Load(SerializedSentinelFaction data)
    {
        savedData = data;
        factionID = data.factionID;
        NpcUUID = data.NpcUUID;
        Riverland._Instance.getLogger().log(Level.WARNING, "NPC Count for " + factionID + " is: " + NpcUUID.size());
        ownerFaction = Factions.getInstance().getByTag(factionID);
        // iterate over npc's and store them ..
       // Iterable<NPCRegistry> iter = CitizensAPI.getNPCRegistries();
        for(int i = 0; i < NpcUUID.size(); i++)
        {
            Riverland._Instance.getLogger().log(Level.WARNING, NpcUUID.get(i).toString());
        }
     //   {

     //      // for (NPCRegistry npcs : iter) {
     //           // if (!activeSentinels.contains())
     //           NPC npc = CitizensAPI.getNPCRegistry().getByUniqueId(NpcUUID.get(i));
//
     //           SentinelTrait sentinelTrait = npc.getTrait(SentinelTrait.class);
     //           if (!activeSentinels.contains(sentinelTrait))
     //               activeSentinels.add(sentinelTrait);
     //      // }
     //   }
    }
    public void Save()
    {
        // serialize Sentinel UUID's and Faction Name..
        savedData.factionID = factionID;
        savedData.NpcUUID = NpcUUID;
    }
    public boolean PurchaseSentinel()
    {
        // get faction, deduct costs, etc..
        storedNPCs++;
        NPCCount++;
        return false;
    }
    public boolean Upkeep()
    {
        return false;
    }
    public void SpawnSentinel(Location location)
    {
        if (storedNPCs<=0)
            return;

        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, ownerFaction.getTag() + "'s SellSword");
        npc.addTrait(SentinelTrait.class);
        npc.spawn(location);

        SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
        sentinel.respawnTime = -1;
        sentinel.enemyDrops = true;

        sentinel.avoidRange = 1.5;

        new SentinelTargetLabel("npcs").addToList(sentinel.allAvoids);
        npc.addTrait(RiverlandSentinel.class);
        RiverlandSentinel sentinel1 = npc.getTrait(RiverlandSentinel.class);
        sentinel1.ownerFaction = ownerFaction.getTag();
        new SentinelTargetLabel("factionsenemy:"+ownerFaction.getTag()).addToList(sentinel.allTargets);
        new SentinelTargetLabel("monsters").addToList(sentinel.allTargets);

        sentinel.chaseRange = 20;
        NpcUUID.add(sentinel.getNPC().getUniqueId());
        storedNPCs --;
    }
    void AddSentinel(SentinelTrait sentinel)
    {
        activeSentinels.add(sentinel);
        NpcUUID.add(sentinel.getNPC().getUniqueId());
    }
    void RemoveSentinel(SentinelTrait sentinel)
    {
        NpcUUID.remove(sentinel.getNPC().getUniqueId());
        activeSentinels.remove(sentinel);
    }
}
