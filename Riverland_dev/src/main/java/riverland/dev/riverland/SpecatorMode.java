package riverland.dev.riverland;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SpecatorMode
{
    boolean isSpectatorAvailable = false;
    ArrayList<Player> currentSpectators = new ArrayList<>();
    ArrayList<Player> playersToSpectate = new ArrayList<>();
    Player currentSpectatedPlayer = null;

    public void SetSpecatorAvaiable(boolean isAvailable)
    {
        isSpectatorAvailable = isAvailable;
        if(!isSpectatorAvailable)
        {
            ClearPlayers();
        }
    }
    public void AddPlayerToSpectator(Player player)
    {
        if (!currentSpectators.contains(player))
        {
            currentSpectators.add(player);
            player.setGameMode(GameMode.SPECTATOR);
            player.setSpectatorTarget(currentSpectatedPlayer);
        }
    }
    public void UpdateSpectatorPlayers(ArrayList<Player> activePlayers)
    {
        playersToSpectate = activePlayers;
        if (activePlayers.size() > 0)
        {
            currentSpectatedPlayer = playersToSpectate.get(0);
            for(Player player : currentSpectators)
            {
                player.setSpectatorTarget(currentSpectatedPlayer);
            }
        }
    }
    public void RemovePlayerFromSpectator(Player player)
    {
        if (currentSpectators.contains(player))
        {
            player.setGameMode(GameMode.SURVIVAL);
            currentSpectators.remove(player);
        }
    }
    public void ClearPlayers()
    {
        for(Player player : currentSpectators)
        {
            player.setGameMode(GameMode.SURVIVAL);
        }
        currentSpectators.clear();
    }
}
