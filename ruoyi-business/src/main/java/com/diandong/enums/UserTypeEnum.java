package com.diandong.enums;

/**
 * @description: 用户表 用户类型
 * @author: 滕玉静
 * @date: 2022-05-11
 */
public enum UserTypeEnum {
    SYSTEM( "00","系统用户"),
    OPERATOR( "01","食堂用户"),
    DINERS( "02","食客"),
//    SHOP( "02","商户"),
    ;

    private String value;
    private String description;

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 构造方法
     * @param value
     * @param description
     */
    UserTypeEnum(String value, String description) {
        this.description=description; this.value = value;
    }

    public static String getDescriptionByValue(String value){
        String result=null;
        if(value==null){ return result; }
        for(UserTypeEnum item: UserTypeEnum.values()) {
            if(item.getValue().equals(value)) {
                return item.getDescription();
            }
        }
        return result;
    }

}
