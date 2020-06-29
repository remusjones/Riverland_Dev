package riverland.dev.riverland;

import net.minecraft.server.v1_15_R1.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
import riverland.dev.riverland.CustomEntityGiant;

import java.util.Map;

//import static riverland.dev.riverland.CustomEntityGiant.getPrivateField;

/*
#
#
# MADE REDUNDANT KEEPING HERE FOR REFERENCE ONLY
#
#
 */
@Deprecated
public enum EntityTypesCustom
{
    //NAME("Entity name", Entity ID, yourcustomclass.class);
    CUSTOM_GIANT("Giant", 53, CustomEntityGiant.class); //You can add as many as you want.

    private EntityTypesCustom(String name, int id, Class<? extends Entity> custom)
    {
        addToMaps(custom, name, id);
    }


    public static void spawnEntity(Entity entity, Location loc)
    {
        entity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftWorld)loc.getWorld()).getHandle().addEntity(entity);
    }

    private static void addToMaps(Class clazz, String name, int id)
    {
        //getPrivateField is the method from above.
        //Remove the lines with // in front of them if you want to override default entities (You'd have to remove the default entity from the map first though).

        //((Map)getPrivateField("g", net.minecraft.server.v1_7_R4.EntityTypes.class, null)).put(name, Integer.valueOf(id));
    }
}
