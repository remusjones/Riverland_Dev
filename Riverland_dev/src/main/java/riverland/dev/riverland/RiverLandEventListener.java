package riverland.dev.riverland;

import com.destroystokyo.paper.event.block.TNTPrimeEvent;
import com.destroystokyo.paper.event.entity.CreeperIgniteEvent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.minecraft.server.v1_15_R1.EntityLiving;
import net.minecraft.server.v1_15_R1.SpawnerCreature;
import org.apache.commons.lang.math.RandomUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.data.type.TNT;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftCreeper;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.entity.minecart.SpawnerMinecart;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import java.lang.reflect.Array;
import java.util.*;


public class RiverLandEventListener implements Listener
{

    ArrayList<UUID> customExplodingCreepers = new ArrayList<>();
    ArrayList<Egg> thrownCreeperEggs = new ArrayList<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (event != null && event.getItem()!=null) {

            if (event.getItem().getType() == Material.EGG && event.getItem().containsEnchantment(Enchantment.DAMAGE_ALL)) {
                // egg has been yeeted
                if (event.getAction().compareTo(Action.RIGHT_CLICK_AIR) == 0 || event.getAction().compareTo(Action.RIGHT_CLICK_BLOCK) == 0) {
                    Player player = event.getPlayer();
                    Location loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
                    Egg egg = player.getWorld().spawn(loc, Egg.class);
                    egg.setShooter(player);
                    egg.setVelocity(player.getEyeLocation().getDirection().multiply(2));
                    thrownCreeperEggs.add(egg);
                    if (player.getGameMode() == GameMode.SURVIVAL) {
                        ItemStack stack = event.getItem();
                        stack.setAmount((event.getItem().getAmount() - 1));
                        player.getInventory().setItemInMainHand(stack);
                    }
                    event.setCancelled(true);
                }
            }
        }
    }
    @EventHandler
    public void onEggLand(ProjectileHitEvent event)
    {
        if (event != null && event.getEntity() != null) {
            if (event.getEntity().getType() == EntityType.EGG) {
                if (thrownCreeperEggs.contains(event.getEntity())) {
                    if (event.getHitBlock() != null) {
                        CraftCreeper myCreeper = (CraftCreeper) Riverland.CreeperTypeInstance.spawn(new Location((org.bukkit.World) event.getHitBlock().getLocation().getWorld(), event.getHitBlock().getLocation().getX(), event.getHitBlock().getLocation().getY(), event.getHitBlock().getLocation().getZ()));
                        customExplodingCreepers.add(myCreeper.getUniqueId());
                        myCreeper.setPowered(true);
                        Riverland._Instance.SetEntityRandomName(myCreeper);
                        myCreeper.setMaxFuseTicks(30);
                        thrownCreeperEggs.remove(event.getEntity());
                    } else if (event.getHitEntity() != null) {
                        CraftCreeper myCreeper = (CraftCreeper) Riverland.CreeperTypeInstance.spawn(new Location((org.bukkit.World) event.getHitEntity().getLocation().getWorld(), event.getHitEntity().getLocation().getX(), event.getHitEntity().getLocation().getY(), event.getHitEntity().getLocation().getZ()));
                        customExplodingCreepers.add(myCreeper.getUniqueId());
                        myCreeper.setPowered(true);
                        Riverland._Instance.SetEntityRandomName(myCreeper);
                        myCreeper.setMaxFuseTicks(30);
                        thrownCreeperEggs.remove(event.getEntity());
                    }
                }
            }
        }
    }

    // This method checks for incoming players and sends them a message
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.isOp())
        {
            // check if tickting is running..
            if (Riverland._InstanceRiverLandTicket._IsTaskRunning)
            {
                int size  =  Riverland._InstanceRiverLandTicket.storedTicketIssues.size();
                if (size > 0) {

                    net.md_5.bungee.api.chat.TextComponent msg = new net.md_5.bungee.api.chat.TextComponent("There are " + Riverland._InstanceRiverLandTicket.storedTicketIssues.size() + " Tickets Awaiting Completion.");
                    msg.setColor(net.md_5.bungee.api.ChatColor.GOLD);

                    net.md_5.bungee.api.chat.TextComponent msg2 = new net.md_5.bungee.api.chat.TextComponent("  [Click to View]");
                    msg2.setColor(net.md_5.bungee.api.ChatColor.AQUA);
                    msg2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/opadminhelp display")); // if the user clicks the message, tp them to the coords
                    msg.addExtra(msg2);
                    player.sendMessage(msg);
                }
                else
                {
                    net.md_5.bungee.api.chat.TextComponent msg = new net.md_5.bungee.api.chat.TextComponent("There are " + Riverland._InstanceRiverLandTicket.storedTicketIssues.size() + " Tickets Awaiting Completion.");
                    msg.setColor(net.md_5.bungee.api.ChatColor.GREEN);
                    player.sendMessage(msg);
                }
            }
        }
    }
    @EventHandler
    public void onBlockSpawn(EntitySpawnEvent e)
    {
        if (e.getEntityType().compareTo(EntityType.PRIMED_TNT) == 0)
        {
            Location loc = null;
            double minDist = 999999999f;
            // compare against list..
            Set<Map.Entry<Location, Integer>> map = Riverland._Instance.tntPositions.entrySet();
            for (Map.Entry<Location, Integer> location : map)
            {
                Location entityLoc = e.getLocation();
                if (location.getKey().getWorld() == entityLoc.getWorld())
                {
                    double curDist = location.getKey().distance(e.getLocation());
                    if (curDist < minDist)
                    {
                        minDist = curDist;
                        loc = location.getKey();
                    }
                }
            }

            if (minDist < 0.8)
            {
                if (loc != null)
                {
                    e.getEntity().setMetadata("TNTType", new FixedMetadataValue(Riverland._Instance, "Explosive" + Riverland._Instance.tntPositions.get(loc).intValue()));
                    Riverland._Instance.tntPositions.remove(loc);

                }
            }
        }

    }
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event)
    {
        if (event.getMessage().toLowerCase().contains("wb"))
        {
            String msg = event.getMessage();
            msg = msg.replaceAll("(?i)wb", " ");
            event.setMessage(msg);
        }
    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e)
    {

        //if (event.getItemInHand().getType() == Material.TNT && event.getItemInHand().getItemMeta().hasLore() && ((String)event.getItemInHand().getItemMeta().getLore().get(0)).contains("Explosive")) {
        Material block = e.getBlock().getType();
        if (block == Material.TNT)
        {
            int test = e.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL);
            if (test != 0)
            {
                e.getBlockPlaced().setMetadata("TNTType", new FixedMetadataValue(Riverland._Instance, "Explosive"+test));
                Riverland._Instance.tntPositions.put(e.getBlockPlaced().getLocation(), test);
            }
        }

        if (e.getPlayer().isOp())
        {
            if (e.getItemInHand().getType() == Material.SPAWNER)
            {
                if (e.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL)) {
                    CreatureSpawner spawner = (CreatureSpawner) e.getBlock().getState();
                    CraftEntity entity = (CraftEntity) Riverland.BabyZombieTypeInstance.spawn(e.getPlayer().getLocation());
                    spawner.setSpawnedType(entity.getType());
                    spawner.update(true);
                    entity.remove();
                }
            }
        }
        //if (e.getBlock().getType() == Material.SPAWNER)
        //{
        //    e.getPlayer().sendMessage("Spawner set with custom limit");
        //    CreatureSpawner spawner = (CreatureSpawner)e.getBlock().getState();
        //    // get base spawner cap..
        //    List<Block> spawnersNearby = RiverlandCommands.getNearbyBlocks(e.getBlock().getLocation(), 20);
        //    ArrayList<CreatureSpawner> updateTypeSpawner = new ArrayList<>();
        //    Integer baseMultiplier = 5;
        //    Integer count = 1;
        //    for (Block spawnerBlock : spawnersNearby)
        //    {
        //        CreatureSpawner oldSpawner = (CreatureSpawner)spawnerBlock;
        //        if (oldSpawner.getSpawnedType() == spawner.getSpawnedType() && oldSpawner != spawner)
        //        {
        //            count++;
        //            updateTypeSpawner.add(oldSpawner);
        //        }
        //    }
