package xfacthd.framedblocks.client.model.slope;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import xfacthd.framedblocks.api.util.FramedProperties;
import xfacthd.framedblocks.client.model.FramedDoubleBlockModel;
import xfacthd.framedblocks.common.FBContent;
import xfacthd.framedblocks.common.blockentity.FramedDoubleBlockEntity;
import xfacthd.framedblocks.common.data.*;
import xfacthd.framedblocks.common.data.property.CornerType;

public class FramedDoubleCornerModel extends FramedDoubleBlockModel
{
    private final CornerType type;

    public FramedDoubleCornerModel(BlockState state, BakedModel baseModel)
    {
        super(state, baseModel, true);
        this.type = state.getValue(PropertyHolder.CORNER_TYPE);
    }

    @Override
    public TextureAtlasSprite getParticleIcon(@NotNull ModelData data)
    {
        if (type == CornerType.BOTTOM)
        {
            return getSpriteOrDefault(data, FramedDoubleBlockEntity.DATA_RIGHT, getModels().getB());
        }
        else if (type.isTop())
        {
            return getSpriteOrDefault(data, FramedDoubleBlockEntity.DATA_LEFT, getModels().getA());
        }
        return super.getParticleIcon(data);
    }



    public static BlockState itemSource()
    {
        return FBContent.blockFramedDoubleCorner.get().defaultBlockState().setValue(FramedProperties.FACING_HOR, Direction.WEST);
    }
}