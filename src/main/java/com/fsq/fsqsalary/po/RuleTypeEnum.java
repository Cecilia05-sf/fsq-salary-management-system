package com.fsq.fsqsalary.po;

public enum RuleTypeEnum {
    tax("tax"), pension( "pension"), medicare( "medicare"), unemploy("unemploy"), housing("housing");

    private String type;

    RuleTypeEnum( String type) {
        this.type = type;
    }


    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
    }
}

