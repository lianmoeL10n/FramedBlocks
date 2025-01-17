package xfacthd.framedblocks.common.blockentity.doubled;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import xfacthd.framedblocks.api.block.FramedProperties;
import xfacthd.framedblocks.api.util.Utils;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.blockentity.FramedDoubleBlockEntity;

public class FramedSmallDoubleCornerSlopePanelBlockEntity extends FramedDoubleBlockEntity
{
    public FramedSmallDoubleCornerSlopePanelBlockEntity(BlockPos pos, BlockState state)
    {
        super(FBContent.BE_TYPE_FRAMED_SMALL_DOUBLE_CORNER_SLOPE_PANEL.get(), pos, state);
    }

    @Override
    protected boolean hitSecondary(BlockHitResult hit)
    {
        Direction side = hit.getDirection();
        boolean top = getBlockState().getValue(FramedProperties.TOP);

        if (Utils.isY(side))
        {
            boolean up = side == Direction.UP;
            return top != up;
        }

        Direction facing = getBlockState().getValue(FramedProperties.FACING_HOR);
        if (side == facing.getOpposite() || side == facing.getClockWise())
        {
            return false;
        }

        Vec3 hitVec = hit.getLocation();
        double xz = Utils.fractionInDir(hitVec, side == facing ? facing.getCounterClockWise() : facing) - .5;
        double y = Utils.fractionInDir(hitVec, top ? Direction.UP : Direction.DOWN);
        return (xz * 2D) > y;
    }
}
