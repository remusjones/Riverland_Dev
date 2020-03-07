package riverland.dev.riverland;


import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;

public class PVPEvent implements CommandExecutor, Listener
{

    ArrayList<ItemStack> headsItems = new ArrayList<>();
    ArrayList<ItemStack> chestsItems = new ArrayList<>();
    ArrayList<ItemStack> legsItems = new ArrayList<>();
    ArrayList<ItemStack> feetItems = new ArrayList<>();
    ArrayList<ItemStack> weaponItems = new ArrayList<>();
    ArrayList<ItemStack> offhandItems = new ArrayList<>();

    public static Location playerStart1 = null;
    public static Location playerStart2 = null;
    public static Location spectating = null;

    // enchantment and max level
    Map<ArrayList<Enchantment>,ArrayList<Integer>> armorEnchantmentsMap = new HashMap<>();
    Map<ArrayList<Enchantment>,ArrayList<Integer>> weaponEnchantmentsMap = new HashMap<>();
    Map<ArrayList<Enchantment>,ArrayList<Integer>> bowEnchantmentsMap = new HashMap<>();
    Map<ArrayList<Enchantment>,ArrayList<Integer>> tridentEnchantmentsMap = new HashMap<>();


    public ArrayList<Player> joinedPlayers = new ArrayList<>();
    public ArrayList<Player> combatingPlayers = new ArrayList<>();

    public Player lastVictor = null;
    Integer roundWait = 15;
    Integer currRoundWait = 15;
    Integer joinWait = 60;
    Integer currJoinWait = 60;
    Integer eventStartID = 0;
    Integer roundStartID = 0;
    Integer combatRoundID = 0;
    public boolean isJoinable = false;
    public boolean isRoundRunning = false;

    public boolean player1Win = false;
    public boolean player2Win = false;
    public boolean isTesting = true;

    public PVPEvent()
    {
        SetupEvent();
    }
    public void RestartEvent()
    {
        player2Win = false;
        player1Win = false;
        currJoinWait = joinWait;
        currRoundWait = roundWait;
        isJoinable = false;
        joinedPlayers.clear();
        combatingPlayers.clear();
        Riverland._Instance.getServer().getScheduler().cancelTask(eventStartID);
        Riverland._Instance.getServer().getScheduler().cancelTask(roundStartID);
        Riverland._Instance.getServer().getScheduler().cancelTask(combatRoundID);
    }

