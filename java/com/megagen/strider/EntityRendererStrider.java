package com.megagen.strider;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class EntityRendererStrider extends EntityRenderer {

	private Minecraft mc;

	public EntityRendererStrider(Minecraft mcIn, IResourceManager p_i45076_2_) {
		super(mcIn, p_i45076_2_);
		this.mc = mcIn;
	}
	
	private void orientCamera(float p_78467_1_)
    {
		System.out.println("ThisWorks!");
        Entity entity = this.mc.getRenderViewEntity();
        float f1 = entity.getEyeHeight();
        double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)p_78467_1_;
        double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)p_78467_1_ + (double)f1;
        double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)p_78467_1_;

//        if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPlayerSleeping())
//        {
//            f1 = (float)((double)f1 + 1.0D);
//            GlStateManager.translate(0.0F, 0.3F, 0.0F);
//
//            if (!this.mc.gameSettings.debugCamEnable)
//            {
//                BlockPos blockpos = new BlockPos(entity);
//                IBlockState iblockstate = this.mc.theWorld.getBlockState(blockpos);
//                net.minecraftforge.client.ForgeHooksClient.orientBedCamera(this.mc.theWorld, blockpos, iblockstate, entity);
//
//                GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * p_78467_1_ + 180.0F, 0.0F, -1.0F, 0.0F);
//                GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * p_78467_1_, -1.0F, 0.0F, 0.0F);
//            }
//        }
//        else if (this.mc.gameSettings.thirdPersonView > 0)
//        {
//            double d3 = (double)(this.thirdPersonDistanceTemp + (this.thirdPersonDistance - this.thirdPersonDistanceTemp) * p_78467_1_);
//
//            if (this.mc.gameSettings.debugCamEnable)
//            {
//                GlStateManager.translate(0.0F, 0.0F, (float)(-d3));
//            }
//            else
//            {
//                float f2 = entity.rotationYaw;
//                float f3 = entity.rotationPitch;
//
//                if (this.mc.gameSettings.thirdPersonView == 2)
//                {
//                    f3 += 180.0F;
//                }
//
//                double d4 = (double)(-MathHelper.sin(f2 / 180.0F * (float)Math.PI) * MathHelper.cos(f3 / 180.0F * (float)Math.PI)) * d3;
//                double d5 = (double)(MathHelper.cos(f2 / 180.0F * (float)Math.PI) * MathHelper.cos(f3 / 180.0F * (float)Math.PI)) * d3;
//                double d6 = (double)(-MathHelper.sin(f3 / 180.0F * (float)Math.PI)) * d3;
//
//                for (int i = 0; i < 8; ++i)
//                {
//                    float f4 = (float)((i & 1) * 2 - 1);
//                    float f5 = (float)((i >> 1 & 1) * 2 - 1);
//                    float f6 = (float)((i >> 2 & 1) * 2 - 1);
//                    f4 *= 0.1F;
//                    f5 *= 0.1F;
//                    f6 *= 0.1F;
//                    MovingObjectPosition movingobjectposition = this.mc.theWorld.rayTraceBlocks(new Vec3(d0 + (double)f4, d1 + (double)f5, d2 + (double)f6), new Vec3(d0 - d4 + (double)f4 + (double)f6, d1 - d6 + (double)f5, d2 - d5 + (double)f6));
//
//                    if (movingobjectposition != null)
//                    {
//                        double d7 = movingobjectposition.hitVec.distanceTo(new Vec3(d0, d1, d2));
//
//                        if (d7 < d3)
//                        {
//                            d3 = d7;
//                        }
//                    }
//                }
//
//                if (this.mc.gameSettings.thirdPersonView == 2)
//                {
//                    GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
//                }
//
//                GlStateManager.rotate(entity.rotationPitch - f3, 1.0F, 0.0F, 0.0F);
//                GlStateManager.rotate(entity.rotationYaw - f2, 0.0F, 1.0F, 0.0F);
//                GlStateManager.translate(0.0F, 0.0F, (float)(-d3));
//                GlStateManager.rotate(f2 - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
//                GlStateManager.rotate(f3 - entity.rotationPitch, 1.0F, 0.0F, 0.0F);
//            }
//        }
//        else
//        {
//            GlStateManager.translate(0.0F, 0.0F, -0.1F);
//        }

        if (!this.mc.gameSettings.debugCamEnable)
        {
            float yaw = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * p_78467_1_ + 180.0F;
            float pitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * p_78467_1_;
            float roll = 0.0F;
            if (entity instanceof EntityAnimal)
            {
                EntityAnimal entityanimal = (EntityAnimal)entity;
                yaw = entityanimal.prevRotationYawHead + (entityanimal.rotationYawHead - entityanimal.prevRotationYawHead) * p_78467_1_ + 180.0F;
            }
            Block block = ActiveRenderInfo.getBlockAtEntityViewpoint(this.mc.theWorld, entity, p_78467_1_);
            net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup event = new net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup(this, entity, block, p_78467_1_, yaw, pitch, roll);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event);
            GlStateManager.rotate(event.roll, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(event.pitch, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(event.yaw, 0.0F, 1.0F, 0.0F);
        }

        GlStateManager.translate(0.0F, -f1, 0.0F);
        d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)p_78467_1_;
        d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)p_78467_1_ + (double)f1;
        d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)p_78467_1_;
//        this.cloudFog = this.mc.renderGlobal.hasCloudFog(d0, d1, d2, p_78467_1_);
    }

}
