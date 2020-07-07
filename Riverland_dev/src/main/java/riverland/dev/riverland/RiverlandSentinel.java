package riverland.dev.riverland;

import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.event.NPCSpawnEvent;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.util.DataKey;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class RiverlandSentinel extends Trait
{

    Riverland plugin = null;
    @Persist("ownerFaction") String ownerFaction = "";
    @Persist("Inventory")
            ItemStack[] items;
    Inventory inventory;
    public RiverlandSentinel() {
        super("RiverlandSentinel");
        plugin = JavaPlugin.getPlugin(Riverland.class);

    }
    @EventHandler
    public void OnSpawn(NPCSpawnEvent event)
    {
        inventory = Bukkit.createInventory(null, 54, npc.getId() + "'s Inventory");
    }
    public void load(DataKey key)
    {
        inventory = Bukkit.createInventory(null, 54, npc.getId() + "'s Inventory");
        inventory.setContents(items);
    }
    @EventHandler
    public void OnDeath(NPCDeathEvent event)
    {
        if (inventory == null)
            inventory = Bukkit.createInventory(null, 54, npc.getId() + "'s Inventory");
        for (NPCFaction npcfac:
                plugin.npcFactions)
        {
            if (npcfac.factionID == ownerFaction)
            {
                npcfac.NpcUUID.remove(npc.getUniqueId());
                npcfac.NPCCount--;
            }
        }
        for (ItemStack item: inventory.getContents())
        {
            if (item != null) {
                npc.getStoredLocation().getWorld().dropItemNaturally(npc.getStoredLocation(), item);
            }
        }
        inventory.clear();
        inventory = null;
        //inventory = null;
        event.getNPC().destroy();
    }
    public void Strip()
    {
        if (inventory == null)
            inventory = Bukkit.createInventory(null, 54, npc.getId() + "'s Inventory");
        for (ItemStack item: inventory.getContents())
        {
            if (item != null) {
                npc.getStoredLocation().getWorld().dropItemNaturally(npc.getStoredLocation(), item);
            }
        }
        inventory.clear();
        npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.HAND, new ItemStack(Material.AIR));
        npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.OFF_HAND, new ItemStack(Material.AIR));
        npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.LEGGINGS, new ItemStack(Material.AIR));
        npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.HELMET, new ItemStack(Material.AIR));
        npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.CHESTPLATE, new ItemStack(Material.AIR));
        npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.BOOTS, new ItemStack(Material.AIR));
        items = inventory.getContents();
    }

    public void EquipItem(Player player, ItemStack item)
    {
        if (inventory == null)
        {
            inventory = Bukkit.createInventory(null, 54, npc.getId() + "'s Inventory");
        }
        if (inventory.getContents().length >= this.inventory.getMaxStackSize())
        {
            player.sendMessage("The inventory is full");
            return;
        }
        boolean success = false;
        if (item.getType().toString().contains("BOOTS"))
        {
            npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.BOOTS, item);
            success= true;
        }
        else if (item.getType().toString().contains("HELMET")) {
            npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.HELMET, item);
            success= true;
        }
        else if (item.getType().toString().contains("LEGGINGS")) {
            npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.LEGGINGS, item);
            success= true;
        }
        else if (item.getType().toString().contains("CHESTPLATE")) {
            npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.CHESTPLATE, item);
            success= true;
        }else if (item.getType().toString().contains("SWORD") || item.getType().toString().contains("AXE")|| item.getType().toString().contains("TRIDENT") ){
            npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.HAND, item);
            success = true;
        }else if (item.getType().toString().contains("SHIELD")){
            npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.OFF_HAND, item);
            success = true;
        }
        if (success)
        {
            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
            this.inventory.addItem(item);
        }else player.sendMessage("Invalid item");

        //npc.getTrait(Equipment.class).set(
        items= inventory.getContents();
    }

}