    public void SetupEvent()
    {
        // setup head kit pool
        ItemStack head1  = new ItemStack(Material.IRON_HELMET,1);
        ItemStack head2  = new ItemStack(Material.CHAINMAIL_HELMET,1);
        ItemStack head3  = new ItemStack(Material.GOLDEN_HELMET,1);
        ItemStack head4  = new ItemStack(Material.DIAMOND_HELMET,1);
        ItemStack head5 = new ItemStack(Material.LEATHER_HELMET, 1);
        headsItems.add(head1);
        headsItems.add(head2);
        headsItems.add(head3);
        headsItems.add(head4);
        headsItems.add(head5);

        ItemStack chest1  = new ItemStack(Material.IRON_CHESTPLATE,1);
        ItemStack chest2  = new ItemStack(Material.CHAINMAIL_CHESTPLATE,1);
        ItemStack chest3  = new ItemStack(Material.LEATHER_CHESTPLATE,1);
        ItemStack chest4  = new ItemStack(Material.GOLDEN_CHESTPLATE,1);
        ItemStack chest5 = new ItemStack(Material.DIAMOND_CHESTPLATE, 1);
        chestsItems.add(chest1);
        chestsItems.add(chest2);
        chestsItems.add(chest3);
        chestsItems.add(chest4);
        chestsItems.add(chest5);

        ItemStack legs1  = new ItemStack(Material.IRON_LEGGINGS,1);
        ItemStack legs2  = new ItemStack(Material.CHAINMAIL_LEGGINGS,1);
        ItemStack legs3  = new ItemStack(Material.LEATHER_LEGGINGS,1);
        ItemStack legs4  = new ItemStack(Material.GOLDEN_LEGGINGS,1);
        ItemStack legs5 =  new ItemStack(Material.DIAMOND_LEGGINGS, 1);
        legsItems.add(legs1);
        legsItems.add(legs2);
        legsItems.add(legs3);
        legsItems.add(legs4);
        legsItems.add(legs5);
        ItemStack boots1  = new ItemStack(Material.IRON_BOOTS,1);
        ItemStack boots2  = new ItemStack(Material.CHAINMAIL_BOOTS,1);
        ItemStack boots3  = new ItemStack(Material.LEATHER_BOOTS,1);
        ItemStack boots4  = new ItemStack(Material.GOLDEN_BOOTS,1);
        ItemStack boots5 =  new ItemStack(Material.DIAMOND_BOOTS, 1);
        feetItems.add(boots1);
        feetItems.add(boots2);
        feetItems.add(boots3);
        feetItems.add(boots4);
        feetItems.add(boots5);

        ItemStack weapon1  = new ItemStack(Material.WOODEN_SWORD,1);
        ItemStack weapon2  = new ItemStack(Material.IRON_SWORD,1);
        ItemStack weapon3  = new ItemStack(Material.GOLDEN_SWORD,1);
        ItemStack weapon4  = new ItemStack(Material.DIAMOND_SWORD,1);
        ItemStack weapon5 =  new ItemStack(Material.BOW, 1);
        ItemStack weapon6 =  new ItemStack(Material.TRIDENT, 1);
        ItemStack weapon7 =  new ItemStack(Material.CROSSBOW, 1);
        weaponItems.add(weapon1);
        weaponItems.add(weapon2);
        weaponItems.add(weapon3);
        weaponItems.add(weapon4);
        weaponItems.add(weapon5);
        weaponItems.add(weapon6);
        weaponItems.add(weapon7);
        ItemStack offhand  = new ItemStack(Material.SHIELD,1);
        offhandItems.add(offhand);

        ArrayList<Enchantment> armorEnchantments = new ArrayList<>();
        ArrayList<Integer> armorEnchantmentMax = new ArrayList<>();
        armorEnchantments.add(Enchantment.PROTECTION_FIRE);
        armorEnchantmentMax.add(4);
        armorEnchantments.add(Enchantment.PROTECTION_PROJECTILE);
        armorEnchantmentMax.add(4);
        armorEnchantments.add(Enchantment.PROTECTION_ENVIRONMENTAL);
        armorEnchantmentMax.add(4);
        armorEnchantments.add(Enchantment.DURABILITY);
        armorEnchantmentMax.add(4);
        armorEnchantmentsMap.put(armorEnchantments, armorEnchantmentMax);

        ArrayList<Enchantment> weaponEnchantments = new ArrayList<>();
        ArrayList<Integer> weaponEnchantmentMax = new ArrayList<>();
        weaponEnchantments.add(Enchantment.DAMAGE_ALL);
        weaponEnchantmentMax.add(5);
        weaponEnchantments.add(Enchantment.SWEEPING_EDGE);
        weaponEnchantmentMax.add(4);
        weaponEnchantments.add(Enchantment.PIERCING);
        weaponEnchantmentMax.add(4);
        weaponEnchantments.add(Enchantment.KNOCKBACK);
        weaponEnchantmentMax.add(3);
        weaponEnchantments.add(Enchantment.DURABILITY);
        weaponEnchantmentMax.add(5);
        weaponEnchantmentsMap.put(weaponEnchantments, weaponEnchantmentMax);


        ArrayList<Enchantment> bowEnchantments = new ArrayList<>();
        ArrayList<Integer> bowEnchantmentMax = new ArrayList<>();
        bowEnchantments.add(Enchantment.ARROW_DAMAGE);
        bowEnchantmentMax.add(5);
        bowEnchantments.add(Enchantment.ARROW_KNOCKBACK);
        bowEnchantmentMax.add(3);
        bowEnchantments.add(Enchantment.ARROW_INFINITE);
        bowEnchantmentMax.add(1);
        //bowEnchantments.add(Enchantment.ARROW_FIRE);
        //bowEnchantmentMax.add(1);
        bowEnchantmentsMap.put(bowEnchantments, bowEnchantmentMax);

        ArrayList<Enchantment> tridentEnchantments = new ArrayList<>();
        ArrayList<Integer> tridentEnchantmentMax = new ArrayList<>();
        tridentEnchantments.add(Enchantment.LOYALTY);
        tridentEnchantmentMax.add(1);
        tridentEnchantments.add(Enchantment.DAMAGE_ALL);
        tridentEnchantmentMax.add(5);
        tridentEnchantments.add(Enchantment.KNOCKBACK);
        tridentEnchantmentMax.add(5);
        tridentEnchantments.add(Enchantment.FIRE_ASPECT);
        tridentEnchantmentMax.add(5);
        tridentEnchantmentsMap.put(tridentEnchantments, tridentEnchantmentMax);

    }
    public boolean getRandomBoolean() {
        return Math.random() < 0.5;
        //I tried another approaches here, still the same result
    }
    public ItemStack GenerateEnchantment(Map<ArrayList<Enchantment>,ArrayList<Integer>> enchantments, ItemStack item)
    {
        Set<Map.Entry<ArrayList<Enchantment>,ArrayList<Integer>>> map = enchantments.entrySet(); // get entry point for our stored map
        ItemMeta itemMeta = item.getItemMeta();
        for(Map.Entry<ArrayList<Enchantment>,ArrayList<Integer>> pair : map)
        {
            for(int i = 0; i < pair.getValue().size(); i++)
            {
                if (pair.getValue().get(i) > 1)
                {
                    // we know its a multi choice..
                    // get random number..
                    int enchantLevel = new Random().nextInt(pair.getValue().get(i));
                    if (pair.getValue().get(i) == 1)
                        enchantLevel = 1;
                    itemMeta.addEnchant(pair.getKey().get(i), enchantLevel+1,true);
                }else
                {

                    // its a true false enchant..
                    if (getRandomBoolean())
                        itemMeta.addEnchant(pair.getKey().get(i), pair.getValue().get(i),true);
                }
            }
        }
        item.setItemMeta(itemMeta);
        return item;
    }

