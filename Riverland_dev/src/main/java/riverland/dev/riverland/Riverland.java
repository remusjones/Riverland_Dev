package riverland.dev.riverland;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

<<<<<<< HEAD
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.flags.registry.FlagConflictException;
import com.sk89q.worldguard.protection.flags.registry.FlagRegistry;
import de.dustplanet.util.SilkUtil;
=======
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
import net.minecraft.server.v1_15_R1.EntityTypes;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
<<<<<<< HEAD
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
=======
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Type;
<<<<<<< HEAD
import java.text.DateFormat;
import java.text.SimpleDateFormat;
=======
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
import java.util.*;
import java.util.logging.Level;
public final class Riverland extends JavaPlugin {


<<<<<<< HEAD
    // last death pair
    public static Map<String, Inventory> playerLastDeathMap = new HashMap<>();
=======
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
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
<<<<<<< HEAD
    public static CustomEntityType CreeperTypeInstance;
    public static SilkUtil SilkSpawnerInstance = null;


    public ArrayList<String> randomNameList = new ArrayList<>();
    Gson gsonObj = new Gson();

    public static StateFlag CustomExplosionFlag;
    public static StateFlag SpawnExitCommands;
    private File folder;
    private File f;

    public static SilkUtil _SilkSpawnerInstance()
    {
        return SilkSpawnerInstance;
    }

=======
    public static CustomEntityType GiantTypeInstance;
    public static CustomEntityType CreeperTypeInstance;
    public static CustomEntityType BabyZombieTypeInstance;
    public RiverlandEventManager riverlandEventManager = null;


    public Location giantBossStartLocation = null;
    public Location giantBossEndLocation = null;

    public Location playerWatchLocation = null;
    public Location player1Location = null;
    public Location player2Location = null;


    public ArrayList<String> randomNameList = new ArrayList<>();
    public SpecatorMode spectatorMode;
    Gson gsonObj = new Gson();


    private File folder;
    private File f;

>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
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
        SaveLocations();
        saveConfig();
    }
    @Override
    public void onLoad()
    {
<<<<<<< HEAD


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
=======
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
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
    }
    public void SetEntityRandomName(Entity entity)
    {
        Random rand = new Random();
        String randomElement = randomNameList.get(rand.nextInt(randomNameList.size()));
        entity.setCustomName(randomElement);
        entity.setCustomNameVisible(true);
    }

    public void RegisterEntities()
    {
        // config.yml end
        try {
            //new PetType<PetZombie>("pet_zombie", PetZombie.class, EntityTypes.ZOMBIE, PetZombie::new);
<<<<<<< HEAD
=======
            BabyZombieTypeInstance = new CustomEntityType <CustomEntityBabyZombies> (randomString().toLowerCase(), CustomEntityBabyZombies.class, EntityTypes.ZOMBIE, CustomEntityBabyZombies::new);
            BabyZombieTypeInstance.register();
        } catch (Exception err)
        {
            err.printStackTrace();
        }



        // config.yml end
        try {
            //new PetType<PetZombie>("pet_zombie", PetZombie.class, EntityTypes.ZOMBIE, PetZombie::new);
            GiantTypeInstance = new CustomEntityType <CustomEntityGiant> (randomString().toLowerCase(), CustomEntityGiant.class, EntityTypes.GIANT, CustomEntityGiant::new);
            GiantTypeInstance.register();
        } catch (Exception err)
        {
            err.printStackTrace();
        }

        // config.yml end
        try {
            //new PetType<PetZombie>("pet_zombie", PetZombie.class, EntityTypes.ZOMBIE, PetZombie::new);
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
            CreeperTypeInstance = new CustomEntityType <CustomEntityCreeper> (randomString().toLowerCase(), CustomEntityCreeper.class, EntityTypes.CREEPER, CustomEntityCreeper::new);
            CreeperTypeInstance.register();
        } catch (Exception err)
        {
            err.printStackTrace();
        }
    }
    public void UnRegisterEntities()
    {
<<<<<<< HEAD
=======
        // unregister first if it exists
        try
        {
            BabyZombieTypeInstance.unregister();
        }catch (Exception err)
        {
            err.printStackTrace();
        }
        try
        {
            GiantTypeInstance.unregister();
        }catch (Exception err)
        {
            err.printStackTrace();
        }
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
        try
        {
            CreeperTypeInstance.unregister();
        }catch (Exception err)
        {
            err.printStackTrace();
        }
    }

    @Override
