package xfacthd.framedblocks.common.data.skippreds.slopepanelcorner;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.block.IFramedBlock;
import xfacthd.framedblocks.api.predicate.cull.SideSkipPredicate;
import xfacthd.framedblocks.common.block.AbstractFramedDoubleBlock;
import xfacthd.framedblocks.common.data.BlockType;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.data.property.HorizontalRotation;
import xfacthd.framedblocks.common.data.property.StairsType;
import xfacthd.framedblocks.common.data.skippreds.*;
import xfacthd.framedblocks.common.data.skippreds.slab.SlabCornerSkipPredicate;
import xfacthd.framedblocks.common.data.skippreds.slab.SlabEdgeSkipPredicate;
import xfacthd.framedblocks.common.data.skippreds.slopepanel.*;
import xfacthd.framedblocks.common.data.skippreds.slopeslab.*;
import xfacthd.framedblocks.common.data.skippreds.stairs.*;

@CullTest(BlockType.FRAMED_SMALL_CORNER_SLOPE_PANEL_W)
public final class SmallCornerSlopePanelWallSkipPredicate implements SideSkipPredicate
{
    @Override
    public boolean test(BlockGetter level, BlockPos pos, BlockState state, BlockState adjState, Direction side)
    {
        if (adjState.getBlock() instanceof IFramedBlock block && block.getBlockType() instanceof BlockType type)
        {
            Direction dir = state.getValue(FramedProperties.FACING_HOR);
            HorizontalRotation rot = state.getValue(PropertyHolder.ROTATION);

            return switch (type)
            {
                case FRAMED_SMALL_CORNER_SLOPE_PANEL_W -> testAgainstSmallCornerSlopePanelWall(
                        dir, rot, adjState, side
                );
                case FRAMED_SMALL_INNER_CORNER_SLOPE_PANEL_W -> testAgainstSmallInnerCornerSlopePanelWall(
                        dir, rot, adjState, side
                );
                case FRAMED_LARGE_INNER_CORNER_SLOPE_PANEL_W -> testAgainstLargeInnerCornerSlopePanelWall(
                        dir, rot, adjState, side
                );
                case FRAMED_EXT_CORNER_SLOPE_PANEL_W -> testAgainstExtendedCornerSlopePanelWall(
                        dir, rot, adjState, side
                );
                case FRAMED_SMALL_DOUBLE_CORNER_SLOPE_PANEL_W -> testAgainstSmallDoubleCornerSlopePanelWall(
                        dir, rot, adjState, side
                );
                case FRAMED_LARGE_DOUBLE_CORNER_SLOPE_PANEL_W -> testAgainstLargeDoubleCornerSlopePanelWall(
                        dir, rot, adjState, side
                );
                case FRAMED_INV_DOUBLE_CORNER_SLOPE_PANEL_W -> testAgainstInverseDoubleCornerSlopePanelWall(
                        dir, rot, adjState, side
                );
                case FRAMED_EXT_DOUBLE_CORNER_SLOPE_PANEL_W -> testAgainstExtendedDoubleCornerSlopePanelWall(
                        dir, rot, adjState, side
                );
                case FRAMED_EXT_INNER_DOUBLE_CORNER_SLOPE_PANEL_W -> testAgainstExtendedInnerDoubleCornerSlopePanelWall(
                        dir, rot, adjState, side
                );
                case FRAMED_STACKED_CORNER_SLOPE_PANEL_W -> testAgainstStackedCornerSlopePanelWall(
                        dir, rot, adjState, side
                );
                case FRAMED_STACKED_INNER_CORNER_SLOPE_PANEL_W -> testAgainstStackedInnerCornerSlopePanelWall(
                        dir, rot, adjState, side
                );
                case FRAMED_SLOPE_SLAB -> testAgainstSlopeSlab(
                        dir, rot, adjState, side
                );
                case FRAMED_DOUBLE_SLOPE_SLAB -> testAgainstDoubleSlopeSlab(
                        dir, rot, adjState, side
                );
                case FRAMED_ELEVATED_DOUBLE_SLOPE_SLAB -> testAgainstElevatedDoubleSlopeSlab(
                        dir, rot, adjState, side
                );
                case FRAMED_FLAT_SLOPE_SLAB_CORNER -> testAgainstFlatSlopeSlabCorner(
                        dir, rot, adjState, side
                );
                case FRAMED_FLAT_INNER_SLOPE_SLAB_CORNER -> testAgainstFlatInnerSlopeSlabCorner(
                        dir, rot, adjState, side
                );
                case FRAMED_FLAT_DOUBLE_SLOPE_SLAB_CORNER -> testAgainstFlatDoubleSlopeSlabCorner(
                        dir, rot, adjState, side
                );
                case FRAMED_FLAT_ELEV_DOUBLE_SLOPE_SLAB_CORNER -> testAgainstFlatElevatedDoubleSlopeSlabCorner(
                        dir, rot, adjState, side
                );
                case FRAMED_FLAT_ELEV_INNER_DOUBLE_SLOPE_SLAB_CORNER -> testAgainstFlatElevatedInnerDoubleSlopeSlabCorner(
                        dir, rot, adjState, side
                );
                case FRAMED_SLOPE_PANEL -> testAgainstSlopePanel(
                        dir, rot, adjState, side
                );
                case FRAMED_DOUBLE_SLOPE_PANEL -> testAgainstDoubleSlopePanel(
                        dir, rot, adjState, side
                );
                case FRAMED_EXTENDED_DOUBLE_SLOPE_PANEL -> testAgainstExtendedDoubleSlopePanel(
                        dir, rot, adjState, side
                );
                case FRAMED_FLAT_SLOPE_PANEL_CORNER -> testAgainstFlatSlopePanelCorner(
                        dir, rot, adjState, side
                );
                case FRAMED_FLAT_INNER_SLOPE_PANEL_CORNER -> testAgainstFlatInnerSlopePanelCorner(
                        dir, rot, adjState, side
                );
                case FRAMED_FLAT_DOUBLE_SLOPE_PANEL_CORNER -> testAgainstFlatDoubleSlopePanelCorner(
                        dir, rot, adjState, side
                );
                case FRAMED_FLAT_EXT_DOUBLE_SLOPE_PANEL_CORNER -> testAgainstFlatExtendedDoubleSlopePanelCorner(
                        dir, rot, adjState, side
                );
                case FRAMED_FLAT_EXT_INNER_DOUBLE_SLOPE_PANEL_CORNER -> testAgainstFlatExtendedInnerDoubleSlopePanelCorner(
                        dir, rot, adjState, side
                );
                case FRAMED_SLAB_EDGE -> testAgainstSlabEdge(
                        dir, rot, adjState, side
                );
                case FRAMED_SLAB_CORNER -> testAgainstSlabCorner(
                        dir, rot, adjState, side
                );
                case FRAMED_VERTICAL_STAIRS -> testAgainstVerticalStairs(
                        dir, rot, adjState, side
                );
                case FRAMED_HALF_STAIRS -> testAgainstHalfStairs(
                        dir, rot, adjState, side
                );
                case FRAMED_VERTICAL_HALF_STAIRS -> testAgainstVerticalHalfStairs(
                        dir, rot, adjState, side
                );
                case FRAMED_DOUBLE_STAIRS -> testAgainstDoubleStairs(
                        dir, rot, adjState, side
                );
                case FRAMED_DIVIDED_SLAB -> testAgainstDividedSlab(
                        dir, rot, adjState, side
                );
                case FRAMED_DIVIDED_PANEL_HORIZONTAL -> testAgainstDividedPanelHorizontal(
                        dir, rot, adjState, side
                );
                case FRAMED_DIVIDED_STAIRS -> testAgainstDividedStairs(
                        dir, rot, adjState, side
                );
                case FRAMED_VERTICAL_DIVIDED_STAIRS -> testAgainstVerticalDividedStairs(
                        dir, rot, adjState, side
                );
                default -> false;
            };
        }
        return false;
    }

