package vazkii.botania.common.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;
import com.bioxx.tfc.Core.TFC_Climate;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.WorldGen.Generators.WorldGenBerryBush;
import com.bioxx.tfc.WorldGen.Generators.WorldGenFungi;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

public class BOT implements IWorldGenerator
{
	private static WorldGenFungi plantFungiGen = new WorldGenFungi();




	private static WorldGenBerryBush wintergreenGen = new WorldGenBerryBush(false, 0, 12, 1, 5);
	private static WorldGenBerryBush blueberryGen = new WorldGenBerryBush(false, 1, 6, 1, 4);
	private static WorldGenBerryBush raspberryGen = new WorldGenBerryBush(false, 2, 5, 2, 4);
	private static WorldGenBerryBush strawberryGen = new WorldGenBerryBush(false, 3, 8, 1, 4);
	private static WorldGenBerryBush blackberryGen = new WorldGenBerryBush(false, 4, 5, 2, 4);
	private static WorldGenBerryBush bunchberryGen = new WorldGenBerryBush(false, 5, 8, 1, 4);
	private static WorldGenBerryBush cranberryGen = new WorldGenBerryBush(false, 6, 15, 1, 6);
	private static WorldGenBerryBush snowberryGen = new WorldGenBerryBush(false, 7, 6, 1, 4);
	private static WorldGenBerryBush elderberryGen = new WorldGenBerryBush(false, 8, 5, 2, 4);
	private static WorldGenBerryBush gooseberryGen = new WorldGenBerryBush(false, 9, 8, 1, 4);
	private static WorldGenBerryBush cloudberryGen = new WorldGenBerryBush(false, 10, 12, 1, 6, TFCBlocks.peatGrass);

	public BOT()
	{
	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		chunkX *= 16;
		chunkZ *= 16;

		int xCoord;
		int yCoord;
		int zCoord;

		int grassPerChunk = 0;
		int flowerChunkRarity = 30;
		int mushroomsPerChunk = 0;

		//float evt = TFC_Climate.getCacheManager(world).getEVTLayerAt(chunkX, chunkZ).floatdata1;
		float rain = TFC_Climate.getRainfall(world, chunkX, 144, chunkZ);
		float bioTemperature;

		/*if(rain >= 62.5f)
		{
		}*/
		if(rain >= 125)
		{
			grassPerChunk+=12;
			mushroomsPerChunk += 1;
		}
		if(rain >= 250)
		{
			grassPerChunk+=18;
			flowerChunkRarity -= 2;
			mushroomsPerChunk += 1;
		}
		if(rain >= 500)
		{
			grassPerChunk+=24;
			flowerChunkRarity -= 3;
			mushroomsPerChunk += 1;
		}
		if(rain >= 1000)
		{
			flowerChunkRarity -= 5;
			mushroomsPerChunk += 1;
		}
		if(rain >= 2000)
		{
			flowerChunkRarity -= 5;
			mushroomsPerChunk += 1;
		}
		bioTemperature = TFC_Climate.getBioTemperatureHeight(world, chunkX, Global.SEALEVEL, chunkZ);
		if(bioTemperature < 10)
		{
			grassPerChunk /= 2;
		}
		if(bioTemperature < 5)
		{
			grassPerChunk /= 2;
		}
		if(bioTemperature < 0)
		{
			grassPerChunk = 0;
		}

		WorldGenFlowersBOT.generate(world, random, chunkX, chunkZ, flowerChunkRarity);
		genBushes(random, chunkX, chunkZ, world);
		for (int i  = 0; i < grassPerChunk; ++i)
		{
			xCoord = chunkX + random.nextInt(16) + 8;
			zCoord = chunkZ + random.nextInt(16) + 8;
			yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
			bioTemperature = TFC_Climate.getBioTemperatureHeight(world, xCoord, yCoord, zCoord);
			if (world.isAirBlock(xCoord, yCoord, zCoord) && 
					TFCBlocks.tallGrass.canBlockStay(world, xCoord, yCoord, zCoord) &&
					!TFC_Core.isDryGrass(world.getBlock(xCoord, yCoord - 1, zCoord)))
			{
				world.setBlock(xCoord, yCoord, zCoord, TFCBlocks.tallGrass, (world.rand.nextInt(20) == 0 ? 1 : 0), 0x2); // tallgrass with 1/20 chance to spawn a fern
			}

			if(bioTemperature >= 0)
			{
				if (world.isAirBlock(xCoord, yCoord, zCoord) && 
						TFCBlocks.tallGrass.canBlockStay(world, xCoord, yCoord, zCoord) &&
						TFC_Core.isDryGrass(world.getBlock(xCoord, yCoord - 1, zCoord)))
				{
					world.setBlock(xCoord, yCoord, zCoord, TFCBlocks.tallGrass, (world.rand.nextInt(60) == 0 ? 1 : 2), 0x2); // shortgrass with 1/60 chance to spawn a fern
				}
			}
		}

		for (int i = 0; i < mushroomsPerChunk; ++i)
		{
			if (random.nextInt(4) == 0)
			{
				xCoord = chunkX + random.nextInt(16) + 8;
				zCoord = chunkZ + random.nextInt(16) + 8;
				yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
				plantFungiGen.genWithMeta(world, random, xCoord, yCoord, zCoord, 0); // vanilla brown mushroom
			}

			if (random.nextInt(8) == 0)
			{
				xCoord = chunkX + random.nextInt(16) + 8;
				zCoord = chunkZ + random.nextInt(16) + 8;
				yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
				plantFungiGen.genWithMeta(world, random, xCoord, yCoord, zCoord, 1); // vanilla red mushroom
			}
		}

		if (random.nextInt(70) == 0 && rain >= 500)
		{
			xCoord = chunkX + random.nextInt(16) + 8;
			zCoord = chunkZ + random.nextInt(16) + 8;
			yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
			bioTemperature = TFC_Climate.getBioTemperatureHeight(world, xCoord, yCoord, zCoord);
		}

	}

	private void genBushes(Random random, int chunkX, int chunkZ, World world)
	{
		int xCoord;
		int yCoord;
		int zCoord;
		if (random.nextInt(12) == 0)
		{
			xCoord = chunkX + random.nextInt(16) + 8;
			zCoord = chunkZ + random.nextInt(16) + 8;
			yCoord = world.getTopSolidOrLiquidBlock(xCoord, zCoord);
			switch(random.nextInt(11))
			{
			default:
				wintergreenGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 1:
				blueberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 2:
				raspberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 3:
				strawberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 4:
				blackberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 5:
				bunchberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 6:
				cranberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 7:
				snowberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 8:
				elderberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 9:
				gooseberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			case 10:
				cloudberryGen.generate(world, random, xCoord, yCoord, zCoord);
				break;
			}
		}
	}
}
