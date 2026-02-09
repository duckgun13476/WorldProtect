package com.PinkCats.worldprotect.mixin;

import com.PinkCats.worldprotect.Database.Item.RecordBlockRaw;
import com.PinkCats.worldprotect.Database.WorldProtectKinetic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.PinkCats.worldprotect.Database.Operator.PostFactory.DataValid;
import static com.PinkCats.worldprotect.Database.Operator.PostFactory.SystemUUID;


@Mixin(FireBlock.class)
public class FireEventMixin {


    @Inject(
            method = "tryCatchFire(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;ILnet/minecraft/util/RandomSource;ILnet/minecraft/core/Direction;)V",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;removeBlock(Lnet/minecraft/core/BlockPos;Z)Z"
            )
    )
    private void wp$logFireBurn(
            Level level, BlockPos pos, int p_53434_, RandomSource p_53435_, int p_53436_, Direction face, CallbackInfo ci
    ) {
        if (level.isClientSide()) return;

        // 这里的 pos 是“要被烧掉的方块”的位置（不是火的位置）
        BlockState old = level.getBlockState(pos);
        if (old.isAir()) return;

        // 注意：FireBlock 自己也可能被 remove，不想记可以过滤
        // if (old.is(net.minecraft.world.level.block.Blocks.FIRE)) return;
        String behaviour = "FireBurn";
        boolean ok = WorldProtectKinetic.BlockRawQueue.offer(new RecordBlockRaw(
                "Fire",                 // operator name（系统原因）
                SystemUUID,               // operator uuid
                level.dimension().location().toString(),
                pos.getX(), pos.getY(), pos.getZ(),
                null,
                old.getBlock().getDescriptionId(),
                behaviour            // behaviour
        ));
        DataValid(ok, behaviour);
    }
}
