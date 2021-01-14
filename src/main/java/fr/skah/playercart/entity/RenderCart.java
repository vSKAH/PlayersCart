package fr.skah.playercart.entity;

import fr.skah.playercart.PlayerCart;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderCart extends Render<EntityCart> {

    private final ResourceLocation texture = new ResourceLocation(PlayerCart.MODID + ":textures/entity/cart.png");
    private static final ModelBase MODEL = new ModelCart();

    public RenderCart(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityCart entity) {
        return texture;
    }

    @Override
    public void doRender(EntityCart entity, double x, double y, double z, float entityYaw, float partialTicks) {

        GL11.glPushMatrix();
        GlStateManager.translate(x, y + 2.4D, z);
        GlStateManager.rotate(270 - entityYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        this.bindEntityTexture(entity);
        MODEL.render(entity, partialTicks, 0.0F, 0.0F, 0.0F, 0.0F, 0.1F);
        GL11.glPopMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
}
