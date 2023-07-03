package xfacthd.framedblocks.common.block.slopeslab;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.*;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.shapes.ShapeProvider;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.block.AbstractFramedDoubleBlock;
import xfacthd.framedblocks.common.blockentity.doubled.FramedFlatInverseDoubleSlopeSlabCornerBlockEntity;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.PropertyHolder;

public class FramedFlatInverseDoubleSlopeSlabCornerBlock extends AbstractFramedDoubleBlock
{
    public FramedFlatInverseDoubleSlopeSlabCornerBlock()
    {
        super(BlockType.FRAMED_FLAT_INV_DOUBLE_SLOPE_SLAB_CORNER);
        registerDefaultState(defaultBlockState()
                .setValue(FramedProperties.TOP, false)
                .setValue(FramedProperties.Y_SLOPE, true)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(
                FramedProperties.FACING_HOR, FramedProperties.TOP, BlockStateProperties.WATERLOGGED,
                FramedProperties.Y_SLOPE
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context)
    {
        BlockState state = withCornerFacing(
                defaultBlockState(),
                context.getClickedFace(),
                context.getHorizontalDirection(),
                context.getClickLocation()
        );

        state = withTop(state, context.getClickedFace(), context.getClickLocation());
        return withWater(state, context.getLevel(), context.getClickedPos());
    }

    @Override
    public boolean handleBlockLeftClick(BlockState state, Level level, BlockPos pos, Player player)
    {
        return IFramedBlock.toggleYSlope(state, level, pos, player);
    }

    @Override
    public BlockState rotate(BlockState state, BlockHitResult hit, Rotation rot)
    {
        Direction face = hit.getDirection();

        Direction dir = state.getValue(FramedProperties.FACING_HOR);
        boolean top = state.getValue(FramedProperties.TOP);
        if (face == dir.getOpposite() || face == dir.getClockWise())
        {
            Vec3 vec = Utils.fraction(hit.getLocation());
            if ((vec.y > .5) != top)
            {
                face = top ? Direction.DOWN : Direction.UP;
            }
        }
        else if (face == dir || face == dir.getCounterClockWise())
        {
            Vec3 vec = Utils.fraction(hit.getLocation());

            Direction perpDir = face == dir.getClockWise() ? dir : dir.getCounterClockWise();
            double hor = Utils.isX(perpDir) ? vec.x() : vec.z();
            if (!Utils.isPositive(perpDir))
            {
                hor = 1D - hor;
            }

            double y = vec.y();
            if (top)
            {
                y -= .5;
            }
            else
            {
                y = .5 - y;
            }
            if ((y * 2D) >= hor)
            {
                face = top ? Direction.DOWN : Direction.UP;
            }
        }

        return rotate(state, face, rot);
    }

    @Override
    public BlockState rotate(BlockState state, Direction face, Rotation rot)
    {
        if (Utils.isY(face))
        {
            Direction dir = state.getValue(FramedProperties.FACING_HOR);
            return state.setValue(FramedProperties.FACING_HOR, rot.rotate(dir));
        }
        else if (rot != Rotation.NONE)
        {
            return state.cycle(FramedProperties.TOP);
        }
        return state;
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState rotate(BlockState state, Rotation rotation)
    {
        return rotate(state, Direction.UP, rotation);
    }

    @Override
    @SuppressWarnings("deprecation")
    public BlockState mirror(BlockState state, Mirror mirror)
    {
        return Utils.mirrorCornerBlock(state, mirror);
    }

    @Override
    protected Tuple<BlockState, BlockState> getBlockPair(BlockState state)
    {
        Direction facing = state.getValue(FramedProperties.FACING_HOR);
        boolean top = state.getValue(FramedProperties.TOP);
        boolean ySlope = state.getValue(FramedProperties.Y_SLOPE);

        return new Tuple<>(
                FBContent.BLOCK_FRAMED_FLAT_INNER_SLOPE_SLAB_CORNER.get()
                        .defaultBlockState()
                        .setValue(FramedProperties.FACING_HOR, facing.getOpposite())
                        .setValue(PropertyHolder.TOP_HALF, top)
                        .setValue(FramedProperties.TOP, !top)
                        .setValue(FramedProperties.Y_SLOPE, ySlope),
                FBContent.BLOCK_FRAMED_FLAT_SLOPE_SLAB_CORNER.get()
                        .defaultBlockState()
                        .setValue(FramedProperties.FACING_HOR, facing)
                        .setValue(PropertyHolder.TOP_HALF, !top)
                        .setValue(FramedProperties.TOP, top)
                        .setValue(FramedProperties.Y_SLOPE, ySlope)
        );
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new FramedFlatInverseDoubleSlopeSlabCornerBlockEntity(pos, state);
    }



    public static ShapeProvider generateShapes(ImmutableList<BlockState> states)
    {
        VoxelShape shapeBot = Shapes.or(
                Shapes.join(
                        FramedSlopeSlabBlock.SHAPE_BOTTOM.move(0, .5, 0),
                        Utils.rotateShape(Direction.NORTH, Direction.WEST, FramedSlopeSlabBlock.SHAPE_BOTTOM.move(0, .5, 0)),
                        BooleanOp.AND
                ),
                Shapes.or(
                        Utils.rotateShape(Direction.NORTH, Direction.SOUTH, FramedSlopeSlabBlock.SHAPE_TOP),
                        Utils.rotateShape(Direction.NORTH, Direction.EAST, FramedSlopeSlabBlock.SHAPE_TOP)
                )
        );

        VoxelShape shapeTop = Shapes.or(
                Shapes.join(
                        FramedSlopeSlabBlock.SHAPE_TOP,
                        Utils.rotateShape(Direction.NORTH, Direction.WEST, FramedSlopeSlabBlock.SHAPE_TOP),
                        BooleanOp.AND
                ),
                Shapes.or(
                        Utils.rotateShape(Direction.NORTH, Direction.SOUTH, FramedSlopeSlabBlock.SHAPE_BOTTOM.move(0, .5, 0)),
                        Utils.rotateShape(Direction.NORTH, Direction.EAST, FramedSlopeSlabBlock.SHAPE_BOTTOM.move(0, .5, 0))
                )
        );

        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        for (BlockState state : states)
        {
            Direction facing = state.getValue(FramedProperties.FACING_HOR);
            boolean top = state.getValue(FramedProperties.TOP);

            VoxelShape shape = Utils.rotateShape(
                    Direction.NORTH,
                    facing,
                    top ? shapeTop : shapeBot
            );
            builder.put(state, shape);
        }

        return ShapeProvider.of(builder.build());
    }

    public static BlockState itemModelSource()
    {
        return FBContent.BLOCK_FRAMED_FLAT_INVERSE_DOUBLE_SLOPE_SLAB_CORNER.get()
                .defaultBlockState()
                .setValue(FramedProperties.FACING_HOR, Direction.SOUTH);
    }
}
