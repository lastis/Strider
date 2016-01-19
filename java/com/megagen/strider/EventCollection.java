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
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
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
	public void onPlayerUpdate(LivingUpdateEvent e){
		if(e.entityLiving instanceof EntityPlayerSP && Strider.engaged){
//			EntityPlayerSP thePlayer = (EntityPlayerSP) e.entityLiving;
//			thePlayer.renderArmPitch = thePlayer.renderArmPitch + 10;
		}
	}
	
//	@SubscribeEvent
//	public void onPreRenderHand(RenderHandEvent e){
//		if (Strider.engaged){
////			EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
////			thePlayer.renderArmPitch = thePlayer.prevRenderArmPitch + 60;
////			thePlayer.renderArmYaw = thePlayer.prevRenderArmYaw + 120;
//		}
//	}
	
	@SubscribeEvent
	public void onTickRender(TickEvent.RenderTickEvent e){
		if (e.phase == Phase.START){
			if (Strider.engaged){
				CameraStrider.forward = 0;
				CameraStrider.strafe = 0;
		        if (mc.gameSettings.keyBindForward.isKeyDown()){
		        	CameraStrider.forward++;
		        }
		        if (mc.gameSettings.keyBindBack.isKeyDown()){
		        	CameraStrider.forward--;
		        }
		        if (mc.gameSettings.keyBindLeft.isKeyDown()){
		        	CameraStrider.strafe++;
		        }
		        if (mc.gameSettings.keyBindRight.isKeyDown()){
		        	CameraStrider.strafe--;
		        }
//				EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
//				thePlayer.renderArmPitch = thePlayer.prevRenderArmPitch + 60;
//				thePlayer.renderArmYaw = thePlayer.prevRenderArmYaw + 60;
				// Can disable some mouse input by consuming these inputs.
//				Mouse.getDX();
//				Mouse.getDY();
//				Mouse.getDWheel();
			}
		}
		if (e.phase == Phase.END){
			if (Strider.engaged){
				
			}
		}
	}
	
	@SubscribeEvent
	public void renderLast(RenderWorldLastEvent event){
		
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
        if (!Strider.engaged) return;
    }
}
