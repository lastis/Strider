package com.megagen.strider;

import java.awt.AWTException;
import java.awt.Robot;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class EventCollection {
	private Minecraft mc;
	
	@SubscribeEvent
	public void worldLoad(WorldEvent.Load e){
		if (Strider.DEBUG){
			System.out.println("World Loaded");
		}
		mc = Minecraft.getMinecraft();
	}
	
	@SubscribeEvent
	public void onCameraSetup(EntityViewRenderEvent.CameraSetup e){
		if (Strider.engaged){
			CameraStrider.update(e);
		}
	}
	
	@SubscribeEvent
	public void onTickPlayer(TickEvent.PlayerTickEvent e){
		Strider.tickPrintCnt++;
		if (Strider.tickPrintCnt > 24*2)Strider.tickPrintCnt = 0;
	}
	
	@SubscribeEvent
	public void onTickRender(TickEvent.RenderTickEvent e){
		if (e.phase == Phase.START){
			if (Strider.engaged){
				// Can disable some mouse input by consuming these inputs.
//				Mouse.getDX();
//				Mouse.getDY();
//				Mouse.getDWheel();
			}
		}
	}
	
	@SubscribeEvent
	public void onTickClient(TickEvent.ClientTickEvent e){
		if (e.phase == Phase.START){
			if (Strider.engaged){
				Strider.applyTickSettings();
				
				// Consume and handle all mouse (button) input events.
//				while (Mouse.next()){
//					
//				}
				// Post new events.
				if (Strider.tickPrintCnt == 0){
//					try {
//						Robot r = new Robot();
//						r.mousePress(java.awt.event.InputEvent.BUTTON2_MASK);
//						r.mouseRelease(java.awt.event.InputEvent.BUTTON2_MASK);
//						// Make the system catch the new events. 
//						Display.update();
//					} catch (AWTException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
				}
			}	
		}	
	}
	
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent e) {
        if(KeyBindingsStrider.enableStrider.isPressed()) {
        	if (Strider.engaged) 
        		Strider.disable();
        	else
        		Strider.engage();
        }
    }
}
