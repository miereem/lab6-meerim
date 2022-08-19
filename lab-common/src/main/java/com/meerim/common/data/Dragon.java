package com.meerim.common.data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;


import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import com.opencsv.bean.CsvRecurse;

public class Dragon implements Serializable, Comparable<Dragon> {

    @CsvBindByName
    private String name; //not null, not empty
    @CsvRecurse
    private Coordinates coordinates; //not null
    @CsvBindByName
    private Integer age; // >0, not null
    @CsvBindByName
    private Color color; //null-able
    @CsvBindByName
    private DragonType type; //null-able
    @CsvBindByName
    private DragonCharacter character; // null-able
    @CsvRecurse
    private DragonCave cave; //null-able
    @CsvBindByName
    @CsvDate("yyyy-MM-dd")
    private LocalDate creationDate; //not null, automatic generation
    @CsvBindByName
    private int id; // >0, unique, automatic generation


    public Dragon(String name,
                  Coordinates coordinates,
                  Integer age,
                  Color color,
                  DragonType type,
                  DragonCharacter character,
                  DragonCave cave) {
        this.name = name;
        this.coordinates = coordinates;
        this.age = age;
        this.color = color;
        this.type = type;
        this.character = character;
        this.cave = cave;
        this.creationDate = LocalDate.now();
    }

    public Dragon() {
    }

    public Dragon(String name, Coordinates coordinates, Integer age, Color color, DragonType type, DragonCharacter character, DragonCave cave, LocalDate creationDate, int id) {
        this.name = name;
        this.coordinates = coordinates;
        this.age = age;
        this.color = color;
        this.type = type;
        this.character = character;
        this.cave = cave;
        this.creationDate = creationDate;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dragon)) {
            return false;
        }
        Dragon dragon = (Dragon) o;
        return getId() == dragon.getId() && Objects.equals(name, dragon.name) && Objects.equals(coordinates, dragon.coordinates) && Objects.equals(creationDate, dragon.creationDate) && Objects.equals(age, dragon.age) && color == dragon.color && type == dragon.type && character == dragon.character && Objects.equals(cave, dragon.cave);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, creationDate, age, color, type, character, cave, getId());
    }


    public Integer getAge() {
        return age;
    }

    public DragonCave getCave() {
        return cave;
    }

    @Override
    public String toString() {
        return "Dragon{"
                + "name='" + name + '\''
                + ", coordinates=" + coordinates
                + ", creationDate=" + creationDate
                + ", age=" + age
                + ", color=" + color
                + ", type=" + type
                + ", character=" + character
                + ", cave=" + cave
                + ", id=" + id
                + '}';
    }

    public String[] getData() {
        System.out.println(toString());
        return null;
    }

    @Override
    public int compareTo(Dragon o) {
        Integer oValue = o.getAge();
        Integer thisValue = this.getAge();
        return oValue.compareTo(thisValue);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public DragonType getType() {
        return type;
    }

    public DragonCharacter getCharacter() {
        return character;
    }
    public void setColor(Color c) {
        this.color = c;
    }

    public void setType(DragonType t) {
        this.type = t;
    }

    public void setCharacter(DragonCharacter ch) {
        this.character = ch;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }
}
