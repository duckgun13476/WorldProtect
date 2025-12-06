package com.PinkCats.worldprotect.Database;

import com.PinkCats.worldprotect.Database.Item.RecordItem;

import java.util.ArrayList;
import java.util.List;

import static com.PinkCats.worldprotect.Database.Operator.SqlMapQuery.FetchMapOperatorId;
import static com.PinkCats.worldprotect.Database.SqlInit.DataBaseInit;
import static com.PinkCats.worldprotect.Database.SqlInit.SafeSql;

public class Main {




    public static void main(String[] args) {

        long time = System.currentTimeMillis();
        DataBaseInit();
        ConnectionHub();

        long time2 = System.currentTimeMillis();
        System.out.println("Run time: "+(time2 - time) + " ms");

    }

    private static void ConnectionHub() {
        SafeSql((s)->{

            long time = System.currentTimeMillis();


            int ItemMap = FetchMapOperatorId(s,"dev","39i9id");
            System.out.println("BlockMap: "+ItemMap);

            long time2 = System.currentTimeMillis();
            System.out.println("Run time: "+(time2 - time) + " ms");



        });
    }




}
