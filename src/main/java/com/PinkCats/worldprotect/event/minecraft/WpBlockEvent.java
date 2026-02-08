package com.PinkCats.worldprotect.event.minecraft;

import com.PinkCats.worldprotect.Database.GUI.mes;
import com.PinkCats.worldprotect.Database.Item.RecordBlock;
import com.PinkCats.worldprotect.Database.Item.RecordBlockRaw;
import com.PinkCats.worldprotect.Database.Item.RecordItemRaw;
import com.PinkCats.worldprotect.Database.Lib.NBTData;
import com.mojang.datafixers.types.templates.Tag;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.io.ByteArrayOutputStream;
import java.util.stream.Stream;

import static com.PinkCats.worldprotect.Database.WorldProtectKinetic.BlockRawQueue;
import static com.PinkCats.worldprotect.Database.WorldProtectKinetic.ItemRawQueue;


@Mod.EventBusSubscriber
public class WpBlockEvent {


    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        // ===== 主线程最早返回 =====
        Level level = (Level) event.getLevel();
        if (level.isClientSide) return;

        Player player = event.getPlayer();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();
        Block block = state.getBlock();

        // ---------- 快路径：无 BlockEntity ----------
        if (!state.hasBlockEntity()) {
            boolean ok = BlockRawQueue.offer(new RecordBlockRaw(
                    player.getName().getString(),
                    player.getStringUUID(),
                    player.level().dimension().location().toString(),
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    null,                       // 🚀 没有 NBT
                    block.getDescriptionId(),           // 只存 Block ID
                    "BlockBreak"
            ));
            if (!ok) {
                System.out.println("BlockBreakEvent Not Record!");
            }
            return;
        }

        // ---------- 完整路径：有 BlockEntity ----------
        BlockEntity be = level.getBlockEntity(pos);
        if (be == null) {
            mes.debug("[BREAK][BE] state says hasBlockEntity, but getBlockEntity() == null");
            return;
        }

        byte[] nbtBytes;
        try {
            CompoundTag tag = be.saveWithFullMetadata();
            nbtBytes = NBTData.nbtToBlob(tag);
        } catch (Exception e) {
            mes.debug("[BREAK][BE] NBT serialize failed: " + e);
            return;
        }

        boolean ok = BlockRawQueue.offer(new RecordBlockRaw(
                player.getName().getString(),
                player.getStringUUID(),
                player.level().dimension().location().toString(),
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                nbtBytes,
                block.toString(),
                "BlockBreak"
        ));
        if (!ok) {
            System.out.println("BlockBreakEvent Not Record!");
        }
    }



    @SubscribeEvent
    public void BlockPlaceEvent(BlockEvent.EntityPlaceEvent event) {
        Entity entity = event.getEntity();
        BlockState BlockState = event.getState();
        Stream<TagKey<Block>> tag = event.getState().getTags();
        mes.debug("Place");
        mes.debug(entity);
        mes.debug(tag);
        mes.debug(BlockState.getBlock());


        //if (BlockState.hasBlockEntity()){
        //    Level level = (Level) event.getLevel();
        //    BlockEntity blockEntity = level.getBlockEntity(event.getPos());
        //    CompoundTag nbt = blockEntity.getPersistentData();
        //    System.out.println(nbt);
        //}

        //if (HasNbt)
        //    System.out.println(event.getItem().getItem().getTag()); //nbt
        //System.out.println(ItemCount);  //count
    }


    public static void RegisterBlockEvents()
    {
        MinecraftForge.EVENT_BUS.register(new WpBlockEvent());
    }




}
