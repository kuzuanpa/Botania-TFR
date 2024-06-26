/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [Mar 29, 2015, 10:16:29 PM (GMT)]
 */
package vazkii.botania.common.item.relic;

import baubles.api.BaubleType;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;
import vazkii.botania.api.item.IExtendedWireframeCoordinateListProvider;
import vazkii.botania.common.achievement.ICraftAchievement;
import vazkii.botania.common.achievement.ModAchievements;
import vazkii.botania.common.crafting.recipe.AesirRingRecipe;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lib.LibItemNames;

import java.util.List;

public class ItemAesirRing extends ItemRelicBauble implements IExtendedWireframeCoordinateListProvider, ICraftAchievement {


	public ItemAesirRing() {
		super(LibItemNames.AESIR_RING);
		GameRegistry.addRecipe(new AesirRingRecipe());
		RecipeSorter.register("botania:aesirRing", AesirRingRecipe.class, Category.SHAPELESS, "");
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onDropped(ItemTossEvent event) {
		if(event.entityItem != null && event.entityItem.getEntityItem() != null && !event.entityItem.worldObj.isRemote) {
			ItemStack stack = event.entityItem.getEntityItem();
			if(stack.getItem() != null && stack.getItem() == this) {
				event.entityItem.setDead();

				String user = getSoulbindUsername(stack);
				for(Item item : new Item[] { ModItems.thorRing, ModItems.lokiRing, ModItems.odinRing }) {
					ItemStack stack1 = new ItemStack(item);
					bindToUsername(user, stack1);
					EntityItem entity = new EntityItem(event.entityItem.worldObj, event.entityItem.posX, event.entityItem.posY, event.entityItem.posZ, stack1);
					entity.motionX = event.entityItem.motionX;
					entity.motionY = event.entityItem.motionY;
					entity.motionZ = event.entityItem.motionZ;
					entity.age = event.entityItem.age;
					entity.delayBeforeCanPickup = event.entityItem.delayBeforeCanPickup;
					entity.worldObj.spawnEntityInWorld(entity);
				}
			}
		}
	}

	@Override
	public void onValidPlayerWornTick(ItemStack stack, EntityPlayer player) {
		((ItemOdinRing) ModItems.odinRing).onValidPlayerWornTick(stack, player);
	}

	@Override
	public BaubleType getBaubleType(ItemStack arg0) {
		return BaubleType.RING;
	}

	@Override
	public List<ChunkCoordinates> getWireframesToDraw(EntityPlayer player, ItemStack stack) {
		return null;
	}

	@Override
	public ChunkCoordinates getSourceWireframe(EntityPlayer player, ItemStack stack) {
		return null;
	}

	@Override
	public Achievement getAchievementOnCraft(ItemStack stack, EntityPlayer player, IInventory matrix) {
		return ModAchievements.relicAesirRing;
	}

}
