package com.megagen.strider.mcinterface;

import java.awt.AWTException;
import java.awt.Robot;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import com.megagen.strider.Strider;
import com.megagen.strider.camera.StriderCamera;
import com.megagen.strider.settings.StriderKeyBindings;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderHandEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EventCollection {
	private Minecraft mc;
	
	@SubscribeEvent
	public void worldLoad(WorldEvent.Load e){
		if (Strider.DEBUG){
			System.out.println("World Loaded");
		}
		mc = Minecraft.getMinecraft();
	}
	
//	@SubscribeEvent
//	public void EntitySpawnEvent(PlayerLoggedInEvent e) {
//	}
	
	@SubscribeEvent
	public void onCameraSetup(EntityViewRenderEvent.CameraSetup e){
		if (!Strider.initialized) return;
		Strider.onCameraSetup(e);
		Strider.camera.onCameraSetup(e);
	}
	
//	@SubscribeEvent
//	public void onTickPlayer(TickEvent.PlayerTickEvent e){
//		if (!Strider.initialized) return;
//
//	}
	
//	@SubscribeEvent
//	public void onPlayerUpdate(LivingUpdateEvent e){
//		if (!Strider.initialized) return;
//		if(e.entityLiving instanceof EntityPlayerSP && Strider.engaged){
//			
//		}
//	}
	
//	@SubscribeEvent
//	public void onPreRenderHand(RenderHandEvent e){
//		if (Strider.engaged){
////			EntityPlayerSP thePlayer = Minecraft.getMinecraft().thePlayer;
////			thePlayer.renderArmPitch = thePlayer.prevRenderArmPitch + 60;
////			thePlayer.renderArmYaw = thePlayer.prevRenderArmYaw + 120;
//		}
//	}
	
	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent e){
		if (!Strider.initialized) return;
		if (e.phase == Phase.START){
			if (Strider.engaged){
				Strider.ai.onRenderTick(e.renderTickTime);
			}
		}
		if (e.phase == Phase.END){
			if (Strider.engaged){
				
			}
		}
	}
	
	@SubscribeEvent
	public void renderLast(RenderWorldLastEvent e){
		if (!Strider.initialized) return;
		Strider.renderer.onRenderLast(e);
	}
	
	@SubscribeEvent
	public void onTickClient(TickEvent.ClientTickEvent e){
		if (!Strider.initialized) return;
		if (e.phase == Phase.START){
			Strider.onTick();
			Strider.settings.onTick();
			Strider.inputProcessor.onTick();
		}	
	}
	
	@SubscribeEvent
	public void onCraft(PlayerEvent.ItemCraftedEvent e){
		System.out.println("Crafting");
	}
	
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent e) {
        if(StriderKeyBindings.enableStrider.isPressed()) {
        	if (!Strider.initialized) Strider.initialize();
        	// Toggle between engaged and not.
        	if (Strider.engaged) Strider.disengage();
        	else Strider.engage();
        }
        if (!Strider.initialized) return;
        Strider.inputProcessor.onKeyInput(e);
    }
	
	@SubscribeEvent
	public void onRenderCursorBlock(DrawBlockHighlightEvent e){
		if (!Strider.initialized) return;
	}
	
}
