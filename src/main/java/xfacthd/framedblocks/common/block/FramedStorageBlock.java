package xfacthd.framedblocks.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import xfacthd.framedblocks.common.blockentity.FramedStorageBlockEntity;
import xfacthd.framedblocks.common.data.BlockType;

public class FramedStorageBlock extends FramedBlock
{
    public FramedStorageBlock(BlockType type) { super(type); }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit)
    {
        InteractionResult result = super.use(state, level, pos, player, hand, hit);
        if (result != InteractionResult.PASS) { return result; }

        if (!level.isClientSide())
        {
            if (level.getBlockEntity(pos) instanceof FramedStorageBlockEntity be)
            {
                be.open((ServerPlayer) player);
            }
        }
        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    @SuppressWarnings("deprecation")
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving)
    {
        if (newState.getBlock() != state.getBlock() && level.getBlockEntity(pos) instanceof FramedStorageBlockEntity be)
        {
            be.getDrops().forEach(stack -> popResource(level, pos, stack));
            be.clearContents();
            level.updateNeighbourForOutputSignal(pos, this);
        }

        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean hasAnalogOutputSignal(BlockState state) { return true; }

    @Override
    @SuppressWarnings("deprecation")
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos)
    {
        if (level.getBlockEntity(pos) instanceof FramedStorageBlockEntity be)
        {
            return be.getAnalogOutputSignal();
        }
        return 0;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state)
    {
        return new FramedStorageBlockEntity(pos, state);
    }
}