/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [Jan 30, 2014, 4:49:16 PM (GMT)]
 */
package vazkii.botania.common.item.material;

import java.awt.Color;
import java.util.List;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEAnvil;
import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFC_ItemHeat;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.Achievement;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import vazkii.botania.api.recipe.IElvenItem;
import vazkii.botania.api.recipe.IFlowerComponent;
import vazkii.botania.client.core.helper.IconHelper;
import vazkii.botania.common.Botania;
import vazkii.botania.common.achievement.IPickupAchievement;
import vazkii.botania.common.achievement.ModAchievements;
import vazkii.botania.common.entity.EntityDoppleganger;
import vazkii.botania.common.entity.EntityEnderAirBottle;
import vazkii.botania.common.item.ItemMod;
import vazkii.botania.common.item.equipment.tool.ToolCommons;
import vazkii.botania.common.lib.LibItemNames;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
public class ItemManaResource extends ItemMod implements IFlowerComponent, IElvenItem, IPickupAchievement {

	final int types = 24;
	IIcon[] icons;

	// begin dank_memes
	public IIcon tailIcon = null;
	public IIcon phiFlowerIcon = null;
	public IIcon goldfishIcon = null;
	public IIcon nerfBatIcon = null;
	// end dank_memes

	public ItemManaResource() {
		super();
		setHasSubtypes(true);
		setUnlocalizedName(LibItemNames.MANA_RESOURCE);

	}


	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event) {
		boolean rightEvent = event.action == Action.RIGHT_CLICK_AIR;
		ItemStack stack = event.entityPlayer.getCurrentEquippedItem();
		boolean correctStack = stack != null && stack.getItem() == Items.glass_bottle;
		boolean ender = event.world.provider.dimensionId == 1;

		if(rightEvent && correctStack && ender) {
			MovingObjectPosition pos = ToolCommons.raytraceFromEntity(event.world, event.entityPlayer, false, 5F);

			if(pos == null) {
				ItemStack stack1 = new ItemStack(this, 1, 15);
				event.entityPlayer.addStat(ModAchievements.enderAirMake, 1);

				if(!event.entityPlayer.inventory.addItemStackToInventory(stack1))
					event.entityPlayer.dropPlayerItemWithRandomChoice(stack1, true);

				stack.stackSize--;
				if(stack.stackSize == 0)
					event.entityPlayer.inventory.setInventorySlotContents(event.entityPlayer.inventory.currentItem, null);

				if(event.world.isRemote)
					event.entityPlayer.swingItem();
				else event.world.playSoundAtEntity(event.entityPlayer, "random.pop", 0.5F, 1F);
			}
		}
	}

	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		if(par1ItemStack.getItemDamage() == 4 || par1ItemStack.getItemDamage() == 14)
			return EntityDoppleganger.spawn(par2EntityPlayer, par1ItemStack, par3World, par4, par5, par6, par1ItemStack.getItemDamage() == 14);
		else if(par1ItemStack.getItemDamage() == 20 && net.minecraft.item.ItemDye.applyBonemeal(par1ItemStack, par3World, par4, par5, par6, par2EntityPlayer)) {
			if(!par3World.isRemote)
				par3World.playAuxSFX(2005, par4, par5, par6, 0);

			return true;
		}

		return super.onItemUse(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7, par8, par9, par10);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par3World, EntityPlayer par2EntityPlayer) {
		if(par1ItemStack.getItemDamage() == 15) {
			if(!par2EntityPlayer.capabilities.isCreativeMode)
				--par1ItemStack.stackSize;

			par3World.playSoundAtEntity(par2EntityPlayer, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

			if(!par3World.isRemote)
				par3World.spawnEntityInWorld(new EntityEnderAirBottle(par3World, par2EntityPlayer));
			else par2EntityPlayer.swingItem();
		}

		return par1ItemStack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List) {
		for(int i = 0; i < types; i++)
			if(Botania.gardenOfGlassLoaded || i != 20 && i != 21)
				par3List.add(new ItemStack(par1, 1, i));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		icons = new IIcon[types];
		for(int i = 0; i < icons.length; i++)
			icons[i] = IconHelper.forName(par1IconRegister, LibItemNames.MANA_RESOURCE_NAMES[i]);

		tailIcon = IconHelper.forName(par1IconRegister, "tail");
		phiFlowerIcon = IconHelper.forName(par1IconRegister, "phiFlower");
		goldfishIcon = IconHelper.forName(par1IconRegister, "goldfish");
		nerfBatIcon = IconHelper.forName(par1IconRegister, "nerfBat");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		if(par1ItemStack.getItemDamage() == 5 || par1ItemStack.getItemDamage() == 14)
			return Color.HSBtoRGB(Botania.proxy.getWorldElapsedTicks() * 2 % 360 / 360F, 0.25F, 1F);

		return 0xFFFFFF;
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return "item." + LibItemNames.MANA_RESOURCE_NAMES[Math.min(types - 1, par1ItemStack.getItemDamage())];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1) {
		return icons[Math.min(icons.length - 1, par1)];
	}

	@Override
	public boolean canFit(ItemStack stack, IInventory apothecary) {
		int meta = stack.getItemDamage();
		return meta == 6 || meta == 8 || meta == 5 || meta == 23;
	}

	@Override
	public int getParticleColor(ItemStack stack) {
		return 0x9b0000;
	}

	@Override
	public boolean isElvenItem(ItemStack stack) {
		int meta = stack.getItemDamage();
		return meta == 7 || meta == 8;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack) {
		return itemStack.getItemDamage() == 11 ? itemStack.copy() : null;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		
		addHeatInformation(is, arraylist);
		addItemInformation(is, player, arraylist);
		if (is.hasTagCompound())
		{
			NBTTagCompound tag = is.getTagCompound();
			
			if (tag.hasKey(TEAnvil.ITEM_CRAFTING_VALUE_TAG) || tag.hasKey(TEAnvil.ITEM_CRAFTING_RULE_1_TAG))
				arraylist.add(TFC_Core.translate("gui.ItemWorked"));
		}

	}
	
	@Override
	public Achievement getAchievementOnPickup(ItemStack stack, EntityPlayer player, EntityItem item) {
		return stack.getItemDamage() == 4 ? ModAchievements.terrasteelPickup : null;
	}
	public static void addHeatInformation(ItemStack is, List<String> arraylist)
	{
		if (is.hasTagCompound())
		{
			if(TFC_ItemHeat.hasTemp(is))
			{
				float temp = TFC_ItemHeat.getTemp(is);
				float meltTemp = -1;
				HeatIndex hi = HeatRegistry.getInstance().findMatchingIndex(is);
				if(hi != null)
					meltTemp = hi.meltTemp;

				if(meltTemp != -1)
				{
					if(is.getItem() == TFCItems.stick)
						arraylist.add(TFC_ItemHeat.getHeatColorTorch(temp, meltTemp));
					else
						arraylist.add(TFC_ItemHeat.getHeatColor(temp, meltTemp));
				}
			}
		}
	}
	public void addItemInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
			if(TFC_ItemHeat.hasTemp(is))
			{
				String s = "";
				if(HeatRegistry.getInstance().isTemperatureDanger(is))
				{
					s += EnumChatFormatting.WHITE + TFC_Core.translate("gui.ingot.danger") + " | ";
				}

				if(HeatRegistry.getInstance().isTemperatureWeldable(is))
				{
					s += EnumChatFormatting.WHITE + TFC_Core.translate("gui.ingot.weldable") + " | ";
				}

				if(HeatRegistry.getInstance().isTemperatureWorkable(is))
				{
					s += EnumChatFormatting.WHITE + TFC_Core.translate("gui.ingot.workable");
				}

				if (!"".equals(s))
					arraylist.add(s);
			}
		}
	
	@Override
	public int getItemStackLimit(ItemStack is)
	{
		if (is.hasTagCompound())
		{
			NBTTagCompound tag = is.getTagCompound();
			if (TFC_ItemHeat.hasTemp(is) || tag.hasKey(TEAnvil.ITEM_CRAFTING_VALUE_TAG) || tag.hasKey(TEAnvil.ITEM_CRAFTING_RULE_1_TAG))
			{
				return 1;
			}
		}

		return super.getItemStackLimit(is);
	}
}
