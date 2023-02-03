/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
  *
 * File Created @ [Mar 29, 2015, 10:13:41 PM (GMT)]
 */
package vazkii.botania.common.item.relic;

import baubles.api.BaubleType;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import vazkii.botania.common.core.handler.ConfigHandler;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lib.LibItemNames;

import java.util.ArrayList;
import java.util.List;

public class ItemOdinRing extends ItemRelicBauble {

	public static List<String> damageNegations = new ArrayList();


	public ItemOdinRing() {
		super(LibItemNames.ODIN_RING);
		MinecraftForge.EVENT_BUS.register(this);

		damageNegations.add(DamageSource.drown.damageType);
		damageNegations.add(DamageSource.fall.damageType);
		damageNegations.add(DamageSource.lava.damageType);
		if(ConfigHandler.ringOfOdinFireResist) {
			damageNegations.add(DamageSource.inFire.damageType);
			damageNegations.add(DamageSource.onFire.damageType);
		}

		damageNegations.add(DamageSource.inWall.damageType);
		damageNegations.add(DamageSource.starve.damageType);
	}

	@Override
	public void onValidPlayerWornTick(ItemStack stack, EntityPlayer player) {
		if(player.isBurning() && ConfigHandler.ringOfOdinFireResist)
			player.extinguish();
	}

	@SubscribeEvent
	public void onPlayerAttacked(LivingAttackEvent event) {
		if(event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			if(getOdinRing(player) != null && damageNegations.contains(event.source.damageType))
				event.setCanceled(true);
		}
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.RING;
	}

	public static ItemStack getOdinRing(EntityPlayer player) {
		InventoryBaubles baubles = PlayerHandler.getPlayerBaubles(player);
		ItemStack stack1 = baubles.getStackInSlot(1);
		ItemStack stack2 = baubles.getStackInSlot(2);
		return isOdinRing(stack1) ? stack1 : isOdinRing(stack2) ? stack2 : null;
	}

	private static boolean isOdinRing(ItemStack stack) {
		return stack != null && (stack.getItem() == ModItems.odinRing || stack.getItem() == ModItems.aesirRing);
	}


}

