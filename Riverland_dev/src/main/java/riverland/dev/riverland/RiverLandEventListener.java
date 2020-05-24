package riverland.dev.riverland;

<<<<<<< HEAD
import com.SirBlobman.combatlogx.api.ICombatLogX;
import com.SirBlobman.combatlogx.api.event.PlayerPreTagEvent;
import com.SirBlobman.combatlogx.api.event.PlayerUntagEvent;
import com.SirBlobman.combatlogx.api.utility.ICombatManager;
import com.massivecraft.factions.*;
import com.massivecraft.factions.perms.Relation;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.association.RegionAssociable;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import net.md_5.bungee.api.chat.ClickEvent;
import net.minecraft.server.v1_15_R1.BlockShulkerBox;
import net.raidstone.wgevents.events.RegionEnteredEvent;
import net.raidstone.wgevents.events.RegionsLeftEvent;
import org.bukkit.*;
import org.bukkit.block.*;
import org.bukkit.block.data.type.Chest;
import org.bukkit.block.data.type.Slab;
=======
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftCreeper;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.*;
<<<<<<< HEAD
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
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.material.SpawnEgg;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;
import org.bukkit.event.EventPriority;
import riverland.dev.riverland.Riverland;

import java.lang.reflect.Array;
import java.util.*;
import java.util.logging.Level;


public class RiverLandEventListener implements Listener {
=======
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;
import org.bukkit.event.EventPriority;

import java.util.*;


public class RiverLandEventListener implements Listener
{
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b

