package riverland.dev.riverland;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import de.dustplanet.util.SilkUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.type.TNT;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcmonkey.sentinel.SentinelPlugin;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
public final class Riverland extends JavaPlugin {


    // last death pair
    public static Map<String, Inventory> playerLastDeathMap = new HashMap<>();
    FileConfiguration config = getConfig();
    static public TicketSQL _InstanceRiverLandTicket;
    static public Riverland _Instance;

    public double tntRadiusLow = 5;
    public double tntRadiusDefault = 5;
    public double tntRadiusHigh = 5;
    public double tntBunkerBusterRange = 5;
    public transient Map<Location, Integer> tntPositions = new HashMap<>();
    public boolean IgnoreWater = false;
    public double tntBreakChance = 0.5f;
  //  public static CustomEntityType CreeperTypeInstance;
    public static SilkUtil SilkSpawnerInstance = null;


    public ArrayList<String> randomNameList = new ArrayList<>();
    Gson gsonObj = new Gson();

    public static StateFlag CustomExplosionFlag;
    public static StateFlag SpawnExitCommands;
    private File folder;
    private File f;
    public ArrayList<NPCFaction> npcFactions = new ArrayList<>();

    public static SilkUtil _SilkSpawnerInstance()
    {
        return SilkSpawnerInstance;
    }

