package riverland.dev.riverland;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.output.ByteArrayOutputStream;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
/*
#
#
# MADE REDUNDANT KEEPING HERE FOR REFERENCE ONLY
#
#
 */
@Deprecated
public class InventoryUtil {

    static Inventory ThumbusLootTable = null;
    static String configLocation = "ThumbusLootTable";

    public static void saveInventory() {
        if (InventoryUtil.ThumbusLootTable == null)
            return;

        try (final BukkitObjectOutputStream data = new BukkitObjectOutputStream(new ByteArrayOutputStream())) {
            data.writeInt(ThumbusLootTable.getSize());
            for (int i = 0; i < ThumbusLootTable.getSize(); i++) {
                data.writeObject(ThumbusLootTable.getItem(i));
            }
            //Save it in your config here.
            Riverland._Instance.config.set(configLocation,data);
            Riverland._Instance.saveConfig();
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadInventory() {
        final String encodedString = configLocation;//Retrieve from where it is stored.;
        try (final BukkitObjectInputStream data = new BukkitObjectInputStream(new ByteArrayInputStream(Base64.getDecoder().decode(encodedString)))) {
            final int invSize = data.readInt();
            Inventory temp = Bukkit.createInventory(null, invSize, "ThumbusInventory");
            for (int i = 0; i < invSize; i++) {
                temp.setItem(i, (ItemStack) data.readObject());
            }
            InventoryUtil.ThumbusLootTable = temp;
        } catch (final IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}