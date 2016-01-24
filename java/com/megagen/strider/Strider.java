package com.megagen.strider;

import java.lang.reflect.Field;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovementInput;
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
	private static MovementInput oldInput;
	private static Minecraft mc;
	private static boolean oldViewBobbing;
	private static int oldThidPersonView;
	private static EntityRenderer oldRenderer;
	public static StriderAI striderAI;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	// First access point.
    	KeyBindingsStrider.init();
    	
    	EventCollection events = new EventCollection();
        MinecraftForge.EVENT_BUS.register(events);
        FMLCommonHandler.instance().bus().register(events);
    }
    
    public static void engage(){
    	engaged = true;
    	if (Strider.DEBUG){
			System.out.println("Engaging Strider");
		}
    	mc = Minecraft.getMinecraft();
    	
    	if (striderAI == null) striderAI = new StriderAI(mc,mc.thePlayer);
    	
    	oldInput = mc.thePlayer.movementInput;
    	mc.thePlayer.movementInput = new MovementInputStrider(mc.gameSettings);
    	oldRenderer = mc.entityRenderer;
    	mc.entityRenderer = new EntityRendererStrider(mc,mc.getResourceManager());
    	CameraStrider.onStriderEngage();
    }

	public static void storeTickSettings(){
    	oldThidPersonView = mc.gameSettings.thirdPersonView;
    }
    
    public static void applyTickSettings(){
    	// Applied every tick.
    	mc.gameSettings.thirdPersonView = 1;
    }
    
    public static void removeTickSettings(){
    	mc.gameSettings.thirdPersonView = oldThidPersonView;
    }
    
    public static void disable(){
    	engaged = false;
    	if (Strider.DEBUG){
			System.out.println("Disabling Strider");
		}
    	removeTickSettings();
    	mc.thePlayer.movementInput = oldInput;
    	mc.entityRenderer = oldRenderer;
    }
}