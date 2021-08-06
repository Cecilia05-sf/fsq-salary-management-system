package com.fsq.fsqsalary.po;

public enum RuleTypeEnum {
    TAX("tax"), PENSION( "pension"), MEDICARE( "medicare"), UNEMPLOY("unemploy"), HOUSING("housing");

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

