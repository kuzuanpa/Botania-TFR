/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [Jan 24, 2015, 4:37:33 PM (GMT)]
 */
package vazkii.botania.common.item.lens;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import vazkii.botania.api.internal.IManaBurst;

public class LensDamage extends Lens {

	@Override
	public void updateBurst(IManaBurst burst, EntityThrowable entity, ItemStack stack) {
		AxisAlignedBB axis = AxisAlignedBB.getBoundingBox(entity.posX, entity.posY, entity.posZ, entity.lastTickPosX, entity.lastTickPosY, entity.lastTickPosZ).expand(1, 1, 1);
		List<EntityLivingBase> entities = entity.worldObj.getEntitiesWithinAABB(EntityLivingBase.class, axis);
		for (EntityLivingBase living : entities) {
			if (living instanceof EntityPlayer)
				continue;

			if (living.hurtTime == 0) {
				int mana = burst.getMana();

				int manaCost=mana>160?mana/10:16;
				if (!burst.isFake() && !entity.worldObj.isRemote) {
					DamageSource src = entity.getThrower() != null ?
							new EntityDamageSourceIndirect("indirectMagic", entity, living)
							: DamageSource.magic;
					living.attackEntityFrom(src, manaCost*20);
					break;
				}
				burst.setMana(mana - manaCost);

			}
		}
	}
}