    public void AnnounceCountdown(String message, Integer remaining)
    {
        net.md_5.bungee.api.chat.TextComponent message1 = new net.md_5.bungee.api.chat.TextComponent (message);
        message1.setColor(ChatColor.LIGHT_PURPLE);
        net.md_5.bungee.api.chat.TextComponent message2 = new net.md_5.bungee.api.chat.TextComponent (" " + remaining.toString());
        message2.setColor(ChatColor.GREEN);
        message1.addExtra(message2);

        net.md_5.bungee.api.chat.TextComponent message3 = new net.md_5.bungee.api.chat.TextComponent (" " + "Second(s)");
        message3.setColor(ChatColor.LIGHT_PURPLE);
        message1.addExtra(message3);

        net.md_5.bungee.api.chat.TextComponent click = new net.md_5.bungee.api.chat.TextComponent( " [Click to join]" );
        click.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/PVPArena join")); // if the user clicks the message, tp them to the coords   message.addExtra(click);
        click.setColor(ChatColor.AQUA);
        message1.addExtra(click);

        if (!isTesting)
            Riverland._Instance.getServer().broadcast(message1);
        else
            BroadcastOP(message1);

    }
    public void AnnouncePlainWithJoin(String message)
    {
        net.md_5.bungee.api.chat.TextComponent message1 = new net.md_5.bungee.api.chat.TextComponent (message);
        message1.setColor(ChatColor.LIGHT_PURPLE);
        net.md_5.bungee.api.chat.TextComponent click = new net.md_5.bungee.api.chat.TextComponent( " [Click to join]" );
        click.setClickEvent( new ClickEvent( ClickEvent.Action.RUN_COMMAND, "/PVPArena join")); // if the user clicks the message, tp them to the coords   message.addExtra(click);
        click.setColor(ChatColor.AQUA);
        message1.addExtra(click);
        if(!isTesting)
            Riverland._Instance.getServer().broadcast(message1);
        else
            BroadcastOP(message1);
    }
    public void AnnouncePlainRed(String message)
    {
        net.md_5.bungee.api.chat.TextComponent message1 = new net.md_5.bungee.api.chat.TextComponent (message);
        message1.setColor(ChatColor.RED);
        if (!isTesting)
            Riverland._Instance.getServer().broadcast(message1);
        else
            BroadcastOP(message1);
    }
    public void AnnouncePlainPurple(String message)
    {
        net.md_5.bungee.api.chat.TextComponent message1 = new net.md_5.bungee.api.chat.TextComponent (message);
        message1.setColor(ChatColor.LIGHT_PURPLE);
        if (!isTesting)
            Riverland._Instance.getServer().broadcast(message1);
        else
            BroadcastOP(message1);
    }
    public void AnnouncePlainDarkPurple(String message)
    {
        net.md_5.bungee.api.chat.TextComponent message1 = new net.md_5.bungee.api.chat.TextComponent (message);
        message1.setColor(ChatColor.DARK_PURPLE);
        if (!isTesting)
            Riverland._Instance.getServer().broadcast(message1);
        else
            BroadcastOP(message1);
    }
    public void AnnounceRound(String message, Integer time)
    {
        net.md_5.bungee.api.chat.TextComponent message1 = new net.md_5.bungee.api.chat.TextComponent (message);
        message1.setColor(ChatColor.LIGHT_PURPLE);
        net.md_5.bungee.api.chat.TextComponent message2 = new net.md_5.bungee.api.chat.TextComponent (" " + time.toString());
        message2.setColor(ChatColor.GREEN);

        net.md_5.bungee.api.chat.TextComponent message3 = new net.md_5.bungee.api.chat.TextComponent (" " + "Second(s)");
        message2.setColor(ChatColor.GREEN);
        message1.addExtra(message2);

        if (!isTesting)
            Riverland._Instance.getServer().broadcast(message1);
        else
            BroadcastOP(message1);

    }
    public void BroadcastOP(net.md_5.bungee.api.chat.TextComponent  message)
    {
        for(Player player : Riverland._Instance.getServer().getOnlinePlayers())
        {
            if (player.isOp() || player.hasPermission("Riverland.PVPArena"))
                player.sendMessage(message);
        }
    }
    public void BeginEvent()
    {
        isJoinable = true;
        BukkitScheduler scheduler = Riverland._Instance.getServer().getScheduler();
        eventStartID = scheduler.scheduleSyncRepeatingTask(Riverland._Instance, () ->
        {
            if (currJoinWait == joinWait)
            {
                AnnouncePlainWithJoin("PvP Arena Event Started");
                AnnounceCountdown("Event Available for", currJoinWait);
            }
            if (currJoinWait == 100)
            {
                AnnounceCountdown("Event Available for", currJoinWait);
            }
           // if (currJoinWait == 60)
           // {
           //     AnnounceCountdown("Event Available for", currJoinWait);
           // }
            if (currJoinWait == 30)
            {
                AnnounceCountdown("Event Available for", currJoinWait);
            }
            if (currJoinWait <= 3 && currJoinWait > 0)
            {
                AnnounceCountdown("Event Available for", currJoinWait);
            }
            if (currJoinWait == 0)
            {
                if (joinedPlayers.size() < 2) {
                    AnnouncePlainRed("PvP Event Cancelled, not enough players!");
                    Riverland._Instance.getServer().getScheduler().cancelTask(eventStartID);
                    RestartEvent();
                }
                isJoinable = false;
                for(Player player : joinedPlayers)
                {
                    player.teleport(spectating);
                }
                BeginRoundCountdown();
                Riverland._Instance.getServer().getScheduler().cancelTask(eventStartID);
            }
            currJoinWait--;
        }, 0L, 20L);
    }
    public void EndEvent()
    {
        SendTitle("Victory!", lastVictor);
        lastVictor.teleport(spectating);
        AnnouncePlainPurple(lastVictor.getName() + " has won the tournament!");
        Bukkit.dispatchCommand(Riverland._Instance.getServer().getConsoleSender(), "token give " +  lastVictor.getName() + " 25");
        RestartEvent();
    }