<<<<<<< HEAD
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
=======
    public void onEnable() {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
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
<<<<<<< HEAD
        config.addDefault("PVP_DONATION_COOLDOWN", 12);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date d = new Date();


        saveConfig();
=======
        //config.options().copyDefaults(true);
        saveConfig();
        spectatorMode = new SpecatorMode();
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
        // register
        RegisterEntities();
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
                    getLogger().log(Level.WARNING,"String Contents: " + data);
                    // try load json
                   Type type = new TypeToken<
                           ArrayList<SerializableTNT>>(){}.getType();

                    tntPositions.clear();
                    ArrayList<SerializableTNT> tmp = gsonObj.fromJson(data, type);
                    for(SerializableTNT tnt : tmp)
                    {
                        Location loc = new Location(getServer().getWorld(tnt.world), tnt.x, tnt.y, tnt.z);
                        tntPositions.put(loc, tnt.tntType);
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
<<<<<<< HEAD
=======
        riverlandEventManager = new RiverlandEventManager();
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
        getLogger().log(Level.INFO,"Riverland Plugin Enabled");
        // register command against plugin.yml commands list..
        this.getCommand("AdminHelp").setExecutor(new AdminHelp());
        this.getCommand("OPAdminHelp").setExecutor(new OPAdminHelp());
        this.getCommand("Riverland").setExecutor(new RiverlandCommands());
<<<<<<< HEAD
=======
        PVPEvent event = new PVPEvent();
        this.getCommand("PVPArena").setExecutor(event);
        this.getCommand("EventManager").setExecutor(new RiverlandEventManagerListener());


>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b

        CommandTabCompletion commandTab = new CommandTabCompletion();
        getCommand("Riverland").setTabCompleter(commandTab);
        getCommand("OpAdminHelp").setTabCompleter(commandTab);
        getCommand("AdminHelp").setTabCompleter(commandTab);
<<<<<<< HEAD

        // register join listener..
        getServer().getPluginManager().registerEvents(new RiverLandEventListener(), this);
=======
        // register join listener..
        getServer().getPluginManager().registerEvents(new RiverLandEventListener(), this);
        getServer().getPluginManager().registerEvents(event, this);

>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
        // setup tnt from config file
        tntRadiusDefault = config.getDouble("TNT_ExplosionRadius");
        tntRadiusLow =    config.getDouble("TNT_ExplosionRadiusLow");
        tntRadiusHigh =    config.getDouble("TNT_ExplosionRadiusHigh");
        tntBunkerBusterRange = config.getDouble("TNT_BunkerBusterRange");
        tntBreakChance = config.getDouble("TNT_BreakChance");
        IgnoreWater = config.getBoolean("TNT_BunkerBusterIgnoresWater");
<<<<<<< HEAD
=======
        // load all stored locations..
        LoadLocations();
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b

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

    public void LoadLocations() // loads event locations yml json
    {
<<<<<<< HEAD

    }
    public void SaveLocations() // saves stored event locations
    {
=======
        try
        {

            folder = this.getDataFolder();
            f= new File(folder,"EventLocations.yml");

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
                            ArrayList<SerializableLocation>>(){}.getType();

                    tntPositions.clear();
                    ArrayList<SerializableLocation> tmp = gsonObj.fromJson(data, type);
                    if (tmp.size() == 2)
                    {
                        giantBossStartLocation = tmp.get(0).Get();
                        giantBossEndLocation = tmp.get(1).Get();
                    }
                    if (tmp.size() > 2)
                    {
                        giantBossStartLocation = tmp.get(0).Get();
                        giantBossEndLocation = tmp.get(1).Get();
                        playerWatchLocation = tmp.get(2).Get();
                        player1Location = tmp.get(3).Get();
                        player2Location = tmp.get(4).Get();
                    }
                    myReader.close();
                }

            }

        } catch (Exception exc)
        {
            getLogger().log(Level.WARNING,"Could not set event positions" + exc.toString());
        }
    }
    public void SaveLocations() // saves stored event locations
    {
        if (giantBossStartLocation == null || giantBossEndLocation == null)
        {
            if (giantBossEndLocation == null)
                getLogger().log(Level.WARNING,"Boss location was null.. " );
            if (giantBossStartLocation == null)
                getLogger().log(Level.WARNING,"start location was null.. " );
            return;
        }else
        {
            getLogger().log(Level.WARNING,"start location: " + giantBossStartLocation.toString() );
            getLogger().log(Level.WARNING,"boss location: " + giantBossEndLocation.toString() );
        }



        try {
            folder = this.getDataFolder();
            f = new File(folder, "EventLocations.yml");
        }
        catch (Exception exc)
        {
            exc.printStackTrace();
        }
        try
        {
            getLogger().log(Level.WARNING,"Saving Json.. " );
            gsonObj = new Gson();
            FileWriter myWriter = new FileWriter(f);
            ArrayList<SerializableLocation> list = new ArrayList<>();
            SerializableLocation loc1 = new SerializableLocation();
            loc1.Set(giantBossStartLocation);
            SerializableLocation loc2 = new SerializableLocation();
            loc2.Set(giantBossEndLocation);

            SerializableLocation loc3 = new SerializableLocation();
            loc3.Set(playerWatchLocation);
            SerializableLocation loc4 = new SerializableLocation();
            loc4.Set(player1Location);
            SerializableLocation loc5 = new SerializableLocation();
            loc5.Set(player2Location);

            list.add(loc1);
            list.add(loc2);
            list.add(loc3);
            list.add(loc4);
            list.add(loc5);

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
            getLogger().log(Level.WARNING,"Could not save event locations" + exc.toString());
        }
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
    }

    @Override
    public void onDisable() {

<<<<<<< HEAD
        saveConfig();

=======
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
        getLogger().log(Level.WARNING,"Saving event locations.. " );
        SaveLocations();
        try {
            UnRegisterEntities();
        }
        catch (Exception exc)
        {

        }
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
<<<<<<< HEAD
            getLogger().log(Level.WARNING,"Could not save TNT Json" + exc.toString());
        }
    }
=======
            getLogger().log(Level.WARNING,"Could not load TNT Json" + exc.toString());
        }


    }

>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
}
