package com.PinkCats.worldprotect.event.minecraft;

import com.PinkCats.worldprotect.Database.Item.RecordItemRaw;
import com.PinkCats.worldprotect.Database.WorldProtectKinetic;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemTossEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.PinkCats.worldprotect.Database.Operator.PostFactory.DataValid;
import static com.PinkCats.worldprotect.Database.WorldProtectKinetic.ItemRawQueue;

public class WpItemEvent {


    @SubscribeEvent
    public void EntityItemPickupEvent(EntityItemPickupEvent event) {

        Level level = event.getEntity().level();
        if (level.isClientSide()) return;

        Player player = event.getEntity();
        ItemStack ItemStack = event.getItem().getItem().copy();

        String behaviour = "PickUp";
        boolean IsFull = ItemRawQueue.offer(new RecordItemRaw(
                player.getName().getString(),
                player.getStringUUID(),
                player.level().dimension().location().toString(),
                player.getBlockX(),
                player.getBlockY(),
                player.getBlockZ(),
                ItemStack,
                behaviour
        ));
        DataValid(IsFull, behaviour);
    }

    @SubscribeEvent
    public void EntityItemDropEvent(ItemTossEvent event) {

        Level level = event.getEntity().level();
        if (level.isClientSide()) return;


        Player player = event.getPlayer();
        ItemStack ItemStack = event.getEntity().getItem().copy();

        String behaviour = "DropDown";
        boolean IsFull = ItemRawQueue.offer(new RecordItemRaw(
                player.getName().getString(),
                player.getStringUUID(),
                player.level().dimension().location().toString(),
                player.getBlockX(),
                player.getBlockY(),
                player.getBlockZ(),
                ItemStack,
                behaviour
        ));
        DataValid(IsFull, behaviour);
    }

    // 右键“尝试使用物品”（不对准方块/实体时更常见；对准方块会走 RightClickBlock）
    @SubscribeEvent
    public void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Level level = event.getLevel();
        if (level.isClientSide()) return;
        if (event.getHand() != InteractionHand.MAIN_HAND) return;

        ItemStack stack = event.getItemStack().copy();
        if (stack.getUseDuration() > 0) return;

        Player player = event.getEntity();
        String behaviour = "UseItem";
        boolean ok = WorldProtectKinetic.ItemRawQueue.offer(new RecordItemRaw(
                player.getName().getString(),
                player.getStringUUID(),
                level.dimension().location().toString(),
                player.getBlockX(), player.getBlockY(), player.getBlockZ(),
                stack,
                behaviour
        ));
        DataValid(ok, behaviour);
    }

    // 吃/喝完成（真正消耗完成）
    @SubscribeEvent
    public void onUseItemFinish(LivingEntityUseItemEvent.Finish event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();
        if (level.isClientSide()) return;
        if (!(entity instanceof Player player)) return;

        // Finish 里 event.getItem() 通常是“消耗前的 ItemStack”
        ItemStack stackBefore = event.getItem().copy();

        String behaviour = "ConsumeItem";
        boolean ok = WorldProtectKinetic.ItemRawQueue.offer(new RecordItemRaw(
                player.getName().getString(),
                player.getStringUUID(),
                level.dimension().location().toString(),
                player.getBlockX(), player.getBlockY(), player.getBlockZ(),
                stackBefore,
                behaviour
        ));
        DataValid(ok, behaviour);
    }



    public static void RegisterItemEvents()
    {
        MinecraftForge.EVENT_BUS.register(new WpItemEvent());

    }


}
