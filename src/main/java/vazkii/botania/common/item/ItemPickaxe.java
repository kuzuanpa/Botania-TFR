package vazkii.botania.common.item;

import java.util.List;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEAnvil;
import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFC_ItemHeat;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;

public class ItemPickaxe extends Item {

	public ItemPickaxe(String pickaxe) {
        super();
        this.setUnlocalizedName(pickaxe);
        GameRegistry.registerItem(this, pickaxe);
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