    @CullTest.SingleTarget(BlockType.FRAMED_SMALL_CORNER_SLOPE_PANEL_W)
    private static boolean testAgainstSmallCornerSlopePanelWall(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        HorizontalRotation adjRot = adjState.getValue(PropertyHolder.ROTATION);

        if (getTriDir(dir, rot, side).isEqualTo(getTriDir(adjDir, adjRot, side.getOpposite())))
        {
            return true;
        }
        if (getCornerDir(dir, rot, side).isEqualTo(getCornerDir(adjDir, adjRot, side.getOpposite())))
        {
            return true;
        }
        return false;
    }

    @CullTest.SingleTarget(BlockType.FRAMED_SMALL_INNER_CORNER_SLOPE_PANEL_W)
    private static boolean testAgainstSmallInnerCornerSlopePanelWall(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        HorizontalRotation adjRot = adjState.getValue(PropertyHolder.ROTATION);

        return getCornerDir(dir, rot, side).isEqualTo(SmallInnerCornerSlopePanelWallSkipPredicate.getCornerDir(adjDir, adjRot, side.getOpposite()));
    }

    @CullTest.SingleTarget(BlockType.FRAMED_LARGE_INNER_CORNER_SLOPE_PANEL_W)
    private static boolean testAgainstLargeInnerCornerSlopePanelWall(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        HorizontalRotation adjRot = adjState.getValue(PropertyHolder.ROTATION);

        return getTriDir(dir, rot, side).isEqualTo(LargeInnerCornerSlopePanelWallSkipPredicate.getTriDir(adjDir, adjRot, side.getOpposite()));
    }

