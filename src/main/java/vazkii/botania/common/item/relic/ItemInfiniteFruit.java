/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [Mar 29, 2015, 10:12:55 PM (GMT)]
 */
package vazkii.botania.common.item.relic;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.core.helper.IconHelper;
import vazkii.botania.common.lib.LibItemNames;
import vazkii.botania.common.lib.LibObfuscation;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;

import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
public class ItemInfiniteFruit extends ItemRelic implements IManaUsingItem {

	public static IIcon dasBootIcon;
	public ItemInfiniteFruit() {
		super(LibItemNames.INFINITE_FRUIT);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_) {
		return 48;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack p_77661_1_) {
		return isBoot(p_77661_1_) ? EnumAction.drink : EnumAction.eat;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack p_77659_1_, World p_77659_2_, EntityPlayer p_77659_3_) {
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(p_77659_3_);
		if(foodstats.needFood()&& isRightPlayer(p_77659_3_, p_77659_1_))
			p_77659_3_.setItemInUse(p_77659_1_, this.getMaxItemUseDuration(p_77659_1_));
		return p_77659_1_;
	}
	private EnumFoodGroup yyz = EnumFoodGroup.Grain;
	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		super.onUsingTick(stack, player, count);
	
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);

		


		if(ManaItemHandler.requestManaExact(stack, player, 1000, true)) {
			foodstats.addNutrition(yyz,5);
			foodstats.waterLevel += 2400;
			foodstats.stomachLevel += 2;
			TFC_Core.setPlayerFoodStats(player, foodstats);
			
				if(player.canEat(false))
					ReflectionHelper.setPrivateValue(EntityPlayer.class, player, 20, LibObfuscation.ITEM_IN_USE_COUNT);
				
		}else {
			TFC_Core.sendInfoMessage(player, new ChatComponentText("Consume Mana"));
	
	}

	}
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this);
		dasBootIcon = IconHelper.forName(par1IconRegister, "dasBoot");
	}

	@Override
	public IIcon getIconIndex(ItemStack par1ItemStack) {
		return isBoot(par1ItemStack) ? dasBootIcon : super.getIconIndex(par1ItemStack);
	}

	private boolean isBoot(ItemStack par1ItemStack) {
		String name = par1ItemStack.getDisplayName().toLowerCase().trim();
		return name.equals("das boot");
	}

	@Override
	public boolean usesMana(ItemStack stack) {
		return true;
	}

}


	
