/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [25/11/2015, 19:59:11 (GMT)]
 */
package vazkii.botania.client.gui;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.GUI.GuiContainerTFC;
import com.bioxx.tfc.api.TFCOptions;

import vazkii.botania.common.block.tile.TECrucible;

public class GuiCrucibleCL extends GuiContainerTFC {
	private TECrucible crucibleTE;
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_crucible.png");
	public GuiCrucibleCL(InventoryPlayer inventoryplayer, TECrucible te, World world, int x, int y, int z)
	{
		super(new ContainerCrucible(inventoryplayer, te, world, x, y, z), 176, 113);
		crucibleTE = te;
	}
	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j)
	{
		if (TFCOptions.enableDebugMode)
		{
			this.fontRendererObj.drawString("Temp: " + crucibleTE.temperature, 178, 8, 0xffffff);
		}

		if (crucibleTE.currentAlloy != null)
		{
			if (crucibleTE.currentAlloy.outputAmount == 0)
			{
				this.fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + TFC_Core.translate("gui.empty"), 7, 7, 0x000000);
				return;
			}
			else if (crucibleTE.currentAlloy.outputType != null)
			{
				this.fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + TFC_Core.translate("gui.metal." + crucibleTE.currentAlloy.outputType.name.replace(" ", "")), 7, 7, 0x000000);
			}
			else
			{
				this.fontRendererObj.drawString(EnumChatFormatting.UNDERLINE + TFC_Core.translate("gui.metal.Unknown"), 7, 7, 0x000000);
			}

			for (int c = 0; c < crucibleTE.currentAlloy.alloyIngred.size(); c++)
			{
				double m = crucibleTE.currentAlloy.alloyIngred.get(c).metal;
				m = Math.round(m * 100d) / 100d;
				if (crucibleTE.currentAlloy.alloyIngred.get(c).metalType != null)
				{
					this.fontRendererObj.drawString(EnumChatFormatting.DARK_GRAY + TFC_Core.translate("gui.metal." + crucibleTE.currentAlloy.alloyIngred.get(c).metalType.name.replace(" ", "")) + ": " + EnumChatFormatting.DARK_GREEN + m + "%", 7, 18 + 10 * (c), 0x000000);
				}
			}
		}
	}


	

}
