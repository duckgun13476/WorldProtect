package com.PinkCats.worldprotect.event.minecraft;

import com.PinkCats.worldprotect.Database.GUI.mes;
import com.PinkCats.worldprotect.Database.Item.RecordBlockRaw;
import com.PinkCats.worldprotect.Database.Lib.NBTData;
import com.PinkCats.worldprotect.Database.WorldProtectKinetic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.PinkCats.worldprotect.Database.Operator.PostFactory.DataValid;
import static com.PinkCats.worldprotect.Database.Operator.PostFactory.isInteractiveBlock;
import static com.PinkCats.worldprotect.Database.WorldProtectKinetic.BlockRawQueue;

public class WpBlockEvent {


    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        String behaviour = "BlockBreak";
        Level level = (Level) event.getLevel();
        if (level.isClientSide) return;


        Player player = event.getPlayer();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();
        Block block = state.getBlock();

        //No BlockEntity Fast
        if (!state.hasBlockEntity()) {
            boolean ok = BlockRawQueue.offer(new RecordBlockRaw(
                    player.getName().getString(),
                    player.getStringUUID(),
                    player.level().dimension().location().toString(),
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    null,                       //  NBT
                    block.getDescriptionId(),           // Block ID
                    behaviour
            ));
            DataValid(ok, behaviour);
            return;
        }

        //Have block entity slow
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
                block.getDescriptionId(),
                behaviour
        ));
        DataValid(ok, behaviour);
    }



    @SubscribeEvent
    public void BlockPlaceEvent(BlockEvent.EntityPlaceEvent event) {

        Level level = (Level) event.getLevel();
        if (level.isClientSide) return;

        Entity entity = event.getEntity();
        BlockState BlockState = event.getState();
        BlockPos pos = event.getPos();

        if (entity != null) {
            String behaviour = "BlockPlace";
            boolean ok = WorldProtectKinetic.BlockRawQueue.offer(new RecordBlockRaw(
                    entity.getName().getString(),
                    entity.getStringUUID(),
                    entity.level().dimension().location().toString(),
                    pos.getX(),
                    pos.getY(),
                    pos.getZ(),
                    null,
                    BlockState.getBlock().getDescriptionId(),
                    behaviour
            ));
            DataValid(ok, behaviour);
        }

    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        if (level.isClientSide()) return; // 仅服务端
        if (event.getHand() != InteractionHand.MAIN_HAND) return; // 避免副手重复

        Player player = event.getEntity();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        if (!isInteractiveBlock(state)) {


            ItemStack held = event.getItemStack(); // 等价于 player.getItemInHand(event.getHand())
            if (held.isEmpty())
                if (!state.hasBlockEntity())
                    return;
            if (!held.isEmpty() && held.getItem() instanceof BlockItem) {
                return;
            }

            if (held.getUseDuration() > 0) return;
        }


        Direction face = event.getFace();
        String faceStr = (face == null) ? "UNKNOWN" : face.toString();

        String behaviour = "RightClick:" + faceStr;
        boolean ok = WorldProtectKinetic.BlockRawQueue.offer(new RecordBlockRaw(
                player.getName().getString(),
                player.getStringUUID(),
                player.level().dimension().location().toString(),
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                null,
                state.getBlock().getDescriptionId(),
                behaviour
        ));
        DataValid(ok, behaviour);

        // 额外：潜行右键
        //if (player.isShiftKeyDown()) {
        //    System.out.println("[RCLICK-SNEAK] " + player.getGameProfile().getName()
         //           + " -> " + state.getBlock().getDescriptionId()
        //            + " @ " + pos + " face=" + event.getFace());
        //}
    }

    // click
    //@SubscribeEvent
    //public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event) {
    //    Level level = event.getLevel();
    //    if (level.isClientSide()) return; // 仅服务端
    //    PlayerInteractEvent.LeftClickBlock.Action action = event.getAction();
    //    if (action == PlayerInteractEvent.LeftClickBlock.Action.CLIENT_HOLD) return;

    //    Player player = event.getEntity();
    //    BlockPos pos = event.getPos();
    //    BlockState state = level.getBlockState(pos);

    //    System.out.println("[LCLICK-" + action + "] " + player.getGameProfile().getName()
    //            + " -> " + state.getBlock().getDescriptionId()
    //            + " @ " + pos + " face=" + event.getFace());
    //}





    public static void RegisterBlockEvents()
    {
        MinecraftForge.EVENT_BUS.register(new WpBlockEvent());
    }




}
