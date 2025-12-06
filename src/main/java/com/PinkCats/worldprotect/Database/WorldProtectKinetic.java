package com.PinkCats.worldprotect.Database;

import com.PinkCats.worldprotect.Database.Item.RecordItem;
import com.PinkCats.worldprotect.Database.Item.RecordItemRaw;
import net.minecraft.world.item.ItemStack;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static com.PinkCats.worldprotect.Database.Lib.TimeUtils.getCurrentTimestamp;
import static com.PinkCats.worldprotect.Database.Main.recordItemList;
import static com.PinkCats.worldprotect.Database.Operator.SqlMapQuery.*;
import static com.PinkCats.worldprotect.Database.Operator.SqlNbtQuery.InsertMapNbtItem;
import static com.PinkCats.worldprotect.Database.Operator.SqlNbtQuery.itemStackToBlob;
import static com.PinkCats.worldprotect.Database.SqlEntry.batchInsertRecordItem;
import static com.PinkCats.worldprotect.Database.SqlInit.DataBaseInit;
import static com.PinkCats.worldprotect.Database.SqlInit.SafeSql;

public class WorldProtectKinetic {

    private static final int QUEUE_CAPACITY = 5000;
    public static BlockingQueue<RecordItemRaw> ItemRawQueue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);


    public static void WorldProtectKineticTick(){
        System.out.println("tick");
        SafeSql((s)-> {
            CookItemData(s);

            batchInsertRecordItem(s, recordItemList, 1000);
        });

    }

    private static void CookItemData(Statement s) throws SQLException {


        List<RecordItemRaw> ItemsToProcess = new ArrayList<>();
        ItemRawQueue.drainTo(ItemsToProcess);

        if (ItemsToProcess.isEmpty()) {
            return; // 无数据，直接返回，避免空处理
        }

        for (RecordItemRaw recordItemRaw : ItemsToProcess) {

            int WorldMap = FetchMapWorldId(s,recordItemRaw.getWorld());
            int BehaviourMap = FetchMapBehaviourId(s,recordItemRaw.getBehaviour());
            int OperatorMap = FetchMapOperatorId(s,recordItemRaw.getOperator(),recordItemRaw.getOperatorUUID());
            int ItemMap;

            ItemStack item = recordItemRaw.getItemdata();
            ItemMap = FetchMapItemId(s,item.getItem().toString());
            if (item.hasTag()) {

                ItemMap = - InsertMapNbtItem(s,itemStackToBlob(item));


            }


            recordItemList.add(
                    new RecordItem(
                            getCurrentTimestamp(),
                            OperatorMap,
                            WorldMap,
                            recordItemRaw.getX(),
                            recordItemRaw.getY(),
                            recordItemRaw.getZ(),
                            ItemMap,
                            item.getCount(),
                            BehaviourMap,
                            0
                    )
            );



        }


    }


}
