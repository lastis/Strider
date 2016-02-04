package com.megagen.strider.settings;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Mouse;

import com.megagen.strider.Strider;
import com.megagen.strider.render.StriderCamera;

public class StriderInputProcessor {
	private Minecraft mc;

	public StriderInputProcessor(Minecraft mc) {
		this.mc = mc;
	}

	public void onTick() {
		if (Strider.engaged){
			Strider.camera.forward = 0;
			Strider.camera.strafe = 0;
			Strider.camera.up = 0;	
	        if (mc.gameSettings.keyBindForward.isKeyDown()){
	        	Strider.camera.forward++;
	        }
	        if (mc.gameSettings.keyBindBack.isKeyDown()){
	        	Strider.camera.forward--;
	        }
	        if (mc.gameSettings.keyBindLeft.isKeyDown()){
	        	Strider.camera.strafe++;
	        }
	        if (mc.gameSettings.keyBindRight.isKeyDown()){
	        	Strider.camera.strafe--;
	        }
	        if (mc.gameSettings.keyBindJump.isKeyDown()){
	        	Strider.camera.up++;
	        }
	        if (mc.gameSettings.keyBindSneak.isKeyDown()){
	        	Strider.camera.up--;
	        }
			
			if (Mouse.isButtonDown(0))
			{
				if (Strider.DEBUG) {
					System.out.println(Strider.camera.mop.toString());
				}
				Strider.ai.walkToBlock(Strider.camera.mop.getBlockPos());
			}
			if (Mouse.isButtonDown(1))
			{
				if (Strider.DEBUG) {
					System.out.println(Strider.camera.mop.toString());
				}
				Strider.ai.faceBlock(Strider.camera.mop.getBlockPos());
			}
			while (Mouse.next()) {
                int i = Mouse.getEventButton();
                // Is this important?
//                KeyBinding.setKeyBindState(i - 100, Mouse.getEventButtonState());
                
                // - 98 for first mouse, -99 for right click.
//                if (Mouse.getEventButtonState())
//                {
//                    KeyBinding.onTick(i - 100);
//                }
			}
		}
	}
}
