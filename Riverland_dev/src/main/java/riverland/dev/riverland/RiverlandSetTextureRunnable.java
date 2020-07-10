package riverland.dev.riverland;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class RiverlandSetTextureRunnable extends BukkitRunnable
{

    public static ArrayList<NPCSkinData> skinQueue = new ArrayList<>();

    // should run on timeout interval ..
    // handle next on queue
    /**Used as a Queue to load skins from url*/
    @Override
    public void run()
    {
        // get first in queue..
        if (skinQueue.size() > 0)
        {
            NPCSkinData skinData = new NPCSkinData(skinQueue.get(0));
            skinQueue.remove(0);
            NPCFaction.SetTexture(skinData.url, skinData.npc, skinData.player);
        }

    }

}
