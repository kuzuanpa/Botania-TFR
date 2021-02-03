package vazkii.botania.common.crafting;

import java.util.Map;

import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Crafting.AnvilRecipe;
import com.bioxx.tfc.api.Crafting.AnvilReq;
import com.bioxx.tfc.api.Crafting.PlanRecipe;
import com.bioxx.tfc.api.Enums.RuleEnum;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import vazkii.botania.common.item.ModItems;

public class ModAnvilRecipes {
	public static void initialise() 
	{
		
	}
	public static void initialiseAnvil(World world) {
			AnvilManager anvilManager = AnvilManager.getInstance();
			AnvilManager.world = world;
			
			registerAnvilPlans(anvilManager);
			registerAnvilRecipes(anvilManager);
		}
	public static boolean areAnvilRecipesInitialised() {
		Map<String, PlanRecipe> map = AnvilManager.getInstance().getPlans();
		return map.containsKey("groove");
	}
	private static void registerAnvilPlans(AnvilManager anvilManager) {
		anvilManager.addPlan("pickaxe", new PlanRecipe(new RuleEnum[] {RuleEnum.PUNCHLAST, RuleEnum.BENDNOTLAST, RuleEnum.DRAWNOTLAST}));
		anvilManager.addPlan("sword", new PlanRecipe(new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.BENDSECONDFROMLAST, RuleEnum.BENDTHIRDFROMLAST}));
		anvilManager.addPlan("helmplate", new PlanRecipe(new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.BENDSECONDFROMLAST, RuleEnum.BENDTHIRDFROMLAST}));
		anvilManager.addPlan("chestplate", new PlanRecipe(new RuleEnum[]{RuleEnum.HITLAST, RuleEnum.HITSECONDFROMLAST, RuleEnum.UPSETTHIRDFROMLAST}));
		anvilManager.addPlan("legsplate", new PlanRecipe(new RuleEnum[]{RuleEnum.BENDANY, RuleEnum.DRAWANY, RuleEnum.HITANY}));
		anvilManager.addPlan("bootsplate", new PlanRecipe(new RuleEnum[]{RuleEnum.BENDLAST, RuleEnum.BENDSECONDFROMLAST, RuleEnum.SHRINKTHIRDFROMLAST}));
	}
	
	private static void registerAnvilRecipes(AnvilManager anvilManager) {
	anvilManager.addRecipe(new AnvilRecipe(new ItemStack(ModItems.manaResource, 1, 0),(new ItemStack(ModItems.manaResource, 1, 0)), "pickaxe", AnvilReq.WROUGHTIRON, new ItemStack(ModItems.MANA_STEELPickaxe)).addRecipeSkill(Global.SKILL_TOOLSMITH));
	anvilManager.addRecipe(new AnvilRecipe(new ItemStack(ModItems.manaResource, 1, 7),(new ItemStack(ModItems.manaResource, 1, 7)), "pickaxe", AnvilReq.BLUESTEEL, new ItemStack(ModItems.elementiumPickaxe)).addRecipeSkill(Global.SKILL_TOOLSMITH));
	anvilManager.addRecipe(new AnvilRecipe(new ItemStack(ModItems.manaResource, 1, 4),(new ItemStack(ModItems.manaResource, 1, 4)), "pickaxe", AnvilReq.BLUESTEEL, new ItemStack(ModItems.terrasteelPickaxe)).addRecipeSkill(Global.SKILL_TOOLSMITH));
	
	anvilManager.addRecipe(new AnvilRecipe(new ItemStack(ModItems.manaResource, 1, 0),(new ItemStack(ModItems.manaResource, 1, 0)), "sword", AnvilReq.WROUGHTIRON, new ItemStack(ModItems.manasteelSword)).addRecipeSkill(Global.SKILL_TOOLSMITH));
	anvilManager.addRecipe(new AnvilRecipe(new ItemStack(ModItems.manaResource, 1, 7),(new ItemStack(ModItems.manaResource, 1, 7)), "sword", AnvilReq.BLUESTEEL, new ItemStack(ModItems.elementiumSword)).addRecipeSkill(Global.SKILL_TOOLSMITH));
	anvilManager.addRecipe(new AnvilRecipe(new ItemStack(ModItems.manaResource, 1, 4),(new ItemStack(ModItems.manaResource, 1, 4)), "sword", AnvilReq.BLUESTEEL, new ItemStack(ModItems.terraSword)).addRecipeSkill(Global.SKILL_TOOLSMITH));
	
	
	anvilManager.addRecipe(new AnvilRecipe(new ItemStack(ModItems.terrasteelTK, 1), (new ItemStack(ModItems.manaResource, 1, 4)),"helmplate", AnvilReq.BLUESTEEL, new ItemStack(ModItems.terrasteelHelm, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
	anvilManager.addRecipe(new AnvilRecipe(new ItemStack(ModItems.terrasteelXJ, 1), (new ItemStack(ModItems.manaResource, 1, 4)),"chestplate", AnvilReq.BLUESTEEL, new ItemStack(ModItems.terrasteelChest, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
	anvilManager.addRecipe(new AnvilRecipe(new ItemStack(ModItems.terrasteelKZ, 1), (new ItemStack(ModItems.manaResource, 1, 4)),"legsplate", AnvilReq.BLUESTEEL, new ItemStack(ModItems.terrasteelLegs, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
	anvilManager.addRecipe(new AnvilRecipe(new ItemStack(ModItems.terrasteelXZ, 1), (new ItemStack(ModItems.manaResource, 1, 4)),"bootsplate", AnvilReq.BLUESTEEL, new ItemStack(ModItems.terrasteelBoots, 1)).addRecipeSkill(Global.SKILL_ARMORSMITH).setCraftingXP(3));
	
	}		
}