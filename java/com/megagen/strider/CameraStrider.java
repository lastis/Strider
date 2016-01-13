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
import net.minecraftforge.client.event.EntityViewRenderEvent;

public class CameraStrider {
	static float init_offset_x = 0;
	static float init_offset_y = -3F;
	static float init_offset_z = -4F;
	// static float entitiy_x;
	// static float entitiy_y;
	// static float entitiy_z;
	static float cameraX;
	static float cameraY;
	static float cameraZ;

	// static float camera_x_init;
	// static float camera_y_init;
	// static float camera_z_init;
	// private static EntityPlayerSP thePlayer;

	public static void onStriderEngage() {
		setCameraPos();
	}

	private static void setCameraPos() {
		// Get the current position of the camera relative to the player.
		// I think this position is gathered from previous call, so it will
		// correctly get
		// the camera position even with Strider applied transforms.
		EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
		cameraX = (float) (thePlayer.posX + ActiveRenderInfo.getPosition().xCoord);
		cameraY = (float) (thePlayer.posY + ActiveRenderInfo.getPosition().yCoord);
		cameraZ = (float) (thePlayer.posZ + ActiveRenderInfo.getPosition().zCoord);
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
//		float entitiyX = (float) e.entity.posX;
//		float entitiyY = (float) e.entity.posY;
//		float entitiyZ = (float) e.entity.posZ;
//		// Prevent rotates done by the mouse.
//		e.pitch = 0;
//		e.yaw = 180;
//
//		// Offset the camera so it is static.
//		// GlStateManager.translate(entitiyX-cameraX,entitiyY-cameraY,entitiyZ-cameraZ);
//		// Move the camera to its inital position.
////		GlStateManager.translate(init_offset_x, init_offset_y, init_offset_z);
//
//		if (Strider.DEBUG) {
//			if (Strider.tickPrintCnt == 0)
//				printCameraPosition();
//		}
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
