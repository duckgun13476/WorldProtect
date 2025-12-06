package com.PinkCats.worldprotect.Database.Item;

public class RecordPlayer {

    private String operator;
    private String operatorUUID;

    public RecordPlayer(String operator,String operatorUUID){
        this.operator = operator;
        this.operatorUUID = operatorUUID;
    }

    public String getUUID() {
        return operatorUUID;
    }

    public String getOperator() {
        return operator;
    }
}
