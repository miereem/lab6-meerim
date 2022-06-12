package com.meerim.common.data;

import com.opencsv.bean.CsvBindByName;

import java.util.Objects;

public class DragonCave implements Comparable<DragonCave> {
    @CsvBindByName
    private float numberOfTreasures; //Значение поля должно быть больше 0

    public DragonCave(float numberOfTreasures) {
        this.numberOfTreasures = numberOfTreasures;
    }

    public DragonCave() {
    }
    public float getNumberOfTreasures() {
        return numberOfTreasures;
    }

    public void setNumberOfTreasures(float numberOfTreasures) {
        this.numberOfTreasures = numberOfTreasures;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DragonCave)) {
            return false;
        }
        DragonCave that = (DragonCave) o;
        return Float.compare(that.getNumberOfTreasures(), getNumberOfTreasures()) == 0;
    }

    public String getData() {
        return String.valueOf(numberOfTreasures);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumberOfTreasures());
    }

    @Override
    public String toString() {
        return "DragonCave{"
                + "numberOfTreasures=" + numberOfTreasures
                + '}';
    }

    @Override
    public int compareTo(DragonCave o) {
        Float oValue = o.getNumberOfTreasures();
        Float thisValue = this.getNumberOfTreasures();
        if (oValue - thisValue != 0) {
            return thisValue.compareTo(oValue);
        } else {
            return 0;
        }
    }
}

