package com.megagen.strider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

@Mod(modid = Strider.MODID, version = Strider.VERSION)
public class Strider
{
    public static final String MODID = "strider";
    public static final String VERSION = "1.0";
    public static final boolean DEBUG = true;
    public static boolean engaged = false;
    public static int tickPrintCnt = 0;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	// First access point.
    	KeyBindings.init();
    	EventCollection events = new EventCollection();
        MinecraftForge.EVENT_BUS.register(events);
        FMLCommonHandler.instance().bus().register(events);
    }
    
    public static void engage(){
    	engaged = true;
    	if (Strider.DEBUG){
			System.out.println("Engaging Strider");
		}
    	
    }
    
    public static void disable(){
    	engaged = false;
    	if (Strider.DEBUG){
			System.out.println("Disabling Strider");
		}
    	Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
    }
}