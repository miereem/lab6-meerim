package com.meerim.common.data;

public enum DragonType {
    WATER,
    AIR,
    FIRE;
    /**
     * Returns comma separated list with the forms.
     * @return WATER, AIR, FIRE
     */
    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (DragonType type: values()) {
            nameList.append(type.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }
}

