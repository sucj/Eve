package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;
import net.minecraft.src.Config;
import net.optifine.shaders.Shaders;

public abstract class RenderLiving<T extends EntityLiving> extends RendererLivingEntity<T> {
    public RenderLiving(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
        super(rendermanagerIn, modelbaseIn, shadowsizeIn);
    }

    protected boolean canRenderName(T entity) {
        return super.canRenderName(entity) && (entity.getAlwaysRenderNameTagForRender() || entity.hasCustomName() && entity == this.renderManager.pointedEntity);
    }

    public boolean shouldRender(T livingEntity, ICamera camera, double camX, double camY, double camZ) {
        if (super.shouldRender(livingEntity, camera, camX, camY, camZ)) {
            return true;
        } else if (livingEntity.getLeashed() && livingEntity.getLeashedToEntity() != null) {
            Entity entity = livingEntity.getLeashedToEntity();
            return camera.isBoundingBoxInFrustum(entity.getEntityBoundingBox());
        } else {
            return false;
        }
    }

    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
        this.renderLeash(entity, x, y, z, entityYaw, partialTicks);
    }

    public void setLightmap(T entityLivingIn, float partialTicks) {
        int i = entityLivingIn.getBrightnessForRender(partialTicks);
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
    }

    private double interpolateValue(double start, double end, double pct) {
        return start + (end - start) * pct;
    }

    protected void renderLeash(T entityLivingIn, double x, double y, double z, float entityYaw, float partialTicks) {
        if (!Config.isShaders() || !Shaders.isShadowPass) {
            Entity entity = entityLivingIn.getLeashedToEntity();

            if (entity != null) {
                y = y - (1.6D - entityLivingIn.height) * 0.5D;
                Tessellator tessellator = Tessellator.getInstance();
                WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                double d0 = this.interpolateValue(entity.prevRotationYaw, entity.rotationYaw, (partialTicks * 0.5F)) * 0.01745329238474369D;
                double d1 = this.interpolateValue(entity.prevRotationPitch, entity.rotationPitch, (partialTicks * 0.5F)) * 0.01745329238474369D;
                double d2 = Math.cos(d0);
                double d3 = Math.sin(d0);
                double d4 = Math.sin(d1);

                if (entity instanceof EntityHanging) {
                    d2 = 0.0D;
                    d3 = 0.0D;
                    d4 = -1.0D;
                }

                double d5 = Math.cos(d1);
                double d6 = this.interpolateValue(entity.prevPosX, entity.posX, partialTicks) - d2 * 0.7D - d3 * 0.5D * d5;
                double d7 = this.interpolateValue(entity.prevPosY + entity.getEyeHeight() * 0.7D, entity.posY + entity.getEyeHeight() * 0.7D, partialTicks) - d4 * 0.5D - 0.25D;
                double d8 = this.interpolateValue(entity.prevPosZ, entity.posZ, partialTicks) - d3 * 0.7D + d2 * 0.5D * d5;
                double d9 = this.interpolateValue(entityLivingIn.prevRenderYawOffset, entityLivingIn.renderYawOffset, partialTicks) * 0.01745329238474369D + (Math.PI / 2.0D);
                d2 = Math.cos(d9) * entityLivingIn.width * 0.4D;
                d3 = Math.sin(d9) * entityLivingIn.width * 0.4D;
                double d10 = this.interpolateValue(entityLivingIn.prevPosX, entityLivingIn.posX, partialTicks) + d2;
                double d11 = this.interpolateValue(entityLivingIn.prevPosY, entityLivingIn.posY, partialTicks);
                double d12 = this.interpolateValue(entityLivingIn.prevPosZ, entityLivingIn.posZ, partialTicks) + d3;
                x = x + d2;
                z = z + d3;
                double d13 = ((float) (d6 - d10));
                double d14 = ((float) (d7 - d11));
                double d15 = ((float) (d8 - d12));
                GlStateManager.disableTexture2D();
                GlStateManager.disableLighting();
                GlStateManager.disableCull();

                if (Config.isShaders()) {
                    Shaders.beginLeash();
                }

                int i = 24;
                double d16 = 0.025D;
                worldrenderer.begin(5, DefaultVertexFormats.POSITION_COLOR);

                for (int j = 0; j <= 24; ++j) {
                    float f = 0.5F;
                    float f1 = 0.4F;
                    float f2 = 0.3F;

                    if (j % 2 == 0) {
                        f *= 0.7F;
                        f1 *= 0.7F;
                        f2 *= 0.7F;
                    }

                    float f3 = j / 24.0F;
                    worldrenderer.pos(x + d13 * f3 + 0.0D, y + d14 * (f3 * f3 + f3) * 0.5D + ((24.0F - j) / 18.0F + 0.125F), z + d15 * f3).color(f, f1, f2, 1.0F).endVertex();
                    worldrenderer.pos(x + d13 * f3 + 0.025D, y + d14 * (f3 * f3 + f3) * 0.5D + ((24.0F - j) / 18.0F + 0.125F) + 0.025D, z + d15 * f3).color(f, f1, f2, 1.0F).endVertex();
                }

                tessellator.draw();
                worldrenderer.begin(5, DefaultVertexFormats.POSITION_COLOR);

                for (int k = 0; k <= 24; ++k) {
                    float f4 = 0.5F;
                    float f5 = 0.4F;
                    float f6 = 0.3F;

                    if (k % 2 == 0) {
                        f4 *= 0.7F;
                        f5 *= 0.7F;
                        f6 *= 0.7F;
                    }

                    float f7 = k / 24.0F;
                    worldrenderer.pos(x + d13 * f7 + 0.0D, y + d14 * (f7 * f7 + f7) * 0.5D + ((24.0F - k) / 18.0F + 0.125F) + 0.025D, z + d15 * f7).color(f4, f5, f6, 1.0F).endVertex();
                    worldrenderer.pos(x + d13 * f7 + 0.025D, y + d14 * (f7 * f7 + f7) * 0.5D + ((24.0F - k) / 18.0F + 0.125F), z + d15 * f7 + 0.025D).color(f4, f5, f6, 1.0F).endVertex();
                }

                tessellator.draw();

                if (Config.isShaders()) {
                    Shaders.endLeash();
                }

                GlStateManager.enableLighting();
                GlStateManager.enableTexture2D();
                GlStateManager.enableCull();
            }
        }
    }
}
