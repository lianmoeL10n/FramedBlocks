package xfacthd.framedblocks.common.block.slab;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.api.data.CamoContainer;
import xfacthd.framedblocks.api.data.EmptyCamoContainer;
import xfacthd.framedblocks.api.util.*;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.block.FramedBlock;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.blockentity.FramedDoubleBlockEntity;
import xfacthd.framedblocks.api.block.FramedBlockEntity;

@SuppressWarnings("deprecation")
public class FramedSlabBlock extends FramedBlock
{
    public FramedSlabBlock()
    {
        super(BlockType.FRAMED_SLAB);
        registerDefaultState(defaultBlockState().setValue(FramedProperties.TOP, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(FramedProperties.TOP, BlockStateProperties.WATERLOGGED, FramedProperties.SOLID, FramedProperties.GLOWING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        return withWater(withTop(defaultBlockState(), context.getClickedFace(), context.getClickLocation()), context.getLevel(), context.getClickedPos());
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        ItemStack stack = player.getItemInHand(hand);
        if (stack.getItem() == FBContent.blockFramedSlab.get().asItem())
        {
            boolean top = state.getValue(FramedProperties.TOP);
            Direction face = hit.getDirection();
            if ((face == Direction.UP && !top) || (face == Direction.DOWN && top))
            {
                if (!level.isClientSide())
                {
                    CamoContainer camo = EmptyCamoContainer.EMPTY;
                    boolean glowing = false;
                    boolean intangible = false;

                    if (level.getBlockEntity(pos) instanceof FramedBlockEntity be)
                    {
                        camo = be.getCamo();
                        glowing = be.isGlowing();
                        intangible = be.isIntangible(null);
                    }

                    level.setBlockAndUpdate(pos, FBContent.blockFramedDoubleSlab.get().defaultBlockState());

                    SoundType sound = FBContent.blockFramedCube.get().getSoundType(FBContent.blockFramedCube.get().defaultBlockState());
                    level.playSound(null, pos, sound.getPlaceSound(), SoundSource.BLOCKS, (sound.getVolume() + 1.0F) / 2.0F, sound.getPitch() * 0.8F);

                    if (!player.isCreative())
                    {
                        stack.shrink(1);
                        player.getInventory().setChanged();
                    }

                    if (level.getBlockEntity(pos) instanceof FramedDoubleBlockEntity be)
                    {
                        be.setCamo(camo, top);
                        be.setGlowing(glowing);
                        be.setIntangible(intangible);
                    }
                }
                return InteractionResult.sidedSuccess(level.isClientSide());
            }
        }
        return super.use(state, level, pos, player, hand, hit);
    }

    @Override
    public boolean isPathfindable(BlockState state, BlockGetter level, BlockPos pos, PathComputationType type)
    {
        return type == PathComputationType.WATER && level.getFluidState(pos).is(FluidTags.WATER);
    }

    @Override
    public BlockState rotate(BlockState state, Direction side, Rotation rot)
    {
        if (rot != Rotation.NONE)
        {
            return state.cycle(FramedProperties.TOP);
        }
        return state;
    }



    public static ImmutableMap<BlockState, VoxelShape> generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape bottomShape = box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D);
        VoxelShape topShape = box(0.0D, 8.0D, 0.0D, 16.0D, 16.0D, 16.0D);

        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for (BlockState state : states)
        {
            builder.put(state, state.getValue(FramedProperties.TOP) ? topShape : bottomShape);
        }

        return builder.build();
    }
}