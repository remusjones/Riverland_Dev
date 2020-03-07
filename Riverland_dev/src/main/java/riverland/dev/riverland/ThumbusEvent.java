package riverland.dev.riverland;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public class ThumbusEvent extends RiverlandEvent implements CommandExecutor
{
    public boolean hasBeenAsyncd = false;
    public boolean hasAnnouncedInitialJoin = false;
    public boolean hasBegunRun = false;
    public boolean hasInitRunAnnouncer = false;
    public Location BossSpawnLocation = null;

    public boolean hasExitedEvent = false;

    public boolean isEventJoin = false;
    public Location eventLocation = null;
    private boolean hasInitialisedEventData = false;
    private boolean hasStartedEvent = false;
    private boolean hasEarlyEnded = false;
    private boolean awaitingStart = false;
    private boolean hasAnnouncedCountdown = false;
    private boolean hasDroppedExperience = false;
    public int playerEventRadius = 150;
    public int minimumPlayers = 2;
    public Location lastBossLocation = null;
    public boolean hasSuccededRun = false;


    BossBar bar = null;

    // players inside the event.
    public ArrayList<Player> activePlayers = new ArrayList<>();
    public Map<Player, Location> joinedPlayers = new HashMap<>();
    // players outside the event.
    public ArrayList<Player> blackListedPlayers = new ArrayList<>(); // update list while running the event..
    public List<Player> activeBossMusicPlayers = new ArrayList<>();
    public List<Player> activeRunMusicPlayers = new ArrayList<>();
    private Sound runSound = Sound.MUSIC_DISC_WARD;
    private Sound bossSound = Sound.MUSIC_DISC_WARD;

    Integer timer = 30;
    Integer EventFinalCountdownJoin = 30;

    Integer joinTimer = 120;
    Integer eventTeleportTimer = 120;

    Integer runTimer = 300;
    Integer eventTimer = 300;


    private Integer announcerStartID = 0;
    private Integer announcerJoinID = 0;
    private Integer announcerRunID = 0;
    private Integer EventActiveLoopID = 0;
    private Integer EventMainCountdownID = 0;
    private Integer EventStartStopAnnouncerID = 0;

    private Integer loopOneID = 0;
    private Integer loopTwoID = 0;
    private Integer loopThreeID = 0;
    Integer bossBarID = 0;
    Entity boss;
    public boolean isTesting = false;
    public ThumbusEventListener thumbusEventListener=  null;

    public ThumbusEvent(RiverlandEventManager manager) {
        super(manager);
    }
    @Override
    public boolean RiverlandEventInit()
    {
        // attempt to set //
        eventLocation = Riverland._Instance.giantBossStartLocation;
        BossSpawnLocation = Riverland._Instance.giantBossEndLocation;

        if (eventLocation == null || BossSpawnLocation == null)
            return false;
        else {
            UpdateEvent(eventLocation, true);
            hasAnnouncedInitialJoin = true;
            BukkitScheduler scheduler = Riverland._Instance.getServer().getScheduler();
            EventActiveLoopID           = scheduler.scheduleSyncRepeatingTask(Riverland._Instance, EventActiveLoop, 0, 25);
            EventMainCountdownID        = scheduler.scheduleSyncRepeatingTask(Riverland._Instance, EventMainCountdown, 0, 40);
            EventStartStopAnnouncerID   = scheduler.scheduleSyncRepeatingTask(Riverland._Instance, EventStartStopAnnouncer, 0, 100);
            hasBeenAsyncd = true;
            Riverland._Instance.getCommand("ArenaEvent").setExecutor(this);
            //thumbusEvent
            thumbusEventListener = new ThumbusEventListener(this);
            Riverland._Instance.getServer().getPluginManager().registerEvents(thumbusEventListener, Riverland._Instance);
        }
        return true;
    }

    public void UpdateEvent(Location portLoc, boolean isRunning)
    {
        eventLocation = portLoc;

        if (isRunning == true && !isEventJoin)
        {
            isEventJoin = true;
            // we are starting..
        }else
            if (isEventJoin && isRunning == false)
            {
                // we are stopping..
                isEventJoin = false;
            }

    }
    public Runnable EventStartStopAnnouncer = new BukkitRunnable() {
        @Override
        public void run()
        {
            if (!hasEarlyEnded && isEventJoin) {
                if (!hasAnnouncedCountdown && awaitingStart) {

                    BukkitScheduler scheduler = Riverland._Instance.getServer().getScheduler();
                    announcerStartID = scheduler.scheduleSyncRepeatingTask(Riverland._Instance, new Runnable() {
                        @Override
                        public void run() {
                            if (timer == EventFinalCountdownJoin)
                            {
                                //Riverland._Instance.getServer().broadcastMessage("Event beginning in " + EventFinalCountdownJoin + " Seconds");
                                net.md_5.bungee.api.chat.TextComponent message = new net.md_5.bungee.api.chat.TextComponent( "Event beginning in " + timer + " Seconds" );
                                message.setColor(ChatColor.LIGHT_PURPLE);
                                net.md_5.bungee.api.chat.TextComponent click = new net.md_5.bungee.api.chat.TextComponent( " [Click to join]" );
                                click.setColor(ChatColor.AQUA);
                                click.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/ArenaEvent join")); // if the user clicks the message, tp them to the coords
                                message.addExtra(click);
                                SendOnlyAdminBroadcast(message);
                            }

                            if (timer <= 3 && timer > -1)
                            {
                                net.md_5.bungee.api.chat.TextComponent message = new net.md_5.bungee.api.chat.TextComponent( "Event beginning in " + timer + " Seconds" );
                                message.setColor(ChatColor.LIGHT_PURPLE);
                                net.md_5.bungee.api.chat.TextComponent click = new net.md_5.bungee.api.chat.TextComponent( " [Click to join]" );
                                click.setColor(ChatColor.AQUA);
                                click.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/ArenaEvent join")); // if the user clicks the message, tp them to the coords
                                message.addExtra(click);
                                SendOnlyAdminBroadcast(message);
                            }
                            timer--;

                            if (timer == -1 && hasInitialisedEventData) {
                                // teleport players ..
                                hasStartedEvent = true;

                                for (Player player : activePlayers)
                                {
                                    Location newLoc = eventLocation;
                                   // newLoc.set(newLoc.getX() + (RandomUtils.nextDouble() * 2),newLoc.getY() + (RandomUtils.nextDouble() * 2),newLoc.getZ() + (RandomUtils.nextDouble() * 2));
                                    player.teleport(eventLocation);

                                    player.playSound(eventLocation, runSound,100,1);
                                    activeBossMusicPlayers.add(player);

                                }



                                Entity Myzombie = Riverland.GiantTypeInstance.spawn(new Location(BossSpawnLocation.getWorld(), BossSpawnLocation.getX(),BossSpawnLocation.getY(),BossSpawnLocation.getZ()));
                                // ArrayList<Entity> nearbyEntites = p.getLocation().getWorld().getNearbyEntities(p.getLocation(), 8, 8, 8);

                                ItemStack sword = new ItemStack(Material.GOLDEN_SWORD,1);
                                ItemMeta meta = sword.getItemMeta();
                                meta.addEnchant(Enchantment.DAMAGE_ALL, 8, true);

                                sword.setItemMeta(meta);

                                ((Giant)Myzombie).getEquipment().setItemInMainHand(sword);
                                ((Giant)Myzombie).getEquipment().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET,1));
                                ((Giant)Myzombie).getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE,1));
                                ((Giant)Myzombie).getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS,1));
                                ((Giant)Myzombie).getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS,1));
                                ((Giant)Myzombie).getEquipment().setItemInOffHand(new ItemStack(Material.SHIELD,1));


                                boss = Myzombie;
                                boss.setCustomNameVisible(true);
                                boss.setCustomName("THUMBUS");

                                Riverland._Instance.getServer().getScheduler().cancelTask(announcerStartID);

                                bar = Bukkit.createBossBar("THUMBUS", BarColor.RED, BarStyle.SEGMENTED_20 );
                                bar.addFlag(BarFlag.PLAY_BOSS_MUSIC);
                                bar.addFlag(BarFlag.CREATE_FOG);
                                bar.addFlag(BarFlag.DARKEN_SKY);


                                BukkitScheduler scheduler = Riverland._Instance.getServer().getScheduler();
                                bossBarID = scheduler.scheduleSyncRepeatingTask(Riverland._Instance, new Runnable() {
                                    @Override
                                    public void run()
                                    {
                                        if (((Giant)boss).getHealth() > 0)
                                            bar.setProgress(((Giant) boss).getHealth() < 0 ? 0D : ((Giant) boss).getHealth() / 1000);
                                        if (boss.isDead() || boss == null || hasSuccededRun)
                                        {

                                            bar.removeAll();
                                            bar.setVisible(false);
                                            Riverland._Instance.getServer().getScheduler().cancelTask(bossBarID);
                                        }

                                    }
                                }, 0L, 20L);


                                // start final timer
                                hasBegunRun = true;
                                awaitingStart = false;
                                isEventJoin = false;
                            }else if (!hasInitialisedEventData && timer == -1)
                            {
                                net.md_5.bungee.api.chat.TextComponent message = new net.md_5.bungee.api.chat.TextComponent( "Not enough people joined the event." );
                                message.setColor(ChatColor.RED);
                                Riverland._Instance.getServer().broadcast(message);
                                message = new net.md_5.bungee.api.chat.TextComponent( "Event Cancelled" );
                                message.setColor(ChatColor.RED);
                                Riverland._Instance.getServer().broadcast(message);
                                //Riverland._Instance.getServer().broadcastMessage("Event Cancelled");
                                awaitingStart = false;
                                isEventJoin = false;

                                Riverland._Instance.getServer().getScheduler().cancelTask(announcerStartID);
                            }

                        }
                    }, 0L, 20L);
                    // countdown..
                    hasAnnouncedCountdown = true;

                    timer = EventFinalCountdownJoin;
                }
            }

        }
    };
    public void SendOnlyAdminBroadcast(BaseComponent text)
    {
        for (Player player : Riverland._Instance.getServer().getOnlinePlayers())
        {
            if (isTesting)
            {
                if (player.isOp())
                    player.sendMessage(text);
            }
            else {
                if (player.isOp() || player.hasPermission("Riverland.ArenaEvent"))
                    player.sendMessage(text);
            }
        }

    }

    // main event run loop
    public Runnable EventMainCountdown = new BukkitRunnable() {
        @Override
        public void run()
        {
            if (!hasInitRunAnnouncer && hasBegunRun)
            {
                runTimer = eventTimer;
                BukkitScheduler scheduler = Riverland._Instance.getServer().getScheduler();
                announcerRunID = scheduler.scheduleSyncRepeatingTask(Riverland._Instance, new Runnable() {

                    @Override
                    public void run() {
                        net.md_5.bungee.api.chat.TextComponent message = new net.md_5.bungee.api.chat.TextComponent( "You have " + runTimer + " Seconds to complete the event" );
                        message.setColor(ChatColor.LIGHT_PURPLE);
                        if (runTimer == eventTimer)
                        {
                            message = new net.md_5.bungee.api.chat.TextComponent( "You have " + 5 + " minutes to complete the event" );
                            message.setColor(ChatColor.LIGHT_PURPLE);
                            for (Player p : activePlayers)
                            {
                                p.sendMessage(message);
                            }
                        }
                        if (runTimer == 120)
                        {
                            message = new net.md_5.bungee.api.chat.TextComponent("You have " + 2 + " minutes to complete the event" );
                            message.setColor(ChatColor.LIGHT_PURPLE);
                            for (Player p : activePlayers)
                            {
                                p.sendMessage(message);
                            }
                        }
                        if (runTimer == 60)
                        {
                            message = new net.md_5.bungee.api.chat.TextComponent("You have " + 1 + " minutes to complete the event" );
                            message.setColor(ChatColor.LIGHT_PURPLE);
                            for (Player p : activePlayers)
                            {
                                p.sendMessage(message);
                            }
                        }

                        if (runTimer <= 10 && runTimer > -1 && !hasEarlyEnded)
                        {
                            net.md_5.bungee.api.chat.TextComponent messageTimer = new net.md_5.bungee.api.chat.TextComponent( "Event Ending in " + runTimer + " Seconds" );
                            messageTimer.setColor(ChatColor.DARK_RED);
                            for (Player p : activePlayers)
                            {
                                p.sendMessage(messageTimer);
                            }
                        }
                        runTimer--;

                        if (hasSuccededRun && !hasEarlyEnded)
                        {

                            ArrayList<ItemStack> items= new ArrayList<ItemStack>();
                            items.add(new ItemStack(Material.DIAMOND, 15));

                            ItemStack godItemSword = new ItemStack(Material.DIAMOND_SWORD);
                            ItemMeta godSwordMeta = godItemSword.getItemMeta();
                            godSwordMeta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 5, true);
                            godSwordMeta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
                            godSwordMeta.addEnchant(Enchantment.KNOCKBACK, 2, true);
                            godSwordMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 3, true);
                            godSwordMeta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
                            godSwordMeta.addEnchant(Enchantment.DAMAGE_UNDEAD, 5, true);
                            godSwordMeta.addEnchant(Enchantment.DURABILITY, 3, true);
                            godSwordMeta.setDisplayName("THUMBUS' SWORD");

                            godItemSword.setItemMeta(godSwordMeta);
                            items.add(godItemSword);

                            ItemStack godItemHead = new ItemStack(Material.DIAMOND_HELMET);
                            ItemMeta godHeadMeta = godItemHead.getItemMeta();
                            godHeadMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 3, true);
                            godHeadMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
                            godHeadMeta.addEnchant(Enchantment.OXYGEN, 3, true);
                            godHeadMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
                            godHeadMeta.addEnchant(Enchantment.DURABILITY, 6, true);
                            godHeadMeta.setDisplayName("THUMBUS' HELM");
                            godItemHead.setItemMeta(godHeadMeta);
                            items.add(godItemHead);


                            ItemStack godItemChest = new ItemStack(Material.DIAMOND_CHESTPLATE);
                            ItemMeta godChestMeta = godItemChest.getItemMeta();
                            godChestMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 3, true);
                            godChestMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
                            godChestMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
                            godChestMeta.addEnchant(Enchantment.DURABILITY, 6, true);
                            godChestMeta.setDisplayName("THUMBUS' MANTLE");
                            godItemChest.setItemMeta(godChestMeta);
                            items.add(godItemChest);


                            ItemStack godItemLegs = new ItemStack(Material.DIAMOND_LEGGINGS);
                            ItemMeta godLegMeta = godItemLegs.getItemMeta();
                            godLegMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 3, true);
                            godLegMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
                            godLegMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
                            godLegMeta.addEnchant(Enchantment.DURABILITY, 6, true);
                            godLegMeta.setDisplayName("THUMBUS' GREAVES");
                            godItemLegs.setItemMeta(godLegMeta);
                            items.add(godItemLegs);


                            ItemStack godItemBoots = new ItemStack(Material.DIAMOND_BOOTS);
                            ItemMeta godBootMeta = godItemBoots.getItemMeta();
                            godBootMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 3, true);
                            godBootMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
                            godBootMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
                            godBootMeta.addEnchant(Enchantment.DURABILITY, 3, true);
                            godBootMeta.addEnchant(Enchantment.PROTECTION_FALL, 5, true);
                            godBootMeta.setDisplayName("THUMBUS' BOOTS");
                            godItemBoots.setItemMeta(godBootMeta);
                            items.add(godItemBoots);


                            ItemStack godItemWings = new ItemStack(Material.ELYTRA);
                            ItemMeta godWingsMeta = godItemWings.getItemMeta();
                            godWingsMeta.addEnchant(Enchantment.MENDING, 1, true);
                            godWingsMeta.addEnchant(Enchantment.DURABILITY, 3, true);
                            godWingsMeta.setDisplayName("THUMBUS' WINGS");
                            godItemWings.setItemMeta(godWingsMeta);
                            items.add(godItemWings);

                            ItemStack godItemBow = new ItemStack(Material.BOW);
                            ItemMeta godBowMeta = godItemBow.getItemMeta();
                            godBowMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
                            godBowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
                            godBowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 2, true);
                            godBowMeta.setDisplayName("THUMBUS' BOW");
                            godItemBow.setItemMeta(godBowMeta);
                            items.add(godItemBow);

                            ItemStack godItemPickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
                            ItemMeta godPickaxeMeta = godItemPickaxe.getItemMeta();
                            godPickaxeMeta.addEnchant(Enchantment.DIG_SPEED, 5, true);
                            godPickaxeMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, true);
                            godPickaxeMeta.setDisplayName("THUMBUS' PICKAXE");
                            godItemPickaxe.setItemMeta(godPickaxeMeta);
                            items.add(godItemPickaxe);


                            ItemStack potionStrength = new ItemStack(Material.POTION,1);
                            PotionMeta potStrengthMeta = (PotionMeta)potionStrength.getItemMeta();
                            potStrengthMeta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 300*20, 1), true);
                            potStrengthMeta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300, 3), true);
                            potStrengthMeta.addCustomEffect(new PotionEffect(PotionEffectType.HUNGER, 300*20, 1), true);
                            potStrengthMeta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, 300*20, 2), true);
                            potStrengthMeta.setDisplayName("THUMBUS' STRENGTH POTION");
                            potionStrength.setItemMeta(potStrengthMeta);
                            items.add(potionStrength);

                            ItemStack potionHealing = new ItemStack(Material.POTION,1);
                            PotionMeta potHealingMeta = (PotionMeta)potionHealing.getItemMeta();
                            potHealingMeta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 60*20, 3), true);
                            potHealingMeta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 60*20, 1), true);
                            potHealingMeta.setDisplayName("THUMBUS' HEALING POTION");
                            potionHealing.setItemMeta(potHealingMeta);
                            items.add(potionHealing);

                            ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING,1);
                            items.add(totem);

                            ItemStack head = new ItemStack(Material.ZOMBIE_HEAD,1);
                            ItemMeta headMeta = head.getItemMeta();
                            headMeta.setDisplayName("THUMBUS' HEAD");
                            headMeta.addEnchant(Enchantment.BINDING_CURSE, 1,true);
                            headMeta.addEnchant(Enchantment.VANISHING_CURSE, 1,true);
                            headMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 5,true);
                            headMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5,true);
                         //   headMeta.addEnchant(Enchantment.BINDING_CURSE, 1,true);
                            items.add(head);



                            if (hasSuccededRun)
                            {

                                ArrayList<Player> rewardedPlayers = new ArrayList<>();
                                for(Player p : activePlayers)
                                {
                                    if (p.getLocation().distanceSquared(boss.getLocation()) <= 750) // do a final check
                                    {
                                        rewardedPlayers.add(p);
                                    }
                                }
                                GiveRewards(rewardedPlayers, items);
                            }
                            hasEarlyEnded = true;

                            runTimer = 30;

                            message = new net.md_5.bungee.api.chat.TextComponent("Event has been completed, event closing in " + runTimer.toString() + " Seconds");
                            message.setColor(ChatColor.LIGHT_PURPLE);
                            for (Player p : activePlayers)
                            {
                                p.sendMessage(message);
                            }
                        }
                        if (runTimer < 0)
                        {
                            if (!hasSuccededRun)
                            {
                                message = new net.md_5.bungee.api.chat.TextComponent("Event Failed");
                                message.setColor(ChatColor.RED);
                                for (Player p : activePlayers)
                                {
                                    p.sendMessage(message);
                                }
                            }
                            TeleportPlayersHome(joinedPlayers);
                            Reset();

                            Riverland._Instance.getServer().getScheduler().cancelTask(announcerRunID);

                        }
                    }
                }, 0L, 20L);
                hasInitRunAnnouncer = true;
            }
            if (hasBegunRun && boss != null)
            {
                for(Player p : activePlayers)
                {
                    if (p.getLocation().distanceSquared(boss.getLocation()) < 750)
                    {
                        if (activeBossMusicPlayers.contains(p))
                        {
                            if (activeRunMusicPlayers.contains(p))
                            {
                                p.stopSound(runSound);
                                activeRunMusicPlayers.remove(p);
                            }
                        }else if (!activeBossMusicPlayers.contains(p))
                        {
                            if (activePlayers.contains(p))
                            {
                                p.stopSound(runSound);
                                activeRunMusicPlayers.remove(p);
                            }


                            p.playSound(p.getLocation(),bossSound,100,1);
                            activeBossMusicPlayers.add(p);
                        }
                        if (!bar.getPlayers().contains(p))
                        {
                            bar.addPlayer(p);
                        }
                    }else {
                        if (bar.getPlayers().contains(p))
                        {
                            bar.removePlayer(p);
                        }
                    }
                }
                if (activePlayers.size() == 0)
                    bar.removeAll();
                if (boss == null)
                    bar.removeAll();


                if (boss!=null &&boss.isDead())
                {
                    for(Player player : activeBossMusicPlayers)
                    {
                        player.stopSound(bossSound);
                    }
                    for(Player player : activeRunMusicPlayers)
                    {
                        player.stopSound(runSound);
                    }
                    activeRunMusicPlayers.clear();
                    activeBossMusicPlayers.clear();

                    hasSuccededRun = true;
                   // Riverland._Instance.spectatorMode.SetSpecatorAvaiable(false);
                }else
                    lastBossLocation = boss.getLocation();

                for(Player p : blackListedPlayers)
                {
                    if (activeBossMusicPlayers.contains(p))
                    {
                        p.stopSound(bossSound);
                        activeBossMusicPlayers.remove(p);
                    }
                    if (activeRunMusicPlayers.contains(p))
                    {
                        p.stopSound(runSound);
                        activeRunMusicPlayers.remove(p);
                    }
                }

                if (boss != null) {
                    ((Giant) boss).setAI(true);
                    Riverland._Instance.spectatorMode.SetSpecatorAvaiable(true);
                   // Riverland._Instance.spectatorMode.UpdateSpectatorPlayers(activePlayers);
                }
                if (boss == null || boss.isDead())
                {
                    if (!hasDroppedExperience)
                    {
                        for(int exp = 0; exp < 3; exp++ )
                        {
                            ExperienceOrb orb= (ExperienceOrb)lastBossLocation.getWorld().spawnEntity(lastBossLocation, EntityType.EXPERIENCE_ORB);
                            orb.setExperience(5000);
                        }
                        hasDroppedExperience = true;
                    }
                    hasSuccededRun = true;
                }


            }

        }
    };
    public void TeleportPlayersHome(Map<Player, Location> map)
    {
        Set<Map.Entry<Player, Location>> mapEntry = map.entrySet();
        for (Map.Entry pair : mapEntry)
        {
            ((Player)pair.getKey()).teleport((Location)pair.getValue());

            if (activeBossMusicPlayers.contains(pair.getKey()))
            {
                ((Player)pair.getKey()).stopSound(bossSound);
                activeBossMusicPlayers.remove(pair.getKey());
            }
            if (activeRunMusicPlayers.contains(pair.getKey()))
            {
                ((Player)pair.getKey()).stopSound(runSound);
                activeRunMusicPlayers.remove(pair.getKey());
            }
        }
    }
    public void Reset()
    {
        //hasDroppedExperience = false;
        //hasBeenAsyncd = false;
        //hasAnnouncedInitialJoin = false;
        //hasBegunRun = false;
        //hasInitRunAnnouncer = false;
        //hasExitedEvent = false;
//
        //isEventJoin = false;
        //hasInitialisedEventData = false;
        //hasStartedEvent = false;
        //hasEarlyEnded = false;
        //awaitingStart = false;
        //hasAnnouncedCountdown = false;
        //boss = null;
        //playerEventRadius = 150;
        //minimumPlayers = 3;
//
        //boolean hasSuccededRun = false;
//
//
//
        //// players inside the event.
        //activePlayers.clear();
        //// players outside the event.
        //blackListedPlayers.clear(); // update list while running the event..
        //joinedPlayers.clear();
        //timer = EventFinalCountdownJoin;
        //joinTimer = eventTeleportTimer;
        //Integer runTimer = 300;
        //runTimer = 300;
        //_____________


        //if (Riverland._Instance.getServer().getScheduler().isCurrentlyRunning(announcerStartID))
        try {
            Riverland._Instance.getServer().getScheduler().cancelTask(EventActiveLoopID);
            Riverland._Instance.getServer().getScheduler().cancelTask(EventMainCountdownID);
            Riverland._Instance.getServer().getScheduler().cancelTask(EventStartStopAnnouncerID);
            Riverland._Instance.getServer().getScheduler().cancelTask(announcerStartID);
            Riverland._Instance.getServer().getScheduler().cancelTask(announcerJoinID);
            Riverland._Instance.getServer().getScheduler().cancelTask(announcerRunID);
            Riverland._Instance.getServer().getScheduler().cancelTask(bossBarID);
        }catch (Exception e)
        {

        }


        if (boss != null && !boss.isDead()) {
            ((Giant) boss).setHealth(0);
            boss.remove();

            for(Player p : activeBossMusicPlayers)
            {
                p.stopSound(bossSound);

            }
            activeBossMusicPlayers.clear();
            for(Player p : activeRunMusicPlayers)
            {
                p.stopSound(runSound);

            }
            activeRunMusicPlayers.clear();
            boss = null;

        }
        hasAnnouncedInitialJoin = false;
        hasBegunRun = false;
        hasInitRunAnnouncer = false;
        BossSpawnLocation = null;

        hasExitedEvent = false;

        isEventJoin = false;
        eventLocation = null;
        hasInitialisedEventData = false;
        hasStartedEvent = false;
        hasEarlyEnded = false;
        awaitingStart = false;
        hasAnnouncedCountdown = false;
        hasDroppedExperience = false;
        playerEventRadius = 150;
        //minimumPlayers = 3;
        lastBossLocation = null;
        hasSuccededRun = false;


        if (bar != null)
        {
            bar.removeAll();
            bar.setVisible(false);
        }
         bar = null;


         activePlayers = new ArrayList<>();
         joinedPlayers = new HashMap<>();
         blackListedPlayers = new ArrayList<>(); // update list while running the event..

         timer = 30;
         EventFinalCountdownJoin = 30;

         joinTimer = 120;
         eventTeleportTimer = 120;

         runTimer = 300;
         eventTimer = 300;

         announcerStartID = 0;
        announcerJoinID = 0;
        announcerRunID = 0;
        bossBarID = 0;
        HandlerList.unregisterAll(thumbusEventListener); // cleanup event listener
        this.InvokeManagerEventFinish();


    }
    public void GiveRewards(ArrayList<Player> players, ArrayList<ItemStack> rewardsList)
    {
        Integer randomItem = 3;
        Integer potionMultiplier = 3;
        Integer expOrbs = 25;


        for (Player p : players)
        {
            for(int exp = 0; exp < expOrbs; exp++ )
            {

                Location loc = p.getLocation();
                ExperienceOrb e = ((ExperienceOrb) p.getLocation().getWorld().spawn(loc, ExperienceOrb.class));
                e.setExperience(250);
            }
            for(int exp = 0; exp <expOrbs; exp++ )
            {
                p.getLocation().getWorld().spawnEntity(p.getLocation(), EntityType.EXPERIENCE_ORB);
            }
            // store our rewards for this player
            ArrayList<ItemStack> actualRewards = new ArrayList<>();
            // a copy of our rewards list to modify
            ArrayList<ItemStack> copy = new ArrayList<>(rewardsList);
            // iterate through reward count
            for (int i = 0; i < randomItem; i++)
            {
                // generate random based on max size
                int index = new Random().nextInt(copy.size());

                boolean hasGottenGarbage = false;
                if (copy.get(index).getType() == Material.POTION)
                {
                    actualRewards.add(copy.get(index));
                    copy.remove(index);
                    hasGottenGarbage = true;
                }
                else if (copy.get(index).getType() == Material.ZOMBIE_HEAD) {
                    actualRewards.add(copy.get(index));
                    copy.remove(index);
                    hasGottenGarbage = true;
                }
                if (hasGottenGarbage)
                    index = new Random().nextInt(copy.size());

                actualRewards.add(copy.get(index));
                copy.remove(index);
            }



            int freeSlots = 32;
            for(ItemStack item : p.getInventory().getStorageContents())
            {
                if (item!= null)
                    freeSlots--;
            }
            // create random
            for (ItemStack i : actualRewards)
            {
                if (freeSlots <= 0)
                {
                    if (i.getType() == Material.POTION)
                    {
                        for(int it = 0; it < potionMultiplier; it++)
                        {
                            p.getLocation().getWorld().dropItem(p.getLocation(), i);
                        }
                    }else
                        p.getLocation().getWorld().dropItem(p.getLocation(), i);
                }
                else {

                    if (i.getType() == Material.POTION) {
                        for (int it = 0; it < potionMultiplier; it++) {

                            if (freeSlots <= 0)
                            {
                                p.getLocation().getWorld().dropItem(p.getLocation(), i);
                            }else {
                                    p.getInventory().addItem(i);
                                    freeSlots--;
                            }
                        }
                    }
                    else {
                        p.getInventory().addItem(i);
                        freeSlots--;
                    }
                }

            }
        }
    }

    public void GiveRewardsToMe(Player player)
    {
        ArrayList<ItemStack> items= new ArrayList<ItemStack>();
        items.add(new ItemStack(Material.DIAMOND, 15));

        Integer expOrbs = 5;
        Random rand = new Random();
        for(int exp = 0; exp < expOrbs; exp++ )
        {

            Location loc = player.getLocation();
            ExperienceOrb e = ((ExperienceOrb) player.getLocation().getWorld().spawn(loc, ExperienceOrb.class));
            e.setExperience(250);
        }

        ItemStack godItemSword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta godSwordMeta = godItemSword.getItemMeta();
        godSwordMeta.addEnchant(Enchantment.DAMAGE_ARTHROPODS, 5, true);
        godSwordMeta.addEnchant(Enchantment.FIRE_ASPECT, 2, true);
        godSwordMeta.addEnchant(Enchantment.KNOCKBACK, 2, true);
        godSwordMeta.addEnchant(Enchantment.LOOT_BONUS_MOBS, 3, true);
        godSwordMeta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
        godSwordMeta.addEnchant(Enchantment.DAMAGE_UNDEAD, 5, true);
        godSwordMeta.addEnchant(Enchantment.DURABILITY, 3, true);
        godSwordMeta.setDisplayName("THUMBUS' SWORD");

        godItemSword.setItemMeta(godSwordMeta);
        items.add(godItemSword);

        ItemStack godItemHead = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta godHeadMeta = godItemHead.getItemMeta();
        godHeadMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 3, true);
        godHeadMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
        godHeadMeta.addEnchant(Enchantment.OXYGEN, 3, true);
        godHeadMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
        godHeadMeta.addEnchant(Enchantment.DURABILITY, 6, true);
        godHeadMeta.setDisplayName("THUMBUS' HELM");

        godItemHead.setItemMeta(godHeadMeta);
        items.add(godItemHead);


        ItemStack godItemChest = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta godChestMeta = godItemChest.getItemMeta();
        godChestMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 3, true);
        godChestMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
        godChestMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
        godChestMeta.addEnchant(Enchantment.DURABILITY, 6, true);
        godChestMeta.setDisplayName("THUMBUS' MANTLE");
        godItemChest.setItemMeta(godChestMeta);
        items.add(godItemChest);


        ItemStack godItemLegs = new ItemStack(Material.DIAMOND_LEGGINGS);
        ItemMeta godLegMeta = godItemLegs.getItemMeta();
        godLegMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 3, true);
        godLegMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
        godLegMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
        godLegMeta.addEnchant(Enchantment.DURABILITY, 6, true);
        godLegMeta.setDisplayName("THUMBUS' GREAVES");
        godItemLegs.setItemMeta(godLegMeta);
        items.add(godItemLegs);


        ItemStack godItemBoots = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta godBootMeta = godItemBoots.getItemMeta();
        godBootMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 3, true);
        godBootMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
        godBootMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
        godBootMeta.addEnchant(Enchantment.DURABILITY, 3, true);
        godBootMeta.addEnchant(Enchantment.PROTECTION_FALL, 5, true);
        godBootMeta.setDisplayName("THUMBUS' BOOTS");
        godItemBoots.setItemMeta(godBootMeta);
        items.add(godItemBoots);


        ItemStack godItemWings = new ItemStack(Material.ELYTRA);
        ItemMeta godWingsMeta = godItemWings.getItemMeta();
        godWingsMeta.addEnchant(Enchantment.MENDING, 1, true);
        godWingsMeta.addEnchant(Enchantment.DURABILITY, 3, true);
        godWingsMeta.setDisplayName("THUMBUS' WINGS");
        godItemWings.setItemMeta(godWingsMeta);
        items.add(godItemWings);

        ItemStack godItemBow = new ItemStack(Material.BOW);
        ItemMeta godBowMeta = godItemBow.getItemMeta();
        godBowMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        godBowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
        godBowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 2, true);
        godBowMeta.setDisplayName("THUMBUS' BOW");
        godItemBow.setItemMeta(godBowMeta);
        items.add(godItemBow);

        ItemStack godItemPickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta godPickaxeMeta = godItemPickaxe.getItemMeta();
        godPickaxeMeta.addEnchant(Enchantment.DIG_SPEED, 5, true);
        godPickaxeMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, true);
        godPickaxeMeta.setDisplayName("THUMBUS' PICKAXE");
        godItemPickaxe.setItemMeta(godPickaxeMeta);
        items.add(godItemPickaxe);


        ItemStack potionStrength = new ItemStack(Material.POTION,1);
        PotionMeta potStrengthMeta = (PotionMeta)potionStrength.getItemMeta();
        potStrengthMeta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 300*20, 1), true);
        potStrengthMeta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300*20, 3), true);
        potStrengthMeta.addCustomEffect(new PotionEffect(PotionEffectType.HUNGER, 300*20, 1), true);
        potStrengthMeta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, 300*20, 2), true);
        potStrengthMeta.setDisplayName("THUMBUS' STRENGTH POTION");
        potionStrength.setItemMeta(potStrengthMeta);
        items.add(potionStrength);

        ItemStack potionHealing = new ItemStack(Material.POTION,1);
        PotionMeta potHealingMeta = (PotionMeta)potionHealing.getItemMeta();
        potHealingMeta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 60*20, 3), true);
        potHealingMeta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 60*20, 1), true);
        potHealingMeta.setDisplayName("THUMBUS' HEALING POTION");
        potionHealing.setItemMeta(potHealingMeta);
        items.add(potionHealing);

        ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING,1);
        items.add(totem);

        ItemStack head = new ItemStack(Material.ZOMBIE_HEAD,1);
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName("THUMBUS' HEAD");
        headMeta.addEnchant(Enchantment.BINDING_CURSE, 1,true);
        headMeta.addEnchant(Enchantment.VANISHING_CURSE, 1,true);
        headMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 5,true);
        headMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5,true);
        head.setItemMeta(headMeta);
        items.add(head);


        Integer randomItem = 3;
        Integer potionMultiplier = 3;
      //  for (Player p : players)
        {
            // store our rewards for this player
            ArrayList<ItemStack> actualRewards = new ArrayList<>();
            // a copy of our rewards list to modify
            ArrayList<ItemStack> copy = new ArrayList<>(items);
            // iterate through reward count
            for (int i = 0; i < randomItem; i++) {
                // generate random based on max size
                int index = new Random().nextInt(copy.size());
                // add our index
                actualRewards.add(copy.get(index));
                // remove from this players reward pool
                copy.remove(index);
            }


            int freeSlots = 32;
            for (ItemStack item : player.getInventory().getStorageContents()) {
                if (item != null)
                    freeSlots--;
            }
            // create random
            for (ItemStack i : actualRewards) {
                if (freeSlots <= 0) {
                    if (i.getType() == Material.POTION) {
                        for (int it = 0; it < potionMultiplier; it++) {
                            player.getLocation().getWorld().dropItem(player.getLocation(), i);
                        }
                    } else
                        player.getLocation().getWorld().dropItem(player.getLocation(), i);
                } else {

                    if (i.getType() == Material.POTION) {
                        for (int it = 0; it < potionMultiplier; it++) {

                            if (freeSlots <= 0) {
                                player.getLocation().getWorld().dropItem(player.getLocation(), i);
                            } else {
                                player.getInventory().addItem(i);
                                freeSlots--;
                            }
                        }
                    } else {
                        player.getInventory().addItem(i);
                        freeSlots--;
                    }
                }

            }
        }
       // }
    }


    public Runnable EventActiveLoop = new BukkitRunnable() {
        @Override
        public void run()
        {
            if (isEventJoin)
            {
                if (activePlayers.size() >= minimumPlayers && !hasInitialisedEventData)
                {
                    hasInitialisedEventData = true;
                }

                if (hasAnnouncedInitialJoin)
                {

                    BukkitScheduler scheduler = Riverland._Instance.getServer().getScheduler();
                    announcerJoinID = scheduler.scheduleSyncRepeatingTask(Riverland._Instance, new Runnable() {
                        @Override
                        public void run() {
                            //Riverland._Instance.getServer().broadcastMessage("Event Available for " + joinTimer + " Seconds");
                           if (joinTimer == eventTeleportTimer)
                            {
                                //Riverland._Instance.getServer().broadcastMessage("Event Available for " + joinTimer + " Seconds");
                                net.md_5.bungee.api.chat.TextComponent message = new net.md_5.bungee.api.chat.TextComponent( "Event Available for " + joinTimer + " Seconds" );
                                message.setColor(ChatColor.LIGHT_PURPLE);
                                net.md_5.bungee.api.chat.TextComponent click = new net.md_5.bungee.api.chat.TextComponent( " [Click to join]" );
                                click.setColor(ChatColor.AQUA);
                                click.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/ArenaEvent join")); // if the user clicks the message, tp them to the coords   message.addExtra(click);
                                message.addExtra(click);
                                SendOnlyAdminBroadcast(message);
                            }
                           if (joinTimer == 60)
                           {
                               net.md_5.bungee.api.chat.TextComponent message = new net.md_5.bungee.api.chat.TextComponent( "Event Available for " + joinTimer + " Seconds" );
                               message.setColor(ChatColor.LIGHT_PURPLE);
                               net.md_5.bungee.api.chat.TextComponent click = new net.md_5.bungee.api.chat.TextComponent( " [Click to join]" );
                               click.setColor(ChatColor.AQUA);
                               click.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/ArenaEvent join")); // if the user clicks the message, tp them to the coords   message.addExtra(click);
                               message.addExtra(click);
                               SendOnlyAdminBroadcast(message);
                           }

                            if (joinTimer <= 3 && joinTimer > -1)
                            {
                               // Riverland._Instance.getServer().broadcastMessage("Event Available for " + joinTimer.toString() + " Seconds");
                                net.md_5.bungee.api.chat.TextComponent message = new net.md_5.bungee.api.chat.TextComponent( "Event Available for " + joinTimer + " Seconds" );
                                message.setColor(ChatColor.LIGHT_PURPLE);
                                net.md_5.bungee.api.chat.TextComponent click = new net.md_5.bungee.api.chat.TextComponent( " [Click to join]" );
                                click.setColor(ChatColor.AQUA);
                                click.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/ArenaEvent join")); // if the user clicks the message, tp them to the coords  message.addExtra(click);
                                message.addExtra(click);
                                SendOnlyAdminBroadcast(message);
                            }
                            joinTimer--;

                            if (joinTimer ==30 ) {
                                // announcing start..
                                hasStartedEvent = true;
                                awaitingStart = true;
                                joinTimer = eventTeleportTimer;
                                Riverland._Instance.getServer().getScheduler().cancelTask(announcerJoinID);

                            }
                        }
                    }, 0L, 20L);
                    // check for isEvent started, or if the hasStarted or if the event has ended early..
                    hasAnnouncedInitialJoin = false;
                }



            }else if (hasInitialisedEventData && hasStartedEvent && !hasEarlyEnded)
            {

                for (Player p : activePlayers)
                {
                    if (p.getLocation().distanceSquared(eventLocation) > (playerEventRadius * playerEventRadius))
                    {
                        p.sendMessage("You have left the event, you will not receive rewards.");
                        blackListedPlayers.add(p);
                        activePlayers.remove(p);
                        joinedPlayers.remove(p);
                        if (activeBossMusicPlayers.contains(p))
                        {
                            p.stopSound(bossSound);
                            activeBossMusicPlayers.remove(p);
                        }
                        if (activeRunMusicPlayers.contains(p))
                        {
                            p.stopSound(runSound);
                            activeRunMusicPlayers.remove(p);
                        }
                    }
                }
            }

        }
    };

    public void RegisterPlayer(Player player)
    {
        if (!activePlayers.contains(player))
        {
            activePlayers.add(player);
            joinedPlayers.put(player, player.getLocation());
            net.md_5.bungee.api.chat.TextComponent message = new net.md_5.bungee.api.chat.TextComponent("Player: " + player.getName() + " Has joined the event." );
            message.setColor(ChatColor.DARK_PURPLE);
            net.md_5.bungee.api.chat.TextComponent message2 = new net.md_5.bungee.api.chat.TextComponent("There are " + joinedPlayers.size() + " Player(s) in the event.");
            message2.setColor(ChatColor.DARK_PURPLE);
            if (!isTesting)
            {
                Riverland._Instance.getServer().broadcast(message);
                Riverland._Instance.getServer().broadcast(message2);
            }else {
                for (Player p : activePlayers) {
                    p.sendMessage(message);
                    p.sendMessage(message2);
                }
            }
        }
    }
    public void TeleportPlayer(Player player)
    {
        //Location loc = new Location(Riverland._Instance.getServer().getWorld(args[1]),Double.parseDouble(args[2]), Double.parseDouble(args[3]) , Double.parseDouble(args[4]));
        player.teleport(eventLocation);
    }



    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
     //   if (label.compareToIgnoreCase("leave") == 0)
     //   {
     //       if (args.length == 0) {
     //           Riverland._Instance.spectatorMode.SetSpecatorAvaiable(false);
     //           Riverland._Instance.spectatorMode.RemovePlayerFromSpectator((Player) sender);
     //       }
     //       else {
     //           Riverland._Instance.spectatorMode.SetSpecatorAvaiable(true);
     //           Riverland._Instance.spectatorMode.AddPlayerToSpectator((Player) sender);
     //           ArrayList<Player> t = new ArrayList<>();
     //           t.add(Riverland._Instance.getServer().getPlayer("beacle"));
