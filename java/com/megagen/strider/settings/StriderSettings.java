package com.megagen.strider.settings;

import com.megagen.strider.Strider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.util.MovementInput;

public class StriderSettings {
	private MovementInput oldInput;
	private Minecraft mc;
	private boolean oldViewBobbing;
	private int oldThidPersonView;
	private EntityRenderer oldRenderer;
	private StriderEntityRenderer striderEntityRender;
	private StriderMovementInput striderMovementInput;
	
	public StriderSettings(Minecraft mc) {
		this.mc = mc;
		striderEntityRender = new StriderEntityRenderer(mc,mc.getResourceManager());
		striderMovementInput = new StriderMovementInput(mc.gameSettings);
	}
	
	public void onEngage(){
		storeOldSettings();
    	mc.thePlayer.movementInput = striderMovementInput;
    	mc.entityRenderer = striderEntityRender;
	}
	
	public void onDisengage(){
		restoreSettings();
	}
	
	public void onTick(){
		if (Strider.engaged) {
			applyTickSettings();
		}
	}
	
	public void storeOldSettings(){
		oldInput = mc.thePlayer.movementInput;
		oldRenderer = mc.entityRenderer; 
    	oldThidPersonView = mc.gameSettings.thirdPersonView;
    }
    
    public void applyTickSettings(){
    	mc.gameSettings.thirdPersonView = 1;
    }
    
    public void restoreSettings(){
    	mc.thePlayer.movementInput = oldInput;
    	mc.entityRenderer = oldRenderer;
    	mc.gameSettings.thirdPersonView = oldThidPersonView;
    }
}
