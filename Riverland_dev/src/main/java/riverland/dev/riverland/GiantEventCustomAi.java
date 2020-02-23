package riverland.dev.riverland;

import com.destroystokyo.paper.entity.Pathfinder;
import org.bukkit.Bukkit;
import org.bukkit.entity.Giant;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;


import java.util.ArrayList;

public class GiantEventCustomAi
{
    ArrayList<Player> targets = new ArrayList<>();
    boolean isDead = false;
    Giant giant;
    Zombie pilot;
    Integer aggroRange = 400;
    Integer attackRange = 20;
    Integer damagePerHit = 20;
    Pathfinder pathfinder;
    int attackCooldown = 0;
    int attackCooldownCap = 60;
    GiantEventCustomAi(ArrayList<Player> players, Integer maxHealth, Integer maxDamage, Giant entity, Zombie pilotZombie, Pathfinder myPathfinder)
    {
        targets = players;
        pilot = pilotZombie;
        giant = entity;
        pathfinder = myPathfinder;
        damagePerHit = maxDamage;
    }

    boolean TryAttackPlayer(Player player) // attempts to attack nearest player
    {


        double dist = giant.getLocation().distanceSquared(player.getLocation());
        if (dist < aggroRange)
        {
            giant.setTarget(player);
            if (dist < attackRange)
            {
                giant.setTarget(giant);
                // swing, send player anim packets
                if (attackCooldown<= 0)
                {
                    player.damage(damagePerHit, this.giant);
                    attackCooldown = attackCooldownCap;
                }
            }
        }
        return true;

    }
    void PlayerAnimation()
    {

      //  giant.setAI(true);
        //((LivingEntity)giant).
    }
    Player FindNearestPlayer()
    {
        Player closestPlayer = null;
        double max = aggroRange;
        for (Player p : targets)
        {
            double dist = giant.getLocation().distanceSquared(p.getLocation());
            if (giant.getLocation().distanceSquared(p.getLocation()) < max)
            {
                closestPlayer = p;
                max = dist;
            }
        }
        return closestPlayer;
    }
    public void Run()
    {
        Player closestPlayer= FindNearestPlayer();
        if (closestPlayer != null)
        {

        }
        attackCooldown--;
    }





}
