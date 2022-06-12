package com.meerim.common.data;

import com.opencsv.bean.CsvBindByName;


import java.util.Objects;

public class Coordinates {
    @CsvBindByName
    private Integer x; //> -896
    @CsvBindByName
    private Long y; //<=135, not null

    public Coordinates(Integer x, Long y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
    }

    @Override
    public String toString() {
        return "Coordinates{"
                + "x=" + x
                + ", y=" + y
                + '}';
    }
    public String getData() {
        return x + "," + y;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordinates that = (Coordinates) o;
        return x == that.x && Objects.equals(y, that.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
