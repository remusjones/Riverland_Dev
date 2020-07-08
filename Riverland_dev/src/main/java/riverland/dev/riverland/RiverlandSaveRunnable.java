package riverland.dev.riverland;

import org.bukkit.scheduler.BukkitRunnable;

public class RiverlandSaveRunnable  extends BukkitRunnable
{
    @Override
    public void run()
    {
        Riverland._Instance.SaveNPCFaction();
    }
}
