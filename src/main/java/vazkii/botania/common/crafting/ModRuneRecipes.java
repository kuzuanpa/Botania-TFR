/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 * 
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 * 
 * File Created @ [Feb 6, 2014, 5:59:28 PM (GMT)]
 */
package vazkii.botania.common.crafting;

import java.util.ArrayList;
import java.util.List;

import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.recipe.RecipeRuneAltar;
import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.crafting.recipe.HeadRecipe;
import vazkii.botania.common.item.ItemManaCookie;
import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lib.LibOreDict;

public final class ModRuneRecipes {

	public static RecipeRuneAltar recipeWaterRune;
	public static RecipeRuneAltar recipeWaterRune2;
	public static RecipeRuneAltar recipeFireRune;
	public static List<RecipeRuneAltar> recipesEarthRune;
	public static List<RecipeRuneAltar> recipesAirRune;
	public static RecipeRuneAltar recipeSpringRune;
	public static RecipeRuneAltar recipeSummerRune;
	public static RecipeRuneAltar recipeAutumnRune;
	public static List<RecipeRuneAltar> recipesWinterRune;
	public static RecipeRuneAltar recipeManaRune;
	public static RecipeRuneAltar recipeLustRune;
	public static RecipeRuneAltar recipeGluttonyRune;
	public static RecipeRuneAltar recipeGreedRune;
	public static RecipeRuneAltar recipeSlothRune;
	public static RecipeRuneAltar recipeWrathRune;
	public static RecipeRuneAltar recipeEnvyRune;
	public static RecipeRuneAltar recipePrideRune;
	public static RecipeRuneAltar end;
	public static RecipeRuneAltar end2;
	public static RecipeRuneAltar recipeHead;

	public static void init() {
		final int end1 = 10000;
		final int costTier1 = 5200;
		final int costTier2 = 8000;
		final int costTier3 = 12000;
		end = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.manaResource, 1, 15),end1,new ItemStack(TFCItems.glassBottle),new ItemStack(Blocks.end_stone));
		end2 = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.manaResource, 1, 15),end1,new ItemStack(Items.glass_bottle),new ItemStack(Blocks.end_stone));
		recipeWaterRune = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.rune, 2, 0), costTier1, LibOreDict.SXQ, LibOreDict.PETAL[11], LibOreDict.PETAL[11], "rockGtPetrifiedWood");
		recipeWaterRune2 = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.rune, 2, 0), costTier1, LibOreDict.SXQ, LibOreDict.PETAL[3], LibOreDict.PETAL[3], "rockGtPetrifiedWood");
		recipeFireRune = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.rune, 2, 1), costTier1, "rockGtPetrifiedWood", LibOreDict.MANA_STEEL, new ItemStack(Items.coal), new ItemStack(Items.gunpowder), new ItemStack(TFCBlocks.thatch),"rockGtPetrifiedWood");

		recipesEarthRune = new ArrayList();
		recipesEarthRune.add(BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.rune, 2, 2), costTier1, LibOreDict.MANA_POWDER, LibOreDict.MANA_STEEL, "stone", new ItemStack(Items.coal), new ItemStack(TFCBlocks.tallGrass),"rockGtPetrifiedWood"));


		recipesAirRune = new ArrayList();
		
			recipesAirRune.add(BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.rune, 2, 3), costTier1, LibOreDict.MANA_POWDER, LibOreDict.MANA_STEEL, new ItemStack(TFCItems.woolYarn), new ItemStack(Blocks.carpet, 1, 0), new ItemStack(Items.string),"rockGtPetrifiedWood"));

		recipeSpringRune = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.rune, 1, 4), costTier2, LibOreDict.RUNE[0], LibOreDict.RUNE[1], "treeSapling", "treeSapling", "treeSapling", ItemManaCookie.createTag(new ItemStack(TFCItems.wheatWhole),80));
		recipeSpringRune = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.rune, 1, 4), costTier2, LibOreDict.RUNE[0], LibOreDict.RUNE[1], "treeSapling", "treeSapling", "treeSapling", ItemManaCookie.createTag(new ItemStack(TFCItems.barleyWhole),80));
		
		recipeSummerRune = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.rune, 1, 5), costTier2, LibOreDict.RUNE[2], LibOreDict.RUNE[3], new ItemStack(Block.getBlockFromName("sand")), new ItemStack(Block.getBlockFromName("sand")),LibOreDict.MANA_POWDER, LibOreDict.MANA_STEEL);
		recipeAutumnRune = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.rune, 1, 6), costTier2, LibOreDict.RUNE[1], LibOreDict.RUNE[3], "treeLeaves", "treeLeaves", "treeLeaves", new ItemStack(Items.spider_eye));

		recipesWinterRune = new ArrayList();
		
			recipesWinterRune.add(BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.rune, 1, 7), costTier2, LibOreDict.RUNE[0], LibOreDict.RUNE[2], new ItemStack(Blocks.snow), new ItemStack(Blocks.snow), new ItemStack(TFCItems.woolCloth)));

		recipeManaRune = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.rune, 1, 8), costTier2, LibOreDict.MANA_STEEL, LibOreDict.MANA_STEEL, LibOreDict.MANA_STEEL, LibOreDict.MANA_STEEL, LibOreDict.MANA_STEEL, LibOreDict.MANA_POWDER,"rockGtPetrifiedWood");

		recipeLustRune = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.rune, 1, 9), costTier3, LibOreDict.MANA_DIAMOND, LibOreDict.MANA_DIAMOND, LibOreDict.RUNE[5], LibOreDict.RUNE[3]);
		recipeGluttonyRune = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.rune, 1, 10), costTier3, LibOreDict.MANA_DIAMOND, LibOreDict.MANA_DIAMOND, LibOreDict.RUNE[7], LibOreDict.RUNE[1]);
		recipeGreedRune = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.rune, 1, 11), costTier3, LibOreDict.MANA_DIAMOND, LibOreDict.MANA_DIAMOND, LibOreDict.RUNE[4], LibOreDict.RUNE[0]);
		recipeSlothRune = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.rune, 1, 12), costTier3, LibOreDict.MANA_DIAMOND, LibOreDict.MANA_DIAMOND, LibOreDict.RUNE[6], LibOreDict.RUNE[3]);
		recipeWrathRune = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.rune, 1, 13), costTier3, LibOreDict.MANA_DIAMOND, LibOreDict.MANA_DIAMOND, LibOreDict.RUNE[7], LibOreDict.RUNE[2]);
		recipeEnvyRune = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.rune, 1, 14), costTier3, LibOreDict.MANA_DIAMOND, LibOreDict.MANA_DIAMOND, LibOreDict.RUNE[7], LibOreDict.RUNE[0]);
		recipePrideRune = BotaniaAPI.registerRuneAltarRecipe(new ItemStack(ModItems.rune, 1, 15), costTier3, LibOreDict.MANA_DIAMOND, LibOreDict.MANA_DIAMOND, LibOreDict.RUNE[5], LibOreDict.RUNE[1]);

		recipeHead = new HeadRecipe(new ItemStack(Items.skull, 1, 3), 22500, new ItemStack(Items.skull), LibOreDict.PIXIE_DUST, LibOreDict.PRISMARINE_SHARD, new ItemStack(Items.name_tag), new ItemStack(Items.golden_apple));
		BotaniaAPI.runeAltarRecipes.add(recipeHead);
	}
}