    @EventHandler
    public void damage(EntityDamageEvent event) //Listens to EntityDamageEvent
    {
        if (isRoundRunning)
        {
            // check if player
            if (event.getEntity().getType() == EntityType.PLAYER)
            {
                if (combatingPlayers.contains(((Player) event.getEntity())))
                {
                    if ((((Player) event.getEntity()).getHealth() - event.getFinalDamage()) <= 0)
                    {
                        ((Player) event.getEntity()).setHealth(((Player) event.getEntity()).getMaxHealth());
                        if (combatingPlayers.get(0) == event.getEntity()) {
                            player2Win = true;
                        } else {
                            player1Win = true;
                        }
                        event.setCancelled(true);
                    }
                }
            }
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        if (joinedPlayers.contains(event.getPlayer()))
            joinedPlayers.remove(event.getPlayer());
        if (combatingPlayers.contains(event.getPlayer()))
        {
            if (combatingPlayers.get(0) == event.getPlayer()) {
                player2Win = true;
            } else {
                player1Win = true;
            }
        }
    }
    public void BeginRoundCountdown()
    {
        isJoinable = false;

        if (joinedPlayers.size() <= 1)
        {
            EndEvent();
            return;
        }
        else if (joinedPlayers.size() > 1)
        {
            AnnouncePlainPurple(joinedPlayers.size() + " Players Remain");
            ArrayList<Player> availablePlayers = new ArrayList<>(joinedPlayers);
            int firstPlayer = new Random().nextInt(availablePlayers.size());
            Player player1 = (Player) availablePlayers.get(firstPlayer);
            availablePlayers.remove(firstPlayer);
            int secondPlayer = new Random().nextInt(availablePlayers.size());
            Player player2 = (Player) availablePlayers.get(secondPlayer);


            combatingPlayers.add(player1);
            combatingPlayers.add(player2);
            AnnouncePlainRed(player1.getName() + " vs " + player2.getName());
        }

        BukkitScheduler scheduler = Riverland._Instance.getServer().getScheduler();
        roundStartID = scheduler.scheduleSyncRepeatingTask(Riverland._Instance, () ->
        {
            if (combatingPlayers.get(0) == null || combatingPlayers.get(1) == null)
            {
                AnnouncePlainRed("A combatant has left!");
                currRoundWait = roundWait;
                Riverland._Instance.getServer().getScheduler().cancelTask(roundStartID);
            }

            if(joinedPlayers.size() == 0)
            {
                RestartEvent();
            }
            if (currRoundWait == 30)
            {
                AnnounceRound("Round Starting in ", currRoundWait);
            }

            if (currRoundWait <= 3 && currRoundWait > 0)
            {
                AnnounceRound("Round Starting in ", currRoundWait);
            }

            if (currRoundWait == 0)
            {
                AnnouncePlainWithJoin("Spectate now! ");
                AnnouncePlainRed("Fight!");
                StartRound();
                currRoundWait = roundWait;
                Riverland._Instance.getServer().getScheduler().cancelTask(roundStartID);
            }
            currRoundWait--;
        },0L, 20L);
    }
    public void SendTitle(String message, Player player)
    {
        player.sendTitle(message, "", 20, 100, 20);
    }
    @EventHandler(priority= EventPriority.HIGHEST, ignoreCancelled=false)
    public void isCancelledTP(PlayerTeleportEvent event) {
        if(event.isCancelled())
        {
            if (joinedPlayers.contains(event.getPlayer()))
                event.setCancelled(false);
        }

    }
    public void HandleVictor(Player winner, Player loser)
    {
        if (loser != null)
        {
            loser.getInventory().clear();
            loser.getEquipment().clear();
            loser.teleport(spectating);
            joinedPlayers.remove(loser);
            SendTitle("Defeated", loser);
        }
        lastVictor = winner;
        if (joinedPlayers.size() > 1)
        {
            AnnouncePlainPurple(winner.getName() + " Won the round..");
            winner.teleport(spectating);
        }

        winner.getInventory().clear();
        winner.getEquipment().clear();
        winner.setHealth(winner.getMaxHealth());

        winner.setFireTicks(0);
        SendTitle("Victory!", winner );
        currRoundWait = roundWait;
        player1Win = false;
        player2Win = false;
        isRoundRunning = false;

        lastVictor = winner;
    }
    public void StartRound()
    {

        if (combatingPlayers.size() > 1) {
            combatingPlayers.get(0).getInventory().clear();
            combatingPlayers.get(1).getInventory().clear();
            combatingPlayers.get(0).getEquipment().clear();
            combatingPlayers.get(1).getEquipment().clear();

            combatingPlayers.get(0).setGameMode(GameMode.SURVIVAL);
            combatingPlayers.get(1).setGameMode(GameMode.SURVIVAL);
            // Teleport Players
            combatingPlayers.get(0).teleport(playerStart1);
            combatingPlayers.get(1).teleport(playerStart2);
            combatingPlayers.get(0).setHealth(combatingPlayers.get(0).getMaxHealth());
            combatingPlayers.get(1).setHealth(combatingPlayers.get(1).getMaxHealth());
            combatingPlayers.get(0).setFoodLevel(20);
            combatingPlayers.get(1).setFoodLevel(20);
            // Outfit Players
            GenerateLoadout(combatingPlayers.get(0), combatingPlayers.get(1));
        }else {
            EndEvent();
        }

        // wait for winner..
        BukkitScheduler scheduler = Riverland._Instance.getServer().getScheduler();
        combatRoundID = scheduler.scheduleSyncRepeatingTask(Riverland._Instance, () ->
        {
            if (combatingPlayers.get(0).getLocation().distanceSquared(playerStart1) > (30 * 30))
            {
                player2Win = true;
            }
            if (combatingPlayers.get(1).getLocation().distanceSquared(playerStart2) > (30 * 30))
            {
                player1Win = true;
            }
            if (player1Win)
            {
                HandleVictor(combatingPlayers.get(0), combatingPlayers.get(1));
                combatingPlayers.clear();
                currRoundWait = 15;
                BeginRoundCountdown();
                Riverland._Instance.getServer().getScheduler().cancelTask(combatRoundID);
            }
            else if (player2Win)
            {
                HandleVictor(combatingPlayers.get(1), combatingPlayers.get(0));
                combatingPlayers.clear();
                currRoundWait = 15;
                BeginRoundCountdown();
                Riverland._Instance.getServer().getScheduler().cancelTask(combatRoundID);
            }else
                isRoundRunning = true;
        },0L, 1L);

    }
    public void GenerateLoadout(Player p1, Player p2)
    {

        // get head item
        // head
        // armour
        // legs
        // feet
        int headIndex = new Random().nextInt(headsItems.size());
        ItemStack head = GenerateEnchantment(armorEnchantmentsMap, headsItems.get(headIndex));

        int chestIndex = new Random().nextInt(headsItems.size());
        ItemStack chest = GenerateEnchantment(armorEnchantmentsMap, chestsItems.get(chestIndex));

        int legIndex = new Random().nextInt(legsItems.size());
        ItemStack legs = GenerateEnchantment(armorEnchantmentsMap, legsItems.get(legIndex));

        int feetIndex = new Random().nextInt(feetItems.size());
        ItemStack feet = GenerateEnchantment(armorEnchantmentsMap, feetItems.get(feetIndex));
        // weapon
        int weaponIndex = new Random().nextInt(weaponItems.size());
        ItemStack weapon = null;
        if ( weaponItems.get(weaponIndex).getType() == Material.BOW || weaponItems.get(weaponIndex).getType() == Material.CROSSBOW)
        {
            weapon = GenerateEnchantment(bowEnchantmentsMap, weaponItems.get(weaponIndex));
            ItemMeta meta = weapon.getItemMeta();
            meta.addEnchant(Enchantment.ARROW_INFINITE, 1,true);
            weapon.setItemMeta(meta);
        }else if (weaponItems.get(weaponIndex).getType() == Material.TRIDENT)
        {
            ItemMeta meta = weapon.getItemMeta();
            meta.addEnchant(Enchantment.LOYALTY, 1,true);
            meta.addEnchant(Enchantment.IMPALING, new Random().nextInt(4) + 1, true);
            weapon.setItemMeta(meta);
        }else
            weapon = GenerateEnchantment(weaponEnchantmentsMap, weaponItems.get(weaponIndex));

        if (getRandomBoolean())
        {
            p1.getInventory().setItemInOffHand(offhandItems.get(0));
            p2.getInventory().setItemInOffHand(offhandItems.get(0));
        }

        p1.getEquipment().setHelmet(head);
        p2.getEquipment().setHelmet(head);

        p1.getEquipment().setChestplate(chest);
        p2.getEquipment().setChestplate(chest);

        p1.getEquipment().setLeggings(legs);
        p2.getEquipment().setLeggings(legs);

        p1.getEquipment().setBoots(feet);
        p2.getEquipment().setBoots(feet);

        p1.getInventory().setItemInMainHand(weapon);
        p2.getInventory().setItemInMainHand(weapon);
        ItemStack arrows = new ItemStack(Material.TIPPED_ARROW, 64);
        ItemStack arrows1 = new ItemStack(Material.TIPPED_ARROW, 64);
        PotionMeta arrowMeta = (PotionMeta)arrows.getItemMeta();
        arrowMeta.setBasePotionData(new PotionData(PotionType.POISON));
        arrows1.setItemMeta(arrowMeta);
        arrows.setItemMeta(arrowMeta);
        p1.getInventory().addItem(arrows);
        p2.getInventory().addItem(arrows);
        p1.getInventory().addItem(arrows1);
        p2.getInventory().addItem(arrows1);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (args[0].equalsIgnoreCase("start"))
        {
            playerStart1 = Riverland._Instance.player1Location;
            playerStart2 = Riverland._Instance.player2Location;
            spectating = Riverland._Instance.playerWatchLocation;
            RestartEvent();
            // start
            BeginEvent();
            isJoinable = true;
            return true;
        }
        if (args[0].equalsIgnoreCase("join"))
        {
            if ( isJoinable) {
                if (!joinedPlayers.contains((Player) sender)) {
                    joinedPlayers.add((Player) sender);
                    AnnouncePlainDarkPurple(((Player) sender).getName() + " has joined the event!");
                }else
                {
                    ((Player)sender).sendMessage("You've already joined the event");
                }
            }
            else{
                if (!combatingPlayers.contains(((Player)sender))) {
                    ((Player) sender).sendMessage("You are assigned as spectator");
                    ((Player) sender).teleport(spectating);
                }
            }

            return true;
        }
        if (sender.isOp()) {
            if (args[0].equalsIgnoreCase("set1")) {
                playerStart1 = ((Player) (sender)).getLocation();
                Riverland._Instance.player1Location = playerStart1;
                Riverland._Instance.SaveLocations();
                return true;
            }
            if (args[0].equalsIgnoreCase("set2")) {
                playerStart2 = ((Player) (sender)).getLocation();
                Riverland._Instance.player2Location = playerStart2;
                Riverland._Instance.SaveLocations();
                return true;
            }
            if (args[0].equalsIgnoreCase("watch")) {
                spectating = ((Player) (sender)).getLocation();
                Riverland._Instance.playerWatchLocation = spectating;
                Riverland._Instance.SaveLocations();
                return true;
            }
        }
        return false;
    }
}
