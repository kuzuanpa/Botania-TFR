/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [Mar 21, 2014, 8:44:35 PM (GMT)]
 */
package vazkii.botania.common.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import vazkii.botania.client.core.helper.IconHelper;
import vazkii.botania.client.lib.LibResources;
import vazkii.botania.common.core.BotaniaCreativeTab;
import vazkii.botania.common.lib.LibItemNames;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Core.Player.SkillStats.SkillRank;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.*;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ICookableFood;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.Interfaces.IMergeableFood;
import com.bioxx.tfc.api.Interfaces.ISize;
import com.bioxx.tfc.api.Util.Helper;
public class ItemManaCookie extends ItemTerra implements ISize, ICookableFood, IMergeableFood{
	private IIcon totalBiscuitIcon;
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
	public IIcon getIcon(ItemStack stack, int pass) {
		return getIconIndex(stack);
	}

	@Override
	public IIcon getIconIndex(ItemStack stack) {
		return stack.getDisplayName().toLowerCase().equals("totalbiscuit") ? totalBiscuitIcon : super.getIconIndex(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister) {
		itemIcon = IconHelper.forItem(par1IconRegister, this);
		totalBiscuitIcon = IconHelper.forName(par1IconRegister, "totalBiscuit");
	}
	private EnumFoodGroup foodgroup;

	public int foodID;
	public float decayRate = 1.0f;
	public boolean edible = true;
	public boolean canBeUsedRaw = true;
	protected int tasteSweet;
	protected int tasteSour;
	protected int tasteSalty;
	protected int tasteBitter;
	protected int tasteUmami;
	protected boolean canBeSmoked;
	protected float smokeAbsorb;

	public IIcon cookedIcon;
	protected boolean hasCookedIcon;

	public ItemManaCookie(EnumFoodGroup fg)
	{
		super();
		setCreativeTab(BotaniaCreativeTab.INSTANCE);
		setUnlocalizedName(LibItemNames.MANA_COOKIE);
		foodgroup = fg;
		TFCItems.foodList.add(this);
		this.setMaxDamage(100);
		this.hasSubtypes = false;
		smokeAbsorb = 0.5f;

		foodID = FoodRegistry.getInstance().registerFood(fg, this);
	}

	public ItemManaCookie(EnumFoodGroup fg,  boolean edible)
	{
		this(fg);
		this.edible = edible;
	}

	public ItemManaCookie(EnumFoodGroup fg,  boolean edible, boolean usable)
	{
		this(fg,  edible);
		canBeUsedRaw = usable;
	}

	public ItemManaCookie setDecayRate(float f)
	{
		this.decayRate = f;
		return this;
	}

	public ItemManaCookie setCanSmoke()
	{
		this.canBeSmoked = true;
		return this;
	}

	public ItemManaCookie setHasCookedIcon()
	{
		this.hasCookedIcon = true;
		return this;
	}

	
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}


	@Override
	public int getRenderPasses(int metadata)
	{
		return 1;
	}

	@Override
	public float getDecayRate(ItemStack is)
	{

		
		return  0;
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(ItemManaCookie.createTag(new ItemStack(this, 1), 32));
	}

	@Override
	public String getItemStackDisplayName(ItemStack is)
	{
		StringBuilder name = new StringBuilder();
		if (is.hasTagCompound()) // Circular reference avoidance
		{
			if(Food.isPickled(is))
				name.append(TFC_Core.translate("word.pickled")).append(' ');
			else if(Food.isBrined(is) && !Food.isDried(is))
				name.append(TFC_Core.translate("word.brined")).append(' ');

			if(Food.isSalted(is))
				name.append(TFC_Core.translate("word.salted")).append(' ');
			if(Food.isCooked(is))
				name.append(TFC_Core.translate("word.cooked")).append(' ');
			else if(Food.isSmoked(is))
				name.append(TFC_Core.translate("word.smoked")).append(' ');

			if(Food.isDried(is) && !Food.isCooked(is))
				name.append(TFC_Core.translate("word.dried")).append(' ');
			if(Food.isInfused(is))
				name.append(TFC_Core.translate(Food.getInfusion(is) + ".name")).append(' ');
		}

		return name.append(TFC_Core.translate(this.getUnlocalizedName(is) + ".name")).append(getCookedLevelString(is)).toString();
	}

	protected String getCookedLevelString(ItemStack is)
	{
		String s = "";
		if(Food.isCooked(is))
		{
			s+= " (";
			int cookedLevel = ((int)Food.getCooked(is)-600)/120;
			switch(cookedLevel)
			{
			case 0: s+=TFC_Core.translate("food.cooked.0");break;
			case 1: s+=TFC_Core.translate("food.cooked.1");break;
			case 2: s+=TFC_Core.translate("food.cooked.2");break;
			case 3: s+=TFC_Core.translate("food.cooked.3");break;
			default: s+=TFC_Core.translate("food.cooked.4");break;
			}
			s+= ")";
		}
		return s;
	}

