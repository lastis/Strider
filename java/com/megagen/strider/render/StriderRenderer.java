package com.megagen.strider.render;

import org.lwjgl.opengl.GL11;

import com.megagen.strider.Strider;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.event.RenderWorldLastEvent;

public class StriderRenderer {
	private Minecraft mc;

	public StriderRenderer(Minecraft mc) {
		this.mc = mc;
	}

	public void onRenderLast(RenderWorldLastEvent e) {
		if (Strider.engaged && Strider.camera.mop != null){
			mc.renderGlobal.drawSelectionBox(mc.thePlayer, Strider.camera.mop, 0, (float) e.partialTicks);
			BlockPos blockpos = Strider.camera.mop.getBlockPos().offset(EnumFacing.UP);
			renderMarker(blockpos);
		}
	}

	public void renderMarker(BlockPos blockpos) {
		double d0 = Strider.playerX;
        double d1 = Strider.playerY;
        double d2 = Strider.playerZ;
        IBlockState blockState = mc.theWorld.getBlockState(blockpos);
        Block block = blockState.getBlock();
        AxisAlignedBB boundingBox = block.getSelectedBoundingBox(mc.theWorld, blockpos).offset(-d0, -d1, -d2);
        
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        
		if (mc.theWorld.getWorldBorder().contains(blockpos))
		{
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
			GlStateManager.color(0.0F, 1.0F, 0.0F, 0.5F);
			GL11.glLineWidth(5.0F);
			GlStateManager.disableTexture2D();
			GlStateManager.depthMask(true);
//			GlStateManager.clear(GL11.GL_DEPTH_BUFFER_BIT);
			
			worldrenderer.startDrawing(3);
			
			worldrenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
			worldrenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
			worldrenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
			worldrenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
			worldrenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
			tessellator.draw();
			worldrenderer.startDrawing(3);
			
			worldrenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
			worldrenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
			worldrenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
			worldrenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
			worldrenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
			tessellator.draw();
			worldrenderer.startDrawing(1);
			
			worldrenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
			worldrenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
			worldrenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
			worldrenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
			worldrenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
			worldrenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
			worldrenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
			worldrenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
			tessellator.draw();
			GlStateManager.enableTexture2D();
			GlStateManager.disableBlend();
		}
	}
}
