package com.PinkCats.worldprotect.Database.Item;

public class RecordItem {
    private int time;
    private int operator;
    private int world;
    private int x;
    private int y;
    private int z;
    private int itemdata;
    private int count;
    private int behaviour;
    private int rollback;

    // 构造函数
    public RecordItem(int time, int operator, int world, int x, int y, int z, int itemdata,int count, int behaviour, int rollback) {
        this.time = time;
        this.operator = operator;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.itemdata = itemdata;
        this.count = count;
        this.behaviour = behaviour;
        this.rollback = rollback;
    }

    public int getTime() { return time; }
    public short getOperator() {
        isSafeToShort(operator,"Operator");
        return (short) operator;
    }
    public short getWorld() {
        isSafeToShort(world,"World");
        return (short) world;
    }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getZ() { return z; }
    public int getItemdata() { return itemdata; }

    public short getBehaviour() {
        isSafeToShort(behaviour,"Behaviour");
        return (short)behaviour;
    }
    public short getRollback() {
        isSafeToShort(rollback,"Rollback");
        return (short)rollback;
    }

    /**
     * 校验单条RecordItem数据的合法性（复用校验逻辑）
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

    private static boolean isSafeToShort(int value, String fieldName) {
        if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
            System.err.printf("【错误】字段%s的值%d超出short类型范围！short取值范围：%d ~ %d%n",
                    fieldName, value, Short.MIN_VALUE, Short.MAX_VALUE);
            return false;
        }
        return true;
    }

    public int getCount() {
        return count;
    }
}