	public static void addFoodHeatInformation(ItemStack is, List<String> arraylist)
	{
		if (TFC_ItemHeat.hasTemp(is))
		{
			float meltTemp = TFC_ItemHeat.isCookable(is);
			if (meltTemp != -1)
				arraylist.add(TFC_ItemHeat.getHeatColorFood(TFC_ItemHeat.getTemp(is), meltTemp));
		}
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);
		arraylist.add(ItemManaCookie.getFoodGroupName(this.getFoodGroup()));

		if (is.hasTagCompound())
		{
			ItemFoodTFC.addFoodHeatInformation(is, arraylist);
			addFoodInformation(is, player, arraylist);
		}
		else
		{
			arraylist.add(TFC_Core.translate("gui.badnbt"));
			TerraFirmaCraft.LOG.error(TFC_Core.translate("error.error") + " " + is.getUnlocalizedName() + " " +
					TFC_Core.translate("error.NBT") + " " + TFC_Core.translate("error.Contact"));
		}
	}

	public void addFoodInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		float ounces = Helper.roundNumber(Food.getWeight(is), 100);
		if (ounces > 0 && ounces <= 16)
			arraylist.add(TFC_Core.translate("gui.food.amount") + " " + ounces + " oz / " + 16 + " oz");

		float decay = Food.getDecay(is);
		if (decay > 0)
			arraylist.add(EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.food.decay") + " " + Helper.roundNumber(decay / ounces * 100, 10) + "%");
		if (TFCOptions.enableDebugMode)
		{
			arraylist.add(EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.food.decay") + ": " + decay);
			arraylist.add(EnumChatFormatting.DARK_GRAY + "Decay Rate: " + Helper.roundNumber(this.getDecayRate(is), 100));
		}

		if (TFC_Core.showCtrlInformation())
			ItemFoodTFC.addTasteInformation(is, player, arraylist);
		else
			arraylist.add(TFC_Core.translate("gui.showtaste"));
	}

	public static void addTasteInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		IFood food = (IFood) is.getItem();
		int sweet = food.getTasteSweet(is);
		int sour = food.getTasteSour(is);
		int salty = food.getTasteSalty(is);
		int bitter = food.getTasteBitter(is);
		int savory = food.getTasteSavory(is);
		SkillRank rank = TFC_Core.getSkillStats(player).getSkillRank(Global.SKILL_COOKING);
		if(Food.hasMealSkill(is))
			rank = SkillRank.values()[Food.getMealSkill(is)];

		int[] prefs = TFC_Core.getPlayerFoodStats(player).getPrefTaste();

		String sSweet = EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.taste.sweet") + ": " + EnumChatFormatting.WHITE;
		String sSour = EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.taste.sour") + ": " + EnumChatFormatting.WHITE;
		String sSalty = EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.taste.salty") + ": " + EnumChatFormatting.WHITE;
		String sBitter = EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.taste.bitter") + ": " + EnumChatFormatting.WHITE;
		String sSavory = EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.taste.savory") + ": " + EnumChatFormatting.WHITE;

		if(rank == SkillRank.Novice)
		{
			sSweet += (sweet > prefs[0] ? TFC_Core.translate("gui.taste.novice.sweet1") : TFC_Core.translate("gui.taste.novice.sweet0"));
			sSour += (sour > prefs[1] ? TFC_Core.translate("gui.taste.novice.sour1") : TFC_Core.translate("gui.taste.novice.sour0"));
			sSalty += (salty > prefs[2] ? TFC_Core.translate("gui.taste.novice.salty1") : TFC_Core.translate("gui.taste.novice.salty0"));
			sBitter += (bitter > prefs[3] ? TFC_Core.translate("gui.taste.novice.bitter1") : TFC_Core.translate("gui.taste.novice.bitter0"));
			sSavory += (savory > prefs[4] ? TFC_Core.translate("gui.taste.novice.savory1") : TFC_Core.translate("gui.taste.novice.savory0"));
		}
		else if(rank == SkillRank.Adept)
		{
			sSweet += ItemManaCookie.createBasicString(sweet, prefs[0], "sweet");
			sSour += ItemManaCookie.createBasicString(sour, prefs[1], "sour");
			sSalty += ItemManaCookie.createBasicString(salty, prefs[2], "salty");
			sBitter += ItemManaCookie.createBasicString(bitter, prefs[3], "bitter");
			sSavory += ItemManaCookie.createBasicString(savory, prefs[4], "savory");
		}
		else if(rank == SkillRank.Expert)
		{
			sSweet += ItemManaCookie.createExpertString(sweet, prefs[0], "sweet");
			sSour += ItemManaCookie.createExpertString(sour, prefs[1], "sour");
			sSalty += ItemManaCookie.createExpertString(salty, prefs[2], "salty");
			sBitter += ItemManaCookie.createExpertString(bitter, prefs[3], "bitter");
			sSavory += ItemManaCookie.createExpertString(savory, prefs[4], "savory");
		}
		else if(rank == SkillRank.Master)
		{
			sSweet += ItemManaCookie.createBasicString(sweet, prefs[0], "sweet") + " (" + (sweet - prefs[0]) + ")";
			sSour += ItemManaCookie.createBasicString(sour, prefs[1], "sour") + " (" + (sour - prefs[1]) + ")";
			sSalty += ItemManaCookie.createBasicString(salty, prefs[2], "salty") + " (" + (salty - prefs[2]) + ")";
			sBitter += ItemManaCookie.createBasicString(bitter, prefs[3], "bitter") + " (" + (bitter - prefs[3]) + ")";
			sSavory += ItemManaCookie.createBasicString(savory, prefs[4], "savory") + " (" + (savory - prefs[4]) + ")";
		}

		arraylist.add(sSweet);
		arraylist.add(sSour);
		arraylist.add(sSalty);
		arraylist.add(sBitter);
		arraylist.add(sSavory);
	}

	private static String createExpertString(int val, int pref, String name)
	{
		int abs = Math.abs(val - pref);

		String out = ItemManaCookie.createBasicString(val, pref, name);

		if(abs <= 5)
			out += " (+-5)";
		else if(abs <= 10)
			out += " (+-10)";
		else if(abs <= 15)
			out += " (+-15)";
		else if(abs <= 20)
			out += " (+-20)";

		return out;
	}

	private static String createBasicString(int val, int pref, String name)
	{
		int dif = val - pref;

		if(dif >= -5 && dif <= 5)
			return TFC_Core.translate("gui.taste.4") + " " + TFC_Core.translate("gui.taste." + name);
		else if(dif >= -10 && dif < -5)
			return TFC_Core.translate("gui.taste.3") + " " + TFC_Core.translate("gui.taste." + name);
		else if(dif >= -15 && dif < -10)
			return TFC_Core.translate("gui.taste.2") + " " + TFC_Core.translate("gui.taste." + name);
		else if(dif >= -20 && dif < -15)
			return TFC_Core.translate("gui.taste.1") + " " + TFC_Core.translate("gui.taste." + name);
		else if(dif < -20)
			return TFC_Core.translate("gui.taste.0") + " " + TFC_Core.translate("gui.taste." + name);
		else if(dif > 5 && dif <= 10)
			return TFC_Core.translate("gui.taste.5") + " " + TFC_Core.translate("gui.taste." + name);
		else if(dif > 10 && dif <= 15)
			return TFC_Core.translate("gui.taste.6") + " " + TFC_Core.translate("gui.taste." + name);
		else if(dif > 15 && dif <= 20)
			return TFC_Core.translate("gui.taste.7") + " " + TFC_Core.translate("gui.taste." + name);
		else if(dif > 20)
			return TFC_Core.translate("gui.taste.8") + " " + TFC_Core.translate("gui.taste." + name);

		return "";
	}

	public static String getFoodGroupName(EnumFoodGroup fg)
	{
		if(fg == EnumFoodGroup.Dairy)
			return (EnumChatFormatting.WHITE + TFC_Core.translate("gui.food.dairy"));
		else if(fg == EnumFoodGroup.Fruit)
			return (EnumChatFormatting.DARK_PURPLE + TFC_Core.translate("gui.food.fruit"));
		else if(fg == EnumFoodGroup.Vegetable)
			return (EnumChatFormatting.DARK_GREEN + TFC_Core.translate("gui.food.vegetable"));
		else if(fg == EnumFoodGroup.Protein)
			return (EnumChatFormatting.DARK_RED + TFC_Core.translate("gui.food.protein"));
		else if(fg == EnumFoodGroup.Grain)
			return (EnumChatFormatting.YELLOW + TFC_Core.translate("gui.food.grain"));
		else
			return "N/A";
	}

	public static String getFoodGroupColor(EnumFoodGroup fg)
	{
		if(fg == EnumFoodGroup.Dairy)
			return EnumChatFormatting.WHITE.toString();
		else if(fg == EnumFoodGroup.Fruit)
			return EnumChatFormatting.DARK_PURPLE.toString();
		else if(fg == EnumFoodGroup.Vegetable)
			return EnumChatFormatting.DARK_GREEN.toString();
		else if(fg == EnumFoodGroup.Protein)
			return EnumChatFormatting.DARK_RED.toString();
		else if(fg == EnumFoodGroup.Grain)
			return EnumChatFormatting.YELLOW.toString();
		else
			return "N/A";
	}

	
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		if (foodstats.needFood() && this.isEdible(is))
			player.setItemInUse(is, 16);

		return is;
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		if(!world.isRemote && this.isEdible(is))
		{
			if(is.hasTagCompound())
			{
				//NBTTagCompound nbt = is.getTagCompound();
				float weight = Food.getWeight(is);
				float decay = Math.max(Food.getDecay(is), 0);

				float eatAmount = Math.min(weight - decay, 5f);
				float stomachDiff = foodstats.stomachLevel+eatAmount-foodstats.getMaxStomach(foodstats.player);
				if(stomachDiff > 0)
					eatAmount-=stomachDiff;

				
				foodstats.addNutrition(((IFood)(is.getItem())).getFoodGroup(), eatAmount*2);
				foodstats.stomachLevel += eatAmount*3;
				if(FoodStatsTFC.reduceFood(is, eatAmount))
					is.stackSize = 0;
			}
			else
			{
				foodstats.addNutrition(((IFood)(is.getItem())).getFoodGroup(), 1f);

				String error = TFC_Core.translate("error.error") + " " + is.getUnlocalizedName() + " " +
								TFC_Core.translate("error.NBT") + " " + TFC_Core.translate("error.Contact");
				TerraFirmaCraft.LOG.error(error);
				TFC_Core.sendInfoMessage(player, new ChatComponentText(error));
			}
		}

		world.playSoundAtEntity(player, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		TFC_Core.setPlayerFoodStats(player, foodstats);
		return is;
	}

	public ItemStack onConsumedByEntity(ItemStack is, World world, EntityLivingBase entity)
	{
		if(entity instanceof IAnimal)
		{
			if(!world.isRemote)
			{
				float weight = Food.getWeight(is);
				float decay = Math.max(Food.getDecay(is), 0);
				float eatAmount = Math.min(weight - decay, 5f);
				if(FoodStatsTFC.reduceFood(is, eatAmount))
					is.stackSize = 0;
			}
			world.playSoundAtEntity(entity, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);
		}
		return is;
	}

	
	public static ItemStack createTag(ItemStack is)
	{
		return ItemFoodTFC.createTag(is, 999);
	}

	public static ItemStack createTag(ItemStack is, float weight)
	{
		if (!is.hasTagCompound())
			is.setTagCompound(new NBTTagCompound());

		Food.setWeight(is, weight);
		Food.setDecay(is, -24);
		Food.setDecayTimer(is, (int) TFC_Time.getTotalHours() + 1);

		return is;
	}

	public static ItemStack createTag(ItemStack is, float weight, float decay)
	{
		is = ItemFoodTFC.createTag(is, weight);
		Food.setDecay(is, decay);
		return is;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack is)
	{
		return 16;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack is)
	{
		return EnumAction.eat;
	}

	@Override
	public int getDisplayDamage(ItemStack is)
	{
		
		float weight = Food.getWeight(is);
		int percent = (int) ((weight) * 100);
		percent = percent > 0 ? percent < 100 ? percent : 100 : 0;
		return percent;
	}

	@Override
	public boolean isDamaged(ItemStack is)
	{
		/*if (is.hasTagCompound())
			return true;
		else*/
		return false;
	}

	@Override
	public int getMaxDamage(ItemStack is)
	{
		return 16;
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		float weight = Food.getWeight(is);

			return EnumSize.SMALL;

	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{

			return EnumWeight.LIGHT;
	}
	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	public EnumFoodGroup getFoodGroup()
	{
		return foodgroup;
	}

	@Override
	public int getFoodID()
	{
		return foodID;
	}

	@Override
	public ItemStack onDecayed(ItemStack is, World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public boolean isEdible(ItemStack is)
	{
		return edible || Food.isCooked(is);
	}

	@Override
	public boolean isUsable(ItemStack is)
	{
		return canBeUsedRaw || Food.isCooked(is);
	}

	@Override
	public int getTasteSweet(ItemStack is) {


		return 40;
	}

	@Override
	public int getTasteSour(ItemStack is) {
		return 40;
	}

	@Override
	public int getTasteSalty(ItemStack is) {
		return 40;
	}

	@Override
	public int getTasteBitter(ItemStack is) {
		return 40;
	}

	@Override
	public int getTasteSavory(ItemStack is) {
		return 40;
	}

	@Override
	public float getFoodMaxWeight(ItemStack is) {
		return 16;
	}

	@Override
	public boolean renderDecay() {
		return true;
	}

	@Override
	public boolean renderWeight() {
		return true;
	}

	@Override
	public boolean canSmoke() {
		return canBeSmoked;
	}

	@Override
	public float getSmokeAbsorbMultiplier()
	{
		return this.smokeAbsorb;
	}

	public ItemManaCookie setSmokeAbsorbMultiplier(float s) {
		smokeAbsorb = s;
		return this;
	}
}

	


