package com.megagen.strider;

import java.lang.reflect.Field;

import com.megagen.strider.ai.StriderAI;
import com.megagen.strider.camera.StriderCamera;
import com.megagen.strider.mcinterface.EventCollection;
import com.megagen.strider.render.StriderRenderer;
import com.megagen.strider.settings.StriderEntityRenderer;
import com.megagen.strider.settings.StriderInputProcessor;
import com.megagen.strider.settings.StriderKeyBindings;
import com.megagen.strider.settings.StriderMovementInput;
import com.megagen.strider.settings.StriderSettings;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.EntityViewRenderEvent.CameraSetup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Strider.MODID, version = Strider.VERSION)
@SideOnly(Side.CLIENT)
public class Strider
{
    public static final String MODID = "strider";
    public static final String VERSION = "1.0";
    public static final boolean DEBUG = true;
    public static boolean engaged = false;
    public static boolean initialized = false;
    public static int tickPrintCnt = 0;
	public static StriderAI ai;
	public static StriderSettings settings;
	public static StriderInputProcessor inputProcessor;
	public static StriderRenderer renderer;
	public static StriderCamera camera;
	public static double playerX;
	public static double playerY;
	public static double playerZ;
	private static Minecraft mc;
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	// First access point.
    	StriderKeyBindings.init();
    	
    	EventCollection events = new EventCollection();
        MinecraftForge.EVENT_BUS.register(events);
        FMLCommonHandler.instance().bus().register(events);
    }
    
    public static void engage(){
    	engaged = true;
    	if (Strider.DEBUG){
			System.out.println("Engaging Strider");
		}
    	StriderCamera.onStriderEngage();
    	settings.onEngage();
    }
    
    public static void disengage(){
    	engaged = false;
    	if (Strider.DEBUG){
			System.out.println("Disabling Strider");
			settings.onDisengage();
		}
    }
    
    public static void onTick(){
		Strider.tickPrintCnt++;
		if (Strider.tickPrintCnt > 48) Strider.tickPrintCnt = 0;
    }
    
    public static void initialize(){
    	mc = Minecraft.getMinecraft();
    	if (mc.thePlayer == null) System.out.println("HELLOOOO");
    	if (ai == null) ai = new StriderAI(mc,mc.thePlayer);
    	if (settings == null) settings = new StriderSettings(mc);
    	if (inputProcessor == null) inputProcessor = new StriderInputProcessor(mc);
    	if (renderer == null) renderer = new StriderRenderer(mc);
    	if (camera == null) camera = new StriderCamera(mc);
    	initialized = true;
    }
    
	public static void onCameraSetup(CameraSetup e) {
		playerX = e.entity.lastTickPosX + (e.entity.posX - e.entity.lastTickPosX) * (double)e.renderPartialTicks;
		playerY = e.entity.lastTickPosY + (e.entity.posY - e.entity.lastTickPosY) * (double)e.renderPartialTicks;
		playerZ = e.entity.lastTickPosZ + (e.entity.posZ - e.entity.lastTickPosZ) * (double)e.renderPartialTicks;
	}
}