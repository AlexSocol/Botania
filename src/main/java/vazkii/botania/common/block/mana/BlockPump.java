/**
 * This class was created by <Vazkii>. It's distributed as
 * part of the Botania Mod. Get the Source Code in github:
 * https://github.com/Vazkii/Botania
 *
 * Botania is Open Source and distributed under the
 * Botania License: http://botaniamod.net/license.php
 *
 * File Created @ [Mar 17, 2015, 9:46:53 PM (GMT)]
 */
package vazkii.botania.common.block.mana;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import vazkii.botania.api.lexicon.ILexiconable;
import vazkii.botania.api.lexicon.LexiconEntry;
import vazkii.botania.api.state.BotaniaStateProps;
import vazkii.botania.common.block.BlockMod;
import vazkii.botania.common.block.tile.mana.TilePump;
import vazkii.botania.common.lexicon.LexiconData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockPump extends BlockMod implements ILexiconable {

	private static final VoxelShape X_SHAPE = makeCuboidShape(0, 0, 4, 16, 8, 12);
	private static final VoxelShape Z_SHAPE = makeCuboidShape(4, 0, 0, 12, 8, 16);

	public BlockPump(Properties builder) {
		super(builder);
		setDefaultState(getStateContainer().getBaseState().with(BotaniaStateProps.CARDINALS, EnumFacing.SOUTH));
	}

	@Override
	protected void fillStateContainer(StateContainer.Builder<Block, IBlockState> builder) {
		builder.add(BotaniaStateProps.CARDINALS);
	}

	@Nullable
	@Override
	public IBlockState getStateForPlacement(BlockItemUseContext context) {
		return getDefaultState().with(BotaniaStateProps.CARDINALS, context.getNearestLookingDirection().getOpposite());
	}

	@Nonnull
	@Override
	public VoxelShape getShape(IBlockState state, IBlockReader world, BlockPos pos) {
		if(state.get(BotaniaStateProps.CARDINALS).getAxis() == EnumFacing.Axis.X) {
			return X_SHAPE;
		} else {
			return Z_SHAPE;
		}
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
		return ((TilePump) world.getTileEntity(pos)).comparator;
	}

	@Override
	public LexiconEntry getEntry(World world, BlockPos pos, EntityPlayer player, ItemStack lexicon) {
		return LexiconData.poolCart;
	}


	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Nonnull
	@Override
	public TileEntity createTileEntity(@Nonnull IBlockState state, @Nonnull IBlockReader world) {
		return new TilePump();
	}

	@Nonnull
	@Override
	public BlockFaceShape getBlockFaceShape(IBlockReader world, IBlockState state, BlockPos pos, EnumFacing side) {
		return BlockFaceShape.UNDEFINED;
	}
}
