package riverland.dev.riverland;
import net.minecraft.server.v1_15_R1.*;
import net.minecraft.server.v1_15_R1.EntityTypes;

public class CustomEntityCreeper extends EntityCreeper
{

    public CustomEntityCreeper(EntityTypes<CustomEntityCreeper> customEntityGiantEntityTypes, World world)
    {

        super(EntityTypes.CREEPER, world);
        // override our attributes here
    }


    @Override
    protected void initPathfinder() {
        this.goalSelector.a(1, new PathfinderGoalFloat(this));
        this.goalSelector.a(2, new PathfinderGoalSwell(this));
        this.goalSelector.a(3, new PathfinderGoalAvoidTarget(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.a(3, new PathfinderGoalAvoidTarget(this, EntityCat.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 2.2D, false));
        this.goalSelector.a(5, new PathfinderGoalRandomStrollLand(this, 0.8D));
        this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
        this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, true));
        this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, new Class[0]));
    }
    public void InitCustomAttributes()
    {
        super.initAttributes();

        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(2);
    }
    @Override
    public boolean damageEntity(DamageSource damagesource, float f)
    {
        if (damagesource.isExplosion())
            return false;

        return super.damageEntity(damagesource, f);

    }


}