    @CullTest.SingleTarget(BlockType.FRAMED_EXT_CORNER_SLOPE_PANEL_W)
    private static boolean testAgainstExtendedCornerSlopePanelWall(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        HorizontalRotation adjRot = adjState.getValue(PropertyHolder.ROTATION);

        return getCornerDir(dir, rot, side).isEqualTo(ExtendedCornerSlopePanelWallSkipPredicate.getCornerDir(adjDir, adjRot, side.getOpposite()));
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_SMALL_DOUBLE_CORNER_SLOPE_PANEL_W,
            partTargets = { BlockType.FRAMED_SMALL_INNER_CORNER_SLOPE_PANEL_W, BlockType.FRAMED_SMALL_CORNER_SLOPE_PANEL_W }
    )
    private static boolean testAgainstSmallDoubleCornerSlopePanelWall(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstSmallInnerCornerSlopePanelWall(dir, rot, states.getA(), side) ||
               testAgainstSmallCornerSlopePanelWall(dir, rot, states.getB(), side);
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_LARGE_DOUBLE_CORNER_SLOPE_PANEL_W,
            partTargets = BlockType.FRAMED_LARGE_INNER_CORNER_SLOPE_PANEL_W
    )
    private static boolean testAgainstLargeDoubleCornerSlopePanelWall(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstLargeInnerCornerSlopePanelWall(dir, rot, states.getA(), side);
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_INV_DOUBLE_CORNER_SLOPE_PANEL_W,
            partTargets = BlockType.FRAMED_SMALL_INNER_CORNER_SLOPE_PANEL_W
    )
    private static boolean testAgainstInverseDoubleCornerSlopePanelWall(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstSmallInnerCornerSlopePanelWall(dir, rot, states.getB(), side);
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_EXT_DOUBLE_CORNER_SLOPE_PANEL_W,
            partTargets = { BlockType.FRAMED_EXT_CORNER_SLOPE_PANEL_W, BlockType.FRAMED_LARGE_INNER_CORNER_SLOPE_PANEL_W }
    )
    private static boolean testAgainstExtendedDoubleCornerSlopePanelWall(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstExtendedCornerSlopePanelWall(dir, rot, states.getA(), side) ||
               testAgainstLargeInnerCornerSlopePanelWall(dir, rot, states.getB(), side);
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_EXT_INNER_DOUBLE_CORNER_SLOPE_PANEL_W,
            partTargets = BlockType.FRAMED_SMALL_CORNER_SLOPE_PANEL_W
    )
    private static boolean testAgainstExtendedInnerDoubleCornerSlopePanelWall(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstSmallCornerSlopePanelWall(dir, rot, states.getB(), side);
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_STACKED_CORNER_SLOPE_PANEL_W,
            partTargets = BlockType.FRAMED_SLAB_EDGE
    )
    private static boolean testAgainstStackedCornerSlopePanelWall(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstSlabEdge(dir, rot, states.getA(), side);
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_STACKED_INNER_CORNER_SLOPE_PANEL_W,
            partTargets = BlockType.FRAMED_SMALL_INNER_CORNER_SLOPE_PANEL_W
    )
    private static boolean testAgainstStackedInnerCornerSlopePanelWall(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstSmallInnerCornerSlopePanelWall(dir, rot, states.getB(), side);
    }

