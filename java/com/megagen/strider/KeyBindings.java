package com.megagen.strider;


import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class KeyBindings {

	public static KeyBinding enableStrider;
	
	public static void init(){
		enableStrider = new KeyBinding("Start",Keyboard.KEY_G,"Strider");
		ClientRegistry.registerKeyBinding(enableStrider);
	}

}
