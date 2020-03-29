package riverland.dev.riverland;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitScheduler;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RiverlandCommands implements CommandExecutor
{
    Integer currBlockX = 0;
    Integer currBlockZ = 0;
    World targetWorld = null;
    Player vic = null;
    Integer loadBlockID = 0;

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

                Riverland._Instance.reloadConfig();
                if (sender instanceof Player) // get instance of player.
                {
                    Player player = (Player) sender;

                    player.sendMessage(ChatColor.GREEN + " Reloading Config");
                }
            }
            else if (args[0].equalsIgnoreCase("tntSmall"))
            {
                try
                {
                    if (sender instanceof Player) // get instance of player.
                    {
                        Player player = (Player) sender;
                        float newTntRad = Float.parseFloat(args[1]);
                        Riverland._Instance.tntRadiusLow = newTntRad;
                        Riverland._Instance.UpdateConfig();
                        player.sendMessage(ChatColor.GREEN + " TNT Radius Modified");
                    }
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
                    if (sender instanceof Player) // get instance of player.
                    {
                        Player player = (Player) sender;
                        float newTntRad = Float.parseFloat(args[1]);
                        Riverland._Instance.tntRadiusDefault = newTntRad;
                        Riverland._Instance.UpdateConfig();
                        player.sendMessage(ChatColor.GREEN + " TNT Radius Modified");
                    }
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
                    if (sender instanceof Player) // get instance of player.
                    {
                        Player player = (Player) sender;
                        float newTntRad = Float.parseFloat(args[1]);
                        Riverland._Instance.tntRadiusHigh = newTntRad;
                        Riverland._Instance.UpdateConfig();
                        player.sendMessage(ChatColor.GREEN + " TNT Radius Modified");
                    }
                }
                catch (Exception exc)
                {
                    return false;
                }
            }else if (args[0].equalsIgnoreCase("tntBuster"))
            {
                if (sender instanceof Player) // get instance of player.
                {
                    Player player = (Player) sender;
                    float newTntRad = Float.parseFloat(args[1]);
                    Riverland._Instance.tntBunkerBusterRange = newTntRad;
                    Riverland._Instance.UpdateConfig();
                    player.sendMessage(ChatColor.GREEN + " TNT Radius Modified");
                }
            }else if (args[0].equalsIgnoreCase("TNTIgnoreWater"))
            {
                try {
                    Player player = (Player) sender;
                    Boolean ignWater = Boolean.parseBoolean(args[1]);
                    Riverland._Instance.IgnoreWater = ignWater;
                    Riverland._Instance.UpdateConfig();
                    player.sendMessage(ChatColor.GREEN + " BunkerBuster water penetration changed");
                }
                catch (Exception exc)
                {
                    return false;
                }
            }
            else if (args[0].equalsIgnoreCase("ObsidianBreakChance"))
            {
                Player player = (Player) sender;
                float newTntRad = Float.parseFloat(args[1]);
                Riverland._Instance.tntBreakChance = newTntRad;
                Riverland._Instance.UpdateConfig();
                player.sendMessage(ChatColor.GREEN + " Obsidian Break Chance Modified");
            }
            else if (args[0].equalsIgnoreCase("CustomTNT1"))
            {
                if (sender instanceof Player)
                {
                    Player player = (Player) sender;
                    ItemStack testEnchant = new ItemStack (Material.TNT, 1);
                    ItemMeta testEnchantMeta = testEnchant.getItemMeta();
                    testEnchantMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                    testEnchant.setItemMeta(testEnchantMeta);
                    player.getInventory().addItem(testEnchant);

                }
            }
            else if (args[0].equalsIgnoreCase("CustomTNT2"))
            {
                if (sender instanceof Player)
                {
                    Player player = (Player) sender;
                    ItemStack testEnchant = new ItemStack (Material.TNT, 1);
                    ItemMeta testEnchantMeta = testEnchant.getItemMeta();
                    testEnchantMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
                    testEnchant.setItemMeta(testEnchantMeta);
                    player.getInventory().addItem(testEnchant);
                }
            }
            else if (args[0].equalsIgnoreCase("CustomTNT3"))
            {
                if (sender instanceof Player)
                {
                    Player player = (Player) sender;
                    ItemStack testEnchant = new ItemStack (Material.TNT, 1);
                    ItemMeta testEnchantMeta = testEnchant.getItemMeta();
                    testEnchantMeta.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
                    testEnchant.setItemMeta(testEnchantMeta);
                    player.getInventory().addItem(testEnchant);
                }
            } else if (args[0].equalsIgnoreCase("CustomTNT4"))
            {
                if (sender instanceof Player)
                {
                    Player player = (Player) sender;
                    ItemStack testEnchant = new ItemStack (Material.TNT, 1);
                    ItemMeta testEnchantMeta = testEnchant.getItemMeta();
                    testEnchantMeta.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
                    testEnchant.setItemMeta(testEnchantMeta);
                    player.getInventory().addItem(testEnchant);
                }
            }else if (args[0].equalsIgnoreCase("ClearTNT"))
            {
                Riverland._Instance.ClearTNTMemory();
            }
            else if (args[0].equalsIgnoreCase("Pickup")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.setCanPickupItems(!player.getCanPickupItems());
                    

                    if (player.getCanPickupItems())
                        player.sendMessage(ChatColor.GREEN + "You can now pickup items");
                    else
                        player.sendMessage(ChatColor.GREEN + "You can no longer pickup items");

                }
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
                        Player player = (Player) sender;

                        //args[1] world
                        //args[2] x
                        //args[3] y
                        //args[4] z

                        Location loc = new Location(Riverland._Instance.getServer().getWorld(args[1]),Double.parseDouble(args[2]), Double.parseDouble(args[3]) , Double.parseDouble(args[4]));
                        player.teleport(loc);
                        player.sendMessage("Teleporting..");
                    }

                }catch (Exception exc)
                {
                    Player player = (Player) sender;
                    player.sendMessage("Err: " + exc.toString());

                }

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
            else if (sender.isOp() && args[0].equalsIgnoreCase("deathsee") && args.length > 1)
            {
                Player target = Riverland._Instance.getServer().getPlayer(args[1]);
                if (target != null)
                {
                    Player requester = ((Player)sender);
                    if (Riverland._Instance.playerLastDeathMap.containsKey(target.getName()))
                    {
                        requester.openInventory(Riverland._Instance.playerLastDeathMap.get(target.getName()));
                    }
                }
            }
            else if (sender.isOp() && args[0].equalsIgnoreCase("deathrefund") && args.length > 1)
            {
                Player target = Riverland._Instance.getServer().getPlayer(args[1]);
                if (target != null) {
                    if (Riverland._Instance.playerLastDeathMap.containsKey(target.getName()))
                    {
                        Inventory oldInventory = Riverland._Instance.playerLastDeathMap.get(target.getName());
                        for(ItemStack item : oldInventory.getContents())
                        {
                            if (item != null)
                                target.getWorld().dropItemNaturally(target.getLocation(), item);
                        }
                        Riverland._Instance.playerLastDeathMap.get(target.getName()).clear();
                    }
                }
            }//else if (sender.hasPermission(""))

           //Riverland.ThumbusEventDonator:
           //description: Trigger Thumbus event for everybody
           //default: op
           //    Riverland.PVPEventDonator:
           //    description: Trigger 1v1 PvP event for everybody
           //default: op
            if (sender.isOp() && args[0].equalsIgnoreCase("ThumbusEventResetTime"))
            {
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();
                date.setTime(1000999);
                Riverland._Instance.config.set("THUMBUS_LASTSTART", dateFormat.format(date));
                Riverland._Instance.saveConfig();
                sender.sendMessage("Time reset");
            }
        }
        return true;
    }

}