package com.PinkCats.worldprotect.Database.Item;

import com.PinkCats.worldprotect.Database.GUI.mes;

public class RecordItem {
    private final int operator;
    private final int world;
    private final int behaviour;
    private final int rollback;
    private int count;
    private final int time;
    private final int x;
    private final int y;
    private final int z;
    private final int ItemData;

    // 构造函数
    public RecordItem(int time, int operator, int world, int x, int y, int z, int ItemData, int count, int behaviour,
                      int rollback) {
        this.operator = operator;
        this.world = world;
        this.behaviour = behaviour;
        this.rollback = rollback;
        this.count = count;
        this.time = time;
        this.x = x;
        this.y = y;
        this.z = z;
        this.ItemData = ItemData;
    }

    public short getOperator() {
        isSafeToShort(this.operator, "Operator");
        return (short) operator;
    }

    public short getWorld() {
        isSafeToShort(this.world, "World");
        return (short) world;
    }

    public short getBehaviour() {
        isSafeToShort(this.behaviour, "Behaviour");
        return (short) behaviour;
    }

    public int getRollback() {
        isSafeToShort(this.rollback, "Rollback");
        return rollback;
    }

    public int getCount() {
        isSafeToShort(this.count, "count");
        return count;
    }
    
    public int getItemData() {
        return ItemData;
    }

    public int getTime() {
        return time;
    }


    public int getX() {
        isSafeToShort(this.x, "x");
        return x;
    }
    public int getY() {
        isSafeToShort(this.y, "y");
        return y;
    }
    public int getZ() {
        isSafeToShort(this.z, "z");
        return z;
    }
    

    public void setCount(int count) {
        this.count = count;
    }

    /**
     * 校验单条RecordItem数据的合法性（复用校验逻辑）
     *
     * @param item 待校验数据
     * @return 是否合法
     */
    public static boolean validateRecordItem(RecordItem item) {
        if (item == null) return false;
        // 核心字段校验（与单条插入一致）
        if (item.getTime() <= 0) return false;
        if (item.getX() < -30000000 || item.getX() > 30000000) return false;
        if (item.getY() < 0 || item.getY() > 255) return false;
        return item.getZ() >= -30000000 && item.getZ() <= 30000000;
    }

    private static void isSafeToShort(int value, String fieldName) {
        if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
            mes.error(
                    "【错误】字段" + fieldName +
                            "的值" + value +
                            "超出short类型范围！short取值范围：" +
                            Short.MIN_VALUE + " ~ " + Short.MAX_VALUE
            );
        }
    }

    public static boolean CanBulk(RecordItem item,RecordItem item2) {
        if (item.getWorld() != item2.getWorld()) return false;
        if (item.getX() != item2.getX()) return false;
        if (item.getY() != item2.getY()) return false;
        if (item.getZ() != item2.getZ()) return false;
        return item.getItemData() == item2.getItemData();
    }

    @Override
    public String toString() {
        return "RecordItem{" +
                "time=" + time +
                ", operator=" + operator +
                ", world=" + world +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", ItemData=" + ItemData +
                ", behaviour=" + behaviour +
                ", rollback=" + rollback +
                '}';
    }
}