package vazkii.botania.common.crafting;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.Phase;
import cpw.mods.fml.common.gameevent.TickEvent.WorldTickEvent;

public class ServerTickHandler 
{
	private long wSeed = Long.MIN_VALUE;
	
	@SubscribeEvent 
    public void onServerWorldTick(WorldTickEvent e) 
    { 
        if (e.phase == Phase.START) 
        { 
        	if (e.world.provider.dimensionId == 0 && e.world.getWorldInfo().getSeed() != wSeed) {
        		ModAnvilRecipes.initialiseAnvil(e.world);
        		wSeed = e.world.getWorldInfo().getSeed();
        	}
        } 
    } 
}
