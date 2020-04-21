package riverland.dev.riverland;

import com.google.common.collect.HashMultimap;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.Serializable;
import java.util.*;
/*
#
#
# MADE REDUNDANT KEEPING HERE FOR REFERENCE ONLY
#
#
 */
@Deprecated
public class LootTable implements Serializable
{

    @Expose
    @SerializedName("MaxRewards")
    Integer maxRewards = 3;

    @Expose
    @SerializedName("ItemChancePair")
    HashMultimap<String, Float> itemChancePair = HashMultimap.create();
    @Expose
    @SerializedName("CommandChancePair")
    HashMultimap<String, Float> commandChancePair = HashMultimap.create();

    @Expose
    @SerializedName("FallbackMax")
    Integer maxFallbackRewards = 3;

    @Expose
    @SerializedName("FallbackItems")
    ArrayList<String> fallbackItems = new ArrayList<>();
    @Expose
    @SerializedName("FallbackCommands")
    ArrayList<String> fallbackCommands = new ArrayList<>();



    // actual storage
    HashMultimap<ItemStack, Float> itemChancePairReal = HashMultimap.create();
    HashMultimap<String, Float> commandChanceReal = HashMultimap.create();
    List<ItemStack> fallbackItemReal = new ArrayList<>();
    List<String> fallbackCommandReal = new ArrayList<>();

    public void Serialize()
    {
        Set<Map.Entry<ItemStack, Float>> map = itemChancePairReal.entries();
        Gson gson = new Gson();

        for (Map.Entry<ItemStack, Float> pair : map)
        {
            String json = gson.toJson(pair.getKey().serialize());
            itemChancePair.put(json, pair.getValue());
        }
        for(ItemStack is : fallbackItemReal)
        {
            
            String json = gson.toJson(is.serialize());
            fallbackItems.add(json);
        }
    }

    // internal use only - don't expose to users.
    public void PopulateDefaultItems()
    {

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
        itemChancePairReal.put(godItemSword, 12f);

        ItemStack godItemHead = new ItemStack(Material.DIAMOND_HELMET);
        ItemMeta godHeadMeta = godItemHead.getItemMeta();
        godHeadMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 3, true);
        godHeadMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
        godHeadMeta.addEnchant(Enchantment.OXYGEN, 3, true);
        godHeadMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
        godHeadMeta.addEnchant(Enchantment.DURABILITY, 6, true);
        godHeadMeta.setDisplayName("THUMBUS' HELM");

        godItemHead.setItemMeta(godHeadMeta);
        itemChancePairReal.put(godItemHead, 12f);


        ItemStack godItemChest = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemMeta godChestMeta = godItemChest.getItemMeta();
        godChestMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 3, true);
        godChestMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
        godChestMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
        godChestMeta.addEnchant(Enchantment.DURABILITY, 6, true);
        godChestMeta.setDisplayName("THUMBUS' MANTLE");
        godItemChest.setItemMeta(godChestMeta);
        itemChancePairReal.put(godItemChest, 12f);

        ItemStack godItemLegs = new ItemStack(Material.DIAMOND_LEGGINGS);
        ItemMeta godLegMeta = godItemLegs.getItemMeta();
        godLegMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 3, true);
        godLegMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
        godLegMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
        godLegMeta.addEnchant(Enchantment.DURABILITY, 6, true);
        godLegMeta.setDisplayName("THUMBUS' GREAVES");
        godItemLegs.setItemMeta(godLegMeta);
        itemChancePairReal.put(godItemLegs, 12f);


        ItemStack godItemBoots = new ItemStack(Material.DIAMOND_BOOTS);
        ItemMeta godBootMeta = godItemBoots.getItemMeta();
        godBootMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS, 3, true);
        godBootMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 3, true);
        godBootMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3, true);
        godBootMeta.addEnchant(Enchantment.DURABILITY, 3, true);
        godBootMeta.addEnchant(Enchantment.PROTECTION_FALL, 5, true);
        godBootMeta.setDisplayName("THUMBUS' BOOTS");
        godItemBoots.setItemMeta(godBootMeta);
        itemChancePairReal.put(godItemBoots, 12f);


        ItemStack godItemWings = new ItemStack(Material.ELYTRA);
        ItemMeta godWingsMeta = godItemWings.getItemMeta();
        godWingsMeta.addEnchant(Enchantment.MENDING, 1, true);
        godWingsMeta.addEnchant(Enchantment.DURABILITY, 3, true);
        godWingsMeta.setDisplayName("THUMBUS' WINGS");
        godItemWings.setItemMeta(godWingsMeta);

        itemChancePairReal.put(godItemWings, 12f);

        ItemStack godItemBow = new ItemStack(Material.BOW);
        ItemMeta godBowMeta = godItemBow.getItemMeta();
        godBowMeta.addEnchant(Enchantment.ARROW_FIRE, 1, true);
        godBowMeta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
        godBowMeta.addEnchant(Enchantment.ARROW_KNOCKBACK, 2, true);
        godBowMeta.setDisplayName("THUMBUS' BOW");
        godItemBow.setItemMeta(godBowMeta);
        itemChancePairReal.put(godItemBow, 12f);

        ItemStack godItemPickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta godPickaxeMeta = godItemPickaxe.getItemMeta();
        godPickaxeMeta.addEnchant(Enchantment.DIG_SPEED, 5, true);
        godPickaxeMeta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 3, true);
        godPickaxeMeta.setDisplayName("THUMBUS' PICKAXE");
        godItemPickaxe.setItemMeta(godPickaxeMeta);
        itemChancePairReal.put(godItemPickaxe, 12f);

    }
    public void PopulateDefaultCommands()
    {

    }
    public void PopulateDefaultFallbackItems()
    {
        ItemStack potionStrength = new ItemStack(Material.POTION,1);
        PotionMeta potStrengthMeta = (PotionMeta)potionStrength.getItemMeta();
        potStrengthMeta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 300*20, 1), true);
        potStrengthMeta.addCustomEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 300*20, 3), true);
        potStrengthMeta.addCustomEffect(new PotionEffect(PotionEffectType.HUNGER, 300*20, 1), true);
        potStrengthMeta.addCustomEffect(new PotionEffect(PotionEffectType.JUMP, 300*20, 2), true);
        potStrengthMeta.setDisplayName("THUMBUS' STRENGTH POTION");
        potionStrength.setItemMeta(potStrengthMeta);
        fallbackItemReal.add(potionStrength);

        ItemStack potionHealing = new ItemStack(Material.POTION,1);
        PotionMeta potHealingMeta = (PotionMeta)potionHealing.getItemMeta();
        potHealingMeta.addCustomEffect(new PotionEffect(PotionEffectType.REGENERATION, 60*20, 3), true);
        potHealingMeta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 60*20, 1), true);
        potHealingMeta.setDisplayName("THUMBUS' HEALING POTION");
        potionHealing.setItemMeta(potHealingMeta);
        fallbackItemReal.add(potionHealing);
        ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING,1);
        fallbackItemReal.add(totem);
        ItemStack head = new ItemStack(Material.ZOMBIE_HEAD,1);
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName("THUMBUS' HEAD");
        headMeta.addEnchant(Enchantment.BINDING_CURSE, 1,true);
        headMeta.addEnchant(Enchantment.VANISHING_CURSE, 1,true);
        headMeta.addEnchant(Enchantment.PROTECTION_PROJECTILE, 5,true);
        headMeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 5,true);
        head.setItemMeta(headMeta);
        fallbackItemReal.add(head);

    }
    public void PopulateDefaultFallbackCommands()
    {

    }

}
