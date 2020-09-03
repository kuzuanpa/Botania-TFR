package vazkii.botania.common.crafting;

import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ChunkEventHandler {
	
	@SubscribeEvent
	public void onLoadWorld(WorldEvent.Load e) {
		if (!e.world.isRemote && e.world.provider.dimensionId == 0 && !ModAnvilRecipes.areAnvilRecipesInitialised()) {
			ModAnvilRecipes.initialiseAnvil(e.world);
		}
	}

	@SubscribeEvent
	public void onUnloadWorld(WorldEvent.Unload e) {

	}

}