    static public boolean isRunningMaintenance = false;
    ArrayList<UUID> customExplodingCreepers = new ArrayList<>();
    ArrayList<Egg> thrownCreeperEggs = new ArrayList<>();
<<<<<<< HEAD
    List<UUID> handledPlayerIds = new ArrayList<>();


    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event != null && event.getItem() != null) {

            if (event.getPlayer().getWorld().getEnvironment() == World.Environment.NETHER && event.getClickedBlock() != null && event.getClickedBlock().getType() != null && event.getClickedBlock().getType() == Material.SPAWNER) {
                CreatureSpawner spawner = (CreatureSpawner) event.getClickedBlock().getState();
                if (spawner != null && spawner.getSpawnedType() == EntityType.BLAZE) {
=======

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (event != null && event.getItem()!=null)
        {

            if (event.getPlayer().getWorld().getEnvironment() == World.Environment.NETHER && event.getClickedBlock() != null&& event.getClickedBlock().getType() != null && event.getClickedBlock().getType() == Material.SPAWNER)
            {
                CreatureSpawner spawner = (CreatureSpawner) event.getClickedBlock().getState();
                if (spawner != null && spawner.getSpawnedType() == EntityType.BLAZE)
                {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                    event.getPlayer().sendMessage("You cannot do that here.");
                    event.setCancelled(true);
                }
            }
<<<<<<< HEAD
            if (event.getItem().getType() == Material.EGG && event.getItem().containsEnchantment(Enchantment.DAMAGE_ALL))
            {
                if (event.getPlayer() == null)
                    return;

                boolean isSponge = false;
                NamespacedKey key = new NamespacedKey(Riverland._Instance, "spongekey");
                ItemMeta itemMeta = event.getItem().getItemMeta();
                if(itemMeta.getCustomTagContainer().hasCustomTag(key , ItemTagType.INTEGER)) {
                    int foundValue = itemMeta.getCustomTagContainer().getCustomTag(key, ItemTagType.INTEGER);
                    if (foundValue == 1)
                        isSponge = true;
                }


                // egg has been yeeted
                if (event.getAction().compareTo(Action.RIGHT_CLICK_AIR) == 0 || event.getAction().compareTo(Action.RIGHT_CLICK_BLOCK) == 0) {
                    FLocation location = new FLocation(event.getPlayer().getLocation());
                    Faction faction = Board.getInstance().getFactionAt(location);
                    FPlayer factionPlayer = FPlayers.getInstance().getByPlayer(event.getPlayer());
                    Relation relation =  factionPlayer.getFaction().getRelationTo(faction);


                    if (faction.isWilderness() || relation == Relation.ENEMY)
                    {
                        Player player = event.getPlayer();
                        Location loc = player.getEyeLocation().toVector().add(player.getLocation().getDirection().multiply(2)).toLocation(player.getWorld(), player.getLocation().getYaw(), player.getLocation().getPitch());
                        Egg egg = player.getWorld().spawn(loc, Egg.class);
                        if (isSponge) {
                            egg.setMetadata("Sponge", new FixedMetadataValue(Riverland._Instance, true));

                            if (faction.isWilderness())
                            {
                                Riverland._Instance.getServer().getLogger().log(Level.INFO, "Player [" + factionPlayer.getFaction().getTag() + "] " + event.getPlayer().getName() + " threw a Sponge Raid Egg inside the wilderness" + " | Coords: " + event.getPlayer().getLocation().toString() );
                            }else
                                Riverland._Instance.getServer().getLogger().log(Level.INFO, "Player [" + factionPlayer.getFaction().getTag()+ "] " + event.getPlayer().getName() + " threw a Sponge Raid Egg inside " + faction.getTag() +"'s land" + " | Coords: " + event.getPlayer().getLocation().toString() );

                        }else
                        {
                            if (faction.isWilderness())
                            {
                                Riverland._Instance.getServer().getLogger().log(Level.INFO, "Player [" + factionPlayer.getFaction().getTag()+ "] " + event.getPlayer().getName() + " threw a Creeper Raid Egg inside the wilderness" + " | Coords: " + event.getPlayer().getLocation().toString() );
                            }else
                                Riverland._Instance.getServer().getLogger().log(Level.INFO, "Player [" + factionPlayer.getFaction().getTag()+ "] " + event.getPlayer().getName() + " threw a Creeper Raid Egg inside " + faction.getTag() +"'s land" + " | Coords: " + event.getPlayer().getLocation().toString() );


                        }
                        egg.setMetadata("Faction", new FixedMetadataValue(Riverland._Instance, factionPlayer.getFactionId() ));
                        egg.setShooter(player);
                        egg.setVelocity(player.getEyeLocation().getDirection().multiply(2));
                        thrownCreeperEggs.add(egg);
                        if (player.getGameMode() == GameMode.SURVIVAL) {
                            ItemStack stack = event.getItem();
                            stack.setAmount((event.getItem().getAmount() - 1));
                            player.getInventory().setItemInMainHand(stack);
                        }
                        event.setCancelled(true);
                    }else {
                        event.getPlayer().sendMessage("You can only do that in Enemy Territory!");
                        event.setCancelled(true);
                    }
                }

=======
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
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
            }
        }

    }

    @EventHandler(priority = EventPriority.LOWEST)
<<<<<<< HEAD
    public void onPlayerPlaceEgg(PlayerInteractEvent event) {
        if (event != null && event.getPlayer().getInventory().getItemInMainHand() != null) {
            ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

            if (item.getType() == Material.PIG_SPAWN_EGG) {
                if (event.getPlayer().getWorld().getEnvironment() == World.Environment.NETHER && event.getClickedBlock().getType() == Material.SPAWNER) {
=======
    public void onPlayerPlaceEgg(PlayerInteractEvent event)
    {
        if (event != null && event.getPlayer().getInventory().getItemInMainHand() != null)
        {
            ItemStack item = event.getPlayer().getInventory().getItemInMainHand();

            if (item.getType() == Material.PIG_SPAWN_EGG)
            {
                if (event.getPlayer().getWorld().getEnvironment() == World.Environment.NETHER && event.getClickedBlock().getType() == Material.SPAWNER)
                {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                    event.getPlayer().sendMessage("You cannot do that here.");
                    event.setCancelled(true);
                }

<<<<<<< HEAD
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Riverland._Instance, new Runnable() {
                    public void run() {
=======
               Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(Riverland._Instance, new Runnable(){
                    public void run(){
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                        item.setAmount(item.getAmount() - 1);
                    }
                }, 1);
                // sched change
            }
        }
    }

    @EventHandler
<<<<<<< HEAD
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
    public void onCustomExplode(BlockExplodeEvent explodeEvent)
    {

        if (Riverland._SilkSpawnerInstance() == null)
            return;

        Map<Location, ItemStack > spawners = new HashMap<>();
        for(Block block : explodeEvent.blockList())
        {
            if (block.getType() == Material.SPAWNER)
            {

                if (block.getWorld().getEnvironment() == World.Environment.NETHER)
                {
                    CreatureSpawner type = ((CreatureSpawner) block.getState());
                    if (type.getSpawnedType() == EntityType.BLAZE)
                        continue;
                }
                String id = Riverland._SilkSpawnerInstance().getSpawnerEntityID(block);
                ItemStack spawnerItem = Riverland._SilkSpawnerInstance().newSpawnerItem(id, id, 1, true);
                spawners.put(block.getLocation(), spawnerItem);
            }
        }
        // wait and spawn item..


        if (spawners.size() > 0)
        {
            Bukkit.getScheduler().scheduleSyncDelayedTask(Riverland._Instance, new Runnable() {
                @Override
                public void run() {
                    Set<Map.Entry<Location, ItemStack>> map = spawners.entrySet();
                    for(Map.Entry<Location, ItemStack> itempair : map)
                    {
                        itempair.getKey().getWorld().dropItemNaturally(itempair.getKey(), itempair.getValue());
                    }
                }

            }, 5L);
        }


    }
    @EventHandler
    public void onEggLand(ProjectileHitEvent event) {

        boolean canSpawn = CanExplode(event.getEntity().getLocation());


=======
    public void onEggLand(ProjectileHitEvent event)
    {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
        if (event != null && event.getEntity() != null) {
            if (event.getEntity().getType() == EntityType.EGG) {
                if (thrownCreeperEggs.contains(event.getEntity())) {
                    if (event.getHitBlock() != null) {
<<<<<<< HEAD
                        if (canSpawn)
                        {
                            // check nbt data, if successful parse metadata tag over to creeper..
                            if (event.getEntity().hasMetadata("Faction"))
                            {
                                boolean isSpongeEgg = event.getEntity().hasMetadata("Sponge");



                                String metaData = event.getEntity().getMetadata("Faction").get(0).asString();
                                // parse the faction p
                                FLocation location = new FLocation(event.getHitBlock().getLocation());
                                Faction otherFaction = Board.getInstance().getFactionAt(location);
                                Faction faction = Factions.getInstance().getFactionById(metaData);
                                if (otherFaction.isWilderness())
                                {
                                    if (!isSpongeEgg) {
                                        CraftCreeper myCreeper = (CraftCreeper) Riverland.CreeperTypeInstance.spawn(new Location((org.bukkit.World) event.getHitBlock().getLocation().getWorld(), event.getHitBlock().getLocation().getX(), event.getHitBlock().getLocation().getY(), event.getHitBlock().getLocation().getZ()));
                                        customExplodingCreepers.add(myCreeper.getUniqueId());
                                        myCreeper.setPowered(true);
                                        Riverland._Instance.SetEntityRandomName(myCreeper);
                                        myCreeper.setPersistent(true);

                                        myCreeper.setMaxFuseTicks(30);
                                        // egg.setMetadata("Faction", new FixedMetadataValue(Riverland._Instance, factionPlayer.getFactionId() ));
                                        myCreeper.setMetadata("Faction", new FixedMetadataValue(Riverland._Instance, metaData));
                                    }else
                                    {
                                        if (event.getHitBlock().getState() instanceof Sign || event.getHitBlock().getBlockData() instanceof Slab)
                                        {
                                         //   if (block.getType() == Material.AIR || block.getType() == Material.CAVE_AIR || block.getType() == Material.WATER || block.getType() == Material.LAVA) {
                                                // do a area crawl
                                                ArrayList<Block> lavaBlocks = GetLavaBlocks(4, event.getHitBlock().getLocation(), null);
                                                for (Block lavaBlock : lavaBlocks) {

                                                    if (lavaBlock != null) {
                                                        lavaBlock.setType(Material.AIR);
                                                    }
                                                }
                                                event.getHitBlock().setType(Material.SPONGE);
                                          //  }
                                        }else {
                                            Block block = event.getHitBlock().getRelative(event.getHitBlockFace());
                                       //     Riverland._Instance.getServer().broadcastMessage(block.getType().toString());
                                            if (block.getType() == Material.AIR || block.getType() == Material.CAVE_AIR || block.getType() == Material.WATER || block.getType() == Material.LAVA || block.getState() instanceof Sign || block.getBlockData() instanceof Slab) {
                                                // do a area crawl
                                                ArrayList<Block> lavaBlocks = GetLavaBlocks(4, block.getLocation(), null);
                                                for (Block lavaBlock : lavaBlocks) {

                                                    if (lavaBlock != null) {
                                                        lavaBlock.setType(Material.AIR);
                                                    }
                                                }
                                                block.setType(Material.SPONGE);
                                            }
                                        }

                                    }

                                }else{

                                    Relation relation =  faction.getRelationTo(otherFaction);

                                    if (relation == Relation.ENEMY)
                                    {
                                        if (otherFaction.getRelationWish(faction) != Relation.ENEMY)
                                        {
                                            otherFaction.setRelationWish(faction, Relation.ENEMY);
                                            for(FPlayer player : otherFaction.getFPlayersWhereOnline(true))
                                            {
                                                player.sendMessage(net.md_5.bungee.api.ChatColor.GREEN + ""+net.md_5.bungee.api.ChatColor.BOLD +  otherFaction.getTag() + net.md_5.bungee.api.ChatColor.RED + " is now" + net.md_5.bungee.api.ChatColor.DARK_RED + net.md_5.bungee.api.ChatColor.BOLD + " Enemy" + net.md_5.bungee.api.ChatColor.RED + " with " + net.md_5.bungee.api.ChatColor.DARK_RED + net.md_5.bungee.api.ChatColor.BOLD + faction.getTag());
                                                player.sendMessage(net.md_5.bungee.api.ChatColor.YELLOW + "Owner: " +net.md_5.bungee.api.ChatColor.RED + faction.getFPlayerAdmin().getNameAndTitle());

                                                otherFaction.addAnnouncement(player, net.md_5.bungee.api.ChatColor.GREEN + ""+net.md_5.bungee.api.ChatColor.BOLD +  otherFaction.getTag() + net.md_5.bungee.api.ChatColor.RED + " is now" + net.md_5.bungee.api.ChatColor.DARK_RED + net.md_5.bungee.api.ChatColor.BOLD + " Enemy" + net.md_5.bungee.api.ChatColor.RED + " with " + net.md_5.bungee.api.ChatColor.DARK_RED + net.md_5.bungee.api.ChatColor.BOLD + faction.getTag());
                                                otherFaction.addAnnouncement(player, net.md_5.bungee.api.ChatColor.YELLOW + "Owner: " +net.md_5.bungee.api.ChatColor.RED + faction.getFPlayerAdmin().getNameAndTitle());
                                            }
                                            for(FPlayer player : otherFaction.getFPlayersWhereOnline(false))
                                            {
                                                otherFaction.addAnnouncement(player, net.md_5.bungee.api.ChatColor.GREEN + ""+net.md_5.bungee.api.ChatColor.BOLD +  otherFaction.getTag() + net.md_5.bungee.api.ChatColor.RED + " is now" + net.md_5.bungee.api.ChatColor.DARK_RED + net.md_5.bungee.api.ChatColor.BOLD + " Enemy" + net.md_5.bungee.api.ChatColor.RED + " with " + net.md_5.bungee.api.ChatColor.DARK_RED + net.md_5.bungee.api.ChatColor.BOLD + faction.getTag());
                                                otherFaction.addAnnouncement(player, net.md_5.bungee.api.ChatColor.YELLOW + "Owner: " +net.md_5.bungee.api.ChatColor.RED + faction.getFPlayerAdmin().getNameAndTitle());
                                            }
                                        }
                                        if (!isSpongeEgg) {
                                            CraftCreeper myCreeper = (CraftCreeper) Riverland.CreeperTypeInstance.spawn(new Location((org.bukkit.World) event.getHitBlock().getLocation().getWorld(), event.getHitBlock().getLocation().getX(), event.getHitBlock().getLocation().getY(), event.getHitBlock().getLocation().getZ()));
                                            customExplodingCreepers.add(myCreeper.getUniqueId());
                                            myCreeper.setPowered(true);
                                            Riverland._Instance.SetEntityRandomName(myCreeper);
                                            myCreeper.setMaxFuseTicks(30);
                                            myCreeper.setMetadata("Faction", new FixedMetadataValue(Riverland._Instance, metaData));
                                        }else
                                        {
                                            if (event.getHitBlock().getState() instanceof Sign || event.getHitBlock().getBlockData() instanceof Slab)
                                            {
                                                //   if (block.getType() == Material.AIR || block.getType() == Material.CAVE_AIR || block.getType() == Material.WATER || block.getType() == Material.LAVA) {
                                                // do a area crawl
                                                ArrayList<Block> lavaBlocks = GetLavaBlocks(4, event.getHitBlock().getLocation(), null);
                                                for (Block lavaBlock : lavaBlocks) {

                                                    if (lavaBlock != null) {
                                                        lavaBlock.setType(Material.AIR);
                                                    }
                                                }
                                                event.getHitBlock().setType(Material.SPONGE);
                                                //  }
                                            }else {
                                                Block block = event.getHitBlock().getRelative(event.getHitBlockFace());
                                                //     Riverland._Instance.getServer().broadcastMessage(block.getType().toString());
                                                if (block.getType() == Material.AIR || block.getType() == Material.CAVE_AIR || block.getType() == Material.WATER || block.getType() == Material.LAVA || block.getState() instanceof Sign || block.getBlockData() instanceof Slab) {
                                                    // do a area crawl
                                                    ArrayList<Block> lavaBlocks = GetLavaBlocks(4, block.getLocation(), null);
                                                    for (Block lavaBlock : lavaBlocks) {

                                                        if (lavaBlock != null) {
                                                            lavaBlock.setType(Material.AIR);
                                                        }
                                                    }
                                                    block.setType(Material.SPONGE);
                                                }
                                            }

                                        }
                                    }
                                }
                            }
                        }
                        thrownCreeperEggs.remove(event.getEntity());
                    } else if (event.getHitEntity() != null) {
                        if (canSpawn)
                        {
                            if (event.getEntity().hasMetadata("Faction"))
                            {
                                boolean isSpongeEgg = event.getEntity().hasMetadata("Sponge");


                                String metaData = event.getEntity().getMetadata("Faction").get(0).asString();
                                // parse the faction p
                                FLocation location = new FLocation(event.getHitEntity().getLocation());
                                Faction otherFaction = Board.getInstance().getFactionAt(location);
                                Faction faction = Factions.getInstance().getFactionById(metaData);
                                if (otherFaction.isWilderness())
                                {
                                    if(!isSpongeEgg)
                                    {
                                        CraftCreeper myCreeper = (CraftCreeper) Riverland.CreeperTypeInstance.spawn(new Location((org.bukkit.World) event.getHitEntity().getLocation().getWorld(), event.getHitEntity().getLocation().getX(), event.getHitEntity().getLocation().getY(), event.getHitEntity().getLocation().getZ()));
                                        customExplodingCreepers.add(myCreeper.getUniqueId());
                                        myCreeper.setPowered(true);
                                        Riverland._Instance.SetEntityRandomName(myCreeper);
                                        myCreeper.setMaxFuseTicks(30);
                                        myCreeper.setMetadata("Faction", new FixedMetadataValue(Riverland._Instance, metaData));
                                    }else
                                    {
                                        Block block = event.getHitEntity().getLocation().getBlock();
                                        if (block.getType() == Material.AIR || block.getType() == Material.CAVE_AIR || block.getType() == Material.WATER || block.getType() == Material.LAVA)
                                        {
                                            ArrayList<Block> lavaBlocks = GetLavaBlocks(4,block.getLocation(), null);
                                            for(Block lavaBlock : lavaBlocks)
                                            {

                                                if (lavaBlock != null)
                                                {
                                                    lavaBlock.setType(Material.AIR);
                                                }
                                            }

                                            block.setType(Material.SPONGE);
                                        }
                                    }
                                }else
                                {
                                    Relation relation =  faction.getRelationTo(otherFaction);

                                    if (relation == Relation.ENEMY)
                                    {
                                        if (otherFaction.getRelationWish(faction) != Relation.ENEMY)
                                        {
                                            otherFaction.setRelationWish(faction, Relation.ENEMY);
                                            for(FPlayer player : otherFaction.getFPlayersWhereOnline(true))
                                            {
                                                player.sendMessage(net.md_5.bungee.api.ChatColor.GREEN + ""+net.md_5.bungee.api.ChatColor.BOLD +  otherFaction.getTag() + net.md_5.bungee.api.ChatColor.RED + " is now" + net.md_5.bungee.api.ChatColor.DARK_RED + net.md_5.bungee.api.ChatColor.BOLD + " Enemy" + net.md_5.bungee.api.ChatColor.RED + " with " + net.md_5.bungee.api.ChatColor.DARK_RED + net.md_5.bungee.api.ChatColor.BOLD + faction.getTag());
                                                player.sendMessage(net.md_5.bungee.api.ChatColor.YELLOW + "Owner: " +net.md_5.bungee.api.ChatColor.RED + faction.getFPlayerAdmin().getNameAndTitle());

                                                otherFaction.addAnnouncement(player, net.md_5.bungee.api.ChatColor.GREEN + ""+net.md_5.bungee.api.ChatColor.BOLD +  otherFaction.getTag() + net.md_5.bungee.api.ChatColor.RED + " is now" + net.md_5.bungee.api.ChatColor.DARK_RED + net.md_5.bungee.api.ChatColor.BOLD + " Enemy" + net.md_5.bungee.api.ChatColor.RED + " with " + net.md_5.bungee.api.ChatColor.DARK_RED + net.md_5.bungee.api.ChatColor.BOLD + faction.getTag());
                                                otherFaction.addAnnouncement(player, net.md_5.bungee.api.ChatColor.YELLOW + "Owner: " +net.md_5.bungee.api.ChatColor.RED + faction.getFPlayerAdmin().getNameAndTitle());
                                            }
                                            for(FPlayer player : otherFaction.getFPlayersWhereOnline(false))
                                            {
                                                otherFaction.addAnnouncement(player, net.md_5.bungee.api.ChatColor.GREEN + ""+net.md_5.bungee.api.ChatColor.BOLD +  otherFaction.getTag() + net.md_5.bungee.api.ChatColor.RED + " is now" + net.md_5.bungee.api.ChatColor.DARK_RED + net.md_5.bungee.api.ChatColor.BOLD + " Enemy" + net.md_5.bungee.api.ChatColor.RED + " with " + net.md_5.bungee.api.ChatColor.DARK_RED + net.md_5.bungee.api.ChatColor.BOLD + faction.getTag());
                                                otherFaction.addAnnouncement(player, net.md_5.bungee.api.ChatColor.YELLOW + "Owner: " +net.md_5.bungee.api.ChatColor.RED + faction.getFPlayerAdmin().getNameAndTitle());
                                            }
                                        }


                                        if (!isSpongeEgg)
                                        {
                                            CraftCreeper myCreeper = (CraftCreeper) Riverland.CreeperTypeInstance.spawn(new Location((org.bukkit.World) event.getHitEntity().getLocation().getWorld(), event.getHitEntity().getLocation().getX(), event.getHitEntity().getLocation().getY(), event.getHitEntity().getLocation().getZ()));
                                            customExplodingCreepers.add(myCreeper.getUniqueId());
                                            myCreeper.setPowered(true);
                                            Riverland._Instance.SetEntityRandomName(myCreeper);
                                            myCreeper.setMaxFuseTicks(30);
                                            myCreeper.setMetadata("Faction", new FixedMetadataValue(Riverland._Instance, metaData));
                                        }
                                        else
                                        {
                                            Block block = event.getHitEntity().getLocation().getBlock();
                                            if (block.getType() == Material.AIR || block.getType() == Material.CAVE_AIR || block.getType() == Material.WATER || block.getType() == Material.LAVA)
                                            {
                                                ArrayList<Block> lavaBlocks = GetLavaBlocks(4,block.getLocation(), null);
                                                for(Block lavaBlock : lavaBlocks)
                                                {

                                                    if (lavaBlock != null)
                                                    {
                                                        lavaBlock.setType(Material.AIR);
                                                    }
                                                }

                                                block.setType(Material.SPONGE);
                                            }
                                        }
                                    }
                                }
                            }
                        }
=======
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
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
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
<<<<<<< HEAD
        if (player.isOp()) {
            // check if tickting is running..
            if (Riverland._InstanceRiverLandTicket._IsTaskRunning) {
                int size = Riverland._InstanceRiverLandTicket.storedTicketIssues.size();
=======
        if (isRunningMaintenance)
        {
            event.getPlayer().sendMessage(" Welcome " + event.getPlayer().getName()+" you have joined in a scheduled maintenance period\nExpect Lag - Generating Map - Wild Stacker disabled temporarily" );
        }


        if (player.isOp())
        {
            // check if tickting is running..
            if (Riverland._InstanceRiverLandTicket._IsTaskRunning)
            {
                int size  =  Riverland._InstanceRiverLandTicket.storedTicketIssues.size();
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                if (size > 0) {

                    net.md_5.bungee.api.chat.TextComponent msg = new net.md_5.bungee.api.chat.TextComponent("There are " + Riverland._InstanceRiverLandTicket.storedTicketIssues.size() + " Tickets Awaiting Completion.");
                    msg.setColor(net.md_5.bungee.api.ChatColor.GOLD);

                    net.md_5.bungee.api.chat.TextComponent msg2 = new net.md_5.bungee.api.chat.TextComponent("  [Click to View]");
                    msg2.setColor(net.md_5.bungee.api.ChatColor.AQUA);
                    msg2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/opadminhelp display")); // if the user clicks the message, tp them to the coords
                    msg.addExtra(msg2);
                    player.sendMessage(msg);
                }
<<<<<<< HEAD
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
=======
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
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
            Location loc = null;
            double minDist = 999999999f;
            // compare against list..
            Set<Map.Entry<Location, Integer>> map = Riverland._Instance.tntPositions.entrySet();
<<<<<<< HEAD
            for (Map.Entry<Location, Integer> location : map) {
                Location entityLoc = e.getLocation();
                if (location.getKey().getWorld() == entityLoc.getWorld()) {
                    double curDist = location.getKey().distance(e.getLocation());
                    if (curDist < minDist) {
=======
            for (Map.Entry<Location, Integer> location : map)
            {
                Location entityLoc = e.getLocation();
                if (location.getKey().getWorld() == entityLoc.getWorld())
                {
                    double curDist = location.getKey().distance(e.getLocation());
                    if (curDist < minDist)
                    {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                        minDist = curDist;
                        loc = location.getKey();
                    }
                }
            }

<<<<<<< HEAD
            if (minDist < 0.8) {
                if (loc != null) {
=======
            if (minDist < 0.8)
            {
                if (loc != null)
                {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                    e.getEntity().setMetadata("TNTType", new FixedMetadataValue(Riverland._Instance, "Explosive" + Riverland._Instance.tntPositions.get(loc).intValue()));
                    Riverland._Instance.tntPositions.remove(loc);

                }
            }
        }

    }

    @EventHandler
<<<<<<< HEAD
    public void onBlockPlace(BlockPlaceEvent e) {
=======
    public void onBlockPlace(BlockPlaceEvent e)
    {
        //if (event.getItemInHand().getType() == Material.TNT && event.getItemInHand().getItemMeta().hasLore() && ((String)event.getItemInHand().getItemMeta().getLore().get(0)).contains("Explosive")) {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
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
<<<<<<< HEAD
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
=======

        if (e.getPlayer().isOp())
        {
            if (e.getItemInHand().getType() == Material.SPAWNER)
            {
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
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
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
<<<<<<< HEAD
                        if (isOk) {
=======
                        if (isOk)
                        {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                            obsidian.add(testBlock);
                        }
                    }
                }
            }
<<<<<<< HEAD
        } else {
=======
        }
        else {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b

            Location location = loc;
            Block b = loc.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
            for (int x = -(radius); x <= radius; x++) {
                for (int y = -(radius); y <= radius; y++) {
                    for (int z = -(radius); z <= radius; z++) {

                        Location bLoc = b.getLocation();
                        Block testBlock = b.getRelative(x, y, z);

                        Vector from = bLoc.toVector();
<<<<<<< HEAD
                        Vector to = testBlock.getLocation().toVector();
=======
                        Vector to =  testBlock.getLocation().toVector();
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b

                        Vector dir = to.subtract(from);
                        location.setDirection(dir);

<<<<<<< HEAD
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
=======
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
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                                    //if (!obsidian.contains(hit))
                                    {
                                        if (hit.getType() == Material.OBSIDIAN) {
                                            if (!obsidian.contains(hit))
                                                obsidian.add(hit);

                                            currTraversal = maxTraversal;
                                            break;
<<<<<<< HEAD
                                        } else
=======
                                        }else
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                                            obsidian.add(hit);
                                        //}else
                                        //    obsidian.add(hit);
                                    }
<<<<<<< HEAD
                                } catch (Exception exc) {
=======
                                }catch (Exception exc)
                                {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
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

<<<<<<< HEAD
    ArrayList<Block> GetLavaBlocks(int rad, Location loc, List<Block> m_ignoreBlocks) {
        ArrayList<Block> lava = new ArrayList<>();
        int radius = rad;
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
                            if (hit.getType() != Material.WATER && hit.getType() != Material.LAVA)
                                break;

                            try {

                                if (hit.getType() == Material.LAVA) {
                                    if (!lava.contains(hit))
                                        lava.add(hit);
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

        return lava;
    }

    public boolean randomBoolean() {
        return Math.random() < Riverland._Instance.tntBreakChance;
    }

    public boolean randomBoolean(float customChance) {
        return Math.random() < customChance;
    }

    public List<Block> getBlocks(Block start, int radius){
        if (radius < 0) {
            return new ArrayList<Block>(0);
        }
        int iterations = (radius * 2) + 1;
        List<Block> blocks = new ArrayList<Block>(iterations * iterations * iterations);
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    blocks.add(start.getRelative(x, y, z));
                }
            }
        }
        return blocks;
=======
    public boolean randomBoolean(){
        return Math.random() < Riverland._Instance.tntBreakChance;
    }
    public boolean randomBoolean(float customChance){
        return Math.random() < customChance;
    }
    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event)
    {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b

    }

    @EventHandler
<<<<<<< HEAD
    public void onCreeperExplode(ExplosionPrimeEvent event) {
        boolean canExplode = CanExplode(event.getEntity().getLocation());

        FLocation location = new FLocation(event.getEntity().getLocation());
        Faction faction = Board.getInstance().getFactionAt(location);
        if (customExplodingCreepers.contains(event.getEntity().getUniqueId()))
        {
            if (canExplode && !faction.isPeaceful() && !faction.isSafeZone() && !faction.isWarZone())
            {
                if (event.getEntity().hasMetadata("Faction"))
                {
                    String metaData = event.getEntity().getMetadata("Faction").get(0).asString();
                    // Faction Creeper
                    Faction factionCreeper = Factions.getInstance().getFactionById(metaData);
                    // relation
                    Relation relation = faction.getRelationTo(factionCreeper);


                    if (relation.isEnemy() || faction.isWilderness()) {
                        if (!factionCreeper.isWilderness())
                        {
                            event.getEntity().getLocation().getWorld().createExplosion(event.getEntity().getLocation(), (float) Riverland._Instance.tntRadiusLow);
                            event.getEntity().remove();




                            customExplodingCreepers.remove(event.getEntity().getUniqueId());
                            event.setCancelled(true);
                        }
                    }else
                    {
                        customExplodingCreepers.remove(event.getEntity().getUniqueId());
                        event.getEntity().remove();
                        event.setCancelled(true);
                    }
                }
            }
=======
    public void onCreeperExplode(ExplosionPrimeEvent  event)
    {
        if (customExplodingCreepers.contains(event.getEntity().getUniqueId()))
        {
            event.getEntity() .getLocation().getWorld().createExplosion(event.getEntity() .getLocation(), (float)Riverland._Instance.tntRadiusLow);
            event.getEntity().remove();
            customExplodingCreepers.remove(  event.getEntity().getUniqueId());
            event.setCancelled(true);
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b

        }
    }

<<<<<<< HEAD
   // @EventHandler
   // public void onRegionEntered(RegionsLeftEvent event)
   // {
   //     Player player = Bukkit.getPlayer(event.getUUID());
   //     if (player == null) return;
//
   //     if(event.getRegionsNames().contains("worldspawn"))
   //     {
   //         Bukkit.dispatchCommand(player, "procosmetics unequip mount");
   //     }
   // }
//
    public void DropSpawner(Block block)
    {

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
                                         //   Riverland._Instance.getServer().broadcastMessage("block: " +block.toString());
                                            if (randomBoolean()) {
                                                block.setType(Material.AIR);
                                            }


                                        }
                                    }

                                }, 1L);
                            }
                            else {
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
=======

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
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
            ItemMeta testEnchantMeta = testEnchant.getItemMeta();
            testEnchantMeta.addEnchant(Enchantment.DAMAGE_ALL, level, true);
            testEnchant.setItemMeta(testEnchantMeta);
            pos.getWorld().dropItem(pos, testEnchant);
            event.setDropItems(false);
            Riverland._Instance.tntPositions.remove(event.getBlock().getLocation());
<<<<<<< HEAD
        } else if (event.getPlayer().getGameMode() == GameMode.SURVIVAL && event.getBlock().getType().compareTo(Material.SPAWNER) == 0 && event.getPlayer().getWorld().getEnvironment() == World.Environment.NETHER) {
            // if (!event.getPlayer().isOp())
            {
                CreatureSpawner type = ((CreatureSpawner) event.getBlock().getState());
                if (type.getSpawnedType() == EntityType.BLAZE) {
=======
        }else if (event.getPlayer().getGameMode() == GameMode.SURVIVAL && event.getBlock().getType().compareTo(Material.SPAWNER) == 0 && event.getPlayer().getWorld().getEnvironment() == World.Environment.NETHER)
        {
           // if (!event.getPlayer().isOp())
            {
                CreatureSpawner type = ((CreatureSpawner) event.getBlock().getState());
                if (type.getSpawnedType() == EntityType.BLAZE)
                {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                    event.getPlayer().sendMessage("You cant mine this spawner in the Nether!");
                    event.setCancelled(true);
                }
            }
        }


    }

<<<<<<< HEAD
    public boolean isInCombat(Player player) {
        // Make sure to check that CombatLogX is enabled before using it for anything.
        ICombatLogX plugin = (ICombatLogX) Bukkit.getPluginManager().getPlugin("CombatLogX");
        ICombatManager combatManager = plugin.getCombatManager();

        return combatManager.isInCombat(player);
    }
  // @EventHandler(priority=EventPriority.HIGHEST)
  // public void onPlayerQuit(PlayerQuitEvent event) {
  //     ICombatLogX plugin = (ICombatLogX) Bukkit.getPluginManager().getPlugin("CombatLogX");
  //     ICombatManager combatManager = plugin.getCombatManager();
//
  //     if (!handledPlayerIds.contains(event.getPlayer().getUniqueId())) {
  //         combatManager.tag(event.getPlayer(), event.getPlayer(), PlayerPreTagEvent.TagType.UNKNOWN, PlayerPreTagEvent.TagReason.ATTACKED);
  //         handledPlayerIds.add(event.getPlayer().getUniqueId());
  //     }
  // }

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
=======

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent respawn)
    {
       // if (AIArenaEvent.blackListedPlayers.contains(respawn.getPlayer()) || AIArenaEvent.activePlayers.contains(respawn.getPlayer())) {
       //     Riverland._Instance.spectatorMode.AddPlayerToSpectator(respawn.getPlayer());
       //     respawn.getPlayer().sendMessage("You have been added to Spectator, type /leave to return back to survival");
       // }
    }


>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event)
    {
        Block pos = event.getEntity().getLocation().getBlock();
        String message = ChatColor.RED + "Oops, you've died at: " + ChatColor.GOLD.toString() + "(X = " + pos.getX() + "," + " Y = " + pos.getY() + ", Z = " + pos.getZ() + ")";
        event.getEntity().sendMessage(message);

<<<<<<< HEAD
        // loop players on server..
        for(Player player : Bukkit.getOnlinePlayers())
        {
            boolean notify = event.getEntity().getLocation().getWorld().getGameRuleValue(GameRule.SHOW_DEATH_MESSAGES);
            if (notify) {
                if (player.isOp()) // check if player is operator
                {

                    net.md_5.bungee.api.chat.TextComponent message2 = new net.md_5.bungee.api.chat.TextComponent("Player " + event.getEntity().getName() + " Has Died");
                    message2.setColor(net.md_5.bungee.api.ChatColor.GOLD);

                    net.md_5.bungee.api.chat.TextComponent click = new net.md_5.bungee.api.chat.TextComponent(" [Click to Teleport]");


                    player.sendMessage("World Named: " + event.getEntity().getPlayer().getWorld().getName());
                    // tp command
                    click.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/riverland teleport" + " " + event.getEntity().getPlayer().getWorld().getName() + " " + pos.getX() + " " + pos.getY() + " " + pos.getZ())); // if the user clicks the message, tp them to the coords

                    click.setColor(net.md_5.bungee.api.ChatColor.AQUA);
                    message2.addExtra(click);

                    player.sendMessage(message2);
                }
=======

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
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
            }
        }
    }
}
