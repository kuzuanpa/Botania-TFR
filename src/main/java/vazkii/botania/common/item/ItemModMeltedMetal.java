package vazkii.botania.common.item;

import net.minecraft.client.renderer.texture.IIconRegister;

import com.bioxx.tfc.Items.ItemMeltedMetal;

public class ItemModMeltedMetal extends ItemMeltedMetal {

	@Override
	public void registerIcons(IIconRegister registerer) {
		this.itemIcon = registerer.registerIcon("BOT" + ":" + textureFolder + this.getUnlocalizedName().replace("item.", ""));
	}
	
}
