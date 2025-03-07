/**
 * This class was created by
 <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [Jan 24, 2015, 4:41:44 PM (GMT)]
 */
package vazkii.botania.common.item.lens;

import cn.kuzuanpa.ktfruaddon.api.client.fx.FxRenderBlockOutline;
import cpw.mods.fml.common.FMLLog;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MovingObjectPosition;
import org.apache.logging.log4j.Level;
import vazkii.botania.api.internal.IManaBurst;
import vazkii.botania.api.mana.BurstProperties;

import java.awt.*;

public class LensOreFinding extends Lens {

	private static final int MANA_COST = 4;
	private static final int MANA_COST_FOUND = 40;

	@Override
	public boolean collideBurst(IManaBurst burst, EntityThrowable entity, MovingObjectPosition pos, boolean isManaBlock, boolean dead, ItemStack stack) {
		if(!isManaBlock) {
			dead=false;
			short[] color = cn.kuzuanpa.ktfruaddon.api.code.OreScanner.getOreColor(entity.worldObj,pos.blockX,pos.blockY,pos.blockZ);
			if (color.length == 0){
				burst.setMana(burst.getMana()- MANA_COST);
				return false;
			}
			burst.setMana(burst.getMana()- MANA_COST_FOUND);
			FxRenderBlockOutline.addBlockOutlineToRender(new ChunkCoordinates(pos.blockX,pos.blockY,pos.blockZ),new Color(color[0],color[1],color[2],color[3]).getRGB(), 2,System.currentTimeMillis()+30000);
		}
		return dead;
	}

	@Override
	public void apply(ItemStack stack, BurstProperties props) {
		props.motionModifier *= 2F;
		props.maxMana *= 0.75F;
		props.ticksBeforeManaLoss = 0;
	}
}
