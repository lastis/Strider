package com.megagen.strider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;

public class StriderAI {
	public float yawSpeed = 50;
	public float pitchSpeed = 50;
	public float angleThresh = 1;
	public float targetPitch = 0;
	public float targetYaw = 0;
	private Minecraft mc;
	private EntityPlayerSP thePlayer;
	public float pitch;
	public float yaw;
	

	public StriderAI(Minecraft mc, EntityPlayerSP thePlayer) 
	{
		this.mc = mc;
		this.thePlayer = thePlayer;
	}
	
	public void targetBlock(BlockPos pos)
	{
		double dx = pos.getX() + 0.5 - CameraStrider.entityX;
		double dy = pos.getY() + 0.5 - CameraStrider.entityY - thePlayer.eyeHeight;
		double dz = pos.getZ() + 0.5 - CameraStrider.entityZ;
		targetYaw = -(float) (Math.atan2(dx, dz)/Math.PI*180);
		targetPitch = -(float) (Math.atan2(dy,Math.sqrt(dz*dz+dx*dx))/Math.PI*180);
	}
	
	public void onTick(float dTick)
	{
		float yawApplied = 0;
		yaw = (float) (thePlayer.rotationYaw - 360 * Math.floor((thePlayer.rotationYaw+180)/360));
		float dYaw = yaw - targetYaw;
		if (Math.abs(dYaw) > angleThresh) {
			if (yaw - targetYaw > 0) 
			{
				yawApplied = -yawSpeed*dTick;
			}
			else
			{
				yawApplied = yawSpeed*dTick;
			}
		}
		float pitchApplied = 0;
		pitch = thePlayer.rotationPitch;
		if (Math.abs(pitch - targetPitch) > angleThresh) {
			if (pitch - targetPitch > 0) 
			{
				pitchApplied = pitchSpeed*dTick;
			}
			else
			{
				pitchApplied = -pitchSpeed*dTick;
			}
		}
		thePlayer.setAngles(yawApplied, pitchApplied);
	}
}
