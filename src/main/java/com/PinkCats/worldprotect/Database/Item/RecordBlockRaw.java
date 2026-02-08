package com.PinkCats.worldprotect.Database.Item;

public class RecordBlockRaw {
    private final String operator;
    private final String operatorUUID;
    private final String world;
    private final int x;
    private final int y;
    private final int z;

    private final String blockId;
    private final byte[] nbtBytes;
    private final String behaviour;

    // 无 BlockEntity：nbtBytes = null
    public RecordBlockRaw(String operator,
                          String operatorUUID,
                          String world,
                          int x, int y, int z,
                          byte[] nbtBytes,
                          String blockId,
                          String behaviour) {
        this.operator = operator;
        this.operatorUUID = operatorUUID;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.nbtBytes = nbtBytes;
        this.blockId = blockId;
        this.behaviour = behaviour;
    }

    public String getOperator() { return operator; }
    public String getOperatorUUID() { return operatorUUID; }
    public String getWorld() { return world; }
    public int getX() { return x; }
    public int getY() { return y; }
    public int getZ() { return z; }

    public String getBlockId() { return blockId; }
    public byte[] getNbtBytes() {
        return nbtBytes;
    }

    public String getBehaviour() { return behaviour; }

    public boolean hasBlockEntityData() {
        return nbtBytes != null && nbtBytes.length > 0;
    }
}
