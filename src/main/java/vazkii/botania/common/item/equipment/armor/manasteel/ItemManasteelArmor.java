/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [Apr 13, 2014, 6:38:21 PM (GMT)]
 */
package vazkii.botania.common.item.equipment.armor.manasteel;

import java.util.List;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.Items.Tools.ItemTerraTool;
import com.bioxx.tfc.api.Armor;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.IClothing;
import com.bioxx.tfc.api.Interfaces.ISize;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import thaumcraft.api.IRunicArmor;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.item.IPhantomInkable;
import vazkii.botania.api.mana.IManaUsingItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.client.core.helper.IconHelper;
import vazkii.botania.client.lib.LibResources;
import vazkii.botania.client.model.armor.ModelArmorManasteel;
import vazkii.botania.common.core.BotaniaCreativeTab;
import vazkii.botania.common.core.handler.ConfigHandler;
import vazkii.botania.common.core.helper.ItemNBTHelper;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.item.equipment.tool.ToolCommons;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Optional.Interface(modid = "Thaumcraft", iface = "thaumcraft.api.IRunicArmor")
public class ItemManasteelArmor extends ItemArmor implements ISpecialArmor, IManaUsingItem, IPhantomInkable, IRunicArmor,ISize, IClothing{
	public static Armor ManaSteelPlate = 	new Armor(11, 1000, 1000, 1000, "Mana Steel");
	public static Armor EltPlate = 	new Armor(12, 2200, 2500, 2200, "Elt Steel");
	public static Armor Weave = 	new Armor(13, 500, 500, 500, "Weave");
	public static Armor TerraSteelPlate = 		new Armor(14, 2400, 2700, 2400, "Terra Steel");
	
	private static final int MANA_PER_DAMAGE = 100;

	private static final String TAG_PHANTOM_INK = "phantomInk";
	public Armor armorTypeTFC;
	protected ModelBiped[] models = null;
	public int type;
	private int trueType;
	public ItemManasteelArmor(Armor armor ,int armorSlot,int type, String name) {
		this(type, name, BotaniaAPI.manasteelArmorMaterial);
		armorTypeTFC = armor;
		this.trueType = armorSlot;
		this.setMaxDamage(armorTypeTFC.getDurability(armorSlot));
	}

	public ItemManasteelArmor(int type, String name, ArmorMaterial mat) {
		super(mat, 0, type);
		this.type = type;
		setCreativeTab(BotaniaCreativeTab.INSTANCE);
		setUnlocalizedName(name);
	}

	@Override
	public Item setUnlocalizedName(String par1Str) {
		GameRegistry.registerItem(this, par1Str);
		return super.setUnlocalizedName(par1Str);
	}

