package vazkii.botania.common.crafting;

import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRaw;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFCItems;

import net.minecraft.item.ItemStack;
import vazkii.botania.common.item.ModItems;
public class ModItemHeat{
	
	public static void setupItemHeat()
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		
	HeatRaw manasteelRaw = new HeatRaw(0.3, 1500);
	HeatRaw terrasteelRaw = new HeatRaw(0.2,2000);
	manager.addIndex(new HeatIndex(new ItemStack(ModItems.manaResource, 1, 0), manasteelRaw, new ItemStack(TFCItems.wroughtIronUnshaped,1)));
	manager.addIndex(new HeatIndex(new ItemStack(ModItems.manaResource, 1, 7), manasteelRaw, new ItemStack(TFCItems.wroughtIronUnshaped,1)));
	manager.addIndex(new HeatIndex(new ItemStack(ModItems.manaResource, 1, 4), terrasteelRaw, new ItemStack(TFCItems.wroughtIronUnshaped,1)));
		
	manager.addIndex(new HeatIndex(new ItemStack(ModItems.terrasteelTK,1), terrasteelRaw, new ItemStack(TFCItems.wroughtIronUnshaped,1)));
	manager.addIndex(new HeatIndex(new ItemStack(ModItems.terrasteelXJ, 1), terrasteelRaw, new ItemStack(TFCItems.wroughtIronUnshaped,1)));
	manager.addIndex(new HeatIndex(new ItemStack(ModItems.terrasteelKZ, 1), terrasteelRaw, new ItemStack(TFCItems.wroughtIronUnshaped,1)));
	manager.addIndex(new HeatIndex(new ItemStack(ModItems.terrasteelXZ, 1), terrasteelRaw, new ItemStack(TFCItems.wroughtIronUnshaped,1)));
}
}