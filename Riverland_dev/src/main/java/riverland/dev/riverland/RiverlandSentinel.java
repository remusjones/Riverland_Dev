package riverland.dev.riverland;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.perms.Relation;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.event.NPCSpawnEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import net.citizensnpcs.api.persistence.Persist;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.util.DataKey;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcmonkey.sentinel.SentinelTrait;

import java.util.logging.Level;

public class RiverlandSentinel extends Trait
{

    Riverland plugin = null;

    @Persist("ownerFaction") String ownerFaction = "";
    @Persist("Inventory")
            ItemStack[] items;

    SentinelTrait sentinelTrait = null;

    public RiverlandSentinel() {
        super("RiverlandSentinel");
        plugin = JavaPlugin.getPlugin(Riverland.class);

    }
    public void load(DataKey key)
    {
        ownerFaction = key.getString("ownerFaction");

    }
    public void ForceRemove(NPCFaction faction)
    {
        Strip();
        faction.NPCCount--;
        faction.NpcUUID.remove(Integer.valueOf(npc.getId()));
        this.npc.destroy();
    }
    @Override
    public void onRemove()
    {

    }

    @EventHandler
    public void OnDeath(NPCDeathEvent event)
    {
        if (event.getNPC() != this.npc)
            return;

        for (NPCFaction npcfac:
                plugin.npcFactions)
        {
            if (npcfac.factionID.equals(ownerFaction))
            {
              //  npcfac.NpcUUID.remove(npc.getId());
                npcfac.NpcUUID.remove(Integer.valueOf(npc.getId()));
                npcfac.NPCCount--;
            }
        }
        Riverland._Instance.getLogger().log(Level.WARNING, "NPC Died");
        Strip();
        event.getNPC().destroy();
    }
    public void Strip()
    {

        Equipment equipment = npc.getTrait(Equipment.class);

        for (ItemStack item:  equipment.getEquipment())
        {
            if (item != null && item.getType() != Material.AIR) {
                npc.getStoredLocation().getWorld().dropItemNaturally(npc.getStoredLocation(), item);
            }
        }

        npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.HAND, null);
        npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.OFF_HAND, null);
        npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.LEGGINGS, null);
        npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.HELMET,  null);
        npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.CHESTPLATE, null);
        npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.BOOTS, null);

    }

    /**Attempts to equip item stack to relevant item, sends player a message if unable.
     *
     * */
    public void EquipItem(Player player, ItemStack item)
    {

        boolean success = false;
        if (item.getType().toString().contains("BOOTS"))
        {
            Equipment equipment = npc.getTrait(Equipment.class);
            if (equipment.get(Equipment.EquipmentSlot.BOOTS) != null)
            {
                npc.getStoredLocation().getWorld().dropItemNaturally(npc.getStoredLocation(),equipment.get(Equipment.EquipmentSlot.BOOTS));
            }
            npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.BOOTS, item);

            success= true;
        }
        else if (item.getType().toString().contains("HELMET")) {
            Equipment equipment = npc.getTrait(Equipment.class);
            if (equipment.get(Equipment.EquipmentSlot.HELMET) != null)
            {
                npc.getStoredLocation().getWorld().dropItemNaturally(npc.getStoredLocation(),equipment.get(Equipment.EquipmentSlot.HELMET));
            }
            npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.HELMET, item);
            success= true;
        }
        else if (item.getType().toString().contains("LEGGINGS")) {
            Equipment equipment = npc.getTrait(Equipment.class);
            if (equipment.get(Equipment.EquipmentSlot.LEGGINGS)!= null)
            {
                npc.getStoredLocation().getWorld().dropItemNaturally(npc.getStoredLocation(),equipment.get(Equipment.EquipmentSlot.LEGGINGS));
            }
            npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.LEGGINGS, item);
            success= true;
        }
        else if (item.getType().toString().contains("CHESTPLATE")) {
            Equipment equipment = npc.getTrait(Equipment.class);
            if (equipment.get(Equipment.EquipmentSlot.CHESTPLATE)!= null)
            {
                npc.getStoredLocation().getWorld().dropItemNaturally(npc.getStoredLocation(),equipment.get(Equipment.EquipmentSlot.CHESTPLATE));
            }
            npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.CHESTPLATE, item);
            success= true;
        }else if (item.getType().toString().contains("SWORD") || item.getType().toString().contains("AXE")|| item.getType().toString().contains("TRIDENT") ){
            Equipment equipment = npc.getTrait(Equipment.class);
            if (equipment.get(Equipment.EquipmentSlot.HAND)!= null)
            {
                npc.getStoredLocation().getWorld().dropItemNaturally(npc.getStoredLocation(),equipment.get(Equipment.EquipmentSlot.HAND));
            }
            npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.HAND, item);
            success = true;
        }else if (item.getType().toString().contains("SHIELD")){
            Equipment equipment = npc.getTrait(Equipment.class);
            if (equipment.get(Equipment.EquipmentSlot.OFF_HAND)!= null)
            {
                npc.getStoredLocation().getWorld().dropItemNaturally(npc.getStoredLocation(),equipment.get(Equipment.EquipmentSlot.OFF_HAND));
            }
            npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.OFF_HAND, item);
            success = true;
        }else if (item.getType().toString().contains("BOW"))
        {
            Equipment equipment = npc.getTrait(Equipment.class);
            if (equipment.get(Equipment.EquipmentSlot.HAND)!= null)
            {
                npc.getStoredLocation().getWorld().dropItemNaturally(npc.getStoredLocation(),equipment.get(Equipment.EquipmentSlot.HAND));
            }
            npc.getTrait(Equipment.class).set(Equipment.EquipmentSlot.HAND, item);
            success = true;
        }
        if (success)
        {            player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        }else player.sendMessage("Invalid item");
    }

}