	@Override
	public String getUnlocalizedNameInefficiently(ItemStack par1ItemStack) {
		return super.getUnlocalizedNameInefficiently(par1ItemStack).replaceAll("item.", "item." + LibResources.PREFIX_MOD);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this);
	}

	@Override
	public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
		if(source.isUnblockable())
			return new ArmorProperties(0, 0, 0);
		return new ArmorProperties(0, damageReduceAmount / 25D, armor.getMaxDamage() + 1 - armor.getItemDamage());
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return damageReduceAmount;
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity player, int par4, boolean par5) {
		if(player instanceof EntityPlayer)
			onArmorTick(world, (EntityPlayer) player, stack);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		if(!world.isRemote && stack.getItemDamage() > 0 && ManaItemHandler.requestManaExact(stack, player, MANA_PER_DAMAGE * 2, true))
			stack.setItemDamage(stack.getItemDamage() - 1);
	}

	@Override
	public void damageArmor(EntityLivingBase entity, ItemStack stack, DamageSource source, int damage, int slot) {
		ToolCommons.damageItem(stack, damage, entity, MANA_PER_DAMAGE);
	}

	@Override
	public final String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		return hasPhantomInk(stack) ? LibResources.MODEL_INVISIBLE_ARMOR : getArmorTextureAfterInk(stack, slot);
	}

	public String getArmorTextureAfterInk(ItemStack stack, int slot) {
		return ConfigHandler.enableArmorModels ? LibResources.MODEL_MANASTEEL_NEW : slot == 2 ? LibResources.MODEL_MANASTEEL_1 : LibResources.MODEL_MANASTEEL_0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, int armorSlot) {
		if(ConfigHandler.enableArmorModels) {
			ModelBiped model = getArmorModelForSlot(entityLiving, itemStack, armorSlot);
			if(model == null)
				model = provideArmorModelForSlot(itemStack, armorSlot);

			if(model != null)
				return model;
		}

		return super.getArmorModel(entityLiving, itemStack, armorSlot);
	}

	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModelForSlot(EntityLivingBase entity, ItemStack stack, int slot) {
		if(models == null)
			models = new ModelBiped[4];

		return models[slot];
	}

	@SideOnly(Side.CLIENT)
	public ModelBiped provideArmorModelForSlot(ItemStack stack, int slot) {
		models[slot] = new ModelArmorManasteel(slot);
		return models[slot];
	}

	@Override
	public boolean getIsRepairable(ItemStack par1ItemStack, ItemStack par2ItemStack) {
		return par2ItemStack.getItem() == ModItems.manaResource && par2ItemStack.getItemDamage() == 0 ? true : super.getIsRepairable(par1ItemStack, par2ItemStack);
	}

	@Override
	public boolean usesMana(ItemStack stack) {
		return true;
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean adv) {
		ItemTerra.addSizeInformation(stack, list);
		ItemTerra.addHeatInformation(stack, list);
		ItemTerraTool.addSmithingBonusInformation(stack, list);
		if(GuiScreen.isShiftKeyDown()) {
			addInformationAfterShift(stack, player, list, adv);
			list.add(EnumChatFormatting.WHITE + TFC_Core.translate("gui.Advanced") + ":");
			list.add(EnumChatFormatting.ITALIC + TFC_Core.translate("gui.Armor.Pierce") + ": " + EnumChatFormatting.AQUA + armorTypeTFC.getPiercingAR());
			list.add(EnumChatFormatting.ITALIC + TFC_Core.translate("gui.Armor.Slash") + ": " + EnumChatFormatting.AQUA + armorTypeTFC.getSlashingAR());
			list.add(EnumChatFormatting.ITALIC + TFC_Core.translate("gui.Armor.Crush") + ": " + EnumChatFormatting.AQUA + armorTypeTFC.getCrushingAR());
			list.add("");
			if (stack.hasTagCompound())
			{
				NBTTagCompound stackTagCompound = stack.getTagCompound();

				if(stackTagCompound.hasKey("creator"))
					list.add(EnumChatFormatting.ITALIC + TFC_Core.translate("gui.Armor.ForgedBy") + " " + stackTagCompound.getString("creator"));
			}
		}
		else
			addStringToTooltip(StatCollector.translateToLocal("botaniamisc.shiftinfo"), list);

	}
	

	public void addInformationAfterShift(ItemStack stack, EntityPlayer player, List list, boolean adv) {
		addStringToTooltip(getArmorSetTitle(player), list);
		addArmorSetDescription(stack, list);
		ItemStack[] stacks = getArmorSetStacks();
		for(int i = 0; i < stacks.length; i++)
			addStringToTooltip((hasArmorSetItem(player, i) ? EnumChatFormatting.GREEN : "") + " - " + stacks[i].getDisplayName(), list);
		if(hasPhantomInk(stack))
			addStringToTooltip(StatCollector.translateToLocal("botaniamisc.hasPhantomInk"), list);
	}
	
	public void addStringToTooltip(String s, List<String> tooltip) {
		tooltip.add(s.replaceAll("&", "\u00a7"));
	}

	static ItemStack[] armorset;

	public ItemStack[] getArmorSetStacks() {
		if(armorset == null)
			armorset = new ItemStack[] {
				new ItemStack(ModItems.manasteelHelm),
				new ItemStack(ModItems.manasteelChest),
				new ItemStack(ModItems.manasteelLegs),
				new ItemStack(ModItems.manasteelBoots)
		};

		return armorset;
	}

	public boolean hasArmorSet(EntityPlayer player) {
		return hasArmorSetItem(player, 0) && hasArmorSetItem(player, 1) && hasArmorSetItem(player, 2) && hasArmorSetItem(player, 3);
	}

	public boolean hasArmorSetItem(EntityPlayer player, int i) {
		ItemStack stack = player.inventory.armorInventory[3 - i];
		if(stack == null)
			return false;

		switch(i) {
		case 0: return stack.getItem() == ModItems.manasteelHelm || stack.getItem() == ModItems.manasteelHelmRevealing;
		case 1: return stack.getItem() == ModItems.manasteelChest;
		case 2: return stack.getItem() == ModItems.manasteelLegs;
		case 3: return stack.getItem() == ModItems.manasteelBoots;
		}

		return false;
	}

	public int getSetPiecesEquipped(EntityPlayer player) {
		int pieces = 0;
		for(int i = 0; i < 4; i++)
			if(hasArmorSetItem(player, i))
				pieces++;

		return pieces;
	}

	public String getArmorSetName() {
		return StatCollector.translateToLocal("botania.armorset.manasteel.name");
	}

	public String getArmorSetTitle(EntityPlayer player) {
		return StatCollector.translateToLocal("botaniamisc.armorset") + " " + getArmorSetName() + " (" + getSetPiecesEquipped(player) + "/" + getArmorSetStacks().length + ")";
	}

	public void addArmorSetDescription(ItemStack stack, List<String> list) {
		addStringToTooltip(StatCollector.translateToLocal("botania.armorset.manasteel.desc"), list);
	}

	@Override
	public boolean hasPhantomInk(ItemStack stack) {
		return ItemNBTHelper.getBoolean(stack, TAG_PHANTOM_INK, false);
	}

	@Override
	public void setPhantomInk(ItemStack stack, boolean ink) {
		ItemNBTHelper.setBoolean(stack, TAG_PHANTOM_INK, ink);
	}

	@Override
	@Optional.Method(modid = "Thaumcraft")
	public int getRunicCharge(ItemStack itemstack) {
		return 0;
	}
	public int getUnadjustedArmorType()
	{
		return trueType;
	}
	@Override
	protected MovingObjectPosition getMovingObjectPositionFromPlayer(World par1World, EntityPlayer par2EntityPlayer, boolean par3)
	{
		float f = 1.0F;
		float f1 = par2EntityPlayer.prevRotationPitch + (par2EntityPlayer.rotationPitch - par2EntityPlayer.prevRotationPitch) * f;
		float f2 = par2EntityPlayer.prevRotationYaw + (par2EntityPlayer.rotationYaw - par2EntityPlayer.prevRotationYaw) * f;
		double d0 = par2EntityPlayer.prevPosX + (par2EntityPlayer.posX - par2EntityPlayer.prevPosX) * f;
		double d1 = par2EntityPlayer.prevPosY + (par2EntityPlayer.posY - par2EntityPlayer.prevPosY) * f + (par1World.isRemote ? par2EntityPlayer.getEyeHeight() - par2EntityPlayer.getDefaultEyeHeight() : par2EntityPlayer.getEyeHeight()); // isRemote check to revert changes to ray trace position due to adding the eye height clientside and player yOffset differences
		double d2 = par2EntityPlayer.prevPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.prevPosZ) * f;
		Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
		float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		double d3 = 5.0D;
		if (par2EntityPlayer instanceof EntityPlayerMP)
		{
			d3 = ((EntityPlayerMP)par2EntityPlayer).theItemInWorldManager.getBlockReachDistance();
		}
		d3 *= getReach(null).multiplier;
		Vec3 vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
		return par1World.rayTraceBlocks(vec3, vec31, par3);
	}
	
	@Override

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		int i = EntityLiving.getArmorPosition(par1ItemStack);
		ItemStack itemstack1 = par3EntityPlayer.getCurrentArmor((i-1)%4);

		if (itemstack1 == null)
		{
			par3EntityPlayer.setCurrentItemOrArmor(i /*+ 1*/, par1ItemStack.copy()); //Forge: Vanilla bug fix associated with fixed setCurrentItemOrArmor indexs for players.
			par1ItemStack.stackSize = 0;
		}

		return par1ItemStack;
	}
	@Override
	public int getBodyPart()
	{
		return 3-armorType;
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
	}

	@Override
	public int getThermal() {
		return 50;
	}

	@Override
	public boolean canStack() {
		return false;
	}

	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return (int) (super.getMaxDamage(stack)+(super.getMaxDamage(stack) * AnvilManager.getDurabilityBuff(stack)));
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.HEAVY;
	}
	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.LARGE;
	}
	@Override
	public int getItemStackLimit()
	{
		if(canStack())
			return this.getSize(null).stackSize * getWeight(null).multiplier;
		else
			return 1;
	}
}
