package fr.skah.playercart.entity;

import fr.skah.playercart.item.Items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;


//Cart = Charrette
//entityPulling = L'entité qui tire la charrette

public class EntityCart extends Entity {

    //Entity distance maintened between the cart and the entity pull. (ex : 2 = 2Blocks, 3.2F = 3,2 Blocks)
    private static final float ENTITY_SPACING = 3.0F;
    //Current entity pulling, is cart is not pulled this var equals null
    private EntityHorse entityPulling;

    //Location of cart passenger.
    private final ArrayList<String> entityPositions = new ArrayList<>();

    public EntityCart(World worldIn) {
        super(worldIn);
        this.stepHeight = 2.0F;
        this.setSize(4.0F, 2.2F);
    }

    @Override
    protected void entityInit() {
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!this.hasNoGravity()) this.motionY -= 0.04D;
        if (this.entityPulling == null) return;

        /* Don't Edit this part. These lines manage the movement of the cart */
        final Vec3d targetVec = new Vec3d(this.entityPulling.posX, this.entityPulling.posY, this.entityPulling.posZ);
        this.rotationYaw = (float) Math.toDegrees(-Math.atan2(targetVec.x - this.posX, targetVec.z - this.posZ));

        final double lookX = MathHelper.sin(-this.rotationYaw * 0.017453292F - (float) Math.PI);
        final double lookZ = MathHelper.cos(-this.rotationYaw * 0.017453292F - (float) Math.PI);
        final double moveX = targetVec.x - this.posX + lookX * ENTITY_SPACING;
        final double moveZ = targetVec.z - this.posZ + lookZ * ENTITY_SPACING;

        if (!this.entityPulling.onGround && this.entityPulling.fallDistance == 0.0F) this.fallDistance = 0.0F;

        this.move(MoverType.PLAYER, moveX, this.entityPulling.motionY - 1, moveZ);
        /* End of the lines that manage the movement of the cart */

    }


    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        //Check if player is sneak.
        if (player.isSneaking()) {
            //if player is sneak and entity pulling equals null

            if (entityPulling == null) {
                //create list of all horse around in 5 blocks
                final List<EntityHorse> horseNear = player.getEntityWorld().getEntitiesWithinAABB(EntityHorse.class, new AxisAlignedBB(this.posX - 5, this.posY - 5, this.posZ - 5, this.posX + 5, this.posY + 5, this.posZ + 5));
                //if list is not null
                if (!horseNear.isEmpty()) {
                    //get first horse of the list
                    final EntityHorse horse = horseNear.get(0);

                    //change the speed of horse
                    horse.setAIMoveSpeed(0.0f);
                    this.setSpeed(horse, -0.49999998807D);

                    //teleport cart to horse
                    this.setPositionAndUpdate(horse.posX, horse.posY, horse.posZ);
                    //var entityPulling = horse
                    this.entityPulling = horse;
                    return true;
                }
                return true;
            }

            //Reset Horse speed
            this.setSpeed(entityPulling, +0.49999998807D);

            //If player is sneak and entityPulling is not null set entityPulling to null.
            entityPulling = null;

            return true;
        }
        //Check if server side is not equals to client. and chech if current player is not a passenger of this cart and check if player is not on another entity
        if (!player.world.isRemote && !player.isPassenger(this) && player.getRidingEntity() == null) {
            //player ride cart
            player.startRiding(this, true);
            return true;
        }

        return true;
    }


    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        //check is cart is invulnerable to this damage source and stop if it's true.
        if (this.isEntityInvulnerable(source))
            return false;

        //check if server server side is not equals to client and check is entity is not dead.
        if (!this.world.isRemote && !this.isDead) {
            if (source instanceof EntityDamageSourceIndirect && source.getTrueSource() != null && this.isPassenger(source.getTrueSource()))
                return false;
            //depop cart
            this.setDead();
            this.dropItem(Items.ITEM_CART, 1);
            this.setSpeed(entityPulling, +0.49999998807D);
            return true;

        }
        return true;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    @Override
    public void setNoGravity(boolean noGravity) {
        super.setNoGravity(false);
    }

    @Override
    protected boolean canFitPassenger(Entity passenger) {
        return this.getPassengers().size() < 7;
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        passenger.setPosition(this.posX, this.posY, this.posZ);
    }

    @Override
    public void updatePassenger(Entity passenger) {
        setPos();

        int i = 0;
        for (Entity entity : this.getPassengers()) {
            EntityPlayer player = (EntityPlayer) entity;

            //Split
            final String[] positions = entityPositions.get(i).split(":");
            //Parse to double (convert string (string = text) to double)
            final double xln = Double.parseDouble(positions[0]);
            final double zln = Double.parseDouble(positions[1]);
            //vec3d is used for the passenger keep the same positions depending on the orientation of the cart
            final Vec3d vec3d = new Vec3d(xln, 0.0D, zln).rotateYaw(-this.rotationYaw * 0.017453292F - ((float) Math.PI / 2F));
            //set position of passenger (passenger = player on cart)
            player.setPosition(this.posX + vec3d.x, this.posY + 0.8f, this.posZ + vec3d.z);
            i++;
        }
    }

    private void setSpeed(EntityHorse horse, double speed) {
        IAttributeInstance iAttributeInstance = horse.getAttributeMap().getAttributeInstanceByName("generic.movementSpeed");
        iAttributeInstance.getModifiers().clear();
        iAttributeInstance.applyModifier(new AttributeModifier("generic.movementSpeed", speed, 1).setSaved(true));
    }

    private void setPos() {
        entityPositions.clear();
        entityPositions.add("0.8:0.0");
        entityPositions.add("-0.2:0.8");
        entityPositions.add("-0.2:-0.8");
        entityPositions.add("-1.0:0.8");
        entityPositions.add("-1.0:-0.8");
        entityPositions.add("-2.0:0.8");
        entityPositions.add("-2.0:-0.8");
    }

    @Override
    protected boolean canBeRidden(Entity entityIn) {
        return true;
    }

}
