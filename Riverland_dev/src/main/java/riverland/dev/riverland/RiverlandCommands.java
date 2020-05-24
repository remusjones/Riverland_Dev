package riverland.dev.riverland;

<<<<<<< HEAD
import com.massivecraft.factions.*;
import org.bukkit.*;
=======
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
<<<<<<< HEAD
import org.bukkit.inventory.meta.tags.ItemTagType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class RiverlandCommands implements CommandExecutor {
=======
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.List;

public class RiverlandCommands implements CommandExecutor
{
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
    Integer currBlockX = 0;
    Integer currBlockZ = 0;
    World targetWorld = null;
    Player vic = null;
    Integer loadBlockID = 0;

<<<<<<< HEAD
    public void UpdateInventory() {

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.isOp())
            return false;
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("reload")) {
=======
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!sender.isOp())
             return false;

        if (args.length > 0 && args[0].equalsIgnoreCase("setThumbusBoss"))
        {
            ((Player)sender).sendMessage("Event Location set..");
            Riverland._Instance.giantBossEndLocation = ((Player)sender).getLocation();
            Riverland._Instance.SaveLocations();
            return true;
        }
        if (args.length > 0 && args[0].equalsIgnoreCase("setThumbusSpawn"))
        {
            ((Player)sender).sendMessage("Event Location set..");
            Riverland._Instance.giantBossStartLocation = ((Player)sender).getLocation();
            Riverland._Instance.SaveLocations();
            return true;
        }
        if (args.length > 0)
        {
            if (args[0].equalsIgnoreCase("reload"))
            {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b

                Riverland._Instance.reloadConfig();
                if (sender instanceof Player) // get instance of player.
                {
                    Player player = (Player) sender;

                    player.sendMessage(ChatColor.GREEN + " Reloading Config");
                }
<<<<<<< HEAD
            } else if (args[0].equalsIgnoreCase("tntSmall")) {
                try {
=======
            }
            else if (args[0].equalsIgnoreCase("tntSmall"))
            {
                try
                {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                    if (sender instanceof Player) // get instance of player.
                    {
                        Player player = (Player) sender;
                        float newTntRad = Float.parseFloat(args[1]);
                        Riverland._Instance.tntRadiusLow = newTntRad;
                        Riverland._Instance.UpdateConfig();
                        player.sendMessage(ChatColor.GREEN + " TNT Radius Modified");
                    }
<<<<<<< HEAD
                } catch (Exception exc) {
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("tntMedium")) {
                try {
=======
                }
                catch (Exception exc)
                {
                    return false;
                }
            }
            else if (args[0].equalsIgnoreCase("tntMedium"))
            {
                try
                {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                    if (sender instanceof Player) // get instance of player.
                    {
                        Player player = (Player) sender;
                        float newTntRad = Float.parseFloat(args[1]);
                        Riverland._Instance.tntRadiusDefault = newTntRad;
                        Riverland._Instance.UpdateConfig();
                        player.sendMessage(ChatColor.GREEN + " TNT Radius Modified");
                    }
<<<<<<< HEAD
                } catch (Exception exc) {
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("tntLarge")) {
                try {
=======
                }
                catch (Exception exc)
                {
                    return false;
                }
            }
            else if (args[0].equalsIgnoreCase("tntLarge"))
            {
                try
                {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                    if (sender instanceof Player) // get instance of player.
                    {
                        Player player = (Player) sender;
                        float newTntRad = Float.parseFloat(args[1]);
                        Riverland._Instance.tntRadiusHigh = newTntRad;
                        Riverland._Instance.UpdateConfig();
                        player.sendMessage(ChatColor.GREEN + " TNT Radius Modified");
                    }
<<<<<<< HEAD
                } catch (Exception exc) {
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("tntBuster")) {
=======
                }
                catch (Exception exc)
                {
                    return false;
                }
            }else if (args[0].equalsIgnoreCase("tntBuster"))
            {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                if (sender instanceof Player) // get instance of player.
                {
                    Player player = (Player) sender;
                    float newTntRad = Float.parseFloat(args[1]);
                    Riverland._Instance.tntBunkerBusterRange = newTntRad;
                    Riverland._Instance.UpdateConfig();
                    player.sendMessage(ChatColor.GREEN + " TNT Radius Modified");
                }
<<<<<<< HEAD
            } else if (args[0].equalsIgnoreCase("TNTIgnoreWater")) {
=======
            }else if (args[0].equalsIgnoreCase("TNTIgnoreWater"))
            {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                try {
                    Player player = (Player) sender;
                    Boolean ignWater = Boolean.parseBoolean(args[1]);
                    Riverland._Instance.IgnoreWater = ignWater;
                    Riverland._Instance.UpdateConfig();
                    player.sendMessage(ChatColor.GREEN + " BunkerBuster water penetration changed");
<<<<<<< HEAD
                } catch (Exception exc) {
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("ObsidianBreakChance")) {
=======
                }
                catch (Exception exc)
                {
                    return false;
                }
            }
            else if (args[0].equalsIgnoreCase("ObsidianBreakChance"))
            {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                Player player = (Player) sender;
                float newTntRad = Float.parseFloat(args[1]);
                Riverland._Instance.tntBreakChance = newTntRad;
                Riverland._Instance.UpdateConfig();
                player.sendMessage(ChatColor.GREEN + " Obsidian Break Chance Modified");
<<<<<<< HEAD
            } else if (args[0].equalsIgnoreCase("CustomTNT1")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    ItemStack testEnchant = new ItemStack(Material.TNT, 1);
=======
            }
            else if (args[0].equalsIgnoreCase("CustomTNT1"))
            {
                if (sender instanceof Player)
                {
                    Player player = (Player) sender;
                    ItemStack testEnchant = new ItemStack (Material.TNT, 1);
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                    ItemMeta testEnchantMeta = testEnchant.getItemMeta();
                    testEnchantMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                    testEnchant.setItemMeta(testEnchantMeta);
                    player.getInventory().addItem(testEnchant);

                }
<<<<<<< HEAD
            } else if (args[0].equalsIgnoreCase("CustomTNT2")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    ItemStack testEnchant = new ItemStack(Material.TNT, 1);
=======
            }
            else if (args[0].equalsIgnoreCase("CustomTNT2"))
            {
                if (sender instanceof Player)
                {
                    Player player = (Player) sender;
                    ItemStack testEnchant = new ItemStack (Material.TNT, 1);
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                    ItemMeta testEnchantMeta = testEnchant.getItemMeta();
                    testEnchantMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
                    testEnchant.setItemMeta(testEnchantMeta);
                    player.getInventory().addItem(testEnchant);
                }
<<<<<<< HEAD
            } else if (args[0].equalsIgnoreCase("CustomTNT3")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    ItemStack testEnchant = new ItemStack(Material.TNT, 1);
=======
            }
            else if (args[0].equalsIgnoreCase("CustomTNT3"))
            {
                if (sender instanceof Player)
                {
                    Player player = (Player) sender;
                    ItemStack testEnchant = new ItemStack (Material.TNT, 1);
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                    ItemMeta testEnchantMeta = testEnchant.getItemMeta();
                    testEnchantMeta.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
                    testEnchant.setItemMeta(testEnchantMeta);
                    player.getInventory().addItem(testEnchant);
                }
<<<<<<< HEAD
            } else if (args[0].equalsIgnoreCase("CustomTNT4")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    ItemStack testEnchant = new ItemStack(Material.TNT, 1);
=======
            } else if (args[0].equalsIgnoreCase("CustomTNT4"))
            {
                if (sender instanceof Player)
                {
                    Player player = (Player) sender;
                    ItemStack testEnchant = new ItemStack (Material.TNT, 1);
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                    ItemMeta testEnchantMeta = testEnchant.getItemMeta();
                    testEnchantMeta.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
                    testEnchant.setItemMeta(testEnchantMeta);
                    player.getInventory().addItem(testEnchant);
                }
<<<<<<< HEAD
            } else if (args[0].equalsIgnoreCase("ClearTNT")) {
                Riverland._Instance.ClearTNTMemory();
            } else if (args[0].equalsIgnoreCase("Pickup")) {
=======
            }else if (args[0].equalsIgnoreCase("ClearTNT"))
            {
                Riverland._Instance.ClearTNTMemory();
            }
            else if (args[0].equalsIgnoreCase("Pickup")) {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.setCanPickupItems(!player.getCanPickupItems());

<<<<<<< HEAD

=======
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                    if (player.getCanPickupItems())
                        player.sendMessage(ChatColor.GREEN + "You can now pickup items");
                    else
                        player.sendMessage(ChatColor.GREEN + "You can no longer pickup items");

                }
<<<<<<< HEAD
            } else if (args[0].equalsIgnoreCase("Teleport") && sender.isOp()) {
                // world string
                // double x
                // double y
                // double z
                try {
                    if (sender instanceof Player) {
=======
            }else if (args[0].equalsIgnoreCase("Teleport") && sender.isOp())
            {
                // world string
                 // double x
                    // double y
                        // double z
                try
                {
                    if (sender instanceof Player)
                    {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                        Player player = (Player) sender;

                        //args[1] world
                        //args[2] x
                        //args[3] y
                        //args[4] z

<<<<<<< HEAD
                        Location loc = new Location(Riverland._Instance.getServer().getWorld(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]));
=======
                        Location loc = new Location(Riverland._Instance.getServer().getWorld(args[1]),Double.parseDouble(args[2]), Double.parseDouble(args[3]) , Double.parseDouble(args[4]));
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                        player.teleport(loc);
                        player.sendMessage("Teleporting..");
                    }

<<<<<<< HEAD
                } catch (Exception exc) {
=======
                }catch (Exception exc)
                {
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
                    Player player = (Player) sender;
                    player.sendMessage("Err: " + exc.toString());

                }

<<<<<<< HEAD
            }else if (args[0].equalsIgnoreCase("spongeegg") && sender.isOp() && sender instanceof Player)
            {
                ItemStack egg = new ItemStack(Material.EGG);
                egg.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                ItemMeta spongeData = egg.getItemMeta();
                NamespacedKey key = new NamespacedKey(Riverland._Instance, "spongekey");
                spongeData.getCustomTagContainer().setCustomTag(key, ItemTagType.INTEGER, 1);
                egg.setItemMeta(spongeData);

                ((Player) sender).getInventory().addItem(egg);
            }else if (args[0].equalsIgnoreCase("givespongeegg") && sender.isOp())
            {
                ItemStack egg = new ItemStack(Material.EGG);
                egg.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                ItemMeta spongeData = egg.getItemMeta();
                NamespacedKey key = new NamespacedKey(Riverland._Instance, "spongekey");
                spongeData.getCustomTagContainer().setCustomTag(key, ItemTagType.INTEGER, 1);


                spongeData.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&b[&5&lRAID-SPONGE&b]"));
                ArrayList<String> Lore = new ArrayList<String>();
                Lore.add("&d------------------------------------");
                Lore.add("&5&lRaid Sponge");
                Lore.add("&eSpawns a Sponge");
                Lore.add("&eWorks with Water/Lava");
                Lore.add("&cWill only work in &4&lenemy &cfaction land");
                Lore.add("&d------------------------------------");
                ArrayList<String> convertedLore = new ArrayList<>();


                for (String s : Lore) {
                    convertedLore.add(ChatColor.translateAlternateColorCodes('&', s));
                }

                spongeData.setLore(convertedLore);
                egg.setItemMeta(spongeData);

                Riverland._Instance.getServer().getPlayer(args[1]).getInventory().addItem(egg);

            }
            return true;
        }
        return false;
    }
}

=======
            }else if (args[0].equalsIgnoreCase("SpamVote") && sender.isOp())
            {
                net.md_5.bungee.api.chat.TextComponent message1 = new net.md_5.bungee.api.chat.TextComponent("Oh");
                message1.setColor(net.md_5.bungee.api.ChatColor.GREEN);
                net.md_5.bungee.api.chat.TextComponent message2 = new net.md_5.bungee.api.chat.TextComponent(" wow");
                message2.setColor(net.md_5.bungee.api.ChatColor.GOLD);
                net.md_5.bungee.api.chat.TextComponent message3 = new net.md_5.bungee.api.chat.TextComponent(" nice");
                message3.setColor(net.md_5.bungee.api.ChatColor.RED);
                net.md_5.bungee.api.chat.TextComponent message4 = new net.md_5.bungee.api.chat.TextComponent(" VOTE");
                message4.setColor(net.md_5.bungee.api.ChatColor.AQUA);
                message4.setBold(true);
                message4.setItalic(true);
                message4.setUnderlined(true);
                ClickEvent eventOnClick = new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/vote");
                HoverEvent eventOnHover = new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Click me").create());

                message4.setClickEvent(eventOnClick);
                message4.setHoverEvent(eventOnHover);
                net.md_5.bungee.api.chat.TextComponent message5 = new net.md_5.bungee.api.chat.TextComponent("!");
                message5.setObfuscated(true);
                message5.setColor(net.md_5.bungee.api.ChatColor.DARK_PURPLE);
                net.md_5.bungee.api.chat.TextComponent message6 = new net.md_5.bungee.api.chat.TextComponent("!");
                message6.setBold(true);
                message6.setColor(net.md_5.bungee.api.ChatColor.LIGHT_PURPLE);
                net.md_5.bungee.api.chat.TextComponent message7 = new net.md_5.bungee.api.chat.TextComponent("!");
                message7.setObfuscated(true);
                message7.setColor(net.md_5.bungee.api.ChatColor.DARK_PURPLE);

                net.md_5.bungee.api.chat.TextComponent messageFinal = new net.md_5.bungee.api.chat.TextComponent();
                messageFinal.addExtra(message1);
                messageFinal.addExtra(message2);
                messageFinal.addExtra(message3);
                messageFinal.addExtra(message4);
                messageFinal.addExtra(message5);
                messageFinal.addExtra(message6);
                messageFinal.addExtra(message7);

                Riverland._Instance.getServer().broadcast(messageFinal);
            }else if (args[0].equalsIgnoreCase("HandItem") && sender.isOp())
            {
                Player player = (Player)sender;
                ItemStack items = ((Player) sender).getInventory().getItemInMainHand();
                ItemMeta meta = items.getItemMeta();
                meta.setDisplayName(args[1]);
                items.setItemMeta(meta);
                player.sendMessage( ((Player) sender).getActiveItem().getItemMeta().toString());
            }
            else if ( sender.isOp() && args[0].equalsIgnoreCase("fill"))
            {

                RiverLandEventListener.isRunningMaintenance = !RiverLandEventListener.isRunningMaintenance;
                if (sender instanceof  Player)
                    ((Player)sender).sendMessage("Maintenance Set: " + RiverLandEventListener.isRunningMaintenance);
            }
        }
        return true;
    }

}
>>>>>>> 89bc361f785924f34d027f6f7113f8a198456a8b
