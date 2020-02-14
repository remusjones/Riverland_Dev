package riverland.dev.riverland;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class Riverland extends JavaPlugin {


    FileConfiguration config = getConfig();
    static public TicketSQL _InstanceRiverLandTicket;
    static public Riverland _Instance;



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
        config.options().copyDefaults(true);
        saveConfig();
        // config.yml end

        // setup ticketer info
        _InstanceRiverLandTicket = new TicketSQL(config.getString("SQL_Host"), config.getInt("SQL_Port"),config.getString("SQL_TablePrefix"),config.getString("SQL_Database"),config.getString("SQL_Username"),config.getString("SQL_Password"),config.getInt("SQL_MaxTicketIssuesPerPlayer"));

        getLogger().log(Level.INFO,"Riverland Plugin Enabled");
        // register command against plugin.yml commands list..
        this.getCommand("AdminHelp").setExecutor(new AdminHelp());
        this.getCommand("OPAdminHelp").setExecutor(new OPAdminHelp());
        // register join listener..
        getServer().getPluginManager().registerEvents(new RiverLandEventListener(), this);



        if (!Riverland._InstanceRiverLandTicket._IsTaskRunning) // check if server is running an async sql thingo..
        {
            getLogger().log(Level.INFO,"Starting SQL Thread..");
            Riverland._InstanceRiverLandTicket.WriteSQLData.runTaskTimerAsynchronously(Riverland._Instance,0,25);
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().log(Level.INFO,"Riverland Plugin Disabled");
    }

}
