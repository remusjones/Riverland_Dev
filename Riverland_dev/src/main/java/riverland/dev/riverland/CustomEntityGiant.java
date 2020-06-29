package riverland.dev.riverland;

//import com.destroystokyo.paper.event.entity.EntityTransformedEvent;
//import com.mojang.brigadier.exceptions.CommandSyntaxException;
//import net.minecraft.server.v1_15_R1.*;
//import net.minecraft.server.v1_15_R1.EntityTypes;
//import net.minecraft.server.v1_15_R1.Entity;
//import net.minecraft.server.v1_15_R1.EntityTypes;
//import net.minecraft.server.v1_15_R1.Item;
//import org.bukkit.Bukkit;
//import org.bukkit.Location;
//import org.bukkit.block.data.type.Fire;
//import org.bukkit.craftbukkit.v1_15_R1.CraftWorld;
//import org.bukkit.craftbukkit.v1_15_R1.entity.CraftCreeper;
//import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
//import org.bukkit.craftbukkit.v1_15_R1.entity.CraftZombie;
//import org.bukkit.craftbukkit.v1_15_R1.event.CraftEventFactory;
//import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
//import org.bukkit.enchantments.Enchantment;
//import org.bukkit.entity.*;
//import org.bukkit.event.entity.*;
//import org.bukkit.inventory.EntityEquipment;
//import org.bukkit.inventory.meta.ItemMeta;
//import org.bukkit.metadata.MetadataValue;
//import org.bukkit.potion.PotionEffect;
//import org.bukkit.potion.PotionType;
//
//import javax.annotation.Nullable;
//import java.lang.reflect.Field;
//import java.time.LocalDate;
//import java.time.temporal.ChronoField;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//
///*
//#
//#
//# MADE REDUNDANT KEEPING HERE FOR REFERENCE ONLY
//#
//#
// */
//@Deprecated
public class CustomEntityGiant {}//extends EntityGiantZombie {
//    private ArrayList<Player> playerList = new ArrayList<>();
//
//    Integer HitsForZombieSpawn = 2;
//    Integer HitsForZombiePack = 13;
//    Integer ZombiePackCount = 15;
//    Integer currHitsAfterMaxHealth = 0;
//    Integer currHitsAfterMaxHealthPack = 0;
//
//    boolean creepersHaveSpawned;
//    Integer creepersPackSpawn = 20;
//
//
//    public CustomEntityGiant(World world) //You can also directly use the nms world class but this is easier if you are spawning this entity.
//    {
//        super(EntityTypes.GIANT, world);
//
////
//        this.goalSelector.a(0, new PathfinderGoalFloat(this));
//        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
//        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
//        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
//        this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
//       // this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true, true));
//
//        ItemStack testSword = CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(org.bukkit.Material.IRON_SWORD, 1));
//    }
//
//    public void UpdateLookTarget() {
//        this.setGoalTarget((EntityLiving) ((Entity) playerList.get(0)));
//    }
//
//    public void CreateZombieConfigs(ArrayList<Player> players, int maxHP) {
//        playerList = players;
//        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(maxHP);
//        this.getAttributeInstance(GenericAttributes.ATTACK_KNOCKBACK).setValue(10);
//        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(1);
//        this.setHealth(maxHP);
//    }
//
//
//
//    public CustomEntityGiant(World world, EntityTypes<? extends EntityGiantZombie> entityType) {
//        this(world);
//
//        this.goalSelector.a(0, new PathfinderGoalFloat(this));
//        this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
//        this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
//        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
//        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
//
//    }
//
//    public void Update() {
//
//    }
//
//    // Construct our entity with our super class, and make persistent.
//    public CustomEntityGiant(EntityTypes<CustomEntityGiant> customEntityGiantEntityTypes, World world) {
//
//        super(EntityTypes.GIANT, world);
//        l();
//        this.persistent = true;
//    }
//
//    // setup out pathfinder goals
//    protected void initPathfinder() {
//        this.goalSelector.a(4, new CustomEntityGiant.a(this, 1.0D, 3));
//        this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
//        this.persistent = true;
//        this.persist = true;
//        this.setPersistent();
//        this.l();
//    }
//
//    protected void l()
//    {
//    //    this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(0.1D);
//
//        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(2000);
//        this.getAttributeInstance(GenericAttributes.KNOCKBACK_RESISTANCE).setValue(100);
//       // this.getAttributeInstance(GenericAttributes.ARMOR).setValue(16);
//        this.targetSelector.a(1, (new PathfinderGoalHurtByTarget(this, new Class[0])).a(new Class[]{EntityPlayer.class}));
//        this.targetSelector.a(1, (new PathfinderGoalHurtByTarget(this, new Class[0])).a(new Class[]{EntityPigZombie.class}));
//      //  this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D));
//        this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
//        this.targetSelector.a(2, new PathfinderGoalGiantAttack(this, 1.0D, false));
//    }
//
//    @Override
//    protected void initAttributes() {
//        super.initAttributes();
//        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(35.0D);
//        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.3);
//        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(300.0D);
//       // this.getAttributeInstance(GenericAttributes.ARMOR).setValue(2.0D);
//        this.getAttributeInstance(GenericAttributes.ATTACK_KNOCKBACK).setValue(3);
//        this.getAttributeInstance(GenericAttributes.MAX_HEALTH).setValue(2000);
//    }
//
//    protected int getExpValue(EntityHuman entityhuman) {
//        if (this.isBaby()) {
//            this.f = (int) ((float) this.f * 2.5F);
//        }
//        return super.getExpValue(entityhuman);
//    }
//
//    public void a(DataWatcherObject<?> datawatcherobject) {
//        super.a(datawatcherobject);
//    }
//
//    protected boolean et() {
//        return true;
//    }
//
//    public void tick() {
//        super.tick();
//    }
//
//    public void movementTick() {
//        super.movementTick();
//    }
//
//
//    protected void ev() {
//        this.world.a((EntityHuman) null, 1040, new BlockPosition(this), 0);
//    }
//
//    protected void b(EntityTypes<? extends CustomEntityGiant> entitytypes) {
//        if (!this.dead) {
//            CustomEntityGiant entityzombie = (CustomEntityGiant) entitytypes.a(this.world);
//            entityzombie.u(this);
//            entityzombie.setCanPickupLoot(this.canPickupLoot());
//            entityzombie.v(entityzombie.world.getDamageScaler(new BlockPosition(entityzombie)).d());
//            EnumItemSlot[] aenumitemslot = EnumItemSlot.values();
//            int i = aenumitemslot.length;
//
//            for (int j = 0; j < i; ++j) {
//                EnumItemSlot enumitemslot = aenumitemslot[j];
//                ItemStack itemstack = this.getEquipment(enumitemslot);
//                if (!itemstack.isEmpty()) {
//                    entityzombie.setSlot(enumitemslot, itemstack.cloneItemStack());
//                    entityzombie.a(enumitemslot, this.d(enumitemslot));
//                    itemstack.setCount(0);
//                }
//            }
//
//            if (this.hasCustomName()) {
//                entityzombie.setCustomName(this.getCustomName());
//                entityzombie.setCustomNameVisible(this.getCustomNameVisible());
//            }
//
//            if (this.isPersistent()) {
//                entityzombie.setPersistent();
//            }
//
//            entityzombie.setInvulnerable(this.isInvulnerable());
//            if (CraftEventFactory.callEntityTransformEvent(this, entityzombie, EntityTransformEvent.TransformReason.DROWNED).isCancelled()) {
//                ((Zombie) this.getBukkitEntity()).setConversionTime(-1);
//                return;
//            }
//
//            if (!(new EntityTransformedEvent(this.getBukkitEntity(), entityzombie.getBukkitEntity(), EntityTransformedEvent.TransformedReason.DROWNED)).callEvent()) {
//                return;
//            }
//
//        }
//
//    }
//    //public boolean r(Entity entity)
//    //{
//    //    float f = (float) this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).getValue();
//    //    int i = 0;
////
//    //    boolean flag = entity.damageEntity(DamageSource.mobAttack(this), f);
////
//    //    if (flag) {
//    //      // if (i > 0) {
//    //      //     entity.g((double) (-MathHelper.sin(this.yaw * 3.1415927F / 180.0F) * (float) i * 0.5F), 0.1D, (double) (MathHelper.cos(this.yaw * 3.1415927F / 180.0F) * (float) i * 0.5F));
//    //      //     this.setMot(this.getMot().x * 0.6D, );;
//    //      //     this.motZ *= 0.6D;
//    //      // }
////
//    //        int j = EnchantmentManager.getFireAspectEnchantmentLevel(this);
////
//    //        if (j > 0) {
//    //            // CraftBukkit start - Call a combust event when somebody hits with a fire enchanted item
//    //            EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent(this.getBukkitEntity(), entity.getBukkitEntity(), j * 4);
//    //            org.bukkit.Bukkit.getPluginManager().callEvent(combustEvent);
////
//    //            if (!combustEvent.isCancelled()) {
//    //                entity.setOnFire(combustEvent.getDuration());
//    //            }
//    //            // CraftBukkit end
//    //        }
////
//    //        this.a((EntityLiving) this, entity);
//    //    }
////
//    //    return flag;
//    //}
//    public boolean a(EntityHuman entityhuman, EnumHand enumhand) {
//        ItemStack itemstack = entityhuman.b(enumhand);
//        Item item = itemstack.getItem();
//        if (item instanceof ItemMonsterEgg && ((ItemMonsterEgg) item).a(itemstack.getTag(), this.getEntityType())) {
//            if (!this.world.isClientSide) {
//                EntityZombie entityzombie = (EntityZombie) this.getEntityType().a(this.world);
//                if (entityzombie != null) {
//                    entityzombie.setBaby(true);
//                    entityzombie.setPositionRotation(this.locX(), this.locY(), this.locZ(), 0.0F, 0.0F);
//                    this.world.addEntity(entityzombie);
//                    if (itemstack.hasName()) {
//                        entityzombie.setCustomName(itemstack.getName());
//                    }
//
//                    if (!entityhuman.abilities.canInstantlyBuild) {
//                        itemstack.subtract(1);
//                    }
//                }
//            }
//
//            return true;
//        } else {
//            return super.a(entityhuman, enumhand);
//        }
//    }
//    public void ForceAI()
//    {
//        this.setNoAI(false);
//        this.doAITick();
//    }
//
//    @Override
//    public void move(EnumMoveType moveType,Vec3D move )
//    {
//        if (!moveType.equals(EnumMoveType.PLAYER))
//        {
//            super.move(moveType,move);
//        }
//    }
//
//    ArrayList<String> creeperNames = new ArrayList<String>();
//    @Override
//    public boolean damageEntity(DamageSource damagesource, float f) {
//
//        if (this.getHealth() <= (getMaxHealth() / 2) && !creepersHaveSpawned)
//        {
//            creeperNames.add("James");
//            creeperNames.add("John");
//            creeperNames.add("Robert");
//            creeperNames.add("Michael");
//            creeperNames.add("William");
//            creeperNames.add("David");
//            creeperNames.add("Richard");
//            creeperNames.add("Joseph");
//            creeperNames.add("Thomas");
//            creeperNames.add("Charles");
//            creeperNames.add("Christopher");
//            creeperNames.add("Daniel");
//            creeperNames.add("Mary");
//            creeperNames.add("Patricia");
//            creeperNames.add("Jennifer");
//            creeperNames.add("Linda");
//            creeperNames.add("Elizabeth");
//            creeperNames.add("Barbara");
//            creeperNames.add("Susan");
//            creeperNames.add("Jessica");
//            creeperNames.add("Sarah");
//            creeperNames.add("Karen");
//            creeperNames.add("Nancy");
//            creeperNames.add("Margaret");
//            creeperNames.add("Jason");
//            creeperNames.add("Karen");
//
//            try {
//                for (int i = 0; i < creepersPackSpawn; i++) {
//                    try {
//                        //creeperPack.setPosition(this.locX() + (random.nextDouble() * 5), this.locY(), this.locZ() + (random.nextDouble() * 5));
//                        CraftCreeper myCreeper = (CraftCreeper) Riverland.CreeperTypeInstance.spawn(new Location((org.bukkit.World) world.getWorld(), this.locX() + (random.nextDouble() * 5), this.locY(), this.locZ() + (random.nextDouble() * 5)));
////
//                        // EntityCreeper creeperPack = new EntityCreeper(EntityTypes.CREEPER,world);
//                        IChatBaseComponent chatBase = new ChatMessage(creeperNames.get(i));
//                        myCreeper.setCustomName(creeperNames.get(i));
//                        myCreeper.setCustomNameVisible(true);
//                        myCreeper.setPowered(true);
//                        myCreeper.setMaxFuseTicks(60);
//                        myCreeper.setPersistent(true);
//                    }
//                    catch (Exception exc)
//                    {
//
//                    }
//                }
//            }
//            catch (Exception exc)
//            {
//                exc.printStackTrace();
//            }
//            creepersHaveSpawned = true;
//        }
//
//
//        if (damagesource.j() instanceof EntityArrow)
//            return super.damageEntity(damagesource, f);
//
//        if (damagesource.isFire() || damagesource.j() instanceof EntityZombie) {
//            if (damagesource.j() instanceof EntityZombie)
//                ((EntityZombie) damagesource.j()).killEntity();
//            return false;
//        }
//
//        if (this.isInvulnerable(damagesource)) {
//            return false;
//        } else if (damagesource.j() instanceof EntityLargeFireball || damagesource.getEntity() instanceof Player) {
//            if (this.getHealth() <= (getMaxHealth() / 1.75)) {
//                currHitsAfterMaxHealth++;
//                currHitsAfterMaxHealthPack++;
//                if (currHitsAfterMaxHealth > HitsForZombiePack) {
//
//                    for (int i = 0; i < ZombiePackCount; i++) {
//
//                     //   CraftZombie myZombie = (CraftZombie) Riverland.BabyZombieTypeInstance.spawn(new Location((org.bukkit.World) world.getWorld(), this.locX() + (random.nextDouble() * 5), this.locY(), this.locZ() + (random.nextDouble() * 5)));
//                        EntityZombie zombiePack = new EntityZombie(world);
//                        zombiePack.setBaby(true);
//                        zombiePack.setGoalTarget(this.lastDamager);
//                    }
//                    currHitsAfterMaxHealth = 0;
//                }
//                if (currHitsAfterMaxHealthPack > HitsForZombieSpawn) {
//                    EntityZombie zombiePack = new EntityZombie(world);
//                    zombiePack.setGoalTarget(this.lastDamager);
//                    zombiePack.setPosition(this.locX() + (random.nextDouble() * 5), this.locY(), this.locZ() + (random.nextDouble() * 5));
//                    zombiePack.addEffect(new MobEffect(MobEffects.FASTER_MOVEMENT, 400, 3), EntityPotionEffectEvent.Cause.POTION_DRINK); //        this.addEffect(new MobEffect(MobEffectList.SLOWER_MOVEMENT.id, 10000, 10000));
//                    zombiePack.addEffect(new MobEffect(MobEffects.RESISTANCE, 400, 100), EntityPotionEffectEvent.Cause.POTION_DRINK);
//                    zombiePack.updateEffects = true;
//                    world.addEntity(zombiePack);
//                    currHitsAfterMaxHealthPack = 0;
//                }
//            }
//            return super.damageEntity(damagesource, f);
//        } else if (damagesource.j() instanceof EntityLargeFireball || damagesource.isExplosion()) {
//            return false;
//        } else if (damagesource.j() instanceof Fire) {
//            return false;
//        } else {
//
//            if (damagesource.getEntity() instanceof EntityLiving) {
//                try
//                {
//                    if (damagesource.getEntity().isAlive())
//                        this.setGoalTarget(((EntityLiving) damagesource.getEntity()));
//                }catch (Exception exc)
//                {
//
//                }
//                if (this.getHealth() <= (getMaxHealth() / 1.75)) {
//                    currHitsAfterMaxHealth++;
//
//                    currHitsAfterMaxHealthPack++;
//                    if (currHitsAfterMaxHealth > HitsForZombiePack) {
//
//                        for (int i = 0; i < ZombiePackCount; i++) {
//                       //     CraftZombie myZombie = (CraftZombie) Riverland.BabyZombieTypeInstance.spawn(new Location((org.bukkit.World) world.getWorld(), this.locX() + (random.nextDouble() * 5), this.locY(), this.locZ() + (random.nextDouble() * 5)));
//                            EntityZombie zombiePack = new EntityZombie(world);
//                            zombiePack.setBaby(true);
//                            zombiePack.setGoalTarget(this.lastDamager);
//                        }
//                        currHitsAfterMaxHealth = 0;
//                    }
//                    if (currHitsAfterMaxHealthPack > HitsForZombieSpawn) {
//                        EntityZombie zombiePack = new EntityZombie(world);
//                        zombiePack.setPosition(this.locX() + (random.nextDouble() * 5), this.locY(), this.locZ() + (random.nextDouble() * 5));
//                        zombiePack.setGoalTarget(this.lastDamager);
//                        world.addEntity(zombiePack);
//                        currHitsAfterMaxHealthPack = 0;
//                    }
//                }
//            } else if (damagesource.j() instanceof EntityArrow) {
//
//                if (damagesource.getEntity().isAlive()) {
//                    if (damagesource.getEntity() != null)
//                        this.setGoalTarget(((EntityLiving) damagesource.getEntity()), EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY,false);
//                }
//                if (this.getHealth() <= (getMaxHealth() / 1.75)) {
//                    currHitsAfterMaxHealth++;
//                    currHitsAfterMaxHealthPack++;
//                    if (currHitsAfterMaxHealth > HitsForZombiePack) {
//
//                        for (int i = 0; i < ZombiePackCount; i++) {
//                      //      CraftZombie myZombie = (CraftZombie) Riverland.BabyZombieTypeInstance.spawn(new Location((org.bukkit.World) world.getWorld(), this.locX() + (random.nextDouble() * 5), this.locY(), this.locZ() + (random.nextDouble() * 5)));
//                            EntityZombie zombiePack = new EntityZombie(world);
//                            zombiePack.setBaby(true);
//                            zombiePack.setGoalTarget(this.lastDamager);
//                        }
//                        currHitsAfterMaxHealth = 0;
//                    }
//                    if (currHitsAfterMaxHealthPack > HitsForZombieSpawn) {
//                        EntityZombie zombiePack = new EntityZombie(world);
//                        zombiePack.setPosition(this.locX() + (random.nextDouble() * 5), this.locY(), this.locZ() + (random.nextDouble() * 5));
//                        zombiePack.setGoalTarget(this.lastDamager);
//                        world.addEntity(zombiePack);
//                        currHitsAfterMaxHealthPack = 0;
//                    }
//                }
//               // if (damagesource.j() instanceof EntityArrow)
//                return super.damageEntity(damagesource, f);
//                // spawn fireball at target..
//
//            }
//
//
//
//            return super.damageEntity(damagesource, f);
//        }
//
//    }
//
//    public boolean B(Entity entity) {
//        boolean flag = super.B(entity);
//        if (flag) {
//            float f = this.world.getDamageScaler(new BlockPosition(this)).b();
//            if (this.getItemInMainHand().isEmpty() && this.isBurning() && this.random.nextFloat() < f * 0.3F) {
//                EntityCombustByEntityEvent event = new EntityCombustByEntityEvent(this.getBukkitEntity(), entity.getBukkitEntity(), 2 * (int) f);
//                this.world.getServer().getPluginManager().callEvent(event);
//                if (!event.isCancelled()) {
//                    entity.setOnFire(event.getDuration(), false);
//                }
//            }
//        }
//
//        return flag;
//    }
//    public void addNBT(org.bukkit.entity.Entity e, String value) {
//        net.minecraft.server.v1_15_R1.Entity nms = ((CraftEntity) e).getHandle();
//        NBTTagCompound nbt = new NBTTagCompound();
//        nms.c(nbt);
//        try {
//            NBTTagCompound nbtv = MojangsonParser.parse(value);
//            nbt.a(nbtv);
//            nms.f(nbt);
//        } catch (CommandSyntaxException ex) {
//            // put however you want to handle the error here
//        }
//    }
//    public String getNBT(org.bukkit.entity.Entity e) {
//        net.minecraft.server.v1_15_R1.Entity nms = ((CraftEntity) e).getHandle();
//        NBTTagCompound nbt = new NBTTagCompound();
//        nms.c(nbt);
//        return nbt.toString();
//    }
//    protected SoundEffect getSoundAmbient() {
//        return SoundEffects.ENTITY_ELDER_GUARDIAN_AMBIENT;
//    }
//
//    protected SoundEffect getSoundHurt(DamageSource damagesource) {
//        return SoundEffects.ENTITY_ELDER_GUARDIAN_HURT;
//    }
//
//    protected SoundEffect getSoundDeath() {
//        return SoundEffects.ENTITY_GUARDIAN_DEATH;
//    }
//
//    protected SoundEffect getSoundStep() {
//        return SoundEffects.ENTITY_IRON_GOLEM_STEP;
//    }
//
//    protected void a(BlockPosition blockposition, IBlockData iblockdata) {
//        this.a(this.getSoundStep(), 0.15F, 1.0F);
//    }
//
//    public EnumMonsterType getMonsterType() {
//        return EnumMonsterType.UNDEAD;
//    }
//
//    protected void a(DifficultyDamageScaler difficultydamagescaler) {
//        super.a(difficultydamagescaler);
//
//        if (this.random.nextFloat() < (this.world.getDifficulty() == EnumDifficulty.HARD ? 0.05F : 0.01F)) {
//            int i = this.random.nextInt(3);
//            if (i == 0) {
//                this.setSlot(EnumItemSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
//            } else {
//                this.setSlot(EnumItemSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
//            }
//        }
//
//    }
//
//    public void b(NBTTagCompound nbttagcompound) {
//        super.b(nbttagcompound);
//    }
//
//    public void a(NBTTagCompound nbttagcompound) {
//        super.a(nbttagcompound);
//    }
//
//    public void b(EntityLiving entityliving) {
//        super.b(entityliving);
//        if ((this.world.getDifficulty() == EnumDifficulty.NORMAL || this.world.getDifficulty() == EnumDifficulty.HARD) && entityliving instanceof EntityVillager) {
//            if (this.world.getDifficulty() != EnumDifficulty.HARD && this.random.nextBoolean()) {
//                return;
//            }
//
//            EntityVillager entityvillager = (EntityVillager) entityliving;
//            EntityZombieVillager entityzombievillager = (EntityZombieVillager) EntityTypes.ZOMBIE_VILLAGER.a(this.world);
//            entityzombievillager.u(entityvillager);
//            entityzombievillager.setVillagerData(entityvillager.getVillagerData());
//            entityzombievillager.a((NBTBase) entityvillager.eN().a(DynamicOpsNBT.a).getValue());
//            entityzombievillager.setOffers(entityvillager.getOffers().a());
//            entityzombievillager.a(entityvillager.getExperience());
//            entityzombievillager.setBaby(entityvillager.isBaby());
//            entityzombievillager.setNoAI(entityvillager.isNoAI());
//            if (entityvillager.hasCustomName()) {
//                entityzombievillager.setCustomName(entityvillager.getCustomName());
//                entityzombievillager.setCustomNameVisible(entityvillager.getCustomNameVisible());
//            }
//
//            if (this.isPersistent()) {
//                entityzombievillager.setPersistent();
//            }
//
//            entityzombievillager.setInvulnerable(this.isInvulnerable());
//            if (CraftEventFactory.callEntityTransformEvent(this, entityzombievillager, EntityTransformEvent.TransformReason.INFECTION).isCancelled()) {
//                return;
//            }
//
//            if (!(new EntityTransformedEvent(this.getBukkitEntity(), entityvillager.getBukkitEntity(), EntityTransformedEvent.TransformedReason.INFECTED)).callEvent()) {
//                return;
//            }
//
//            entityvillager.die();
//            this.world.addEntity(entityzombievillager, CreatureSpawnEvent.SpawnReason.INFECTION);
//            this.world.a((EntityHuman) null, 1026, new BlockPosition(this), 0);
//        }
//
//    }
//
//    protected float b(EntityPose entitypose, EntitySize entitysize) {
//        return this.isBaby() ? 0.93F : 1.74F;
//    }
//
//    protected boolean g(ItemStack itemstack) {
//        return itemstack.getItem() == Items.EGG && this.isBaby() && this.isPassenger() ? false : super.g(itemstack);
//    }
//
//    @Nullable
//    public GroupDataEntity prepare(GeneratorAccess generatoraccess, DifficultyDamageScaler difficultydamagescaler, EnumMobSpawn enummobspawn, @Nullable GroupDataEntity groupdataentity, @Nullable NBTTagCompound nbttagcompound) {
//        Object object = super.prepare(generatoraccess, difficultydamagescaler, enummobspawn, groupdataentity, nbttagcompound);
//        float f = difficultydamagescaler.d();
//        this.setCanPickupLoot(this.random.nextFloat() < 0.55F * f);
//        if (object == null) {
//            //  object = new EntityZombie.GroupDataZombie(generatoraccess.getRandom().nextFloat() < 0.05F);
//        }
//
//        if (object instanceof EntityZombie.GroupDataZombie) {
//            CustomEntityGiant.GroupDataZombie entityzombie_groupdatazombie = (CustomEntityGiant.GroupDataZombie) object;
//            if (entityzombie_groupdatazombie.a) {
//                if ((double) generatoraccess.getRandom().nextFloat() < 0.05D) {
//                    List<EntityChicken> list = generatoraccess.a(EntityChicken.class, this.getBoundingBox().grow(5.0D, 3.0D, 5.0D), IEntitySelector.c);
//                    if (!list.isEmpty()) {
//                        EntityChicken entitychicken = (EntityChicken) list.get(0);
//                        entitychicken.r(true);
//                        this.startRiding(entitychicken);
//                    }
//                } else if ((double) generatoraccess.getRandom().nextFloat() < 0.05D) {
//                    EntityChicken entitychicken1 = (EntityChicken) EntityTypes.CHICKEN.a(this.world);
//                    entitychicken1.setPositionRotation(this.locX(), this.locY(), this.locZ(), this.yaw, 0.0F);
//                    entitychicken1.prepare(generatoraccess, difficultydamagescaler, EnumMobSpawn.JOCKEY, (GroupDataEntity) null, (NBTTagCompound) null);
//                    entitychicken1.r(true);
//                    generatoraccess.addEntity(entitychicken1, CreatureSpawnEvent.SpawnReason.MOUNT);
//                    this.startRiding(entitychicken1);
//                }
//            }
//
//            this.a(difficultydamagescaler);
//            this.b((DifficultyDamageScaler) difficultydamagescaler);
//        }
//
//        if (this.getEquipment(EnumItemSlot.HEAD).isEmpty()) {
//            LocalDate localdate = LocalDate.now();
//            int i = localdate.get(ChronoField.DAY_OF_MONTH);
//            int j = localdate.get(ChronoField.MONTH_OF_YEAR);
//            if (j == 10 && i == 31 && this.random.nextFloat() < 0.25F) {
//                this.setSlot(EnumItemSlot.HEAD, new ItemStack(this.random.nextFloat() < 0.1F ? Blocks.JACK_O_LANTERN : Blocks.CARVED_PUMPKIN));
//                this.dropChanceArmor[EnumItemSlot.HEAD.b()] = 0.0F;
//            }
//        }
//
//        this.v(f);
//        return (GroupDataEntity) object;
//    }
//
//
//    protected void v(float f) {
//        this.getAttributeInstance(GenericAttributes.KNOCKBACK_RESISTANCE).addModifier(new AttributeModifier("Random spawn bonus", this.random.nextDouble() * 0.05000000074505806D, AttributeModifier.Operation.ADDITION));
//        double d0 = this.random.nextDouble() * 1.5D * (double) f;
//        if (d0 > 1.0D) {
//            this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).addModifier(new AttributeModifier("Random zombie-spawn bonus", d0, AttributeModifier.Operation.MULTIPLY_TOTAL));
//        }
//
//        if (this.random.nextFloat() < f * 0.05F) {
//
//        }
//
//    }
//
//    public double aR() {
//        return this.isBaby() ? 0.0D : -0.45D;
//    }
//
//    protected void dropDeathLoot(DamageSource damagesource, int i, boolean flag) {
//        super.dropDeathLoot(damagesource, i, flag);
//        Entity entity = damagesource.getEntity();
//        if (entity instanceof EntityCreeper) {
//            EntityCreeper entitycreeper = (EntityCreeper) entity;
//            if (entitycreeper.canCauseHeadDrop()) {
//                entitycreeper.setCausedHeadDrop();
//                ItemStack itemstack = this.es();
//                if (!itemstack.isEmpty()) {
//                    this.a((ItemStack) itemstack);
//                }
//            }
//        }
//    }
//
//    protected ItemStack es() {
//        return new ItemStack(Items.ZOMBIE_HEAD);
//    }
//
//    // static {
//    //     bw = DataWatcher.a(EntityZombie.class, DataWatcherRegistry.i);
//    //     bx = DataWatcher.a(EntityZombie.class, DataWatcherRegistry.b);
//    //     DROWN_CONVERTING = DataWatcher.a(EntityZombie.class, DataWatcherRegistry.i);
//    //     bz = (enumdifficulty) -> {
//    //         return enumdifficulty == EnumDifficulty.HARD;
//    //     };
//    // }
//
//
//    public class GroupDataZombie implements GroupDataEntity {
//        public final boolean a;
//
//        private GroupDataZombie(boolean flag) {
//            this.a = flag;
//        }
//    }
//
//    class a extends PathfinderGoalRemoveBlock {
//        a(EntityCreature entitycreature, double d0, int i) {
//            super(Blocks.TURTLE_EGG, entitycreature, d0, i);
//        }
//
//        public void a(GeneratorAccess generatoraccess, BlockPosition blockposition) {
//            generatoraccess.playSound((EntityHuman) null, blockposition, SoundEffects.ENTITY_ZOMBIE_DESTROY_EGG, SoundCategory.HOSTILE, 0.5F, 0.9F + random.nextFloat() * 0.2F);
//        }
//
//        public void a(World world, BlockPosition blockposition) {
//            world.playSound((EntityHuman) null, blockposition, SoundEffects.ENTITY_TURTLE_EGG_BREAK, SoundCategory.BLOCKS, 0.7F, 0.9F + world.random.nextFloat() * 0.2F);
//        }
//
//        public double h() {
//            return 1.14D;
//        }
//    }
//
//    public static class PathfinderGoalGiantAttack extends PathfinderGoalMeleeAttack {
//        private CustomEntityGiant d = null;
//        private int e;
//        private int fireball = 40;
//        private int levitation = 0;
//
//        public PathfinderGoalGiantAttack(CustomEntityGiant var0, double var1, boolean var3) {
//            super(var0, var1, var3);
//            this.d = var0;
//        }
//
//        public void c() {
//            super.c();
//            this.e = 0;
//        }
//
//        public void d() {
//            super.d();
//            this.d.q(false);
//        }
//
//        // setup multi-target aggressor
//
//        public void e() {
//            try {
//                super.e();
//                if (this.d.getGoalTarget() == null)
//                    return;
//
//                EntityLiving entityliving = this.d.getGoalTarget();
//                double d0 = 64.0D;
//                World world = this.d.world;
//                Vec3D vec3d = this.d.f(1.0F);
//                ++this.e;
//                ++this.fireball;
//                ++this.levitation;
//
//
//                if (fireball > 60) {
//
//                    // create locatio
//
//                    CraftWorld w = world.getWorld();
//                   // this.d.locX(), this.d.locY(), this.d.locZ()
//                    Location loc = new Location(w,this.d.locX(), this.d.locY(), this.d.locZ()); //defines new location
//                    Collection<Player> players = w.getNearbyPlayers(loc, 50);
//
//                    // apply thumbus debuff
//
//                    int i = 0;
//                    for(Player player : players)
//                    {
//                      //  if (levitation >= 1200)
//                      //  {
//                      //      // apply levitation effect
//                      //          player.addPotionEffect(new MobEffect(MobEffects.LEVITATION, 100, 3, false, false));
////
//                      //  }
//
//                        if (i <=3) {
//
//                            Location playerLoc = player.getLocation();
//                            // get target direction between player and thumbus
//                            double d2 = playerLoc.getX() - (this.d.locX() + vec3d.x * 4.0D);
//                            double d3 = entityliving.e(0.5D) - (0.5D + this.d.e(0.5D));
//                            double d4 = playerLoc.getZ() - (this.d.locZ() + vec3d.z * 4.0D);
//
//                            EntityLargeFireball entitylargefireball = new EntityLargeFireball(world, this.d, d2, d3, d4);
//                            entitylargefireball.bukkitYield = (float) (entitylargefireball.yield = 5);
//
//                            entitylargefireball.setPosition(this.d.locX() + vec3d.x * 4.0D, this.d.e(0.5D) + 0.5D, entitylargefireball.locZ() + vec3d.z * 4.0D);
//                            entitylargefireball.forceExplosionKnockback = true;
//                            entitylargefireball.isIncendiary = true;
//                            entitylargefireball.fireTicks = 40;
//                            world.addEntity(entitylargefireball);
//                        }
//
//                        i++;
//                    }
//
//
//
//
//
//
//
//                   //double d2 = entityliving.locX() - (this.d.locX() + vec3d.x * 4.0D);
//                   //double d3 = entityliving.e(0.5D) - (0.5D + this.d.e(0.5D));
//                   //double d4 = entityliving.locZ() - (this.d.locZ() + vec3d.z * 4.0D);
//
//                   //EntityLargeFireball entitylargefireball = new EntityLargeFireball(world, this.d, d2, d3, d4);
//                   //entitylargefireball.bukkitYield = (float) (entitylargefireball.yield = 5);
//
//                   //entitylargefireball.setPosition(this.d.locX() + vec3d.x * 4.0D, this.d.e(0.5D) + 0.5D, entitylargefireball.locZ() + vec3d.z * 4.0D);
//                   //entitylargefireball.forceExplosionKnockback = true;
//                   //world.addEntity(entitylargefireball);
//                    fireball = 0;
//                }
//
//                if (this.e >= 40 && this.b < 10) {
//                    this.d.q(true);
//
//                } else {
//                    this.d.q(false);
//                }
//
//            } catch (Exception err) {
//                err.printStackTrace();
//            }
//        }
//    }
//}
//
//