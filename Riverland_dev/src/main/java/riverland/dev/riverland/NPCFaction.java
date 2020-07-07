package riverland.dev.riverland;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.config.file.MainConfig;
import com.massivecraft.factions.integration.Econ;
import com.sk89q.wepif.VaultResolver;
import jdk.vm.ci.code.ValueUtil;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
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
    long singleCost = 2000;
    //cost per day time
    long costPerNPC = 1000;
    //cost multiplier
    long costMultiplier = 3;
    //current npc count to multiply
    short NPCCount = 0;
    short storedNPCs = 0;
    long lastPurchase = -1;

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
        storedNPCs = data.storedNPCS;
        NPCCount = (short)(NpcUUID.size() + storedNPCs);
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
    public boolean Purchase(Player purchaser)
    {
        // compare vault..
        String acc = ownerFaction.getAccountId();
        double balance = Econ.getBalance(acc);
        double finalCost = 0;
        if (NPCCount == 0)
        {
            finalCost = singleCost;
        }else {
            finalCost = (singleCost) + (costPerNPC * (NPCCount + 1));
        }

        if (balance > finalCost)
        {
            Econ.setBalance(acc, balance - finalCost);
            purchaser.sendMessage("New Faction Balance: " + Econ.getBalance(acc));
            storedNPCs ++;
            return true;
        }

        return false;
    }
    public int UnusedNPCS()
    {
        return storedNPCs;
    }
    public void Save()
    {
        // serialize Sentinel UUID's and Faction Name..
        savedData.factionID = factionID;
        savedData.NpcUUID = NpcUUID;
        savedData.storedNPCS = storedNPCs;
    }
    public boolean Upkeep()
    {
        return false;
    }
    public boolean SpawnSentinel(Location location)
    {
        if (storedNPCs<=0)
            return false;

        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, ownerFaction.getTag() + "'s SellSword");
        npc.addTrait(SentinelTrait.class);
        npc.spawn(location);

        SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
        sentinel.respawnTime = -1;
        sentinel.enemyDrops = true;
        npc.addTrait(RiverlandSentinel.class);
        RiverlandSentinel sentinel1 = npc.getTrait(RiverlandSentinel.class);
        sentinel1.ownerFaction = ownerFaction.getTag();
        new SentinelTargetLabel("factionsenemy:"+ownerFaction.getTag()).addToList(sentinel.allTargets);
        new SentinelTargetLabel("monsters").addToList(sentinel.allTargets);

        sentinel.chaseRange = 20;
        NpcUUID.add(sentinel.getNPC().getUniqueId());
        storedNPCs --;
        return true;
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
