/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [Oct 11, 2015, 5:05:05 PM (GMT)]
 */
package vazkii.botania.common.block.subtile.generating;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.tileentity.TileEntity;
import org.apache.logging.log4j.Level;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.subtile.ISubTileContainer;
import vazkii.botania.api.subtile.RadiusDescriptor;
import vazkii.botania.api.subtile.SubTileEntity;
import vazkii.botania.api.subtile.SubTileGenerating;
import vazkii.botania.common.lexicon.LexiconData;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class SubTileRafflowsiaPlus extends SubTileGenerating {

	public static final String TAG_LAST_FLOWERS = "lastFlowers";
	public static final String TAG_LAST_FLOWER_TIMES = "lastFlowerTimes";
	public static final String TAG_STREAK_LENGTH = "streakLength";

	private final List<String> lastFlowers = new LinkedList<>();
	private int streakLength = -1;
	private int lastFlowerCount = 0;

	private static final int RANGE = 5;

	// Below table generated from this spreadsheet:
	// https://docs.google.com/spreadsheets/d/1D5qvYRrwm6-czKnXVIEjakt93I0asICgxnPJ_q6UCc0
	// Function created from a best-fit approximation on the sorted raw mana costs of production of each flower.
	private static final int[] STREAK_OUTPUTS = {2000, 2400, 2800, 3280, 4033, 4657, 5150, 6622, 7860, 10418, 12600, 14769, 16671, 19000, 25400, 33471, 40900, 47579, 53600, 59057, 64264, 69217, 74483, 79352, 83869, 88059, 92129, 96669, 100940, 105239, 112044, 118442, 124612, 130583, 136228, 141703, 178442, 213959, 247725, 279956, 313671, 345833, 377227, 437689, 495526, 553702, 638554 };

	private int getMaxStreak() {
		return STREAK_OUTPUTS.length - 1;
	}

	private int getValueForStreak(int index) {
		// special-case repeated first flowers
		if (index != 0) {lastFlowerCount = 0;}
		return STREAK_OUTPUTS[index] / ++lastFlowerCount;
	}
	/**
	 * Processes a flower, placing it in the appropriate place in the history.
	 *
	 * @return the last time the flower showed up in history.
	 */
	private int processFlower(SubTileEntity flower) {
		if(flower==null)return 0;
		String flowerKey = flower.getClass().getName();
		for (ListIterator<String> it = lastFlowers.listIterator(); it.hasNext();) {
			int index = it.nextIndex();
			String streakFlower = it.next();

			if (streakFlower.equals(flowerKey)) {
				it.remove();
				lastFlowers.add(0, streakFlower);
				return index;
			}
		}
		lastFlowers.add(0, flowerKey);
		if (lastFlowers.size() >= getMaxStreak()) {
			lastFlowers.remove(lastFlowers.size() - 1);
		}
		return getMaxStreak();
	}
	@Override
	public void onUpdate() {
		super.onUpdate();

		if (getMaxMana() - this.mana >= mana && !supertile.getWorldObj().isRemote&& ticksExisted % 40 == 0) {
			for (int i = 0; i < RANGE * 2 + 1; i++) {
				for (int j = 0; j < RANGE * 2 + 1; j++) {
					for (int k = 0; k < RANGE * 2 + 1; k++) {
						int x = supertile.xCoord + i - RANGE;
						int y = supertile.yCoord + j - RANGE;
						int z = supertile.zCoord + k - RANGE;

						TileEntity state = supertile.getWorldObj().getTileEntity(x,y,z);
						if(!(state instanceof ISubTileContainer))continue;
						SubTileEntity subTile = ((ISubTileContainer) state).getSubTile();
						if (subTile instanceof SubTileGenerating && !(subTile instanceof SubTileRafflowsiaPlus)) {
							streakLength = Math.min(streakLength + 1, processFlower(subTile));

							supertile.getWorldObj().setBlockToAir(x,y,z);
							addMana(getValueForStreak(streakLength));
							sync();
							return;
						}
					}
				}
			}
		}
	}

	@Override
	public void writeToPacketNBT(NBTTagCompound cmp) {
		super.writeToPacketNBT(cmp);

		NBTTagList flowerList = new NBTTagList();
		for (String flower : lastFlowers) {
			flowerList.appendTag(new NBTTagString(flower));
		}
		cmp.setTag(TAG_LAST_FLOWERS, flowerList);
		cmp.setInteger(TAG_LAST_FLOWER_TIMES, lastFlowerCount);
		cmp.setInteger(TAG_STREAK_LENGTH, streakLength);
	}

	@Override
	public void readFromPacketNBT(NBTTagCompound cmp) {
		super.readFromPacketNBT(cmp);

		lastFlowers.clear();
		NBTTagList flowerList = cmp.getTagList(TAG_LAST_FLOWERS, 8/*NBTTagString*/);
		for (int i = 0; i < flowerList.tagCount(); i++) {
			String blockID = flowerList.getStringTagAt(i);
			if (blockID == null) {
				continue;
			}
			lastFlowers.add(blockID);
		}
		lastFlowerCount = cmp.getInteger(TAG_LAST_FLOWER_TIMES);
		streakLength = cmp.getInteger(TAG_STREAK_LENGTH);
	}
	@Override
	public RadiusDescriptor getRadius() {
		return new RadiusDescriptor.Square(toChunkCoordinates(), RANGE);
	}

	@Override
	public int getColor() {
		return 0x502C76;
	}

	@Override
	public int getMaxMana() {
		return 630000;
	}

	@Override
	public LexiconEntry getEntry() {
		return LexiconData.rafflowsiaPlus;
	}

}
