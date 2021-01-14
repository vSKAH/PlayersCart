package fr.skah.playercart.proxy;

import fr.skah.playercart.entity.EntityCart;
import fr.skah.playercart.entity.RenderCart;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends ServerProxy {


    @Override
    public void registerRender() {
        RenderingRegistry.registerEntityRenderingHandler(EntityCart.class, RenderCart::new);
    }
}
