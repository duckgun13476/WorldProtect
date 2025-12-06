package com.PinkCats.worldprotect.event.minecraft;


import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.atomic.AtomicBoolean;

import static com.PinkCats.worldprotect.Database.WorldProtectKinetic.WorldProtectKineticTick;

@Mod.EventBusSubscriber
public class ServerTick {

    static AtomicBoolean TaskLock = new AtomicBoolean(false);

    public static void RunTask() {
        if (TaskLock.compareAndSet(false, true)) {
            try {
                WorldProtectKineticTick();
            } finally {
                TaskLock.set(false);
            }
        }
    }

    private void Tick() {
        String threadName = "WorldProtectKineticMonitor";
        Thread taskThread = new Thread(ServerTick::RunTask, threadName);
        taskThread.start();
    }


    short i=0;
    @SubscribeEvent
    public void ServerTickEvent(TickEvent.ServerTickEvent event) {
        i ++;if (i>=50){i = 0;Tick();}
    }

    public static void RegisterWorldProtectKinetic() {MinecraftForge.EVENT_BUS.register(new ServerTick());}

}
