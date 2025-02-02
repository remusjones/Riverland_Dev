package riverland.dev.riverland;

// com.google.common.collect.BiMap;
//mport com.mojang.datafixers.DataFixUtils;
//mport com.mojang.datafixers.types.Type;
//mport net.minecraft.server.v1_15_R1.*;
//mport org.bukkit.Location;
//mport org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
//mport javax.annotation.Nullable;
//mport java.lang.reflect.Field;
//mport java.util.Map;

import org.apache.http.annotation.Obsolete;

@Obsolete
@Deprecated
class CustomEntityTypes{}
//class CustomEntityType<T extends EntityLiving> {
//
//    @Nullable private static Field REGISTRY_MAT_MAP;
//
//    static {
//        try {
//            REGISTRY_MAT_MAP = RegistryMaterials.class.getDeclaredField("c");
//        } catch (ReflectiveOperationException err) {
//            err.printStackTrace();
//            REGISTRY_MAT_MAP = null;
//            // technically should only occur if server version changes or jar is modified in "weird" ways
//        }
//    }
//
//    private final MinecraftKey key;
//    private final Class<T> clazz;
//    private final EntityTypes.b<T> maker;
//    private EntityTypes<? super T> parentType;
//    private EntityTypes<T> entityType;
//    private boolean registered;
//
//
//    public CustomEntityType(String name, Class<T> customEntityClass, EntityTypes<? super T> parentType, EntityTypes.b<T> maker) {
//        this.key = MinecraftKey.a(name); // returns null if 256+ chars, non-alphanumeric, or contains uppercase chars
//        this.clazz = customEntityClass;
//        this.parentType = parentType;
//        this.maker = maker;
//    }
//
//    public org.bukkit.entity.Entity spawn(Location loc) {
//        // Parameters: nmsWorld, initialNBT, customName, spawningPlayer, blockPos, spawnReason, fixPos, flag1(?)
//        net.minecraft.server.v1_15_R1.Entity entity = entityType.spawnCreature(((CraftWorld)loc.getWorld()).getHandle(),
//                null, null, null, new BlockPosition(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()),
//                EnumMobSpawn.EVENT, true, false);
//        return entity == null ? null : entity.getBukkitEntity();
//    }
//
//    public void register() throws IllegalStateException
//    {
//        boolean IsRegistered = false;
//        try {
//            if (registered || IRegistry.ENTITY_TYPE.getOptional(key).isPresent())
//            {
//                IsRegistered = true;
//            }
//            if(!IsRegistered){
//                // Add custom entity to data fixers map with parent entity's data fixer
//                Map<Object, Type<?>> dataTypes = (Map<Object, Type<?>>) DataConverterRegistry.a()
//                        .getSchema(DataFixUtils.makeKey(SharedConstants.getGameVersion().getWorldVersion()))
//                        .findChoiceType(DataConverterTypes.ENTITY_TREE).types(); // DataConverterTypes.ENTITY in < 1.15.2
//                if (key == null) {
//                    throw new IllegalStateException(String.format
//                            ("Unable to register entity with key it is null"));
//                } else if (dataTypes == null) {
//                    throw new IllegalStateException(String.format
//                            ("Data types null!"));
//                }
//
//                dataTypes.put(key.toString(), dataTypes.get(parentType.h().toString().replace("entity/", "")));
//                // Add our custom entity to the entity registry map
//                EntityTypes.a<T> a = EntityTypes.a.a(maker, EnumCreatureType.CREATURE);
//                entityType = a.a(key.getKey());
//                IRegistry.a(IRegistry.ENTITY_TYPE, key.getKey(), entityType);
//                registered = true;
//            }
//        }
//        catch (Exception exc)
//        {
//            exc.printStackTrace();
//        }
//    }
//
//    @SuppressWarnings("unchecked")
//    public void unregister() throws IllegalStateException {
//        if (!registered) {
//            throw new IllegalArgumentException(String.format
//                    ("Entity with key '%s' could not be unregistered, as it is not in the registry", key));
//        }
//        // Remove custom entity from data fixers map
//        Map<Object, Type<?>> dataTypes = (Map<Object, Type<?>>)DataConverterRegistry.a()
//                .getSchema(DataFixUtils.makeKey(SharedConstants.getGameVersion().getWorldVersion()))
//                .findChoiceType(DataConverterTypes.ENTITY_TREE).types();
//        dataTypes.remove(key.toString());
//        try {
//            // Remove our custom entity from entity registry map, which takes a little reflection in 1.15
//            if (REGISTRY_MAT_MAP == null) {
//                throw new ReflectiveOperationException("Field not initially found");
//            }
//            REGISTRY_MAT_MAP.setAccessible(true);
//            Object o = REGISTRY_MAT_MAP.get(IRegistry.ENTITY_TYPE);
//            ((BiMap<MinecraftKey, ?>)o).remove(key);
//            REGISTRY_MAT_MAP.set(IRegistry.ENTITY_TYPE, o);
//            REGISTRY_MAT_MAP.setAccessible(false);
//            registered = false;
//        } catch (ReflectiveOperationException err) {
//            err.printStackTrace();
//        }
//    }
//}

