package riverland.dev.riverland;


import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.integration.Econ;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.util.Messaging;
import net.citizensnpcs.trait.SkinTrait;
import net.citizensnpcs.util.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.mcmonkey.sentinel.SentinelTrait;
import org.mcmonkey.sentinel.targeting.SentinelTargetLabel;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;



import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class NPCFaction
{
    String factionID = "";
    Faction ownerFaction;

    //cost multiplier
    long costMultiplier = 3;
    //current npc count to multiply
    short NPCCount = 0;
    short storedNPCs = 0;
    long lastPurchaseTime = -1;

    // config variables -
    String defaultSkinURL = "";
    String premiumSkinURL= "";
    long singleCost;
    long costPerNPC;
    public long getSingleCost() {
        return singleCost;
    }
    public long getCostPerNPC() {
        return costPerNPC;
    }
    public static ArrayList<LoadedSkinData> storedSkinData = new ArrayList<>();
    public int remainingHours = 0;


    ArrayList<SentinelTrait> activeSentinels = new ArrayList<>();

    ArrayList<Integer> NpcUUID = new ArrayList<>();

    public SerializedSentinelFaction savedData = new SerializedSentinelFaction();
    // load
    /**Default Constructor for an NPCFaction Object which will attempt to load default values, including faction instance to the class*/
    NPCFaction(String storedFaction)
    {
        factionID = storedFaction;
        ownerFaction = Factions.getInstance().getByTag(factionID);
        singleCost = Riverland._Instance.getConfig().getLong("Merc_Cost");
        costPerNPC = Riverland._Instance.getConfig().getLong("Merc_UpkeepCost");
        defaultSkinURL = Riverland._Instance.getConfig().getString("Merc_DefaultSkinURL");
        premiumSkinURL = Riverland._Instance.getConfig().getString("Merc_PremiumSkinURL");
    }
    /**Used to load the serialized data, after construction.*/
    public void Load(SerializedSentinelFaction data)
    {
        savedData = data;
        factionID = data.factionID;
        NpcUUID = data.NpcUUID;
        storedNPCs = data.storedNPCS;
        lastPurchaseTime = data.lastPurchase;
        if (NpcUUID == null)
        {
            NpcUUID = new ArrayList<>();
        }
        NPCCount = (short)(NpcUUID.size() + storedNPCs);
        Riverland._Instance.getLogger().log(Level.WARNING, "NPC Count for " + factionID + " is: " + NpcUUID.size());
        ownerFaction = Factions.getInstance().getByTag(factionID);

    }
    /**Returns the purchase cost post formula if the user were to purchase a new merc*/
    public double getPurchaseCost()
    {
        double finalCost = 0.0;

        if (NPCCount == 0)
        {
            finalCost = singleCost;
        }else {

            finalCost =(singleCost) * ((NPCCount + 1) * costMultiplier );
        }

        return finalCost;
    }
    /**Returns the Upkeep Cost post formula if the user were to purchase a new merc.*/
    public double getUpkeepCost()
    {
        double finalCost = 0.0;
        if (NPCCount == 0)
        {
            finalCost = costPerNPC;
        }else {
            finalCost = (costPerNPC) * ((NPCCount + 1) * costMultiplier );
        }
        return finalCost;
    }
    /**Returns the current upkeep cost post formula*/
    public double getCurrentUpkeepCost()
    {
        double finalCost = 0.0;
        if (NPCCount == 0)
        {
            finalCost = 0;
        }else {
            finalCost = (costPerNPC) * ((NPCCount) * costMultiplier );
        }
        return finalCost;
    }
    /**Attempts to purchase a merc, sends the player a message if unable*/
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
            finalCost = (singleCost) * ((NPCCount + 1) * costMultiplier );
        }

        if (balance > finalCost)
        {
            Econ.setBalance(acc, balance - finalCost);

            String prefix = ChatColor.DARK_BLUE + "[" + ChatColor.GOLD+"Mercenaries"+ChatColor.DARK_BLUE+"] " +ChatColor.YELLOW;
            purchaser.sendMessage(prefix + "New Faction Balance: " + Econ.getBalance(acc));
            remainingHours = 24;
            storedNPCs ++;
            NPCCount++;

            lastPurchaseTime = System.currentTimeMillis();
            return true;
        }

        return false;
    }
    /**Returns the difference in hours*/
    public static long getDifferenceDays(Date d1, Date d2) {
        long secs = (d1.getTime() - d2.getTime()) / 1000;

        return secs / 3600;

      // long secs = (this.endDate.getTime() - this.startDate.getTime()) / 1000;
      // int hours = secs / 3600;
      // secs = secs % 3600;
      // int mins = secs / 60;
      // secs = secs % 60;
    }
    public static long getDifferenceMinutes(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return TimeUnit.MINUTES.convert(diff, TimeUnit.MILLISECONDS);
    }
    public static Date UnpackLongData(Long date)
    {
        return new Date(date);
    }
    public static Long PackLongData(Date date)
    {
        return date.getTime();
    }

    /**Constructs a serialized data for saving.*/
    public void Save()
    {
        // serialize Sentinel UUID's and Faction Name..
        savedData.factionID = factionID;
        savedData.NpcUUID = NpcUUID;
        savedData.storedNPCS = storedNPCs;
        savedData.lastPurchase = lastPurchaseTime;
    }
    /**Attempts to deduct upkeep costs from faction, if unmet it will remove Mercs until cost is paid or there are no mercs remaining.*/
    public boolean Upkeep()
    {
        if (NPCCount == 0)
            return true;

        String acc = ownerFaction.getAccountId();
        double balance = Econ.getBalance(acc);

        double finalCost = (costPerNPC) * ((NPCCount) * costMultiplier );

        while(finalCost >= balance)
        {
            DespawnLast();
            if (NPCCount <= 0 || finalCost <= 0)
            {
                break;
            }
            Riverland._Instance.getLogger().log(Level.WARNING, "Cost: " + finalCost + " | " + balance + " NPCCount: " );
            finalCost = (costPerNPC) * ((NPCCount) * costMultiplier );
        }

        Econ.setBalance(acc,balance - finalCost);
        lastPurchaseTime = System.currentTimeMillis();
        return true;
    }
    /**Despawns a merc. Pool to End of List*/
    public void DespawnLast()
    {
        if (NPCCount > 0)
        {
            if (storedNPCs > 0)
            {
                storedNPCs --;
                NPCCount --;
            }else if (NpcUUID.size() > 0){
                NPC npc = CitizensAPI.getNPCRegistry().getById(NpcUUID.get(NpcUUID.size() - 1));
                if (npc==null)
                {
                    NpcUUID.remove(NpcUUID.get(NpcUUID.size() - 1));
                    Riverland._Instance.getLogger().log(Level.WARNING, "Mercs: Issue removing an NPC.. " + this.ownerFaction);
                    // check validity of list here.. and cleanup
                    short expectedCount = (short)(this.storedNPCs + this.NpcUUID.size());
                    if (expectedCount != NPCCount)
                    {
                        NPCCount = expectedCount;
                    }
                }else {
                    NpcUUID.remove(Integer.valueOf(npc.getId()));
                    npc.getTrait(RiverlandSentinel.class).ForceRemove(this);
                    short expectedCount = (short)(this.storedNPCs + this.NpcUUID.size());
                    if (expectedCount != NPCCount)
                    {
                        NPCCount = expectedCount;
                    }
                }
            }
        }
    }
    /**Spawns a Sentinel*/
    public boolean SpawnSentinel(Location location, Player player)
    {
        if (storedNPCs<=0)
            return false;

        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, ownerFaction.getTag() + "'s Merc");
        npc.addTrait(SentinelTrait.class);

        // do a permission check
        if (player.hasPermission("Riverland.NpcChangeSkin"))
        {
            SetTextureTryLoad(premiumSkinURL,npc,player);
            //RiverlandSetTextureRunnable.skinQueue.add(new NPCSkinData(premiumSkinURL,npc,player));
           // SetTexture(premiumSkinURL, npc, player);
        }else
        {
            SetTextureTryLoad(premiumSkinURL,npc,player);
            //RiverlandSetTextureRunnable.skinQueue.add(new NPCSkinData(defaultSkinURL,npc,player));
            //SetTexture(defaultSkinURL, npc, player);
        }


        npc.spawn(location);

        SentinelTrait sentinel = npc.getTrait(SentinelTrait.class);
        sentinel.respawnTime = -1;
        sentinel.enemyDrops = true;
        npc.addTrait(RiverlandSentinel.class);
        RiverlandSentinel sentinel1 = npc.getTrait(RiverlandSentinel.class);
        sentinel1.ownerFaction = ownerFaction.getTag();
        new SentinelTargetLabel("factionsenemy:"+ownerFaction.getTag()).addToList(sentinel.allTargets);
        new SentinelTargetLabel("monsters").addToList(sentinel.allTargets);
        // ignores own faction
        new SentinelTargetLabel("factionIgnore:"+ownerFaction.getTag()).addToList(sentinel.allIgnores);
       // new SentinelTargetLabel("factionsally:"+ownerFaction.getTag()).addToList(sentinel.allIgnores);

        sentinel.chaseRange = 20;
        NpcUUID.add(sentinel.getNPC().getId());
        storedNPCs --;



        return true;
    }
    void AddSentinel(SentinelTrait sentinel)
    {
        activeSentinels.add(sentinel);
        NpcUUID.add(sentinel.getNPC().getId());
    }
    void RemoveSentinel(SentinelTrait sentinel)
    {
        NpcUUID.remove(sentinel.getNPC().getUniqueId());
        activeSentinels.remove(sentinel);
    }
    /**Attempts to load a skin from RAM, if unavailable, fallback is download.*/
    public static void SetTextureTryLoad(String url, NPC npc, Player player)
    {
        boolean found = false;
        for(LoadedSkinData skin : storedSkinData)
        {
            if (skin.isValid())
            {
                if (skin.url == url)
                {
                    found = true;
                    Bukkit.getScheduler().runTask(Riverland._Instance, new Runnable() {
                        @Override
                        public void run() {
                            npc.getTrait(SkinTrait.class).setSkinPersistent(skin.uuid, skin.signature, skin.textureEncoded);
                        }
                    });
                }
            }
        }
        if (found == false)
        {
            String prefix = ChatColor.DARK_BLUE + "[" + ChatColor.GOLD+"Mercenaries"+ChatColor.DARK_BLUE+"] " +ChatColor.YELLOW;
            RiverlandSetTextureRunnable.skinQueue.add(new NPCSkinData(url,npc,player));
            player.sendMessage(prefix +"Loading skin..");
        }
    }
    /**Downloads, stores and sets the skin of an NPC from URL.*/
    // ripped from https://github.com/CitizensDev/Citizens2/blob/29e6e20feb66730b2f3fe9052314e0b16bc8489e/main/src/main/java/net/citizensnpcs/commands/NPCCommands.java#L1768
    public static void SetTexture(String url, NPC npc, Player player)
    {
        Bukkit.getScheduler().runTaskAsynchronously(Riverland._Instance, new Runnable() {
            @Override
            public void run() {
                DataOutputStream out = null;
                BufferedReader reader = null;
                try {
                    URL target = new URL("https://api.mineskin.org/generate/url");
                    HttpURLConnection con = (HttpURLConnection) target.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setConnectTimeout(5000);
                    con.setReadTimeout(25000);

                    out = new DataOutputStream(con.getOutputStream());
                    out.writeBytes("url=" + URLEncoder.encode(url, "UTF-8"));
                    out.close();
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    JSONObject output = (JSONObject) new JSONParser().parse(reader);
                    JSONObject data = (JSONObject) output.get("data");
                    String uuid = (String) data.get("uuid");
                    JSONObject texture = (JSONObject) data.get("texture");
                    String textureEncoded = (String) texture.get("value");
                    String signature = (String) texture.get("signature");
                    con.disconnect();
                    Bukkit.getScheduler().runTask(Riverland._Instance, new Runnable() {
                        @Override
                        public void run() {
                            npc.getTrait(SkinTrait.class).setSkinPersistent(uuid, signature, textureEncoded);
                            LoadedSkinData loadedData = new LoadedSkinData();
                            loadedData.url = url;
                            loadedData.uuid = uuid;
                            loadedData.signature = signature;
                            loadedData.textureEncoded = textureEncoded;
                            NPCFaction.storedSkinData.add(loadedData);
                        }
                    });
                } catch (Throwable t) {
                   // if (Messaging.isDebugging()) {
                        t.printStackTrace();

                   // }
                    Bukkit.getScheduler().runTask(Riverland._Instance, new Runnable() {
                        @Override
                        public void run() {
                            Messaging.sendErrorTr(player, Messages.ERROR_SETTING_SKIN_URL, url);
                        }
                    });
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
