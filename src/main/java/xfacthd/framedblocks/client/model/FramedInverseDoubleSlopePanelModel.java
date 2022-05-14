package xfacthd.framedblocks.client.model;

import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.state.BlockState;
import xfacthd.framedblocks.api.util.FramedProperties;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.blockentity.FramedInverseDoubleSlopePanelBlockEntity;
import xfacthd.framedblocks.common.data.PropertyHolder;
import xfacthd.framedblocks.common.data.Rotation;

public class FramedInverseDoubleSlopePanelModel extends FramedDoubleBlockModel
{
    private final Direction facing;
    private final Rotation rotation;

    public FramedInverseDoubleSlopePanelModel(BlockState state, BakedModel baseModel)
    {
        super(baseModel, true);
        this.facing = state.getValue(FramedProperties.FACING_HOR);
        this.rotation = state.getValue(PropertyHolder.ROTATION);
    }

    @Override
    protected Tuple<BlockState, BlockState> getDummyStates()
    {
        return FramedInverseDoubleSlopePanelBlockEntity.getBlockPair(facing, rotation);
    }



    public static BlockState itemSource() { return FBContent.blockFramedInverseDoubleSlopePanel.get().defaultBlockState(); }
}