//
     //           Riverland._Instance.spectatorMode.UpdateSpectatorPlayers(t);
//
     //       }
     //   }
        if (isEventJoin)
        {
            if (args.length > 0 && args[0].equalsIgnoreCase("join"))
            {
                if (sender instanceof Player) {
                    Player p = (Player) sender;
                    if (joinedPlayers.containsKey(p) && !blackListedPlayers.contains(p))
                    {
                        net.md_5.bungee.api.chat.TextComponent message = new net.md_5.bungee.api.chat.TextComponent("You are already in the event!");
                        message.setColor(ChatColor.LIGHT_PURPLE);
                        sender.sendMessage(message);
                    }else
                    {
                        if (!blackListedPlayers.contains(p))
                            RegisterPlayer(p);



                        net.md_5.bungee.api.chat.TextComponent message2 = new net.md_5.bungee.api.chat.TextComponent("The event will start shortly.");
                        message2.setColor(ChatColor.LIGHT_PURPLE);
                        sender.sendMessage(message2);
                    }
                }
                return true;
            }
        }
        if (sender instanceof  Player && sender.isOp())
            {
                if (args.length > 0 && args[0].equalsIgnoreCase("set"))
                {
                    eventLocation = ((Player)sender).getLocation();
                    Riverland._Instance.giantBossStartLocation = eventLocation;
                    ((Player)sender).sendMessage("Event Location set..");
                    Riverland._Instance.SaveLocations();
                    return true;
                }else  if (args.length > 0 && args[0].equalsIgnoreCase("start"))
                {

                    // attempt to set //
                    eventLocation = Riverland._Instance.giantBossStartLocation;
                    BossSpawnLocation = Riverland._Instance.giantBossEndLocation;

                    if (eventLocation == null || BossSpawnLocation == null)
                        ((Player)sender).sendMessage("Event Location Not Set..");
                    else {
                        UpdateEvent(eventLocation, true);
                        ((Player) sender).sendMessage("Event starting..");
                        hasAnnouncedInitialJoin = true;
                        BukkitScheduler scheduler = Riverland._Instance.getServer().getScheduler();
                        EventActiveLoopID           = scheduler.scheduleSyncRepeatingTask(Riverland._Instance, EventActiveLoop, 0, 25);
                        EventMainCountdownID        = scheduler.scheduleSyncRepeatingTask(Riverland._Instance, EventMainCountdown, 0, 40);
                        EventStartStopAnnouncerID   = scheduler.scheduleSyncRepeatingTask(Riverland._Instance, EventStartStopAnnouncer, 0, 100);

                        hasBeenAsyncd = true;
                    }
                    return true;
                }else  if (args.length > 0 && args[0].equalsIgnoreCase("stop"))
                {
                   // UpdateEvent(eventLocation, false);
                    ((Player)sender).sendMessage("Event stopping..");

                    net.md_5.bungee.api.chat.TextComponent message2 = new net.md_5.bungee.api.chat.TextComponent("The event has been cancelled.");
                    message2.setColor(ChatColor.RED);
                    Riverland._Instance.getServer().broadcast(message2);

                    Reset();

                    return true;
                }
                else if (args.length > 0 && args[0].equalsIgnoreCase("boss"))
                {

                    BossSpawnLocation = ((Player)sender).getLocation();
                    ((Player)sender).sendMessage("Event Location set..");
                    Riverland._Instance.giantBossEndLocation = BossSpawnLocation;
                    Riverland._Instance.SaveLocations();
                    return true;
                }else if (args.length > 0 && args[0].equalsIgnoreCase("testmob") && sender.isOp())
                {
                    Player p = (Player)sender;
                    ArrayList<Player> t = new ArrayList<>();
                    t.add(((Player)sender));

                    Entity Myzombie = Riverland.GiantTypeInstance.spawn(new Location(p.getLocation().getWorld(), p.getLocation().getX(),p.getLocation().getY(),p.getLocation().getZ()));

                    Myzombie.setCustomName("THUMBUS");
                    Myzombie.setCustomNameVisible(true);
                    ItemStack sword = new ItemStack(Material.GOLDEN_SWORD,1);
                    ItemMeta meta = sword.getItemMeta();
                    meta.addEnchant(Enchantment.DAMAGE_ALL, 6, true);
                    meta.setDisplayName("THUMBUS' GOLDEN SWORD");

                    sword.setItemMeta(meta);

                    ((Giant)Myzombie).getEquipment().setItemInMainHand(sword);
                    ((Giant)Myzombie).getEquipment().setHelmet(new ItemStack(Material.CHAINMAIL_HELMET,1));
                    ((Giant)Myzombie).getEquipment().setChestplate(new ItemStack(Material.CHAINMAIL_CHESTPLATE,1));
                    ((Giant)Myzombie).getEquipment().setLeggings(new ItemStack(Material.CHAINMAIL_LEGGINGS,1));
                    ((Giant)Myzombie).getEquipment().setBoots(new ItemStack(Material.CHAINMAIL_BOOTS,1));
                    ((Giant)Myzombie).getEquipment().setItemInOffHand(new ItemStack(Material.SHIELD,1));


                }else if (args.length > 0 && args[0].equalsIgnoreCase("testrewards") && sender.isOp())
                {
                    GiveRewardsToMe((Player)sender);
                    sender.sendMessage("Rewarded");
                    return true;
                }else if (args.length > 0 && args[0].equalsIgnoreCase("removeHead") && sender.isOp())
                {
                    ((Player)sender).getEquipment().getHelmet().getEnchantments().clear();
                }else if (args.length > 0 && args[0].equalsIgnoreCase("forceJoin") && sender.isOp())
                {
                    if (args.length > 1) {
                        Player p = Riverland._Instance.getServer().getPlayer(args[1]);
                        // if (sender instanceof Player) {
                        //   Player p = (Player) sender;
                        if (joinedPlayers.containsKey(p) && !blackListedPlayers.contains(p)) {
                            net.md_5.bungee.api.chat.TextComponent message = new net.md_5.bungee.api.chat.TextComponent("You are already in the event!");
                            message.setColor(ChatColor.LIGHT_PURPLE);
                            p.sendMessage(message);
                        } else {
                            if (!blackListedPlayers.contains(p))
                                RegisterPlayer(p);


                            net.md_5.bungee.api.chat.TextComponent message2 = new net.md_5.bungee.api.chat.TextComponent("The event will start shortly.");
                            message2.setColor(ChatColor.LIGHT_PURPLE);
                            p.sendMessage(message2);

                        }
                    }
                }
            }
        return false;
    }
}
