package com.PinkCats.worldprotect.Database.Operator;

import com.PinkCats.worldprotect.Database.GUI.mes;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;

public class PostFactory {

    public static void DataValid(boolean ok, String behaviour){
        if (!ok) {
            mes.error(behaviour + " Not Record!");
        }
    }

    public static boolean isInteractiveBlock(BlockState state) {
        Block b = state.getBlock();

        return  b instanceof DoorBlock
                || b instanceof FenceGateBlock
                || b instanceof TrapDoorBlock
                || b instanceof ButtonBlock
                || b instanceof LeverBlock
                || b instanceof BedBlock
                || b instanceof NoteBlock
                || b instanceof CakeBlock
                || b instanceof CampfireBlock
                || b instanceof SignBlock
                || b instanceof PressurePlateBlock;
        // 你可以继续加：BellBlock, ComparatorBlock, RepeaterBlock, LecternBlock, etc.
    }

    public static final String SystemUUID = "SYSTEM_Fire" ;

}
