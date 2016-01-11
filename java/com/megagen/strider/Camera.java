package com.megagen.strider;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class Camera {
	static float offset_x = 0;
	static float offset_y = -2.0F;
	static float offset_z = -2;
	static float camera_x;
	static float camera_y;
	static float camera_z;
	private Minecraft mc;
	
	public static void printCameraPosition(){
		System.out.println("Camera: " //
                + (int) camera_x +","+(int)camera_y+","+(int)camera_z );
	}

	public static void engageStriderCamera(double posX, double posY, double posZ) {
		GlStateManager.translate(offset_x,offset_y,offset_z);	
		camera_x = (float) (posX + ActiveRenderInfo.getPosition().xCoord);
		camera_y = (float) (posY + ActiveRenderInfo.getPosition().yCoord);
		camera_z = (float) (posZ + ActiveRenderInfo.getPosition().zCoord);
		if (Strider.DEBUG){
				if (Strider.tickPrintCnt == 0) printCameraPosition();
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
	    float f = (float)((viewport.get(0) + viewport.get(2)) / 2);
        float f1 = (float)((viewport.get(1) + viewport.get(3)) / 2);
	    GLU.gluUnProject(f,f1, 0F, modelview, projection, viewport, position);
	    if (Strider.DEBUG){
	    	System.out.println("( " //
	                + (int) position.get(0)+" "+ (int) position.get(1)+" "+(int) position.get(2)
	                + ")");	
	    }
	    return position;
	}
}
