package com.PinkCats.worldprotect.event.minecraft;

import com.PinkCats.worldprotect.Database.Item.RecordItemRaw;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.PinkCats.worldprotect.Database.WorldProtectKinetic.ItemRawQueue;

@Mod.EventBusSubscriber
public class BlockEvent {



    @SubscribeEvent
    public void EntityItemPickupEvent(EntityItemPickupEvent event) {
        Player player = event.getEntity();
        ItemStack ItemStack = event.getItem().getItem().copy();;
        Item Item = ItemStack.getItem();
        int ItemCount = ItemStack.getCount();
        boolean HasNbt = ItemStack.hasTag();


        //if (HasNbt)
        //    System.out.println(event.getItem().getItem().getTag()); //nbt
        //System.out.println(ItemCount);  //count

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
        if (IsFull)
            System.out.println("queue is full drop mox data");

    }

    @SubscribeEvent
    public void EntityItemDropEvent(ItemTossEvent event) {
        Player player = event.getPlayer();
        ItemStack ItemStack = event.getEntity().getItem();
        Item Item = ItemStack.getItem();
        int ItemCount = ItemStack.getCount();
        boolean HasNbt = ItemStack.hasTag();

        System.out.println("Item Toss!");
        System.out.println(player.getName()); //player
        System.out.println(player.getStringUUID()); //player
        System.out.println(Item); //item

        if (HasNbt)
            System.out.println(event.getEntity().getItem().getTag()); //nbt
        System.out.println(ItemCount);  //count
    }




    public static void RegisterEvents()
    {
        MinecraftForge.EVENT_BUS.register(new BlockEvent());

    }


}
