package com.PinkCats.worldprotect.Database.Item;

public record RecordItem(int time, int operator, int world, int x, int y, int z, int ItemData, int count, int behaviour,
                         int rollback) {
    // 构造函数

    public short getOperator() {
        isSafeToShort(operator, "Operator");
        return (short) operator;
    }

    public short getWorld() {
        isSafeToShort(world, "World");
        return (short) world;
    }

    public short getBehaviour() {
        isSafeToShort(behaviour, "Behaviour");
        return (short) behaviour;
    }

    public short getRollback() {
        isSafeToShort(rollback, "Rollback");
        return (short) rollback;
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
        if (item.time() <= 0) return false;
        if (item.x() < -30000000 || item.x() > 30000000) return false;
        if (item.y() < 0 || item.y() > 255) return false;
        return item.z() >= -30000000 && item.z() <= 30000000;
    }

    private static void isSafeToShort(int value, String fieldName) {
        if (value < Short.MIN_VALUE || value > Short.MAX_VALUE) {
            System.err.printf("【错误】字段%s的值%d超出short类型范围！short取值范围：%d ~ %d%n",
                    fieldName, value, Short.MIN_VALUE, Short.MAX_VALUE);
        }
    }
}