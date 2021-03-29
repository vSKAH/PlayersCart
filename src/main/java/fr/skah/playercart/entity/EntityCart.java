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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class EntityCart extends Entity {

    //Entity distance maintened between the cart and the entity pull. (ex : 2 = 2Blocks, 3.2F = 3,2 Blocks)
    private static final float ENTITY_SPACING = 2.0F;

    protected Entity pulling;

    private int steps;
    private double clientX;
    private double clientY;
    private double clientZ;
    private double clientYaw;
    private double clientPitch;

    private float deltaRotation;

    public EntityCart(World worldIn) {
        super(worldIn);
        this.preventEntitySpawning = true;
        this.stepHeight = 1.8F;
        this.setSize(4.0F, 2.2F);

    }

    @Override
    protected void entityInit() {

    }

    @Override
    public void onUpdate() {

        if (!world.isRemote) {
            this.prevPosX = this.posX;
            this.prevPosY = this.posY;
            this.prevPosZ = this.posZ;
        }

        this.setPositionNonDirty();
        super.onUpdate();
        this.tickLerp();

        if (!this.hasNoGravity()) this.motionY -= 0.04D;
        if (this.pulling != null) {

            /* Don't Edit this part. These lines manage the movement of the cart */
            final Vec3d targetVec = new Vec3d(this.pulling.posX, this.pulling.posY, this.pulling.posZ);
            this.rotationYaw = (float) Math.toDegrees(-Math.atan2(targetVec.x - this.posX, targetVec.z - this.posZ));

            final double lookX = MathHelper.sin(-this.rotationYaw * 0.017453292F - (float) Math.PI);
            final double lookZ = MathHelper.cos(-this.rotationYaw * 0.017453292F - (float) Math.PI);
            final double moveX = targetVec.x - this.posX + lookX * ENTITY_SPACING;
            final double moveZ = targetVec.z - this.posZ + lookZ * ENTITY_SPACING;

            if (!this.pulling.onGround && this.pulling.fallDistance == 0.0F) this.fallDistance = 0.0F;

            this.move(MoverType.SELF, moveX, this.pulling.motionY - 1, moveZ);
            /* End of the lines that manage the movement of the cart */
        }

    }


    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        //Check if player is sneak.
        if (player.isSneaking()) {
            //if player is sneak and entity pulling equals null

            if (pulling == null && !world.isRemote) {
                //create list of all horse around in 5 blocks
                final List<EntityHorse> horseNear = player.getEntityWorld().getEntitiesWithinAABB(EntityHorse.class, new AxisAlignedBB(this.posX - 5, this.posY - 5, this.posZ - 5, this.posX + 5, this.posY + 5, this.posZ + 5));
                //if list is not null
                if (!horseNear.isEmpty()) {
                    //get first horse of the list
                    final EntityHorse horse = horseNear.get(0);

                    //change the speed of horse
                    horse.setAIMoveSpeed(0.0f);
                    this.setSpeed(horse, -0.4D);

                    //teleport cart to horse
                    this.setPositionAndUpdate(horse.posX, horse.posY, horse.posZ);
                    //var entityPulling = horse
                    this.pulling = horse;
                    return true;
                }
                return true;
            }

            //If player is sneak and entityPulling is not null set entityPulling to null.
            pulling = null;
            return true;
        }
        //And chech if current player is not a passenger of this cart and check if player is not on another entity
        if (!player.isPassenger(this) && player.getRidingEntity() == null) {

            //Check if server side is not equals to client.
            if (!player.world.isRemote) {
                //Player ride cart
                player.startRiding(this, true);
                return true;
            }
        }
        return false;
    }


    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        if (passenger instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) passenger;
            EnumFacing facing = getHorizontalFacing();

            double offsetX = 0;
            double offsetZ = 0;

            for (int i = 0; i < 4; i++) {
                AxisAlignedBB playerbb = player.getEntityBoundingBox();
                double playerHitboxWidth = (playerbb.maxX - playerbb.minX) / 2;
                double carHitboxWidth = width / 2;

                double offset = playerHitboxWidth + carHitboxWidth + 0.2;

                offsetX += facing.getFrontOffsetX() * offset;
                offsetZ += facing.getFrontOffsetZ() * offset;

                AxisAlignedBB aabb = player.getEntityBoundingBox().offset(offsetX, 0, offsetZ);

                if (!world.checkBlockCollision(aabb)) {
                    break;
                }

                offsetX = 0;
                offsetZ = 0;
                facing = facing.rotateY();
            }

            player.setPositionAndUpdate(posX + offsetX, posY, posZ + offsetZ);
        }
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
            return true;

        }
        return true;
    }


    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {

    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {

    }

    @Override
    public boolean canFitPassenger(Entity passenger) {
        return this.getPassengers().size() < 7;
    }

    private void applyYawToEntity(Entity entityToUpdate) {
        entityToUpdate.setRenderYawOffset(this.rotationYaw);
        float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
        float f1 = MathHelper.clamp(f, -130.0F, 130.0F);
        entityToUpdate.prevRotationYaw += f1 - f;
        entityToUpdate.rotationYaw += f1 - f;
        entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
    }

    @SideOnly(Side.CLIENT)
    public void applyOrientationToEntity(Entity entityToUpdate) {
        this.applyYawToEntity(entityToUpdate);
    }

    private Vec3d[] getPlayerOffsets() {
        return new Vec3d[]{
                new Vec3d(0.8D, 0.8D, 0.0D),
                new Vec3d(-0.2D, 0.8D, 0.8D),
                new Vec3d(-0.2D, 0.8D, -0.8D),
                new Vec3d(-1.0D, 0.8D, 0.8D),
                new Vec3d(-1.0D, 0.8D, -0.8D),
                new Vec3d(-2.0D, 0.8D, 0.8D),
                new Vec3d(-0.2D, 0.8D, -0.8D)};
    }

    @Override
    public void updatePassenger(Entity passenger) {
        if (!isPassenger(passenger)) {
            return;
        }

        double front = 0.0F;
        double side = 0.0F;
        double height = 0.0F;

        List<Entity> passengers = getPassengers();

        if (passengers.size() > 0) {
            int i = passengers.indexOf(passenger);

            Vec3d offset = getPlayerOffsets()[i];
            front = offset.x;
            side = offset.z;
            height = offset.y;
        }

        Vec3d vec3d = (new Vec3d(front, height, side)).rotateYaw(-this.rotationYaw * 0.017453292F - ((float) Math.PI / 2F));
        passenger.setPosition(this.posX + vec3d.x, this.posY + vec3d.y, this.posZ + vec3d.z);
        passenger.rotationYaw += this.deltaRotation;
        passenger.setRotationYawHead(passenger.getRotationYawHead() + this.deltaRotation);
        this.applyYawToEntity(passenger);
    }

    private void tickLerp() {
        if (this.steps > 0 && !this.canPassengerSteer()) {
            double x = posX + (clientX - posX) / (double) steps;
            double y = posY + (clientY - posY) / (double) steps;
            double z = posZ + (clientZ - posZ) / (double) steps;
            double d3 = MathHelper.wrapDegrees(clientYaw - (double) rotationYaw);
            this.rotationYaw = (float) ((double) rotationYaw + d3 / (double) steps);
            this.rotationPitch = (float) ((double) rotationPitch + (clientPitch - (double) rotationPitch) / (double) steps);
            steps--;
            setPosition(x, y, z);
            setRotation(rotationYaw, rotationPitch);
        }
    }

    @SideOnly(Side.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
        this.clientX = x;
        this.clientY = y;
        this.clientZ = z;
        this.clientYaw = yaw;
        this.clientPitch = pitch;
        this.steps = 10;
    }


    @Override
    public boolean canBeRidden(Entity entityIn) {
        return true;
    }

    @Override
    public void setNoGravity(boolean noGravity) {
        super.setNoGravity(false);
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean canBePushed() {
        return true;
    }

    private void setSpeed(EntityHorse horse, double speed) {
        if (horse == null) return;
        IAttributeInstance iAttributeInstance = horse.getAttributeMap().getAttributeInstanceByName("generic.movementSpeed");
        iAttributeInstance.getModifiers().clear();
        iAttributeInstance.applyModifier(new AttributeModifier("generic.movementSpeed", speed, 1).setSaved(false));
    }

}
