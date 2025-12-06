package com.PinkCats.worldprotect.Database.Item;

import net.minecraft.world.item.ItemStack;

public class RecordItemRaw {
    private String operator;
    private String operatorUUID;
    private String world;
    private int x;
    private int y;
    private int z;
    private ItemStack itemdata;
    private String behaviour;

    // 构造函数
    public RecordItemRaw(String operator,String operatorUUID, String world, int x, int y, int z, ItemStack itemdata, String behaviour) {
        this.operator = operator;
        this.world = world;
        this.operatorUUID = operatorUUID;
        this.x = x;
        this.y = y;
        this.z = z;
        this.itemdata = itemdata;
        this.behaviour = behaviour;
    }

    public String getOperator() {
        return operator;
    }

    public String getOperatorUUID() {
        return operatorUUID;
    }

    public String getWorld() {
        return world;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public ItemStack getItemdata() {
        return itemdata;
    }

    public String getBehaviour() {
        return behaviour;
    }


}
