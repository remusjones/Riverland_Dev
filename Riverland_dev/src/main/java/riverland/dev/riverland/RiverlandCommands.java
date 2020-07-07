package riverland.dev.riverland;

import com.massivecraft.factions.*;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagType;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class RiverlandCommands implements CommandExecutor {
    Integer currBlockX = 0;
    Integer currBlockZ = 0;
    World targetWorld = null;
    Player vic = null;
    Integer loadBlockID = 0;

    public void UpdateInventory() {

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.isOp())
            return false;

        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("reload")) {

                Riverland._Instance.reloadConfig();
                if (sender instanceof Player) // get instance of player.
                {
                    Player player = (Player) sender;

                    player.sendMessage(ChatColor.GREEN + " Reloading Config");
                }
            } else if (args[0].equalsIgnoreCase("tntSmall")) {
                try {
                    if (sender instanceof Player) // get instance of player.
                    {
                        Player player = (Player) sender;
                        float newTntRad = Float.parseFloat(args[1]);
                        Riverland._Instance.tntRadiusLow = newTntRad;
                        Riverland._Instance.UpdateConfig();
                        player.sendMessage(ChatColor.GREEN + " TNT Radius Modified");
                    }
                } catch (Exception exc) {
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("tntMedium")) {
                try {
                    if (sender instanceof Player) // get instance of player.
                    {
                        Player player = (Player) sender;
                        float newTntRad = Float.parseFloat(args[1]);
                        Riverland._Instance.tntRadiusDefault = newTntRad;
                        Riverland._Instance.UpdateConfig();
                        player.sendMessage(ChatColor.GREEN + " TNT Radius Modified");
                    }
                } catch (Exception exc) {
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("tntLarge")) {
                try {
                    if (sender instanceof Player) // get instance of player.
                    {
                        Player player = (Player) sender;
                        float newTntRad = Float.parseFloat(args[1]);
                        Riverland._Instance.tntRadiusHigh = newTntRad;
                        Riverland._Instance.UpdateConfig();
                        player.sendMessage(ChatColor.GREEN + " TNT Radius Modified");
                    }
                } catch (Exception exc) {
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("tntBuster")) {
                if (sender instanceof Player) // get instance of player.
                {
                    Player player = (Player) sender;
                    float newTntRad = Float.parseFloat(args[1]);
                    Riverland._Instance.tntBunkerBusterRange = newTntRad;
                    Riverland._Instance.UpdateConfig();
                    player.sendMessage(ChatColor.GREEN + " TNT Radius Modified");
                }
            } else if (args[0].equalsIgnoreCase("TNTIgnoreWater")) {
                try {
                    Player player = (Player) sender;
                    Boolean ignWater = Boolean.parseBoolean(args[1]);
                    Riverland._Instance.IgnoreWater = ignWater;
                    Riverland._Instance.UpdateConfig();
                    player.sendMessage(ChatColor.GREEN + " BunkerBuster water penetration changed");
                } catch (Exception exc) {
                    return false;
                }
            } else if (args[0].equalsIgnoreCase("ObsidianBreakChance")) {
                Player player = (Player) sender;
                float newTntRad = Float.parseFloat(args[1]);
                Riverland._Instance.tntBreakChance = newTntRad;
                Riverland._Instance.UpdateConfig();
                player.sendMessage(ChatColor.GREEN + " Obsidian Break Chance Modified");
            } else if (args[0].equalsIgnoreCase("CustomTNT1")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    ItemStack testEnchant = new ItemStack(Material.TNT, 1);
                    ItemMeta testEnchantMeta = testEnchant.getItemMeta();
                    testEnchantMeta.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
                    testEnchant.setItemMeta(testEnchantMeta);
                    player.getInventory().addItem(testEnchant);

                }
            } else if (args[0].equalsIgnoreCase("CustomTNT2")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    ItemStack testEnchant = new ItemStack(Material.TNT, 1);
                    ItemMeta testEnchantMeta = testEnchant.getItemMeta();
                    testEnchantMeta.addEnchant(Enchantment.DAMAGE_ALL, 2, true);
                    testEnchant.setItemMeta(testEnchantMeta);
                    player.getInventory().addItem(testEnchant);
                }
            } else if (args[0].equalsIgnoreCase("CustomTNT3")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    ItemStack testEnchant = new ItemStack(Material.TNT, 1);
                    ItemMeta testEnchantMeta = testEnchant.getItemMeta();
                    testEnchantMeta.addEnchant(Enchantment.DAMAGE_ALL, 3, true);
                    testEnchant.setItemMeta(testEnchantMeta);
                    player.getInventory().addItem(testEnchant);
                }
            } else if (args[0].equalsIgnoreCase("CustomTNT4")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    ItemStack testEnchant = new ItemStack(Material.TNT, 1);
                    ItemMeta testEnchantMeta = testEnchant.getItemMeta();
                    testEnchantMeta.addEnchant(Enchantment.DAMAGE_ALL, 4, true);
                    testEnchant.setItemMeta(testEnchantMeta);
                    player.getInventory().addItem(testEnchant);
                }
            } else if (args[0].equalsIgnoreCase("ClearTNT")) {
                Riverland._Instance.ClearTNTMemory();
            } else if (args[0].equalsIgnoreCase("Pickup")) {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    player.setCanPickupItems(!player.getCanPickupItems());


                    if (player.getCanPickupItems())
                        player.sendMessage(ChatColor.GREEN + "You can now pickup items");
                    else
                        player.sendMessage(ChatColor.GREEN + "You can no longer pickup items");

                }
            } else if (args[0].equalsIgnoreCase("Teleport") && sender.isOp()) {
                // world string
                // double x
                // double y
                // double z
                try {
                    if (sender instanceof Player) {
                        Player player = (Player) sender;

                        //args[1] world
                        //args[2] x
                        //args[3] y
                        //args[4] z

                        Location loc = new Location(Riverland._Instance.getServer().getWorld(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]));
                        player.teleport(loc);
                        player.sendMessage("Teleporting..");
                    }

                } catch (Exception exc) {
                    Player player = (Player) sender;
                    player.sendMessage("Err: " + exc.toString());

                }

            }else if (args[0].equalsIgnoreCase("spongeegg") && sender.isOp() && sender instanceof Player)
            {
                ItemStack egg = new ItemStack(Material.EGG);
                egg.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                ItemMeta spongeData = egg.getItemMeta();
                NamespacedKey key = new NamespacedKey(Riverland._Instance, "spongekey");
                spongeData.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
                //spongeData.getCustomTagContainer().setCustomTag(key, ItemTagType.INTEGER, 1);
                egg.setItemMeta(spongeData);

                ((Player) sender).getInventory().addItem(egg);
            }else if (args[0].equalsIgnoreCase("givespongeegg") && sender.isOp())
            {
                ItemStack egg = new ItemStack(Material.EGG);
                egg.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                ItemMeta spongeData = egg.getItemMeta();
                NamespacedKey key = new NamespacedKey(Riverland._Instance, "spongekey");
               // spongeData.getCustomTagContainer().setCustomTag(key, ItemTagType.INTEGER, 1);
                spongeData.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);

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
            }else if (args[0].equalsIgnoreCase("NPC"))
            {

                boolean exists = false;
                FPlayer factionPlayer = FPlayers.getInstance().getByPlayer((Player)sender);

                NPCFaction target = null;
                for (NPCFaction faction : Riverland._Instance.npcFactions)
                {

                    Faction ffaction = Factions.getInstance().getBestTagMatch(faction.savedData.factionID);
                    if (ffaction == factionPlayer.getFaction())
                    {
                        target = faction;
                        exists = true;
                        break;
                    }
                }
                Location loc = ((Player)sender).getLocation();
                if (exists)
                {
                    target.storedNPCs++;
                    target.SpawnSentinel(loc);
                }else
                {
                    target = new NPCFaction(factionPlayer.getTag());
                    target.storedNPCs++;
                    target.SpawnSentinel(loc);
                    Riverland._Instance.npcFactions.add(target);
                }
            }
            return true;
        }
        return false;
    }
}

