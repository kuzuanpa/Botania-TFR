package vazkii.botania.common.item;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Level;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.common.crafting.ModItemHeat;
import vazkii.botania.common.lib.LibOreDict;

import static vazkii.botania.common.item.ModItems.*;

public final class ModItemsOreDict {
    public static void init() {
        OreDictionary.registerOre(LibOreDict.LEXICON, lexicon);
        for (int i = 0; i < 16; i++) {
            OreDictionary.registerOre(LibOreDict.PETAL[i], new ItemStack(petal, 1, i));
            OreDictionary.registerOre(LibOreDict.DYE[i], new ItemStack(dye, 1, i));
            OreDictionary.registerOre(LibOreDict.RUNE[i], new ItemStack(rune, 1, i));
        }
        for (int i = 0; i < 7; i++)
            OreDictionary.registerOre(LibOreDict.QUARTZ[i], new ItemStack(quartz, 1, i));
        OreDictionary.registerOre(LibOreDict.MANA_STEEL, new ItemStack(manaResource, 1, 0));
        OreDictionary.registerOre(LibOreDict.MANA_PEARL, new ItemStack(manaResource, 1, 1));
        OreDictionary.registerOre(LibOreDict.MANA_DIAMOND, new ItemStack(manaResource, 1, 2));
        OreDictionary.registerOre(LibOreDict.LIVINGWOOD_TWIG, new ItemStack(manaResource, 1, 3));
        OreDictionary.registerOre(LibOreDict.TERRA_STEEL, new ItemStack(manaResource, 1, 4));
        OreDictionary.registerOre(LibOreDict.LIFE_ESSENCE, new ItemStack(manaResource, 1, 5));
        OreDictionary.registerOre(LibOreDict.REDSTONE_ROOT, new ItemStack(manaResource, 1, 6));
        OreDictionary.registerOre(LibOreDict.ELEMENTIUM, new ItemStack(manaResource, 1, 7));
        OreDictionary.registerOre(LibOreDict.PIXIE_DUST, new ItemStack(manaResource, 1, 8));
        OreDictionary.registerOre(LibOreDict.DRAGONSTONE, new ItemStack(manaResource, 1, 9));
        OreDictionary.registerOre(LibOreDict.PRISMARINE_SHARD, new ItemStack(manaResource, 1, 10));
        OreDictionary.registerOre(LibOreDict.PLACEHOLDER, new ItemStack(manaResource, 1, 11));
        OreDictionary.registerOre(LibOreDict.RED_STRING, new ItemStack(manaResource, 1, 12));
        OreDictionary.registerOre(LibOreDict.DREAMWOOD_TWIG, new ItemStack(manaResource, 1, 13));
        OreDictionary.registerOre(LibOreDict.GAIA_INGOT, new ItemStack(manaResource, 1, 14));
        OreDictionary.registerOre(LibOreDict.ENDER_AIR_BOTTLE, new ItemStack(manaResource, 1, 15));
        OreDictionary.registerOre(LibOreDict.MANA_STRING, new ItemStack(manaResource, 1, 16));
        OreDictionary.registerOre(LibOreDict.MANASTEEL_NUGGET, new ItemStack(manaResource, 1, 17));
        OreDictionary.registerOre(LibOreDict.TERRASTEEL_NUGGET, new ItemStack(manaResource, 1, 18));
        OreDictionary.registerOre(LibOreDict.ELEMENTIUM_NUGGET, new ItemStack(manaResource, 1, 19));
        OreDictionary.registerOre(LibOreDict.ROOT, new ItemStack(manaResource, 1, 20));
        OreDictionary.registerOre(LibOreDict.PEBBLE, new ItemStack(manaResource, 1, 21));
        OreDictionary.registerOre(LibOreDict.MANAWEAVE_CLOTH, new ItemStack(manaResource, 1, 22));
        OreDictionary.registerOre(LibOreDict.MANA_POWDER, new ItemStack(manaResource, 1, 23));
        OreDictionary.registerOre(LibOreDict.VIAL, new ItemStack(vial, 1, 0));
        OreDictionary.registerOre(LibOreDict.FLASK, new ItemStack(vial, 1, 1));

        BotaniaAPI.blackListItemFromLoonium(lexicon);
        BotaniaAPI.blackListItemFromLoonium(overgrowthSeed);
        BotaniaAPI.blackListItemFromLoonium(blackLotus);

        //TFC
        OreDictionary.registerOre("rodBlaze", Items.blaze_rod);
        OreDictionary.registerOre("powderBlaze", Items.blaze_powder);
        FMLLog.log(Level.INFO,"[Botania] Registered OreDict");
    }
}
