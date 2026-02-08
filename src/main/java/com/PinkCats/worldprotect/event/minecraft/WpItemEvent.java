package com.PinkCats.worldprotect.event.minecraft;

import com.PinkCats.worldprotect.Database.Item.RecordItemRaw;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.stream.Stream;

import static com.PinkCats.worldprotect.Database.WorldProtectKinetic.ItemRawQueue;

@Mod.EventBusSubscriber
public class WpItemEvent {


    @SubscribeEvent
    public void EntityItemPickupEvent(EntityItemPickupEvent event) {
        Player player = event.getEntity();
        ItemStack ItemStack = event.getItem().getItem().copy();
        boolean IsFull = ItemRawQueue.offer(new RecordItemRaw(
                player.getName().getString(),
                player.getStringUUID(),
                player.level().dimension().location().toString(),
                player.getBlockX(),
                player.getBlockY(),
                player.getBlockZ(),
                ItemStack,
                "PickUp"
        ));
        if (!IsFull)
            System.out.println("PickUpEvent Not Record!");

    }

    @SubscribeEvent
    public void EntityItemDropEvent(ItemTossEvent event) {
        Player player = event.getPlayer();
        ItemStack ItemStack = event.getEntity().getItem().copy();
        boolean IsFull = ItemRawQueue.offer(new RecordItemRaw(
                player.getName().getString(),
                player.getStringUUID(),
                player.level().dimension().location().toString(),
                player.getBlockX(),
                player.getBlockY(),
                player.getBlockZ(),
                ItemStack,
                "DropDown"
        ));
        if (!IsFull)
            System.out.println("DropDownEvent Not Record!");

    }





    public static void RegisterItemEvents()
    {
        MinecraftForge.EVENT_BUS.register(new WpItemEvent());

    }


}