    public String randomString() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 80;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return generatedString;
    }

    public void ConfigReload()
    {
        config = getConfig(); // reload config?
        tntRadiusDefault = config.getDouble("TNT_ExplosionRadius"); // get config
        tntRadiusHigh = config.getDouble("TNT_ExplosionRadiusHigh"); // get config
        tntRadiusLow = config.getDouble("TNT_ExplosionRadiusLow"); // get config
        tntBunkerBusterRange = config.getDouble("TNT_BunkerBusterRange");
        tntBreakChance = config.getDouble("TNT_BreakChance");
    }
    public void UpdateConfig()
    {
        config.set("TNT_ExplosionRadiusHigh", tntRadiusHigh);
        config.set("TNT_ExplosionRadius", tntRadiusDefault);
        config.set("TNT_ExplosionRadiusLow", tntRadiusLow);
        config.set("TNT_BunkerBusterRange", tntBunkerBusterRange);
        config.set("TNT_BreakChance", tntBreakChance);
        config.set("TNT_BunkerBusterIgnoresWater", IgnoreWater);
        saveConfig();
    }
    @Override
    public void onLoad()
    {
        if (CustomExplosionFlag == null) {
            FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();
            try {
                // create a flag with the name "my-custom-flag", defaulting to true
                StateFlag flag = new StateFlag("RiverlandExplosion", true);
                registry.register(flag);
                CustomExplosionFlag = flag; // only set our field if there was no error
            } catch (FlagConflictException e) {
                // some other plugin registered a flag by the same name already.
                // you can use the existing flag, but this may cause conflicts - be sure to check type
                Flag<?> existing = registry.get("RiverlandExplosion");
                if (existing instanceof StateFlag) {
                    CustomExplosionFlag = (StateFlag) existing;
                } else {
                    // types don't match - this is bad news! some other plugin conflicts with you
                    // hopefully this never actually happens
                }
            }
        }
    }
    public void SetEntityRandomName(Entity entity)
    {
        Random rand = new Random();
        String randomElement = randomNameList.get(rand.nextInt(randomNameList.size()));
        entity.setCustomName(randomElement);
        entity.setCustomNameVisible(true);
    }


    @Override
    public void onEnable()
    {

        randomNameList.add("James");
        randomNameList.add("John");
        randomNameList.add("Robert");
        randomNameList.add("Michael");
        randomNameList.add("William");
        randomNameList.add("David");
        randomNameList.add("Richard");
        randomNameList.add("Joseph");
        randomNameList.add("Thomas");
        randomNameList.add("Charles");
        randomNameList.add("Christopher");
        randomNameList.add("Daniel");
        randomNameList.add("Mary");
        randomNameList.add("Patricia");
        randomNameList.add("Jennifer");
        randomNameList.add("Linda");
        randomNameList.add("Elizabeth");
        randomNameList.add("Barbara");
        randomNameList.add("Susan");
        randomNameList.add("Jessica");
        randomNameList.add("Sarah");
        randomNameList.add("Karen");
        randomNameList.add("Nancy");
        randomNameList.add("Margaret");
        randomNameList.add("Jason");
        randomNameList.add("Karen");

        if (CustomExplosionFlag == null)
        {
            FlagRegistry registry = WorldGuard.getInstance().getFlagRegistry();

            try {
                // create a flag with the name "my-custom-flag", defaulting to true
                StateFlag flag = new StateFlag("RiverlandExplosion", true);
                registry.register(flag);

                CustomExplosionFlag = flag; // only set our field if there was no error
            } catch (FlagConflictException e) {
                // some other plugin registered a flag by the same name already.
                // you can use the existing flag, but this may cause conflicts - be sure to check type
                Flag<?> existing = registry.get("RiverlandExplosion");
                if (existing instanceof StateFlag) {
                    CustomExplosionFlag = (StateFlag) existing;
                } else {
                    // types don't match - this is bad news! some other plugin conflicts with you
                    // hopefully this never actually happens
                }
            }
        }


        SilkSpawnerInstance = SilkUtil.hookIntoSilkSpanwers();
        _Instance = this;
        // config.yml setup
        config.addDefault("WelcomeMessage", "Welcome to Riverlands!");
        config.addDefault("SQL_Host", "localhost");
        config.addDefault("SQL_Port", 3306);
        config.addDefault("SQL_Database", "RiverLand.Dev");
        config.addDefault("SQL_Username", "root");
        config.addDefault("SQL_Password", "***");
        config.addDefault("SQL_TablePrefix", "TICKET_");
        config.addDefault("SQL_MaxTicketIssuesPerPlayer", 5);
        config.addDefault("TNT_ExplosionRadiusLow", 2);
        config.addDefault("TNT_ExplosionRadius", 3);
        config.addDefault("TNT_ExplosionRadiusHigh", 10);
        config.addDefault("TNT_BunkerBusterRange", 1.5);
        config.addDefault("TNT_BreakChance", 0.5);
        config.addDefault("TNT_BunkerBusterIgnoresWater", false);
        config.addDefault("PVP_DONATION_COOLDOWN", 12);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date d = new Date();


        saveConfig();

        if(getServer().getPluginManager().getPlugin("Citizens") == null || getServer().getPluginManager().getPlugin("Citizens").isEnabled() == false) {
            getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        //Register your trait with Citizens.
        net.citizensnpcs.api.CitizensAPI.getTraitFactory().registerTrait(net.citizensnpcs.api.trait.TraitInfo.create(RiverlandSentinel.class).withName("RiverlandSentinel"));

        //SentinelPlugin sentinelPlugin = new SentinelPlugin();

        // try load config for npcFactions..
        try
        {
            folder = this.getDataFolder();
            f= new File(folder,"FactionNPC.json");
            if (f.exists())
            {
                String data = "";
                Scanner myReader = new Scanner(f);
                while (myReader.hasNextLine())
                {
                    data += myReader.nextLine();
                }

                // try load json
                Type type = new TypeToken<
                        ArrayList<SerializedSentinelFaction>>(){}.getType();

                ArrayList<SerializedSentinelFaction> tmp = gsonObj.fromJson(data, type);
                if (tmp.size() > 0)
                {
                    for(SerializedSentinelFaction sentinelData : tmp)
                    {
                        NPCFaction faction = new NPCFaction("");
                        faction.Load(sentinelData);
                        Riverland._Instance.npcFactions.add(faction);
                    }
                }
            }

        }catch (Exception exc)
        {
            getLogger().log(Level.SEVERE, "Could not load Riverland Sentinel Data - Send Stack Trace to Developer");
            exc.printStackTrace();
        }


        try
        {
            folder = this.getDataFolder();
            f= new File(folder,"TNTWorldPositions.yml");

            if (f!=null)
            {
                if(!f.exists())
                {
                    f.createNewFile();
                    getLogger().log(Level.WARNING,"Creating Json File.. ");
                }

                // try load..
                if(f.length() > 3 )
                {
                    getLogger().log(Level.WARNING,"Loading Json.. ");
                    String data = "";
                    Scanner myReader = new Scanner(f);
                    while (myReader.hasNextLine())
                    {
                        data += myReader.nextLine();
                    }

                    // try load json
                   Type type = new TypeToken<
                           ArrayList<SerializableTNT>>(){}.getType();

                    tntPositions.clear();
                    ArrayList<SerializableTNT> tmp = gsonObj.fromJson(data, type);
                    if (tmp.size() > 0)
                        getLogger().log(Level.WARNING,"TNT Serialized count: " + tmp.size());
                    int skippedTNT = 0;
                    for(SerializableTNT tnt : tmp)
                    {
                        Location loc = new Location(getServer().getWorld(tnt.world), tnt.x, tnt.y, tnt.z);
                        if (loc.getBlock().getType() == Material.TNT)
                            tntPositions.put(loc, tnt.tntType);
                        else
                            skippedTNT++;
                    }
                    if (skippedTNT > 0)
                    {
                        getLogger().log(Level.WARNING, skippedTNT + " invalid TNT Locations removed.");
                        getLogger().log(Level.WARNING, "Final TNT Size: " + tntPositions.size());
                    }
                   myReader.close();
                }

            }

        } catch (Exception exc)
        {
                getLogger().log(Level.WARNING,"Could not load TNT Json" + exc.toString());
        }

        // setup ticketer info
        _InstanceRiverLandTicket = new TicketSQL(config.getString("SQL_Host"), config.getInt("SQL_Port"),config.getString("SQL_TablePrefix"),config.getString("SQL_Database"),config.getString("SQL_Username"),config.getString("SQL_Password"),config.getInt("SQL_MaxTicketIssuesPerPlayer"));
        getLogger().log(Level.INFO,"Riverland Plugin Enabled");
        // register command against plugin.yml commands list..
        this.getCommand("AdminHelp").setExecutor(new AdminHelp());
        this.getCommand("OPAdminHelp").setExecutor(new OPAdminHelp());
        this.getCommand("Riverland").setExecutor(new RiverlandCommands());
        this.getCommand("Thrall").setExecutor(new FactionSentinelCommands());
        CommandTabCompletion commandTab = new CommandTabCompletion();
        getCommand("Riverland").setTabCompleter(commandTab);
        getCommand("OpAdminHelp").setTabCompleter(commandTab);
        getCommand("AdminHelp").setTabCompleter(commandTab);

        // register join listener..
        getServer().getPluginManager().registerEvents(new RiverLandEventListener(), this);
        // setup tnt from config file
        tntRadiusDefault = config.getDouble("TNT_ExplosionRadius");
        tntRadiusLow =    config.getDouble("TNT_ExplosionRadiusLow");
        tntRadiusHigh =    config.getDouble("TNT_ExplosionRadiusHigh");
        tntBunkerBusterRange = config.getDouble("TNT_BunkerBusterRange");
        tntBreakChance = config.getDouble("TNT_BreakChance");
        IgnoreWater = config.getBoolean("TNT_BunkerBusterIgnoresWater");

        if (!Riverland._InstanceRiverLandTicket._IsTaskRunning) // check if server is running an async sql thingo..
        {
            getLogger().log(Level.INFO,"Starting SQL Thread..");
            Riverland._InstanceRiverLandTicket.WriteSQLData.runTaskTimerAsynchronously(Riverland._Instance,0,25);
        }
    }

    public void ClearTNTMemory() // call this to clear all saved tnt
    {
        try
        {
            gsonObj = new Gson();
            FileWriter myWriter = new FileWriter(f);
            ArrayList<SerializableTNT> list = new ArrayList<>();
            Set<Map.Entry<Location, Integer>> map = tntPositions.entrySet();
            for(Map.Entry<Location, Integer> pair : map)
            {
                SerializableTNT tnt = new SerializableTNT();
                tnt.Set(pair.getKey(),pair.getValue());
                list.add(tnt);
            }
            getLogger().log(Level.WARNING,"String Contents: " + "[]");
            myWriter.write("[]");
            getLogger().log(Level.WARNING,"Writing Json.." );
            myWriter.close();
        }
        catch (Exception exc)
        {
            getLogger().log(Level.WARNING,"Could not load TNT Json" + exc.toString());
        }
        this.tntPositions.clear();
    }

    @Override
    public void onDisable() {

        saveConfig();
        f = new File(folder, "TNTWorldPositions.yml");
        folder = this.getDataFolder();
        try
        {
            getLogger().log(Level.WARNING,"Saving Json.. " );
            gsonObj = new Gson();
            FileWriter myWriter = new FileWriter(f);
            //
            ArrayList<SerializableTNT> list = new ArrayList<>();
            Set<Map.Entry<Location, Integer>> map = tntPositions.entrySet();
            for(Map.Entry<Location, Integer> pair : map)
            {
                SerializableTNT tnt = new SerializableTNT();
                tnt.Set(pair.getKey(),pair.getValue());
                list.add(tnt);
            }
            String str = gsonObj.toJson(list);

            if (str.length() > 3)
            {
                myWriter.write(str);
                getLogger().log(Level.WARNING,"Writing Json.." );
            }
            myWriter.close();
        }
        catch (Exception exc)
        {
            getLogger().log(Level.WARNING,"Could not save TNT Json" + exc.toString());
        }
        try
        {
            f = new File(folder, "FactionNPC.json");
            folder = this.getDataFolder();

            if (npcFactions.size() > 0) {
                gsonObj = new Gson();
                FileWriter myWriter = new FileWriter(f);
                //
                ArrayList<SerializedSentinelFaction> list = new ArrayList<>();

                for (NPCFaction fac : npcFactions)
                {
                    fac.Save();
                    list.add(fac.savedData);
                }
                String str = gsonObj.toJson(list);

                if (str.length() > 3) {
                    myWriter.write(str);
                }
                myWriter.close();
            }
        }catch (Exception exc)
        {


        }
    }
}
