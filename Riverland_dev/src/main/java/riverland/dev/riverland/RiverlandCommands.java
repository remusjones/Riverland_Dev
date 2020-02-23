package riverland.dev.riverland;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.util.ArrayList;
import java.util.List;

public class RiverlandCommands implements CommandExecutor
{
    public static List<Block> getNearbyBlocks(Location location, int radius) {
        List<Block> blocks = new ArrayList<Block>();
        for(int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for(int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for(int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    Block block = location.getWorld().getBlockAt(x, y, z);
                    if (block.getType() == Material.SPAWNER)
                        blocks.add(block);
                }
            }
        }
        return blocks;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!sender.isOp())
             return false;

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

            }
        }
        return true;
    }

}