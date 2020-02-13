package riverland.dev.riverland;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Level;

public final class Riverland extends JavaPlugin {


    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        // Plugin startup logic
        config.addDefault("WelcomeMessage", "Welcome to Riverlands!");
        config.options().copyDefaults(true);
        saveConfig();

        getLogger().log(Level.INFO,"Riverland Plugin Enabled");
        // register command against plugin.yml commands list..
        this.getCommand("foo").setExecutor(new Foo());
        // register listener..

        String welcomeMsg = config.getString("WelcomeMessage");
        PlayerJoinAutoMessage welcomeMessage = new PlayerJoinAutoMessage(welcomeMsg,this);
        getServer().getPluginManager().registerEvents(welcomeMessage, this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().log(Level.INFO,"Riverland Plugin Disabled");
    }

}
