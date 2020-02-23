package riverland.dev.riverland;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.minecraft.server.v1_15_R1.EntityTypes;
import net.minecraft.server.v1_15_R1.IRegistry;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Giant;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Level;
public final class Riverland extends JavaPlugin {


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
    public static CustomEntityType GiantTypeInstance;
    public static CustomEntityType CreeperTypeInstance;
    public static CustomEntityType BabyZombieTypeInstance;
    public Location giantBossStartLocation = null;
    public Location giantBossEndLocation = null;
    public ArrayList<String> randomNameList = new ArrayList<>();
    public SpecatorMode spectatorMode;
    Gson gsonObj = new Gson();


    private File folder;
    private File f;

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
        giantBossStartLocation = config.getLocation("Event_THUMBUSLocationStart");
        giantBossEndLocation = config.getLocation("Event_THUMBUSLocationSpawn");

    }
    public void UpdateConfig()
    {
        config.set("TNT_ExplosionRadiusHigh", tntRadiusHigh);
        config.set("TNT_ExplosionRadius", tntRadiusDefault);
        config.set("TNT_ExplosionRadiusLow", tntRadiusLow);
        config.set("TNT_BunkerBusterRange", tntBunkerBusterRange);
        config.set("TNT_BreakChance", tntBreakChance);
        config.set("TNT_BunkerBusterIgnoresWater", IgnoreWater);
        if (giantBossEndLocation != null)
            config.set("Event_THUMBUSLocationSpawn", giantBossEndLocation);
        if (giantBossStartLocation != null)
            config.set("Event_THUMBUSLocationStart", giantBossStartLocation);
    }
    @Override
    public void onLoad()
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
            CreeperTypeInstance = new CustomEntityType <CustomEntityCreeper> (randomString().toLowerCase(), CustomEntityCreeper.class, EntityTypes.CREEPER, CustomEntityCreeper::new);
            CreeperTypeInstance.register();
        } catch (Exception err)
        {
            err.printStackTrace();
        }
    }
    public void UnRegisterEntities()
    {
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
        try
        {
            CreeperTypeInstance.unregister();
        }catch (Exception err)
        {
            err.printStackTrace();
        }
    }

    @Override
    public void onEnable() {

        // set an instance of this, to this.
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
        config.options().copyDefaults(true);
        saveConfig();
        spectatorMode = new SpecatorMode();

        // try unregister
        UnRegisterEntities();
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

        getLogger().log(Level.INFO,"Riverland Plugin Enabled");
        // register command against plugin.yml commands list..
        this.getCommand("AdminHelp").setExecutor(new AdminHelp());
        this.getCommand("OPAdminHelp").setExecutor(new OPAdminHelp());
        this.getCommand("Riverland").setExecutor(new RiverlandCommands());

        AIArenaEvent riverlandEvent = new AIArenaEvent();
        this.getCommand("ArenaEvent").setExecutor(riverlandEvent);
        this.getCommand("leave").setExecutor(riverlandEvent);
        CommandTabCompletion commandTab = new CommandTabCompletion();
        getCommand("Riverland").setTabCompleter(commandTab);
        getCommand("OpAdminHelp").setTabCompleter(commandTab);
        getCommand("AdminHelp").setTabCompleter(commandTab);
        // register join listener..
        getServer().getPluginManager().registerEvents(new RiverLandEventListener(), this);

        tntRadiusDefault = config.getDouble("TNT_ExplosionRadius");
        tntRadiusLow =    config.getDouble("TNT_ExplosionRadiusLow");
        tntRadiusHigh =    config.getDouble("TNT_ExplosionRadiusHigh");
        tntBunkerBusterRange = config.getDouble("TNT_BunkerBusterRange");
        tntBreakChance = config.getDouble("TNT_BreakChance");
        IgnoreWater = config.getBoolean("TNT_BunkerBusterIgnoresWater");

        try {
            giantBossStartLocation = config.getLocation("Event_THUMBUSLocationStart");
            giantBossEndLocation  = config.getLocation("Event_THUMBUSLocationSpawn");

        }
        catch (Exception exc)
        {
            getLogger().log(Level.WARNING,"Failed Loading Event Locations..");
        }
        if (!Riverland._InstanceRiverLandTicket._IsTaskRunning) // check if server is running an async sql thingo..
        {
            getLogger().log(Level.INFO,"Starting SQL Thread..");
            Riverland._InstanceRiverLandTicket.WriteSQLData.runTaskTimerAsynchronously(Riverland._Instance,0,25);
        }
    }
    public void ClearTNTMemory()
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

        if (giantBossEndLocation != null)
            config.set("Event_THUMBUSLocationSpawn", giantBossEndLocation);
        if (giantBossStartLocation != null)
            config.set("Event_THUMBUSLocationStart", giantBossStartLocation);
        saveConfig();
        try {
            UnRegisterEntities();
        }
        catch (Exception exc)
        {

        }
        if (f == null || folder == null)
        {
            f = new File(folder, "TNTWorldPositions.yml");
            folder = this.getDataFolder();
        }
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
            getLogger().log(Level.WARNING,"String Contents: " + str);


            if (str.length() > 3)
            {
                myWriter.write(str);
                getLogger().log(Level.WARNING,"Writing Json.." );
            }
            myWriter.close();
        }
        catch (Exception exc)
        {
            getLogger().log(Level.WARNING,"Could not load TNT Json" + exc.toString());
        }


    }

}
