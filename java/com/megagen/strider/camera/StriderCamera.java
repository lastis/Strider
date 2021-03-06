package com.megagen.strider.camera;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.megagen.strider.Strider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.EntityViewRenderEvent;

public class StriderCamera {
	static float init_offset_x = 0;
	static float init_offset_y = 0F;
	static float init_offset_z = -2F;
	private Minecraft mc;
	public double forward = 0;
	public double strafe = 0;
	public double up = 0;
	public double prevCameraX;
	public double prevCameraY;
	public double prevCameraZ;
	static double cameraX;
	static double cameraY;
	static double cameraZ;
	static double cameraSpeed = 0.4;
	public int viewType = 0;
	public MovingObjectPosition mop;

	// static float camera_x_init;
	// static float camera_y_init;
	// static float camera_z_init;
	// private static EntityPlayerSP thePlayer;
	public StriderCamera(Minecraft mc) {
		this.mc = mc;
	}

	public static void onStriderEngage() {
		setCameraPos();
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
	
	public void printPlayerPosition(){
		System.out.println("Player pos: ("+Strider.playerX+","+Strider.playerY+","+Strider.playerZ+")");
	}
	
	public void printCameraPosition() {
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

	public void onCameraSetup(EntityViewRenderEvent.CameraSetup e) {
		if (!Strider.engaged) return;
		// Seems to notify some renderer to render chunks.
		mc.renderGlobal.setDisplayListEntitiesDirty();
		mc = Minecraft.getMinecraft();
		float pitch = e.pitch;
		float yaw = e.yaw;
 		// Apply mouse rotation.
		GlStateManager.rotate(pitch, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(yaw, 0.0F, 1.0F, 0.0F);
    	// Move the camera to its initial position.
		GlStateManager.translate(init_offset_x, init_offset_y, init_offset_z);
 		// Offset the camera so it is static.
 		GlStateManager.translate(Strider.playerX,Strider.playerY,Strider.playerZ);
 		// Apply the strafe, forward and up to the camera position.
        if (strafe != 0 || forward != 0 || up != 0){
        	// Normalize strafe, forward and up.
            double length = MathHelper.sqrt_double(strafe*strafe + forward*forward + up*up);
            double s = strafe/length*cameraSpeed;
            double f = forward/length*cameraSpeed;
            cameraX += - s*e.renderPartialTicks*MathHelper.cos(yaw/180F*(float)Math.PI)
            		+ f*e.renderPartialTicks*MathHelper.sin(yaw/180F*(float)Math.PI);
            cameraZ += - f*e.renderPartialTicks*MathHelper.cos(yaw/180F*(float)Math.PI)
            		- s*e.renderPartialTicks*MathHelper.sin(yaw/180F*(float)Math.PI);	
            cameraY += up/length*cameraSpeed;
        }
 		// Move the camera according to user input.
        GlStateManager.translate(
        		-(prevCameraX+(cameraX-prevCameraX)*e.renderPartialTicks),
        		-(prevCameraY+(cameraY-prevCameraY)*e.renderPartialTicks),
        		-(prevCameraZ+(cameraZ-prevCameraZ)*e.renderPartialTicks));
        prevCameraX = cameraX;
        prevCameraY = cameraY;
        prevCameraZ = cameraZ;
        
        mop = rayTraceMouse();

		if (Strider.DEBUG && Strider.tickPrintCnt == 0) {
			printCameraPosition();
		}
		
	}

	private MovingObjectPosition rayTraceMouse() {
		IntBuffer viewport = GLAllocation.createDirectIntBuffer(16);
		FloatBuffer modelview = GLAllocation.createDirectFloatBuffer(16);
		FloatBuffer projection = GLAllocation.createDirectFloatBuffer(16);
		FloatBuffer winZ = GLAllocation.createDirectFloatBuffer(1);
		FloatBuffer pos = GLAllocation.createDirectFloatBuffer(3);
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelview);
		GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
		GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
		float f = (float) ((viewport.get(0) + viewport.get(2)) / 2);
		float f1 = (float) ((viewport.get(1) + viewport.get(3)) / 2);
		GLU.gluUnProject(f, f1, 0F, modelview, projection, viewport, pos);
		Vec3 pos0 = new Vec3(pos.get(0)+Strider.playerX,pos.get(1)+Strider.playerY,pos.get(2)+Strider.playerZ);
		GLU.gluUnProject(f, f1, 1F, modelview, projection, viewport, pos);
		Vec3 pos1 = new Vec3(pos.get(0)+Strider.playerX,pos.get(1)+Strider.playerY,pos.get(2)+Strider.playerZ);
		
		return mc.theWorld.rayTraceBlocks(pos0, pos1);
	}
}