    @CullTest.SingleTarget(BlockType.FRAMED_SLOPE_SLAB)
    private static boolean testAgainstSlopeSlab(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);
        boolean adjTopHalf = adjState.getValue(PropertyHolder.TOP_HALF);

        return getTriDir(dir, rot, side).isEqualTo(SlopeSlabSkipPredicate.getTriDir(adjDir, adjTopHalf, adjTop, side.getOpposite()));
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_DOUBLE_SLOPE_SLAB,
            partTargets = BlockType.FRAMED_SLOPE_SLAB
    )
    private static boolean testAgainstDoubleSlopeSlab(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstSlopeSlab(dir, rot, states.getA(), side) ||
               testAgainstSlopeSlab(dir, rot, states.getB(), side);
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_ELEVATED_DOUBLE_SLOPE_SLAB,
            partTargets = BlockType.FRAMED_SLOPE_SLAB
    )
    private static boolean testAgainstElevatedDoubleSlopeSlab(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstSlopeSlab(dir, rot, states.getB(), side);
    }

    @CullTest.SingleTarget(BlockType.FRAMED_FLAT_SLOPE_SLAB_CORNER)
    private static boolean testAgainstFlatSlopeSlabCorner(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);
        boolean adjTopHalf = adjState.getValue(PropertyHolder.TOP_HALF);

        return getTriDir(dir, rot, side).isEqualTo(FlatSlopeSlabCornerSkipPredicate.getTriDir(adjDir, adjTopHalf, adjTop, side.getOpposite()));
    }

    @CullTest.SingleTarget(BlockType.FRAMED_FLAT_INNER_SLOPE_SLAB_CORNER)
    private static boolean testAgainstFlatInnerSlopeSlabCorner(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);
        boolean adjTopHalf = adjState.getValue(PropertyHolder.TOP_HALF);

        return getTriDir(dir, rot, side).isEqualTo(FlatInnerSlopeSlabCornerSkipPredicate.getTriDir(adjDir, adjTopHalf, adjTop, side.getOpposite()));
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_FLAT_DOUBLE_SLOPE_SLAB_CORNER,
            partTargets = { BlockType.FRAMED_FLAT_INNER_SLOPE_SLAB_CORNER, BlockType.FRAMED_FLAT_SLOPE_SLAB_CORNER }
    )
    private static boolean testAgainstFlatDoubleSlopeSlabCorner(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstFlatInnerSlopeSlabCorner(dir, rot, states.getA(), side) ||
               testAgainstFlatSlopeSlabCorner(dir, rot, states.getB(), side);
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_FLAT_ELEV_DOUBLE_SLOPE_SLAB_CORNER,
            partTargets = BlockType.FRAMED_FLAT_INNER_SLOPE_SLAB_CORNER
    )
    private static boolean testAgainstFlatElevatedDoubleSlopeSlabCorner(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstFlatInnerSlopeSlabCorner(dir, rot, states.getB(), side);
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_FLAT_ELEV_INNER_DOUBLE_SLOPE_SLAB_CORNER,
            partTargets = BlockType.FRAMED_FLAT_SLOPE_SLAB_CORNER
    )
    private static boolean testAgainstFlatElevatedInnerDoubleSlopeSlabCorner(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstFlatSlopeSlabCorner(dir, rot, states.getB(), side);
    }

    @CullTest.SingleTarget(BlockType.FRAMED_SLOPE_PANEL)
    private static boolean testAgainstSlopePanel(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        HorizontalRotation adjRot = adjState.getValue(PropertyHolder.ROTATION);
        boolean adjFront = adjState.getValue(PropertyHolder.FRONT);

        return getTriDir(dir, rot, side).isEqualTo(SlopePanelSkipPredicate.getTriDir(adjDir, adjRot, adjFront, side.getOpposite()));
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_DOUBLE_SLOPE_PANEL,
            partTargets = BlockType.FRAMED_SLOPE_PANEL
    )
    private static boolean testAgainstDoubleSlopePanel(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstSlopePanel(dir, rot, states.getA(), side) ||
               testAgainstSlopePanel(dir, rot, states.getB(), side);
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_EXTENDED_DOUBLE_SLOPE_PANEL,
            partTargets = BlockType.FRAMED_SLOPE_PANEL
    )
    private static boolean testAgainstExtendedDoubleSlopePanel(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstSlopePanel(dir, rot, states.getB(), side);
    }

    @CullTest.SingleTarget(BlockType.FRAMED_FLAT_SLOPE_PANEL_CORNER)
    private static boolean testAgainstFlatSlopePanelCorner(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        HorizontalRotation adjRot = adjState.getValue(PropertyHolder.ROTATION);
        boolean adjFront = adjState.getValue(PropertyHolder.FRONT);

        return getTriDir(dir, rot, side).isEqualTo(FlatSlopePanelCornerSkipPredicate.getTriDir(adjDir, adjRot, adjFront, side.getOpposite()));
    }

    @CullTest.SingleTarget(BlockType.FRAMED_FLAT_INNER_SLOPE_PANEL_CORNER)
    private static boolean testAgainstFlatInnerSlopePanelCorner(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        HorizontalRotation adjRot = adjState.getValue(PropertyHolder.ROTATION);
        boolean adjFront = adjState.getValue(PropertyHolder.FRONT);

        return getTriDir(dir, rot, side).isEqualTo(FlatInnerSlopePanelCornerSkipPredicate.getTriDir(adjDir, adjRot, adjFront, side.getOpposite()));
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_FLAT_DOUBLE_SLOPE_PANEL_CORNER,
            partTargets = { BlockType.FRAMED_FLAT_INNER_SLOPE_PANEL_CORNER, BlockType.FRAMED_FLAT_SLOPE_PANEL_CORNER }
    )
    private static boolean testAgainstFlatDoubleSlopePanelCorner(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstFlatInnerSlopePanelCorner(dir, rot, states.getA(), side) ||
               testAgainstFlatSlopePanelCorner(dir, rot, states.getB(), side);
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_FLAT_EXT_DOUBLE_SLOPE_PANEL_CORNER,
            partTargets = BlockType.FRAMED_FLAT_INNER_SLOPE_PANEL_CORNER
    )
    private static boolean testAgainstFlatExtendedDoubleSlopePanelCorner(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstFlatInnerSlopePanelCorner(dir, rot, states.getB(), side);
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_FLAT_EXT_INNER_DOUBLE_SLOPE_PANEL_CORNER,
            partTargets = BlockType.FRAMED_FLAT_SLOPE_PANEL_CORNER
    )
    private static boolean testAgainstFlatExtendedInnerDoubleSlopePanelCorner(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstFlatSlopePanelCorner(dir, rot, states.getB(), side);
    }

    @CullTest.SingleTarget(BlockType.FRAMED_SLAB_EDGE)
    private static boolean testAgainstSlabEdge(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);

        return getCornerDir(dir, rot, side).isEqualTo(SlabEdgeSkipPredicate.getCornerDir(adjDir, adjTop, side.getOpposite()));
    }

    @CullTest.SingleTarget(BlockType.FRAMED_SLAB_CORNER)
    private static boolean testAgainstSlabCorner(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);

        return getCornerDir(dir, rot, side).isEqualTo(SlabCornerSkipPredicate.getCornerDir(adjDir, adjTop, side.getOpposite()));
    }

    @CullTest.SingleTarget(BlockType.FRAMED_VERTICAL_STAIRS)
    private static boolean testAgainstVerticalStairs(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        StairsType adjType = adjState.getValue(PropertyHolder.STAIRS_TYPE);

        return getCornerDir(dir, rot, side).isEqualTo(VerticalStairsSkipPredicate.getCornerDir(adjDir, adjType, side.getOpposite()));
    }

    @CullTest.SingleTarget(BlockType.FRAMED_HALF_STAIRS)
    private static boolean testAgainstHalfStairs(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);
        boolean adjRight = adjState.getValue(PropertyHolder.RIGHT);

        return getCornerDir(dir, rot, side).isEqualTo(HalfStairsSkipPredicate.getCornerDir(adjDir, adjTop, adjRight, side.getOpposite()));
    }

    @CullTest.SingleTarget(BlockType.FRAMED_VERTICAL_HALF_STAIRS)
    private static boolean testAgainstVerticalHalfStairs(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Direction adjDir = adjState.getValue(FramedProperties.FACING_HOR);
        boolean adjTop = adjState.getValue(FramedProperties.TOP);

        return getCornerDir(dir, rot, side).isEqualTo(VerticalHalfStairsSkipPredicate.getCornerDir(adjDir, adjTop, side.getOpposite()));
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_DOUBLE_STAIRS,
            partTargets = BlockType.FRAMED_SLAB_EDGE
    )
    private static boolean testAgainstDoubleStairs(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstSlabEdge(dir, rot, states.getB(), side);
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_DIVIDED_SLAB,
            partTargets = BlockType.FRAMED_SLAB_EDGE
    )
    private static boolean testAgainstDividedSlab(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstSlabEdge(dir, rot, states.getA(), side) ||
               testAgainstSlabEdge(dir, rot, states.getB(), side);
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_DIVIDED_PANEL_HORIZONTAL,
            partTargets = BlockType.FRAMED_SLAB_EDGE
    )
    private static boolean testAgainstDividedPanelHorizontal(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstSlabEdge(dir, rot, states.getA(), side) ||
               testAgainstSlabEdge(dir, rot, states.getB(), side);
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_DIVIDED_STAIRS,
            partTargets = BlockType.FRAMED_HALF_STAIRS
    )
    private static boolean testAgainstDividedStairs(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstHalfStairs(dir, rot, states.getA(), side) ||
               testAgainstHalfStairs(dir, rot, states.getB(), side);
    }

    @CullTest.DoubleTarget(
            value = BlockType.FRAMED_VERTICAL_DIVIDED_STAIRS,
            partTargets = BlockType.FRAMED_VERTICAL_HALF_STAIRS
    )
    private static boolean testAgainstVerticalDividedStairs(
            Direction dir, HorizontalRotation rot, BlockState adjState, Direction side
    )
    {
        Tuple<BlockState, BlockState> states = AbstractFramedDoubleBlock.getStatePair(adjState);
        return testAgainstVerticalHalfStairs(dir, rot, states.getA(), side) ||
               testAgainstVerticalHalfStairs(dir, rot, states.getB(), side);
    }



    public static HalfTriangleDir getTriDir(Direction dir, HorizontalRotation rot, Direction side)
    {
        Direction rotDir = rot.withFacing(dir);
        Direction perpRotDir = rot.rotate(Rotation.COUNTERCLOCKWISE_90).withFacing(dir);
        if (side == rotDir)
        {
            return HalfTriangleDir.fromDirections(perpRotDir, dir, true);
        }
        else if (side == perpRotDir)
        {
            return HalfTriangleDir.fromDirections(rotDir, dir, true);
        }
        return HalfTriangleDir.NULL;
    }

    public static CornerDir getCornerDir(Direction dir, HorizontalRotation rot, Direction side)
    {
        if (side == dir)
        {
            Direction rotDir = rot.withFacing(dir);
            Direction perpRotDir = rot.rotate(Rotation.COUNTERCLOCKWISE_90).withFacing(dir);
            return CornerDir.fromDirections(dir, rotDir, perpRotDir);
        }
        return CornerDir.NULL;
    }
}
