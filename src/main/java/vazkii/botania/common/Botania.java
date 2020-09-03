/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [Jan 13, 2014, 6:32:39 PM (GMT)]
 */
package vazkii.botania.common;


import vazkii.botania.common.core.handler.IMCHandler;
import vazkii.botania.common.core.handler.ManaNetworkHandler;
import vazkii.botania.common.core.proxy.CommonProxy;
import vazkii.botania.common.crafting.ChunkEventHandler;
import vazkii.botania.common.crafting.InitClientWorldPacket;
import vazkii.botania.common.crafting.ModPlayerTracker;
import vazkii.botania.common.integration.coloredlights.ILightHelper;
import vazkii.botania.common.integration.coloredlights.LightHelperColored;
import vazkii.botania.common.integration.coloredlights.LightHelperVanilla;
import vazkii.botania.common.item.ItemModTFC;
import vazkii.botania.common.lib.LibMisc;
import vazkii.botania.common.world.BOT;

import com.bioxx.tfc.TerraFirmaCraft;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.ModAPIManager;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLInterModComms;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerAboutToStartEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.event.FMLServerStoppingEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = LibMisc.MOD_ID, name = LibMisc.MOD_NAME, version = LibMisc.VERSION, dependencies = LibMisc.DEPENDENCIES, guiFactory = LibMisc.GUI_FACTORY)
public class Botania {

	public static boolean gardenOfGlassLoaded = false;

	public static boolean thaumcraftLoaded = false;
	public static boolean bcTriggersLoaded = false;
	public static boolean bloodMagicLoaded = false;
	public static boolean coloredLightsLoaded = false;
	public static boolean etFuturumLoaded = false;
	public static boolean storageDrawersLoaded = false;
	public static ILightHelper lightHelper;

	@Instance(LibMisc.MOD_ID)
	public static Botania instance;
	@SidedProxy(serverSide = LibMisc.PROXY_COMMON, clientSide = LibMisc.PROXY_CLIENT)
	public static CommonProxy proxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ItemModTFC TFC = new ItemModTFC();
		GameRegistry.registerWorldGenerator(new BOT(), 0);
		gardenOfGlassLoaded = Loader.isModLoaded("GardenOfGlass");
		thaumcraftLoaded = Loader.isModLoaded("Thaumcraft");
		bcTriggersLoaded = ModAPIManager.INSTANCE.hasAPI("BuildCraftAPI|statements");
		bloodMagicLoaded = Loader.isModLoaded("AWWayofTime"); // Psh, noob
		coloredLightsLoaded = Loader.isModLoaded("easycoloredlights");
		etFuturumLoaded = Loader.isModLoaded("etfuturum");
		storageDrawersLoaded = Loader.isModLoaded("StorageDrawers");
		
		lightHelper = coloredLightsLoaded ? new LightHelperColored() : new LightHelperVanilla();

		proxy.registerTickHandler();
		
		proxy.preInit(event);
	}
	@EventHandler
	public void init(FMLInitializationEvent event) {
		TerraFirmaCraft.PACKET_PIPELINE.registerPacket(InitClientWorldPacket.class);
		FMLCommonHandler.instance().bus().register(new ModPlayerTracker());
		MinecraftForge.EVENT_BUS.register(new ChunkEventHandler());
		proxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		proxy.postInit(event);
	}

	@EventHandler
	public void serverStarting(FMLServerAboutToStartEvent event) {
		proxy.serverAboutToStart(event);
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		proxy.serverStarting(event);
	}

	@EventHandler
	public void serverStopping(FMLServerStoppingEvent event) {
		ManaNetworkHandler.instance.clear();
	}

	@EventHandler
	public void handleIMC(FMLInterModComms.IMCEvent event) {
		IMCHandler.processMessages(event.getMessages());
	}

	@EventHandler
	public void preInitialize(FMLPreInitializationEvent e) {
	/*@EventHandler
	public void missingMappings(FMLMissingMappingsEvent event) {
		AliasHandler.onMissingMappings(event);
	}*/
	}
}
