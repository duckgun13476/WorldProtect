package com.PinkCats.worldprotect.event.minecraft;

import com.PinkCats.worldprotect.Database.Item.RecordItemRaw;
import com.mojang.datafixers.types.templates.Tag;
import net.minecraft.nbt.CompoundTag;
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

import java.util.stream.Stream;

import static com.PinkCats.worldprotect.Database.WorldProtectKinetic.ItemRawQueue;


@Mod.EventBusSubscriber
public class WpBlockEvent {


    @SubscribeEvent
    public void BlockBreakEvent(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Block Block = event.getState().getBlock();
        Stream<TagKey<Block>> tag = event.getState().getTags();

        System.out.println(player.getGameProfile().getName());
        System.out.println(tag);
        System.out.println(Block);


        //if (HasNbt)
        //    System.out.println(event.getItem().getItem().getTag()); //nbt
        //System.out.println(ItemCount);  //count
    }


    @SubscribeEvent
    public void BlockBreakEvent(BlockEvent.EntityPlaceEvent event) {
        Entity entity = event.getEntity();
        BlockState BlockState = event.getState();
        Stream<TagKey<Block>> tag = event.getState().getTags();
        System.out.println(entity);
        System.out.println(tag);
        System.out.println(BlockState.getBlock());


        if (BlockState.hasBlockEntity()){
            Level level = (Level) event.getLevel();
            BlockEntity blockEntity = level.getBlockEntity(event.getPos());
            CompoundTag nbt = blockEntity.getPersistentData();
            System.out.println(nbt);
        }

        //if (HasNbt)
        //    System.out.println(event.getItem().getItem().getTag()); //nbt
        //System.out.println(ItemCount);  //count
    }


    public static void RegisterBlockEvents()
    {
        MinecraftForge.EVENT_BUS.register(new WpBlockEvent());
    }




}