//
        //    for(CreatureSpawner oldSpawners : updateTypeSpawner)
        //    {
        //        if ((count * 5) > 50) {
        //            oldSpawners.setMaxNearbyEntities(50);
        //        }
        //        else {
        //            oldSpawners.setMaxNearbyEntities(count * 5);
        //        }
        //        oldSpawners.update(true);
        //    }
        //    if (count * 5 > 50)
        //        spawner.setMaxNearbyEntities(50);
        //    else
        //        spawner.setMaxNearbyEntities(count * 5);
//
        //    spawner.update(true);
        //}
    }
    public void DestroyObsidian(Location loc){
        Block b = loc.getBlock();
        if(!(b.getType() == Material.OBSIDIAN)){
            return;
        }else{
            b.setType(Material.AIR);
        }
    }
    float lerp(float a, float b, float f)
    {
        return a + f * (b - a);
    }
    ArrayList<Block> GetObsidianBlocks(int rad, Location loc, List<Block> m_ignoreBlocks)
    {
        ArrayList<Block> obsidian = new ArrayList<>();
        int radius = rad;
        if (Riverland._Instance.IgnoreWater) {

            Block b = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
            if (b == null) {
                return obsidian;
            }
            for (int x = -(radius); x <= radius; x++) {
                for (int y = -(radius); y <= radius; y++) {
                    for (int z = -(radius); z <= radius; z++) {
                        Block testBlock = b.getRelative(x, y, z);
                        Boolean isOk = (testBlock.getType() == Material.OBSIDIAN && (x != 0 || y != 0 || z != 0)) || (testBlock.getType() != Material.BEDROCK && testBlock.getType() != Material.END_PORTAL && testBlock.getType() != Material.END_GATEWAY && (x != 0 || y != 0 || z != 0));
                        if (isOk)
                        {
                            obsidian.add(testBlock);
                        }
                    }
                }
            }
        }
        else {

            Location location = loc;
            Block b = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
            for (int x = -(radius); x <= radius; x++) {
                for (int y = -(radius); y <= radius; y++) {
                    for (int z = -(radius); z <= radius; z++) {

                        Location bLoc = b.getLocation();
                        Block testBlock = b.getRelative(x, y, z);

                        Vector from = bLoc.toVector();
                        Vector to =  testBlock.getLocation().toVector();

                        Vector dir = to.subtract(from);
                        location.setDirection(dir);

                        BlockIterator blocksToAdd = new BlockIterator(location, 0f, radius );
                        Location blockToAdd;
                        int maxTraversal = radius;
                        int currTraversal = 0;
                        while (blocksToAdd.hasNext())
                        {
                            Block hit = blocksToAdd.next();
                            if ( currTraversal >= maxTraversal)
                                break;

                            if (x != 0 || y != 0 || z != 0)
                            {
                                if (hit.getType() == Material.AIR)
                                    continue;
                                if(hit.getType() == Material.WATER || hit.getType() == Material.END_PORTAL || hit.getType() == Material.END_GATEWAY || hit.getType() == Material.END_PORTAL_FRAME || hit.getType() == Material.BEDROCK || hit.getType() == Material.LAVA)
                                    break;

                                try
                                {
                                    //if (!obsidian.contains(hit))
                                    {
                                        if (hit.getType() == Material.OBSIDIAN) {
                                            if (!obsidian.contains(hit))
                                                obsidian.add(hit);

                                            currTraversal = maxTraversal;
                                            break;
                                        }else
                                            obsidian.add(hit);
                                        //}else
                                        //    obsidian.add(hit);
                                    }
                                }catch (Exception exc)
                                {
                                    Riverland._Instance.getServer().getLogger().warning(exc.toString());
                                }
                            }

                            currTraversal++;

                        }
                    }
                }
            }
        }
        return obsidian;
    }

    public boolean randomBoolean(){
        return Math.random() < Riverland._Instance.tntBreakChance;
    }
    public boolean randomBoolean(float customChance){
        return Math.random() < customChance;
    }
    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event)
    {
        if ((event.getDamager().isOp() && event.getDamager() instanceof Player) || event.getDamager().hasPermission("essentials.togglejail"))
        {
            // see if damage is stick..
            Player player = (Player)event.getDamager();
            if (event.getEntity() instanceof Player)
            {
                // get the damaged entity..
                if (player.getInventory().getItemInMainHand().getType() == Material.STICK)
                {

                    if (player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.DAMAGE_ALL))
                    {
                        Player vic = (Player)event.getEntity();
                        int vanishingCurseLevel = player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL);
                        // 1m 15m 1d 1h 1mo 1w 1y 3h
                        ///jail Tripinary spawnjail 1m
                        // create string..
                        net.md_5.bungee.api.chat.TextComponent name = new net.md_5.bungee.api.chat.TextComponent(player.getName()); // put name
                        name.setColor(net.md_5.bungee.api.ChatColor.DARK_BLUE); // set blue
                        net.md_5.bungee.api.chat.TextComponent middle = new net.md_5.bungee.api.chat.TextComponent(" Has just jailed "); // put name
                        middle.setColor(net.md_5.bungee.api.ChatColor.GREEN); // set blue
                        net.md_5.bungee.api.chat.TextComponent vicName = new net.md_5.bungee.api.chat.TextComponent(vic.getName()); // put name
                        vicName.setColor(net.md_5.bungee.api.ChatColor.RED); // set blue
                        net.md_5.bungee.api.chat.TextComponent endWord = new net.md_5.bungee.api.chat.TextComponent(" For "); // put name
                        endWord.setColor(net.md_5.bungee.api.ChatColor.GREEN); // set blue
                        net.md_5.bungee.api.chat.TextComponent time = new net.md_5.bungee.api.chat.TextComponent("1m"); // put name
                        time.setColor(net.md_5.bungee.api.ChatColor.GOLD); // set blue

                        boolean success = !vic.isOp() || event.getDamager().hasPermission("essentials.jail.exempt") == false;
                        if (success) {
                            if (vanishingCurseLevel == 1) {
                                ///jail Tripinary spawnjail
                                player.getServer().dispatchCommand(player.getServer().getConsoleSender(), "jail " + vic.getName() + " spawnJail " + " 1m");
                                time.setText("1m");
                            } else if (vanishingCurseLevel == 2) {
                                ///jail Tripinary spawnjail

                                player.getServer().dispatchCommand(player.getServer().getConsoleSender(), "jail " + vic.getName() + " spawnJail " + " 15m");
                                time.setText("15m");
                            } else if (vanishingCurseLevel == 3) {
                                ///jail Tripinary spawnjail
                                player.getServer().dispatchCommand(player.getServer().getConsoleSender(), "jail " + vic.getName() + " spawnJail " + " 1h");
                                time.setText("1h");
                            } else if (vanishingCurseLevel > 3) {
                                ///jail Tripinary spawnjail
                                player.getServer().dispatchCommand(player.getServer().getConsoleSender(), "jail " + vic.getName() + " spawnJail " + " 3h");
                                time.setText("3h");
                            }

                            // construct string
                            // player
                            // play sound ..
                            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 100, 4);
                            vic.getWorld().playSound(vic.getLocation(), Sound.ENTITY_ITEM_PICKUP, 100, 4);
                            name.addExtra(middle); // has just jailed
                            name.addExtra(vicName); // victim name
                            name.addExtra(endWord); // for
                            name.addExtra(time); // time
                            player.getServer().broadcast(name);
                        } else
                        {
                            player.getWorld().playSound(player.getLocation(), Sound.ENTITY_CREEPER_HURT, 100, 4);
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void onCreeperExplode(ExplosionPrimeEvent  event)
    {
        if (customExplodingCreepers.contains(event.getEntity().getUniqueId()))
        {
            event.getEntity() .getLocation().getWorld().createExplosion(event.getEntity() .getLocation(), (float)Riverland._Instance.tntRadiusLow);
            event.getEntity().remove();
            customExplodingCreepers.remove(  event.getEntity().getUniqueId());
            event.setCancelled(true);

        }
    }


    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event)
    {



        if (event.getEntity() instanceof TNTPrimed)
        {
            // compare tnt enchants..
            TNTPrimed tnt =  (TNTPrimed)event.getEntity();

            if (event.getEntity().hasMetadata("TNTType"))
            {
                String compare = event.getEntity().getMetadata("TNTType").get(0).asString();
                if (compare.contains("Explosive1"))
                {
                    tnt.getLocation().getWorld().createExplosion(tnt.getLocation(), (float)Riverland._Instance.tntRadiusLow);

                    event.setCancelled(true);
                    return;
                }else if (compare.contains("Explosive2"))
                {
                    tnt.getLocation().getWorld().createExplosion(tnt.getLocation(), (float)Riverland._Instance.tntRadiusDefault);
                    event.setCancelled(true);
                    return;
                }else if (compare.contains("Explosive3"))
                {
                    tnt.getLocation().getWorld().createExplosion(tnt.getLocation(), (float)Riverland._Instance.tntRadiusHigh);
                    event.setCancelled(true);
                    return;
                }else if (compare.contains("Explosive4"))
                {
                    if (Riverland._Instance.IgnoreWater) {
                        tnt.getLocation().getWorld().createExplosion(tnt.getLocation(), (float) Riverland._Instance.tntRadiusLow);
                        ArrayList<Block> blocks = GetObsidianBlocks((int) Riverland._Instance.tntBunkerBusterRange, tnt.getLocation(), event.blockList());
                        {
                            for (Block block : blocks) {
                                // roll chance..
                                if (randomBoolean())
                                    block.setType(Material.AIR);
                            }
                        }
                    }
                    else {
                        tnt.getLocation().getWorld().createExplosion(tnt.getLocation(), 0);
                        ArrayList<Block> blocks = GetObsidianBlocks((int) Riverland._Instance.tntBunkerBusterRange, tnt.getLocation(), event.blockList());

                        for (Block block : blocks)
                        {
                            // roll chance..
                            if (randomBoolean() && block.getType() == Material.OBSIDIAN)
                                block.setType(Material.AIR);
                            else if (block.getType() != Material.OBSIDIAN)
                                block.setType(Material.AIR);
                        }


                    }

                    event.setCancelled(true);
                    return;
                }

            }
            tnt.getLocation().getWorld().createExplosion(tnt.getLocation(), (float)Riverland._Instance.tntRadiusDefault);
            event.setCancelled(true);
        }

    }
    @EventHandler
    public void onBlockBroken(BlockBreakEvent event)
    {
        if (Riverland._Instance.tntPositions.containsKey(event.getBlock().getLocation()))
        {
            int level = Riverland._Instance.tntPositions.get(event.getBlock().getLocation());
            Location pos = event.getBlock().getLocation();
            String compare = event.getBlock().getMetadata("TNTType").get(0).asString();
            ItemStack testEnchant = new ItemStack (Material.TNT, 1);
            ItemMeta testEnchantMeta = testEnchant.getItemMeta();
            testEnchantMeta.addEnchant(Enchantment.DAMAGE_ALL, level, true);
            testEnchant.setItemMeta(testEnchantMeta);
            pos.getWorld().dropItem(pos, testEnchant);
            event.setDropItems(false);
            Riverland._Instance.tntPositions.remove(event.getBlock().getLocation());
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent player)
    {
        if (AIArenaEvent.activePlayers.contains(player.getPlayer()))
        {
            AIArenaEvent.blackListedPlayers.add(player.getPlayer());
            AIArenaEvent.activePlayers.remove(player.getPlayer());
            AIArenaEvent.joinedPlayers.remove(player.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent respawn)
    {
       // if (AIArenaEvent.blackListedPlayers.contains(respawn.getPlayer()) || AIArenaEvent.activePlayers.contains(respawn.getPlayer())) {
       //     Riverland._Instance.spectatorMode.AddPlayerToSpectator(respawn.getPlayer());
       //     respawn.getPlayer().sendMessage("You have been added to Spectator, type /leave to return back to survival");
       // }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Block pos = event.getEntity().getLocation().getBlock();
        String message = ChatColor.RED + "Oops, you've died at: " + ChatColor.GOLD.toString() + "(X = " + pos.getX() + "," + " Y = " + pos.getY() + ", Z = " + pos.getZ() + ")";
        event.getEntity().sendMessage(message);


        if (AIArenaEvent.activePlayers.contains(event.getEntity()) || AIArenaEvent.joinedPlayers.containsKey(event.getEntity())) {


            AIArenaEvent.blackListedPlayers.add(event.getEntity());
            AIArenaEvent.activePlayers.remove(event.getEntity());
            AIArenaEvent.joinedPlayers.remove(event.getEntity());


            for (Player p : AIArenaEvent.activePlayers) {
                net.md_5.bungee.api.chat.TextComponent message2 = new net.md_5.bungee.api.chat.TextComponent("Player " + event.getEntity().getName() + " Has been defeated");
                message2.setColor(net.md_5.bungee.api.ChatColor.DARK_RED);
                p.sendMessage(message2);

                if (AIArenaEvent.activePlayers.size() <= 5) {
                    net.md_5.bungee.api.chat.TextComponent message3 = new net.md_5.bungee.api.chat.TextComponent((AIArenaEvent.activePlayers.size()) + " Players Remain");
                    message3.setColor(net.md_5.bungee.api.ChatColor.DARK_AQUA);
                    p.sendMessage(message3);

                }
            }
        }


        // loop players on server..
        for(Player player : Bukkit.getOnlinePlayers())
        {
            if (player.isOp()) // check if player is operator
            {
                net.md_5.bungee.api.chat.TextComponent message2 = new net.md_5.bungee.api.chat.TextComponent( "Player " + event.getEntity().getName()  + " Has Died  " );
                message2.setColor( net.md_5.bungee.api.ChatColor.GOLD );

                net.md_5.bungee.api.chat.TextComponent click = new net.md_5.bungee.api.chat.TextComponent( " [Click to Teleport]" );

                player.sendMessage("World Named: " + event.getEntity().getPlayer().getWorld().getName());
                // tp command
                click.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/riverland teleport" + " " + event.getEntity().getPlayer().getWorld().getName() +" " +  pos.getX() + " " + pos.getY() + " " + pos.getZ())); // if the user clicks the message, tp them to the coords

                click.setColor( net.md_5.bungee.api.ChatColor.AQUA );
                message2.addExtra( click );

                player.sendMessage(message2);
            }
        }
    }
}
