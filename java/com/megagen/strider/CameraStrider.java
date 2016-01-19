package com.megagen.strider;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.EntityViewRenderEvent;

public class CameraStrider {
	static float init_offset_x = 0;
	static float init_offset_y = 0F;
	static float init_offset_z = -2F;
	// static float entitiy_x;
	// static float entitiy_y;
	// static float entitiy_z;
	private static double prevOffsetZ = 0;
	private static Minecraft mc;
	public static double forward = 0;
	public static double strafe = 0;
	private static double prevCameraX;
	private static double prevCameraY;
	private static double prevCameraZ;
	static double cameraX;
	static double cameraY;
	static double cameraZ;

	// static float camera_x_init;
	// static float camera_y_init;
	// static float camera_z_init;
	// private static EntityPlayerSP thePlayer;

	public static void onStriderEngage() {
		setCameraPos();
	}
	
	private static void setPrevCameraPos() {
		// Get the current position of the camera relative to the player.
		// I think this position is gathered from previous call, so it will
		// correctly get
		// the camera position even with Strider applied transforms.
		EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
		prevCameraX = (float) (thePlayer.posX + ActiveRenderInfo.getPosition().xCoord);
		prevCameraY = (float) (thePlayer.posY + ActiveRenderInfo.getPosition().yCoord);
		prevCameraZ = (float) (thePlayer.posZ + ActiveRenderInfo.getPosition().zCoord);
	}

	public static void setCameraPos() {
		// Get the current position of the camera relative to the player.
		// I think this position is gathered from previous call, so it will
		// correctly get
		// the camera position even with Strider applied transforms.
		EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
		cameraX = (float) (thePlayer.posX + ActiveRenderInfo.getPosition().xCoord);
		cameraY = (float) (thePlayer.posY + ActiveRenderInfo.getPosition().yCoord);
		cameraZ = (float) (thePlayer.posZ + ActiveRenderInfo.getPosition().zCoord);
	}
	
	private static void undoCameraChange(Entity entity, double p_78467_1_){

        mc = Minecraft.getMinecraft();
		float f1 = entity.getEyeHeight();
		float f2 = entity.rotationYaw;
        float f3 = entity.rotationPitch;
		
		double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)p_78467_1_;
        double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)p_78467_1_ + (double)f1;
        double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)p_78467_1_;
        double d3 = 4;

        double d4 = (double)(-MathHelper.sin(f2 / 180.0F * (float)Math.PI) * MathHelper.cos(f3 / 180.0F * (float)Math.PI)) * d3;
        double d5 = (double)(MathHelper.cos(f2 / 180.0F * (float)Math.PI) * MathHelper.cos(f3 / 180.0F * (float)Math.PI)) * d3;
        double d6 = (double)(-MathHelper.sin(f3 / 180.0F * (float)Math.PI)) * d3;

        for (int i = 0; i < 8; ++i)
        {
            float f4 = (float)((i & 1) * 2 - 1);
            float f5 = (float)((i >> 1 & 1) * 2 - 1);
            float f6 = (float)((i >> 2 & 1) * 2 - 1);
            f4 *= 0.1F;
            f5 *= 0.1F;
            f6 *= 0.1F;
            MovingObjectPosition movingobjectposition = mc.theWorld.rayTraceBlocks(new Vec3(d0 + (double)f4, d1 + (double)f5, d2 + (double)f6), new Vec3(d0 - d4 + (double)f4 + (double)f6, d1 - d6 + (double)f5, d2 - d5 + (double)f6));

            if (movingobjectposition != null)
            {
                double d7 = movingobjectposition.hitVec.distanceTo(new Vec3(d0, d1, d2));

                if (d7 < d3)
                {
                    d3 = d7;
                }
            }
        }
        GlStateManager.translate(0.0F, 0.0F, (float)(d3));
	}

	public static void printCameraPosition() {
		EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
		System.out
				.println("Camera: " //
						+ (int) (thePlayer.posX + ActiveRenderInfo
								.getPosition().xCoord)
						+ ","
						+ (int) (thePlayer.posY + ActiveRenderInfo
								.getPosition().yCoord)
						+ ","
						+ (int) (thePlayer.posZ + ActiveRenderInfo
								.getPosition().zCoord));
	}

	public static void update(EntityViewRenderEvent.CameraSetup e) {
		undoCameraChange(e.entity,e.renderPartialTicks);
		double entityX = e.entity.lastTickPosX + (e.entity.posX - e.entity.lastTickPosX) * (double)e.renderPartialTicks;
        double entityY = e.entity.lastTickPosY + (e.entity.posY - e.entity.lastTickPosY) * (double)e.renderPartialTicks;
        double entityZ = e.entity.lastTickPosZ + (e.entity.posZ - e.entity.lastTickPosZ) * (double)e.renderPartialTicks;
		float pitch = e.pitch;
		float yaw = e.yaw;
 		// Apply mouse rotation.
		GlStateManager.rotate(pitch, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(yaw, 0.0F, 1.0F, 0.0F);
        // Apply the strafe and forward to the camera position.
        cameraX += strafe*e.renderPartialTicks;
        cameraZ += forward*e.renderPartialTicks;
        // Move the camera to its initial position.
		GlStateManager.translate(init_offset_x, init_offset_y, init_offset_z);
 		// Offset the camera so it is static.
 		GlStateManager.translate(entityX,entityY,entityZ);
        GlStateManager.translate(
        		-(prevCameraX+(cameraX-prevCameraX)*e.renderPartialTicks),
        		-(prevCameraY+(cameraY-prevCameraY)*e.renderPartialTicks),
        		-(prevCameraZ+(cameraZ-prevCameraZ)*e.renderPartialTicks));
        prevCameraX = cameraX;
        prevCameraY = cameraY;
        prevCameraZ = cameraZ;
        // Prevent rotation done by the mouse.
        e.pitch = 0;
		e.yaw = 0;

		if (Strider.DEBUG && Strider.tickPrintCnt == 0) {
			printCameraPosition();
		}
		
	}

	private FloatBuffer getMousePosition() {
		IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
		FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
		FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
		FloatBuffer winZ = GLAllocation.createDirectFloatBuffer(1);
		FloatBuffer position = GLAllocation.createDirectFloatBuffer(3);
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
		GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
		float f = (float) ((viewport.get(0) + viewport.get(2)) / 2);
		float f1 = (float) ((viewport.get(1) + viewport.get(3)) / 2);
		GLU.gluUnProject(f, f1, 0F, modelview, projection, viewport, position);
		if (Strider.DEBUG) {
			System.out.println("( " //
					+ (int) position.get(0) + " " + (int) position.get(1)
					+ " "
					+ (int) position.get(2) + ")");
		}
		return position;
	}
}
