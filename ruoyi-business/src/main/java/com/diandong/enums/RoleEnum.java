package com.diandong.enums;

public enum RoleEnum {
    Canteen_Manager("食堂负责人", "Canteen_Manager"),
    Group_Executive_Chef("集团行政总厨", "Group_Executive_Chef"),
    Group_Executive_Vice_President("集团行政副总", "Group_Executive_Vice_President"),
    Group_Purchasing_Manager("集团采购经理", "Group_Purchasing_Manager"),
    Group_Purchasing_Vice_President("集团采购副总", "Group_Purchasing_Vice_President"),

    ;


    private String name;
    private String key;

    RoleEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String key() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }


    /**
     * 获取角色信息
     *
     * @param value
     * @return
     */
    public static RoleEnum getRoleEnum(String value) {

        for (RoleEnum roleEnum : values()) {
            if (roleEnum.key.equals(value)) {
                return roleEnum;
            }
        }
        return null;
    }
}
