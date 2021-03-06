package com.megagen.strider.settings;



import com.megagen.strider.Strider;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInput;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class StriderMovementInput extends MovementInput
{
    private final GameSettings gameSettings;
    private static final String __OBFID = "CL_00000937";

    public StriderMovementInput(GameSettings gameSettingsIn)
    {
        this.gameSettings = gameSettingsIn;
    }

    public void updatePlayerMoveState()
    {
    	this.moveForward = Strider.ai.forward;
    	this.moveStrafe = Strider.ai.strafe;
    	this.jump = Strider.ai.jump;
//        this.moveStrafe = 0.0F;
//        this.moveForward = 0.0F;
//
//        if (KeyBindingsStrider.playerForward.isKeyDown())
//        {
//            ++this.moveForward;
//        }
//
//        if (KeyBindingsStrider.playerBack.isKeyDown())
//        {
//        	System.out.println("Does this happen");
//            --this.moveForward;
//        }
//
//        if (KeyBindingsStrider.playerLeft.isKeyDown())
//        {
//            ++this.moveStrafe;
//        }
//
//        if (KeyBindingsStrider.playerRight.isKeyDown())
//        {
//            --this.moveStrafe;
//        }
//
//        this.jump = this.gameSettings.keyBindJump.isKeyDown();
//        this.sneak = this.gameSettings.keyBindSneak.isKeyDown();
//
        if (this.sneak)
        {
            this.moveStrafe = (float)((double)this.moveStrafe * 0.3D);
            this.moveForward = (float)((double)this.moveForward * 0.3D);
        }
    }
}