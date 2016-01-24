package com.megagen.strider;


import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBindingsStrider {

	public static KeyBinding enableStrider;
	public static KeyBinding playerForward;
	public static KeyBinding playerBack;
	public static KeyBinding playerRight;
	public static KeyBinding playerLeft;
	public static KeyBinding cameraLeft;
	public static KeyBinding cameraRight;
	public static KeyBinding cameraForward;
	public static KeyBinding cameraBack;
	
	public static void init(){
		enableStrider = new KeyBinding("Start",Keyboard.KEY_G,"Strider");
		playerForward = new KeyBinding("Forward",Keyboard.KEY_UP,"Strider");
		playerBack = new KeyBinding("Back",Keyboard.KEY_DOWN,"Strider");
		playerRight = new KeyBinding("Right",Keyboard.KEY_RIGHT,"Strider");
		playerLeft = new KeyBinding("Left",Keyboard.KEY_LEFT,"Strider");
		ClientRegistry.registerKeyBinding(enableStrider);
		ClientRegistry.registerKeyBinding(playerForward);
		ClientRegistry.registerKeyBinding(playerBack);
		ClientRegistry.registerKeyBinding(playerRight);
		ClientRegistry.registerKeyBinding(playerLeft);
	}

}
