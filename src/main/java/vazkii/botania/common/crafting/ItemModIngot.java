package vazkii.botania.common.crafting;

import com.bioxx.tfc.Items.ItemIngot;
import com.bioxx.tfc.api.Enums.EnumSize;

public class ItemModIngot extends ItemIngot {

	public ItemModIngot(String m, int amt) {
		super();
		this.setMetal(m, amt);
		if (amt > 100) {
			this.setSize(EnumSize.LARGE);
		}
	}


}
