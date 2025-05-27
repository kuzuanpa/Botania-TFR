package vazkii.botania.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vazkii.botania.common.block.tile.TEForge;

import com.bioxx.tfc.Containers.ContainerTFC;
import com.bioxx.tfc.Containers.Slots.SlotForge;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.api.TFCItems;

public class ContainerForge extends ContainerTFC
{
	private TEForge forge;
	//private int coolTime;
	//private int freezeTime;
	//private int itemFreezeTime;
	private float firetemp;

	public ContainerForge(InventoryPlayer inventoryplayer, TEForge tileentityforge, World world, int x, int y, int z)
	{
		forge = tileentityforge;
		//coolTime = 0;
		//freezeTime = 0;
		//itemFreezeTime = 0;

		//Input slot
		addSlotToContainer(new SlotForge(inventoryplayer.player,tileentityforge, 0, 44, 8));
		addSlotToContainer(new SlotForge(inventoryplayer.player,tileentityforge, 1, 62, 26));
		addSlotToContainer(new SlotForge(inventoryplayer.player,tileentityforge, 2, 80, 44));
		addSlotToContainer(new SlotForge(inventoryplayer.player,tileentityforge, 3, 98, 26));
		addSlotToContainer(new SlotForge(inventoryplayer.player,tileentityforge, 4, 116, 8));
		//fuel stack

		//Storage slot
		addSlotToContainer(new Slot(tileentityforge, 5, 152, 8));
		addSlotToContainer(new Slot(tileentityforge, 6, 152, 26));
		addSlotToContainer(new Slot(tileentityforge, 7, 152, 44));
		addSlotToContainer(new Slot(tileentityforge, 8, 152, 62));

		PlayerInventory.buildInventoryLayout(this, inventoryplayer, 8, 90, false, true);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int slotNum)
	{
		ItemStack origStack = null;
		Slot slot = (Slot)inventorySlots.get(slotNum);
		Slot[] slotfuel =
		{ (Slot) inventorySlots.get(0), (Slot) inventorySlots.get(1), (Slot) inventorySlots.get(2), (Slot) inventorySlots.get(3), (Slot) inventorySlots.get(4) };

		if(slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();

			// From forge to inventory
			if (slotNum < 9)
			{
				if (!this.mergeItemStack(slotStack, 9, this.inventorySlots.size(), true))
					return null;
			}
			// From Inventory to forge
			else
			{
				
				if(slotStack.getItem() == Items.coal)
				{
					int j = 0;
					while (j < 5)
					{
						if (slotfuel[j].getHasStack())
						{
							j++;
						}
						else
						{
							ItemStack stack = slotStack.copy();
							stack.stackSize = 1;
							slotfuel[j].putStack(stack);
							slotStack.stackSize--;
							j = -1;
							break;
						}
					}
					if (j > 0 && !this.mergeItemStack(slotStack, 10, 9, false))
						return null;
				}
				// First try input slots, then storage
				else if (!this.mergeItemStack(slotStack, 0, 5, false) && !this.mergeItemStack(slotStack, 10, 9, false))
					return null;
			}

			if (slotStack.stackSize <= 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();

			if (slotStack.stackSize == origStack.stackSize)
				return null;

			slot.onPickupFromSlot(player, slotStack);
		}

		return origStack;
	}


	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int var1 = 0; var1 < this.crafters.size(); ++var1)
		{
			ICrafting var2 = (ICrafting)this.crafters.get(var1);
			if (this.firetemp != this.forge.fireTemp)
				var2.sendProgressBarUpdate(this, 0, (int)this.forge.fireTemp);
		}
		firetemp = this.forge.fireTemp;
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
			this.forge.fireTemp = par2;
	}
}
