package xfacthd.framedblocks.common.block.pane;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.block.PlacementStateBuilder;
import xfacthd.framedblocks.api.shapes.ShapeProvider;
import xfacthd.framedblocks.common.block.FramedBlock;
import xfacthd.framedblocks.common.data.BlockType;

@SuppressWarnings("deprecation")
public class FramedFloorBlock extends FramedBlock
{
    public FramedFloorBlock()
    {
        super(BlockType.FRAMED_FLOOR_BOARD);
        registerDefaultState(defaultBlockState().setValue(FramedProperties.TOP, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(FramedProperties.TOP, BlockStateProperties.WATERLOGGED, FramedProperties.SOLID);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx)
    {
        return PlacementStateBuilder.of(this, ctx)
                .withTop()
                .withWater()
                .build();
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos)
    {
        boolean top = state.getValue(FramedProperties.TOP);
        return !level.isEmptyBlock(top ? pos.above() : pos.below());
    }

    @Override
    public BlockState updateShape(
            BlockState state,
            Direction side,
            BlockState neighborState,
            LevelAccessor level,
            BlockPos pos,
            BlockPos neighborPos
    )
    {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, side, neighborState, level, pos, neighborPos);
    }



    public static ShapeProvider generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape shapeBottom = box(0, 0, 0, 16, 1, 16);
        VoxelShape shapeTop = box(0, 15, 0, 16, 16, 16);

        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for (BlockState state : states)
        {
            boolean top = state.getValue(FramedProperties.TOP);
            builder.put(state, top ? shapeTop : shapeBottom);
        }

        return ShapeProvider.of(builder.build());
    }
}