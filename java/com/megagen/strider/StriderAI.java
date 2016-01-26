package com.megagen.strider;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;

public class StriderAI {
	public float yawSpeed = 50;
	public float pitchSpeed = 50;
	public float angleThresh = 2;
	public float targetPitch = 0;
	public float targetYaw = 0;
	public boolean facingTarget = false;
	public boolean standingOnTarget = false;
	private Minecraft mc;
	private EntityPlayerSP thePlayer;
	public float pitch;
	public float yaw;
	public float forward = 0;
	public float strafe = 0;
	public BlockPos targetBlock;
	public Command curCommand = Command.NONE;
	public boolean jump;

	public StriderAI(Minecraft mc, EntityPlayerSP thePlayer) 
	{
		this.mc = mc;
		this.thePlayer = thePlayer;
	}
	
	public void faceBlock(BlockPos pos)
	{
		targetBlock = pos;
		curCommand = Command.VIEWING;
	}
	
	public void walkToBlock(BlockPos pos)
	{
		targetBlock = pos;
		curCommand = Command.WALK_TO;
	}
	
	private void calculateAngles()
	{
		double dx = targetBlock.getX() + 0.5 - CameraStrider.entityX;
		double dy = targetBlock.getY() + 0.5 - CameraStrider.entityY - thePlayer.eyeHeight;
		double dz = targetBlock.getZ() + 0.5 - CameraStrider.entityZ;
		targetYaw = -(float) (Math.atan2(dx, dz)/Math.PI*180);
		targetPitch = -(float) (Math.atan2(dy,Math.sqrt(dz*dz+dx*dx))/Math.PI*180);
		if (curCommand == Command.WALK_TO) targetPitch = 0;
	}
	
	public void onTick(float dTick)
	{
		pitch = thePlayer.rotationPitch;
		yaw = (float) (thePlayer.rotationYaw - 360 * Math.floor((thePlayer.rotationYaw+180)/360));
		if (targetBlock == null) return;
		
		switch (curCommand) {
		case NONE:
			break;
		case VIEWING:
			if (facingTarget) {
				curCommand = Command.NONE;
			}
			calculateAngles();
			faceTarget(dTick);
			break;
		case WALK_TO:
			checkPosition();
			if (standingOnTarget) {
				curCommand = Command.NONE;
			}
			calculateAngles();
			faceTarget(dTick);
			if (thePlayer.isCollidedHorizontally){
				jump = true;
			}
			else{
				jump = false;
			}
			if (facingTarget){
				forward = 1;
			}
			else{
				forward = 0;
			}
			break;
		default:
			break;
		}
	}

	private void checkPosition() {
		if (Math.floor(CameraStrider.entityX) == targetBlock.getX() && 
				Math.floor(CameraStrider.entityY) == targetBlock.getY()+1 &&
				Math.floor(CameraStrider.entityZ) == targetBlock.getZ()){
			standingOnTarget = true;
		}
		else{
			standingOnTarget = false;
		}
		
	}

	private void faceTarget(float dTick) {
		boolean facing = true;
		
		float yawApplied = 0;
		
		float dYaw = (yaw - targetYaw);
		dYaw = (float) (dYaw - 360 * Math.floor((dYaw+180)/360));
		if (Math.abs(dYaw) > angleThresh) {
			if (dYaw > 0) 
			{
				yawApplied = -yawSpeed*dTick;
			}
			else
			{
				yawApplied = yawSpeed*dTick;
			}
			facing = false;
		}
		float pitchApplied = 0;
		if (Math.abs(pitch - targetPitch) > angleThresh) {
			if (pitch - targetPitch > 0) 
			{
				pitchApplied = pitchSpeed*dTick;
			}
			else
			{
				pitchApplied = -pitchSpeed*dTick;
			}
			facing = false;
		}
		thePlayer.setAngles(yawApplied, pitchApplied);		
		facingTarget = facing;
	}
}
