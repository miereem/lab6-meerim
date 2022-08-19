package com.meerim.common.data;

import java.io.Serializable;

public enum Color implements Serializable {
    BLACK,
    BLUE,
    YELLOW,
    WHITE,
    BROWN;
    /**
     * Returns comma separated list with the forms.
     * @return BLACK, BLUE, YELLOW, WHITE, BROWN
     */
    public static String nameList() {
        StringBuilder nameList = new StringBuilder();
        for (Color color: values()) {
            nameList.append(color.name()).append(", ");
        }
        return nameList.substring(0, nameList.length() - 2);
    }

}
