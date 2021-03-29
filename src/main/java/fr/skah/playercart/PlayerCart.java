package fr.skah.playercart;

import fr.skah.playercart.entity.EntityCart;
import fr.skah.playercart.item.Items;
import fr.skah.playercart.proxy.ClientProxy;
import fr.skah.playercart.proxy.ServerProxy;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import org.apache.logging.log4j.Logger;

@Mod(modid = PlayerCart.MODID, name = PlayerCart.NAME, version = PlayerCart.VERSION)
public class PlayerCart {

    public static final String MODID = "playercart";
    public static final String NAME = "Player Cart";
    public static final String VERSION = "0.1";

    @Mod.Instance("playercart")
    private static PlayerCart instance;

    @SidedProxy(clientSide = "fr.skah.playercart.proxy.ClientProxy", serverSide = "fr.skah.playercart.proxy.ServerProxy")
    private static ServerProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.registerRender();
        MinecraftForge.EVENT_BUS.register(new Items());
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        EntityRegistry.registerModEntity(new ResourceLocation(MODID, "cart"), EntityCart.class, "cart", 420, instance, 80, 1, false);
    }
}
