package riverland.dev.riverland;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.mcmonkey.sentinel.SentinelIntegration;

public class RiverlandIgnoresSentinelIntegration extends SentinelIntegration
{
    @Override
    public String getTargetHelp()
    {
        return "factionIgnores:faction";
    }

    @Override
    public String[] getTargetPrefixes() {
        return new String[] { "factionIgnore"};
    }


    @Override
    public boolean isTarget(LivingEntity ent, String prefix, String value)
    {
        if (CitizensAPI.getNPCRegistry().isNPC(ent))
        {
            return true;
        }
        if (ent instanceof Player)
        {
            FPlayer facPlayer = FPlayers.getInstance().getByPlayer((Player)ent);
            Faction ourFac = Factions.getInstance().getBestTagMatch(value);
            if (ourFac == facPlayer)
                return false;
        }

        return true;
    }
}
