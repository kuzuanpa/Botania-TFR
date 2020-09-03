package vazkii.botania.common.block;

import com.bioxx.tfc.Blocks.BlockTerraContainer;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import vazkii.botania.common.Botania;
import vazkii.botania.common.block.tile.TEForge;
import vazkii.botania.common.lib.LibGuiIDs;

public class BlockManaForge extends BlockTerraContainer {
	private IIcon[] icons;

	protected BlockManaForge() {
		super(Material.rock);
		setBlockName("manaForge");
		
	}
	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		if(!world.isRemote && world.getTileEntity(i, j, k) instanceof TEForge)
		{


			entityplayer.openGui(Botania.instance, LibGuiIDs.MANAFORGE, world, i, j, k);
		}
		return true;
	}
	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEForge();
	}
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}

	@Override
	public IIcon getIcon(int i, int j) 
	{
		if(i < 2)
		{
			return icons[0];
		}

		return icons[1];
	}
	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		icons = new IIcon[2];
	  icons[0] = iconRegisterer.registerIcon("botania" + ":" + "Forgeb");
		icons[1] = iconRegisterer.registerIcon("botania" + ":" + "ForgeSide");
	}
}
