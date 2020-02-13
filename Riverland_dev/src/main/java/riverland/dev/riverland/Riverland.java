package riverland.dev.riverland;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class Riverland extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getLogger().log(Level.FINE,"Riverland Plugin Enabled");
        // register Foo..
        this.getCommand("foo").setExecutor(new Foo());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().log(Level.FINE,"Riverland Plugin Disabled");
    }

}
