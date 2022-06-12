package com.meerim.common.util;

import com.meerim.common.data.Dragon;
import com.meerim.common.data.DragonCharacter;
import com.meerim.common.data.Color;
import com.meerim.common.data.DragonCave;
import com.meerim.common.data.DragonType;
import com.meerim.common.data.Coordinates;

import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Asks and receives user input data to make an object.
 */
public class DragonMaker {
    private static final String ERROR_MESSAGE = "Your enter was not correct type. Try again";
    private final OutputManager outputManager;
    private final Asker asker;

    public DragonMaker(UserInputManager userInputManager, OutputManager outputManager) {
        this.outputManager = outputManager;
        this.asker = new Asker(userInputManager, outputManager);
    }

    public Dragon makeDragon() {
        return askForDragon();
    }

    private Dragon askForDragon() {
        outputManager.println("Enter Dragon data");
        String name = asker.ask(arg -> (arg).length() > 0, "Enter name (String)",
                ERROR_MESSAGE, "The string must not be empty", x -> x, false);

        Integer age = asker.ask(arg -> (arg) > 0, "Enter Dragon's age (int) (has to be larger than 0, can't be null)",
                ERROR_MESSAGE, "Your int must be >0. Try again", Integer::parseInt, false); // >0, null-able

        Color color = asker.ask(arg -> true, "Enter Dragon's color (BLACK, BLUE, YELLOW, WHITE)(can be null)",
                ERROR_MESSAGE, ERROR_MESSAGE, Color::valueOf, true); //not null

        DragonType type = asker.ask(arg -> true, "Enter Dragon Type (WATER, AIR, FIRE) (can be null)",
                ERROR_MESSAGE, ERROR_MESSAGE, DragonType::valueOf, true); // null-able

        DragonCharacter character = asker.ask(arg -> true, "Enter Dragon character (CUNNING, GOOD, EVIL, CHAOTIC_EVIL) (can be null)",
                ERROR_MESSAGE, ERROR_MESSAGE, DragonCharacter::valueOf, true); // null-able


        Coordinates coordinates = askForCoordinates(); //not null
        DragonCave cave = askForDragonCave(); //not null
        return new Dragon(name, coordinates, age, color, type, character, cave);
    }

    private Coordinates askForCoordinates() {
        outputManager.println("Enter coordinates data");
        final long xLimitation = -454;
        final long yLimitation = -511;
        Integer x = asker.ask(arg -> (arg) > xLimitation, "Enter x (Integer)",
                ERROR_MESSAGE, "The Integer must be >-454. Try again", Integer::parseInt, false); //
        long y = asker.ask(arg -> (arg) > yLimitation, "Enter y (Double)",
                ERROR_MESSAGE, "The double must be > -511. Try again", Long::parseLong, false); // not null
        return new Coordinates(x, y);
    }
    private DragonCave askForDragonCave() {
        outputManager.println("Enter Dragon Cave");

        Float numberOfTreasures  = asker.ask(arg -> (arg) > 0, "Enter Number of Treasures (Float)(>0)",
                ERROR_MESSAGE, "The float must be >0. Try again", Float::parseFloat, false);

        return new DragonCave(numberOfTreasures);
    }


    public static class Asker {

        private final UserInputManager userInputManager;
        private final OutputManager outputManager;


        public Asker(UserInputManager userInputManager, OutputManager outputManager) {
            this.userInputManager = userInputManager;
            this.outputManager = outputManager;
        }

        public <T> T ask(Predicate<T> predicate,
                         String askMessage,
                         String errorMessage,
                         String wrongValueMessage,
                         Function<String, T> converter,
                         boolean nullable) {
            outputManager.println(askMessage);
            String input;
            T value;
            do {
                try {
                    input = userInputManager.nextLine();
                    if ("".equals(input) && nullable) {
                        return null;
                    }

                    value = converter.apply(input);

                } catch (IllegalArgumentException e) {
                    outputManager.println(errorMessage);
                    continue;
                }
                if (predicate.test(value)) {
                    return value;
                } else {
                    outputManager.println(wrongValueMessage);
                }
            } while (true);
        }
    }
}
