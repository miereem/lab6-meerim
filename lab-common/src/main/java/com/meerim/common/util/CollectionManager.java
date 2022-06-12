package com.meerim.common.util;

import com.meerim.common.data.Dragon;
import com.meerim.common.data.DragonCave;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static java.lang.Integer.MAX_VALUE;

public class CollectionManager {
    private LinkedHashSet<Dragon> mainData = new LinkedHashSet<>();
    private final LocalDate creationDate = LocalDate.now();
    private LinkedHashSet<Integer> idSet = new LinkedHashSet<>();
    private int nextId = 1;

        public int getNextId() {
            while (idSet.contains(nextId)) {
                nextId++;
            }
            return nextId;
        }

    public void initialiseData(LinkedHashSet<Dragon> linkedHashSet) {
        this.mainData = linkedHashSet;
        idSet.clear();
        for (Dragon dragon: mainData) {
            idSet.add(dragon.getId());
        }
    }


    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LinkedHashSet<Dragon> getMainData() {
        return mainData;
    }
    public List<Dragon> getDataForSerialization() {
        List<Dragon> list = new ArrayList<Dragon>(mainData);
        return list;
    }
    public Dragon getMinId() {
        Dragon minIdDragon = null;
        int minId = MAX_VALUE;
        for (Dragon dragon : mainData) {
            if (dragon.getId() < minId) {
                minId = dragon.getId();
                minIdDragon = dragon;
            }
        }
        return minIdDragon;
    }

    public void clear() {
        mainData.clear();
        idSet.clear();
    }
    public void add(Dragon dragon) {
        dragon.setId(getNextId());
        mainData.add(dragon);
        idSet.add(dragon.getId());
    }
    public Dragon getMinAge() {
            int minAge = MAX_VALUE;
            Dragon minAgeDragon = null;
        for (Dragon dragon: mainData) {
            if (dragon.getAge() < minAge) {
                minAge = dragon.getAge();
                minAgeDragon = dragon;
            }
        }
        return minAgeDragon;
        }
    public boolean addIfMin(Dragon dragon) {
        if (mainData.isEmpty() || dragon.compareTo(getMinAge()) > 0) {
            mainData.add(dragon);
            idSet.add(dragon.getId());
            return true;
        }
        return false;
    }
    public double getAverage() {
        double avAge = 0;
        for (Dragon dragon : mainData) {
            avAge = avAge + dragon.getAge();
        }
        avAge = avAge / mainData.size();
        return avAge;
    }
    public Dragon getMinCave() {
        DragonCave minCave = new DragonCave(MAX_VALUE);
        Dragon minCaveDragon = null;
        for (Dragon dragon : mainData) {
            if (minCave.compareTo(dragon.getCave()) > 0) {
                minCave = dragon.getCave();
                minCaveDragon = dragon;
            }
        }
        return minCaveDragon;
    }
    public boolean isEmpty() {
        return mainData.isEmpty();
    }
    public void removeGreater(Dragon dragon) {
        mainData.removeIf(x -> x.compareTo(dragon) < 0);
        initialiseData(mainData);
    }
    public boolean removeById(int intArg) {
        if (mainData.removeIf(x -> x.getId() == intArg)) {
            idSet.remove(intArg);
            return true;
        }
        return false;
    }
    public void removeId(int id) {
            idSet.remove(id);
    }
}
