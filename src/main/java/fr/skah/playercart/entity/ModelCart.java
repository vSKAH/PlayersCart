package fr.skah.playercart.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelCart extends ModelBase {

    private final ModelRenderer bone;

    public ModelCart() {
        textureWidth = 256;
        textureHeight = 256;

        bone = new ModelRenderer(this);
        bone.setRotationPoint(31.0F, 24.0F, 0.0F);
        bone.cubeList.add(new ModelBox(bone, 0, 72, -33.0F, -9.5F, -17.6688F, 1, 1, 36, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 110, 52, -69.0F, -9.5F, 7.8312F, 37, 1, 3, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 0, 12, -69.0F, -9.9F, -11.6688F, 2, 1, 4, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 12, 12, -69.0F, -9.9F, 7.3312F, 2, 1, 4, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 32, 110, -69.0F, -9.5F, -11.1688F, 37, 1, 3, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 38, 73, 5.5F, -9.5F, -17.9688F, 1, 1, 36, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 0, 34, -46.0F, -12.2F, 13.0F, 62, 1, 3, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 20, 9, -1.0F, -11.4F, -15.0F, 2, 3, 2, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 20, 9, -30.0F, -11.5F, 15.0F, 2, 3, 2, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 20, 9, -30.0F, -10.2F, -15.0F, 3, 1, 30, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 20, 9, -30.0F, -11.4F, -15.0F, 2, 3, 2, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 20, 9, -1.0F, -11.5F, 15.0F, 2, 3, 2, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 126, 126, -12.0F, -13.2F, -16.0F, 3, 1, 32, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 0, 30, -46.0F, -12.2F, -15.0F, 62, 1, 3, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 0, 0, -46.0F, -14.2F, -14.0F, 62, 2, 28, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 0, 42, -46.0F, -14.7F, -14.6F, 62, 3, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 103, 49, -33.0F, -30.4F, -14.5F, 49, 2, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 103, 59, -33.0F, -27.3F, -14.5F, 49, 2, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 76, 72, -33.0F, -30.4F, 14.7F, 49, 2, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 16, 17, -24.4F, -31.8F, 14.3F, 2, 8, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 0, 19, -3.4F, -31.8F, 14.3F, 2, 8, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 103, 46, -33.0F, -27.3F, 14.7F, 49, 2, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 76, 76, -33.0F, -23.2F, 14.7F, 49, 8, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 0, 0, -11.3F, -17.7F, 18.2F, 0, 1, 0, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 12, 72, -3.4F, -23.2F, 14.3F, 2, 8, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 22, 17, -24.4F, -31.8F, -14.9F, 2, 8, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 0, 72, -3.4F, -31.8F, -14.9F, 2, 8, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 6, 72, -24.4F, -23.2F, 14.3F, 2, 8, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 6, 19, -3.4F, -23.2F, -15.0F, 2, 8, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 20, 0, -24.4F, -23.2F, -15.0F, 2, 8, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 0, 38, -46.0F, -14.7F, 14.7F, 62, 3, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 76, 85, -33.0F, -23.2F, -14.6F, 49, 8, 1, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 0, 59, -33.0F, -20.5F, -13.6F, 48, 6, 7, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 0, 46, -33.0F, -20.5F, 7.7F, 48, 6, 7, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 84, 94, -43.0F, -20.5F, -13.6F, 9, 6, 28, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 0, 109, -34.0F, -30.0F, -14.6F, 1, 15, 30, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 20, 9, -1.0F, -10.2F, -15.0F, 3, 1, 30, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 30, 122, 13.0F, -13.2F, -16.0F, 3, 1, 32, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 150, 0, 15.0F, -14.5F, -14.7F, 1, 2, 30, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 106, 141, -1.5F, -17.0F, 17.0F, 17, 17, 0, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 106, 141, -39.8F, -17.0F, 17.0F, 17, 17, 0, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 106, 141, -1.5F, -17.0F, -17.0F, 17, 17, 0, 0.0F, false));
        bone.cubeList.add(new ModelBox(bone, 106, 141, -39.8F, -17.0F, -17.0F, 17, 17, 0, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float rotationYaw, float rotationPitch, float scale) {
        this.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, rotationYaw, rotationPitch, scale, entity);
        this.bone.render(scale);
    }


    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) {
        super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);
    }

}