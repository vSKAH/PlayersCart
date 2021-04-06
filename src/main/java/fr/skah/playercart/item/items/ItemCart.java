package fr.skah.playercart.item.items;

import fr.skah.playercart.entity.EntityCart;
import fr.skah.playercart.item.Items;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemCart extends Item {
    public static final String ITEM_NAME = "item_cart";

    public ItemCart() {
        super();
        Items.setItemName(this, ITEM_NAME);
        setCreativeTab(CreativeTabs.TRANSPORTATION);
        setMaxStackSize(1);
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        final Vec3d vec3d = new Vec3d(player.posX, player.posY + player.getEyeHeight(), player.posZ);
        final Vec3d lookVec = player.getLookVec();
        final Vec3d vec3d1 = new Vec3d(lookVec.x * 5.0 + vec3d.x, lookVec.y * 5.0 + vec3d.y, lookVec.z * 5.0 + vec3d.z);
        final RayTraceResult result = world.rayTraceBlocks(vec3d, vec3d1, false);

        if (!world.isRemote && result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
            final EntityCart cart = new EntityCart(world);
            final ItemStack itemStack = player.getHeldItem(hand);
            itemStack.shrink(1);
            player.inventory.markDirty();
            cart.setPositionAndUpdate(result.hitVec.x, result.hitVec.y, result.hitVec.z);
            world.spawnEntity(cart);
            return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
        }
        return new ActionResult<>(EnumActionResult.FAIL, player.getHeldItem(hand));
    }

}
