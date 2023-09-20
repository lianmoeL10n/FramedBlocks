package xfacthd.framedblocks.common.block.slopepanelcorner;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.shapes.ShapeProvider;
import xfacthd.framedblocks.api.shapes.ShapeUtils;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.common.block.FramedBlock;
import xfacthd.framedblocks.common.block.slopepanel.FramedExtendedSlopePanelBlock;
import xfacthd.framedblocks.common.block.slopeslab.FramedElevatedSlopeSlabBlock;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.data.property.HorizontalRotation;

@SuppressWarnings("deprecation")
public class FramedExtendedCornerSlopePanelWallBlock extends FramedBlock
{
    public FramedExtendedCornerSlopePanelWallBlock(BlockType blockType)
    {
        super(blockType);
        registerDefaultState(defaultBlockState().setValue(FramedProperties.Y_SLOPE, true));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        super.createBlockStateDefinition(builder);
        builder.add(
                FramedProperties.FACING_HOR, PropertyHolder.ROTATION, FramedProperties.Y_SLOPE,
                FramedProperties.SOLID, BlockStateProperties.WATERLOGGED
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx)
    {
        return FramedCornerSlopePanelWallBlock.getStateForPlacement(
                defaultBlockState(),
                ctx,
                getBlockType() == BlockType.FRAMED_EXT_CORNER_SLOPE_PANEL
        );
    }

    @Override
    public boolean handleBlockLeftClick(BlockState state, Level level, BlockPos pos, Player player)
    {
        return IFramedBlock.toggleYSlope(state, level, pos, player);
    }

    @Override
    public BlockState rotate(BlockState state, BlockHitResult hit, Rotation rot)
    {
        Direction side = hit.getDirection();

        Direction dir = state.getValue(FramedProperties.FACING_HOR);
        HorizontalRotation rotation = state.getValue(PropertyHolder.ROTATION);
        Direction rotDir = rotation.withFacing(dir);
        Direction perpRotDir = rotation.rotate(Rotation.COUNTERCLOCKWISE_90).withFacing(dir);
        switch ((BlockType) getBlockType())
        {
            case FRAMED_EXT_CORNER_SLOPE_PANEL_W ->
            {
                if (side == rotDir.getOpposite() || side == perpRotDir.getOpposite())
                {
                    side = dir;
                }
            }
            case FRAMED_EXT_INNER_CORNER_SLOPE_PANEL_W ->
            {
                if (side == rotDir || side == perpRotDir)
                {
                    Vec3 hitVec = hit.getLocation();
                    double paralell = Utils.fractionInDir(hitVec, dir);
                    double perp = Utils.fractionInDir(hitVec, side == rotDir ? perpRotDir : rotDir) - .5;
                    if (perp * 2D > paralell)
                    {
                        side = dir;
                    }
                }
            }
        }
        return rotate(state, side, rot);
    }

    @Override
    public BlockState rotate(BlockState state, Direction face, Rotation rot)
    {
        Direction dir = state.getValue(FramedProperties.FACING_HOR);
        if (face.getAxis() == dir.getAxis())
        {
            HorizontalRotation rotation = state.getValue(PropertyHolder.ROTATION);
            return state.setValue(PropertyHolder.ROTATION, rotation.rotate(rot));
        }
        else if (Utils.isY(face))
        {
            return state.setValue(FramedProperties.FACING_HOR, rot.rotate(dir));
        }
        return state;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot)
    {
        return rotate(state, state.getValue(FramedProperties.FACING_HOR), rot);
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror)
    {
        return FramedCornerSlopePanelWallBlock.mirrorCornerPanel(state, mirror);
    }



    public static ShapeProvider generateShapes(ImmutableList<BlockState> states)
    {
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        VoxelShape[] shapes = new VoxelShape[4 * 4];
        for (HorizontalRotation rot : HorizontalRotation.values())
        {
            VoxelShape shapeOne = switch (rot)
            {
                case UP, LEFT -> ShapeUtils.rotateShapeUnoptimized(
                        Direction.NORTH,
                        Direction.WEST,
                        FramedExtendedSlopePanelBlock.SHAPES.get(HorizontalRotation.LEFT)
                );
                case DOWN, RIGHT -> ShapeUtils.rotateShapeUnoptimized(
                        Direction.NORTH,
                        Direction.EAST,
                        FramedExtendedSlopePanelBlock.SHAPES.get(HorizontalRotation.RIGHT)
                );
            };
            VoxelShape shapeTwo = switch (rot)
            {
                case UP, RIGHT ->  FramedElevatedSlopeSlabBlock.SHAPES.get(Boolean.TRUE);
                case DOWN, LEFT -> FramedElevatedSlopeSlabBlock.SHAPES.get(Boolean.FALSE);
            };
            VoxelShape preShape = ShapeUtils.andUnoptimized(shapeOne, shapeTwo);

            for (Direction dir : Direction.Plane.HORIZONTAL)
            {
                int idx = dir.get2DDataValue() | (rot.ordinal() << 2);
                shapes[idx] = ShapeUtils.rotateShape(Direction.NORTH, dir, preShape);
            }
        }

        for (BlockState state : states)
        {
            Direction dir = state.getValue(FramedProperties.FACING_HOR);
            HorizontalRotation rot = state.getValue(PropertyHolder.ROTATION);
            int idx = dir.get2DDataValue() | (rot.ordinal() << 2);
            builder.put(state, shapes[idx]);
        }

        return ShapeProvider.of(builder.build());
    }

    public static ShapeProvider generateInnerShapes(ImmutableList<BlockState> states)
    {
        ImmutableMap.Builder<BlockState, VoxelShape> builder = ImmutableMap.builder();

        VoxelShape[] shapes = new VoxelShape[4 * 4];
        for (HorizontalRotation rot : HorizontalRotation.values())
        {
            VoxelShape shapeOne = switch (rot)
            {
                case UP, LEFT -> ShapeUtils.rotateShapeUnoptimized(
                        Direction.NORTH,
                        Direction.EAST,
                        FramedExtendedSlopePanelBlock.SHAPES.get(HorizontalRotation.RIGHT)
                );
                case DOWN, RIGHT -> ShapeUtils.rotateShapeUnoptimized(
                        Direction.NORTH,
                        Direction.WEST,
                        FramedExtendedSlopePanelBlock.SHAPES.get(HorizontalRotation.LEFT)
                );
            };
            VoxelShape shapeTwo = switch (rot)
            {
                case UP, RIGHT -> FramedElevatedSlopeSlabBlock.SHAPES.get(Boolean.FALSE);
                case DOWN, LEFT -> FramedElevatedSlopeSlabBlock.SHAPES.get(Boolean.TRUE);
            };
            VoxelShape preShape = ShapeUtils.orUnoptimized(shapeOne, shapeTwo);

            for (Direction dir : Direction.Plane.HORIZONTAL)
            {
                int idx = dir.get2DDataValue() | (rot.ordinal() << 2);
                shapes[idx] = ShapeUtils.rotateShape(Direction.NORTH, dir, preShape);
            }
        }

        for (BlockState state : states)
        {
            Direction dir = state.getValue(FramedProperties.FACING_HOR);
            HorizontalRotation rot = state.getValue(PropertyHolder.ROTATION);
            int idx = dir.get2DDataValue() | (rot.ordinal() << 2);
            builder.put(state, shapes[idx]);
        }

        return ShapeProvider.of(builder.build());
    }
}