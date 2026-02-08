package com.PinkCats.worldprotect.Database.Item;

import com.PinkCats.worldprotect.Database.GUI.mes;

public class RecordBlock {
    private final int operator;
    private final int world;
    private final int behaviour;
    private final int rollback;
    private final int time;
    private final int x;
    private final int y;
    private final int z;
    private final int BlockData;

    // 构造函数
    public RecordBlock(int time, int operator, int world, int x, int y, int z, int BlockData, int behaviour,
                       int rollback) {
        this.operator = operator;
        this.world = world;
        this.behaviour = behaviour;
        this.rollback = rollback;
        this.time = time;
        this.x = x;
        this.y = y;
        this.z = z;
        this.BlockData = BlockData;
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

    public int getBlockData() {
        return BlockData;
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
    

    /**
     * 校验单条RecordItem数据的合法性（复用校验逻辑）
     *
     * @param item 待校验数据
     * @return 是否合法
     */
    public static boolean validateRecordItem(RecordBlock item) {
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

    public static boolean CanBulk(RecordBlock item, RecordBlock item2) {
        if (item.getWorld() != item2.getWorld()) return false;
        if (item.getX() != item2.getX()) return false;
        if (item.getY() != item2.getY()) return false;
        if (item.getZ() != item2.getZ()) return false;
        return item.getBlockData() == item2.getBlockData();
    }

    @Override
    public String toString() {
        return "RecordBlock{" +
                "time=" + time +
                ", operator=" + operator +
                ", world=" + world +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", blockData=" + BlockData +
                ", behaviour=" + behaviour +
                ", rollback=" + rollback +
                '}';
    }

}