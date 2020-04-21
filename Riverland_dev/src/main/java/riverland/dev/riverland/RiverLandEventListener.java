package riverland.dev.riverland;

import com.SirBlobman.combatlogx.api.ICombatLogX;
import com.SirBlobman.combatlogx.api.event.PlayerPreTagEvent;
import com.SirBlobman.combatlogx.api.event.PlayerUntagEvent;
import com.SirBlobman.combatlogx.api.utility.ICombatManager;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.association.RegionAssociable;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.md_5.bungee.api.chat.ClickEvent;
import net.minecraft.server.v1_15_R1.BlockShulkerBox;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.ShulkerBox;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftCreeper;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.BlockStateMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SpawnEggMeta;
import org.bukkit.material.SpawnEgg;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;
import org.bukkit.event.EventPriority;
import riverland.dev.riverland.Riverland;

import java.lang.reflect.Array;
import java.util.*;

@Deprecated
public class RiverLandEventListener implements Listener {

    static public boolean isRunningMaintenance = false;
    ArrayList<UUID> customExplodingCreepers = new ArrayList<>();
    ArrayList<Egg> thrownCreeperEggs = new ArrayList<>();
    List<UUID> handledPlayerIds = new ArrayList<>();


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event != null && event.getItem() != null) {

            if (event.getPlayer().getWorld().getEnvironment() == World.Environment.NETHER && event.getClickedBlock() != null && event.getClickedBlock().getType() != null && event.getClickedBlock().getType() == Material.SPAWNER) {
                CreatureSpawner spawner = (CreatureSpawner) event.getClickedBlock().getState();
                if (spawner != null && spawner.getSpawnedType() == EntityType.BLAZE) {
                    event.getPlayer().sendMessage("You cannot do that here.");
                    event.setCancelled(true);
                }
            }
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

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerPlaceEgg(PlayerInteractEvent event) {
        if (event != null && event.getPlayer().getInventory().getItemInMainHand() != null) {
            ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

            if (item.getType() == Material.PIG_SPAWN_EGG) {
                if (event.getPlayer().getWorld().getEnvironment() == World.Environment.NETHER && event.getClickedBlock().getType() == Material.SPAWNER) {
                    event.getPlayer().sendMessage("You cannot do that here.");
                    event.setCancelled(true);
                }

                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Riverland._Instance, new Runnable() {
                    public void run() {
                        item.setAmount(item.getAmount() - 1);
                    }
                }, 1);
                // sched change
            }
        }
    }

    @EventHandler
    public void onShulkEnderchest(InventoryClickEvent event) {
        // check if the player is moving from current chest to ender
        if (event != null) {
            Inventory playerInv = event.getClickedInventory();
            Inventory chestInv = event.getInventory();
            if (chestInv.getType().equals(InventoryType.ENDER_CHEST)) {

                if (event.getAction() == InventoryAction.HOTBAR_SWAP) {
                    event.setCancelled(true);
                }
                if (event.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD) {
                    event.setCancelled(true);
                }

                if (playerInv == chestInv) // ignore?
                    return;

                if (event.getCurrentItem() != null) {
                    ItemStack clicked = event.getCurrentItem();
                    String clickMaterialString = clicked.getType().toString().toLowerCase();

                    if (clickMaterialString.contains("shulker")) // force cancel
                    {
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
    public boolean CanExplode(Location loc)
    {
        RegionContainer container = com.sk89q.worldguard.WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(BukkitAdapter.adapt(loc));
        return set.testState(null, Riverland.CustomExplosionFlag);
    }

    @EventHandler
    public void onEggLand(ProjectileHitEvent event) {

        boolean canSpawn = CanExplode(event.getEntity().getLocation());


        if (event != null && event.getEntity() != null) {
            if (event.getEntity().getType() == EntityType.EGG) {
                if (thrownCreeperEggs.contains(event.getEntity())) {
                    if (event.getHitBlock() != null) {
                        if (canSpawn) {
                            CraftCreeper myCreeper = (CraftCreeper) Riverland.CreeperTypeInstance.spawn(new Location((org.bukkit.World) event.getHitBlock().getLocation().getWorld(), event.getHitBlock().getLocation().getX(), event.getHitBlock().getLocation().getY(), event.getHitBlock().getLocation().getZ()));
                            customExplodingCreepers.add(myCreeper.getUniqueId());
                            myCreeper.setPowered(true);
                            Riverland._Instance.SetEntityRandomName(myCreeper);
                            myCreeper.setMaxFuseTicks(30);
                        }
                        thrownCreeperEggs.remove(event.getEntity());
                    } else if (event.getHitEntity() != null) {
                        if (canSpawn) {
                            CraftCreeper myCreeper = (CraftCreeper) Riverland.CreeperTypeInstance.spawn(new Location((org.bukkit.World) event.getHitEntity().getLocation().getWorld(), event.getHitEntity().getLocation().getX(), event.getHitEntity().getLocation().getY(), event.getHitEntity().getLocation().getZ()));
                            customExplodingCreepers.add(myCreeper.getUniqueId());
                            myCreeper.setPowered(true);
                            Riverland._Instance.SetEntityRandomName(myCreeper);
                            myCreeper.setMaxFuseTicks(30);
                        }

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
        if (player.isOp()) {
            // check if tickting is running..
            if (Riverland._InstanceRiverLandTicket._IsTaskRunning) {
                int size = Riverland._InstanceRiverLandTicket.storedTicketIssues.size();
                if (size > 0) {

                    net.md_5.bungee.api.chat.TextComponent msg = new net.md_5.bungee.api.chat.TextComponent("There are " + Riverland._InstanceRiverLandTicket.storedTicketIssues.size() + " Tickets Awaiting Completion.");
                    msg.setColor(net.md_5.bungee.api.ChatColor.GOLD);

                    net.md_5.bungee.api.chat.TextComponent msg2 = new net.md_5.bungee.api.chat.TextComponent("  [Click to View]");
                    msg2.setColor(net.md_5.bungee.api.ChatColor.AQUA);
                    msg2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/opadminhelp display")); // if the user clicks the message, tp them to the coords
                    msg.addExtra(msg2);
                    player.sendMessage(msg);
                }
            }
        }
        if (handledPlayerIds.contains(event.getPlayer().getUniqueId()))
        {
            handledPlayerIds.remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler
    public void onBlockSpawn(EntitySpawnEvent e) {
        if (e.getEntityType().compareTo(EntityType.PRIMED_TNT) == 0) {
            Location loc = null;
            double minDist = 999999999f;
            // compare against list..
            Set<Map.Entry<Location, Integer>> map = Riverland._Instance.tntPositions.entrySet();
            for (Map.Entry<Location, Integer> location : map) {
                Location entityLoc = e.getLocation();
                if (location.getKey().getWorld() == entityLoc.getWorld()) {
                    double curDist = location.getKey().distance(e.getLocation());
                    if (curDist < minDist) {
                        minDist = curDist;
                        loc = location.getKey();
                    }
                }
            }

            if (minDist < 0.8) {
                if (loc != null) {
                    e.getEntity().setMetadata("TNTType", new FixedMetadataValue(Riverland._Instance, "Explosive" + Riverland._Instance.tntPositions.get(loc).intValue()));
                    Riverland._Instance.tntPositions.remove(loc);

                }
            }
        }

    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        //if (event.getItemInHand().getType() == Material.TNT && event.getItemInHand().getItemMeta().hasLore() && ((String)event.getItemInHand().getItemMeta().getLore().get(0)).contains("Explosive")) {
        if (e != null && e.getBlock() != null) {
            Material block = e.getBlock().getType();
            if (block == Material.TNT) {
                int test = e.getItemInHand().getEnchantmentLevel(Enchantment.DAMAGE_ALL);
                if (test != 0) {
                    e.getBlockPlaced().setMetadata("TNTType", new FixedMetadataValue(Riverland._Instance, "Explosive" + test));
                    Riverland._Instance.tntPositions.put(e.getBlockPlaced().getLocation(), test);
                }
            }
        }

        if (e.getPlayer().isOp()) {
            if (e.getItemInHand().getType() == Material.SPAWNER) {
                if (e.getItemInHand().containsEnchantment(Enchantment.DAMAGE_ALL)) {
                    CreatureSpawner spawner = (CreatureSpawner) e.getBlock().getState();
                    CraftEntity entity = (CraftEntity) Riverland.BabyZombieTypeInstance.spawn(e.getPlayer().getLocation());
                    //spawner.setSpawnedType(((EntityType) (EntityTypes < CustomEntityBabyZombies >));
                    spawner.update(true);
                    entity.remove();
                }
            }
        }

    }

    public void DestroyObsidian(Location loc) {
        Block b = loc.getBlock();
        if (!(b.getType() == Material.OBSIDIAN)) {
            return;
        } else {
            b.setType(Material.AIR);
        }
    }

    float lerp(float a, float b, float f) {
        return a + f * (b - a);
    }

    ArrayList<Block> GetObsidianBlocks(int rad, Location loc, List<Block> m_ignoreBlocks) {
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
                        if (isOk) {
                            obsidian.add(testBlock);
                        }
                    }
                }
            }
        } else {

            Location location = loc;
            Block b = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
            for (int x = -(radius); x <= radius; x++) {
                for (int y = -(radius); y <= radius; y++) {
                    for (int z = -(radius); z <= radius; z++) {

                        Location bLoc = b.getLocation();
                        Block testBlock = b.getRelative(x, y, z);

                        Vector from = bLoc.toVector();
                        Vector to = testBlock.getLocation().toVector();

                        Vector dir = to.subtract(from);
                        location.setDirection(dir);

                        BlockIterator blocksToAdd = new BlockIterator(location, 0f, radius);
                        Location blockToAdd;
                        int maxTraversal = radius;
                        int currTraversal = 0;
                        while (blocksToAdd.hasNext()) {
                            Block hit = blocksToAdd.next();
                            if (currTraversal >= maxTraversal)
                                break;

                            if (x != 0 || y != 0 || z != 0) {
                                if (hit.getType() == Material.AIR)
                                    continue;
                                if (hit.getType() == Material.WATER || hit.getType() == Material.END_PORTAL || hit.getType() == Material.END_GATEWAY || hit.getType() == Material.END_PORTAL_FRAME || hit.getType() == Material.BEDROCK || hit.getType() == Material.LAVA)
                                    break;

                                try {
                                    //if (!obsidian.contains(hit))
                                    {
                                        if (hit.getType() == Material.OBSIDIAN) {
                                            if (!obsidian.contains(hit))
                                                obsidian.add(hit);

                                            currTraversal = maxTraversal;
                                            break;
                                        } else
                                            obsidian.add(hit);
                                        //}else
                                        //    obsidian.add(hit);
                                    }
                                } catch (Exception exc) {
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

    public boolean randomBoolean() {
        return Math.random() < Riverland._Instance.tntBreakChance;
    }

    public boolean randomBoolean(float customChance) {
        return Math.random() < customChance;
    }


    @EventHandler
    public void onCreeperExplode(ExplosionPrimeEvent event) {
        boolean canExplode = CanExplode(event.getEntity().getLocation());

        FLocation location = new FLocation(event.getEntity().getLocation());
        Faction faction = Board.getInstance().getFactionAt(location);
        if (customExplodingCreepers.contains(event.getEntity().getUniqueId())) {
            if (canExplode && !faction.isPeaceful() && !faction.isSafeZone() && !faction.isWarZone())
                event.getEntity().getLocation().getWorld().createExplosion(event.getEntity().getLocation(), (float) Riverland._Instance.tntRadiusLow);
            event.getEntity().remove();
            customExplodingCreepers.remove(event.getEntity().getUniqueId());
            event.setCancelled(true);

        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {

        boolean canExplode = CanExplode(event.getEntity().getLocation());
        if (event.getEntity() instanceof TNTPrimed) {
            // compare tnt enchants..
            TNTPrimed tnt = (TNTPrimed) event.getEntity();
            FLocation location = new FLocation(event.getLocation());
            Faction faction = Board.getInstance().getFactionAt(location);
            if (faction.isPeaceful())
            {
                event.setCancelled(true);
                return;
            }
            if (!faction.isSafeZone() && !faction.isWarZone()){
                if (event.getEntity().hasMetadata("TNTType")) {
                    String compare = event.getEntity().getMetadata("TNTType").get(0).asString();
                    if (compare.contains("Explosive1")) {
                        if (canExplode)
                        {

                            Block block = tnt.getLocation().getWorld().getBlockAt(tnt.getLocation().getBlockX(), tnt.getLocation().getBlockY(),tnt.getLocation().getBlockZ());
                            block.getLocation().getWorld().createExplosion(block.getLocation(), (float) Riverland._Instance.tntRadiusLow);
                        }

                        event.setCancelled(true);
                        return;
                    } else if (compare.contains("Explosive2")) {
                        if (canExplode)
                            tnt.getLocation().getWorld().createExplosion(tnt.getLocation(), (float) Riverland._Instance.tntRadiusDefault);
                        event.setCancelled(true);
                        return;
                    } else if (compare.contains("Explosive3")) {
                        if (canExplode)
                            tnt.getLocation().getWorld().createExplosion(tnt.getLocation(), (float) Riverland._Instance.tntRadiusHigh);
                        event.setCancelled(true);
                        return;
                    } else if (compare.contains("Explosive4")) {
                        if (canExplode) {
                            if (Riverland._Instance.IgnoreWater) {
                                BukkitScheduler scheduler = Riverland._Instance.getServer().getScheduler();
                                scheduler.scheduleSyncDelayedTask(Riverland._Instance, () ->
                                {

                                    tnt.getLocation().getWorld().createExplosion(tnt.getLocation(), (float) Riverland._Instance.tntRadiusLow);
                                    ArrayList<Block> blocks = GetObsidianBlocks((int) Riverland._Instance.tntBunkerBusterRange, tnt.getLocation(), event.blockList());
                                    {
                                        for (Block block : blocks) {
                                            // roll chance..
                                            if (randomBoolean())
                                                block.setType(Material.AIR);
                                        }
                                    }

                                }, 1L);
                            }
                        } else {
                            if (canExplode) {
                                tnt.getLocation().getWorld().createExplosion(tnt.getLocation(), 0);
                                ArrayList<Block> blocks = GetObsidianBlocks((int) Riverland._Instance.tntBunkerBusterRange, tnt.getLocation(), event.blockList());

                                for (Block block : blocks) {
                                    // roll chance..
                                    if (randomBoolean() && block.getType() == Material.OBSIDIAN)
                                        block.setType(Material.AIR);
                                    else if (block.getType() != Material.OBSIDIAN)
                                        block.setType(Material.AIR);
                                }
                            }

                        }

                        event.setCancelled(true);
                        return;
                    }
                }
            }
        }

    }

    @EventHandler
    public void onBlockBroken(BlockBreakEvent event) {
        if (Riverland._Instance.tntPositions.containsKey(event.getBlock().getLocation())) {
            int level = Riverland._Instance.tntPositions.get(event.getBlock().getLocation());
            Location pos = event.getBlock().getLocation();
            String compare = event.getBlock().getMetadata("TNTType").get(0).asString();
            ItemStack testEnchant = new ItemStack(Material.TNT, 1);
            ItemMeta testEnchantMeta = testEnchant.getItemMeta();
            testEnchantMeta.addEnchant(Enchantment.DAMAGE_ALL, level, true);
            testEnchant.setItemMeta(testEnchantMeta);
            pos.getWorld().dropItem(pos, testEnchant);
            event.setDropItems(false);
            Riverland._Instance.tntPositions.remove(event.getBlock().getLocation());
        } else if (event.getPlayer().getGameMode() == GameMode.SURVIVAL && event.getBlock().getType().compareTo(Material.SPAWNER) == 0 && event.getPlayer().getWorld().getEnvironment() == World.Environment.NETHER) {
            // if (!event.getPlayer().isOp())
            {
                CreatureSpawner type = ((CreatureSpawner) event.getBlock().getState());
                if (type.getSpawnedType() == EntityType.BLAZE) {
                    event.getPlayer().sendMessage("You cant mine this spawner in the Nether!");
                    event.setCancelled(true);
                }
            }
        }


    }

    public boolean isInCombat(Player player) {
        // Make sure to check that CombatLogX is enabled before using it for anything.
        ICombatLogX plugin = (ICombatLogX) Bukkit.getPluginManager().getPlugin("CombatLogX");
        ICombatManager combatManager = plugin.getCombatManager();

        return combatManager.isInCombat(player);
    }
  // @EventHandler(priority=EventPriority.HIGHEST)
  // public void onPlayerQuit(PlayerQuitEvent event)
  // {
  //     ICombatLogX plugin = (ICombatLogX) Bukkit.getPluginManager().getPlugin("CombatLogX");
  //     ICombatManager combatManager = plugin.getCombatManager();

  //     if (!handledPlayerIds.contains(event.getPlayer().getUniqueId()))
  //     {
  //         combatManager.tag(event.getPlayer(), event.getPlayer(), PlayerPreTagEvent.TagType.UNKNOWN, PlayerPreTagEvent.TagReason.ATTACKED);
  //         handledPlayerIds.add(event.getPlayer().getUniqueId());
  //     }

  // }
  // @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
  // public void onUntag(PlayerUntagEvent e) {
  //     Player player = e.getPlayer();
  //     PlayerUntagEvent.UntagReason untagReason = e.getUntagReason();
  //     if (e.getUntagReason() == PlayerUntagEvent.UntagReason.QUIT)
  //     {
  //         handledPlayerIds.add(e.getPlayer().getUniqueId());
  //     }
  // }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Block pos = event.getEntity().getLocation().getBlock();
        String message = ChatColor.RED + "Oops, you've died at: " + ChatColor.GOLD.toString() + "(X = " + pos.getX() + "," + " Y = " + pos.getY() + ", Z = " + pos.getZ() + ")";
        event.getEntity().sendMessage(message);

        // loop players on server..
        for(Player player : Bukkit.getOnlinePlayers())
        {
            if (player.isOp()) // check if player is operator
            {

                net.md_5.bungee.api.chat.TextComponent message2 = new net.md_5.bungee.api.chat.TextComponent( "Player " + event.getEntity().getName()  + " Has Died");
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
