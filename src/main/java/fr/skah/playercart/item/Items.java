package fr.skah.playercart.item;

import fr.skah.playercart.PlayerCart;
import fr.skah.playercart.item.items.ItemCart;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = PlayerCart.MODID)
public class Items
{

    public static final Item ITEM_CART = new ItemCart();

    public static void setItemName(Item item, String name)
    {
        item.setRegistryName(PlayerCart.MODID, name).setUnlocalizedName(PlayerCart.MODID + "." + name);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerItemModels(ModelRegistryEvent event)
    {
        registerModel(ITEM_CART, 0);
    }

    @SideOnly(Side.CLIENT)
    public static void registerModel(Item item, int metadata)
    {
        if (metadata < 0) metadata = 0;
        String resourceName = item.getUnlocalizedName().substring(5).replace('.', ':');
        if (metadata > 0) resourceName += "_m" + String.valueOf(metadata);

        ModelLoader.setCustomModelResourceLocation(item, metadata, new ModelResourceLocation(resourceName, "inventory"));
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ITEM_CART);
    }

}