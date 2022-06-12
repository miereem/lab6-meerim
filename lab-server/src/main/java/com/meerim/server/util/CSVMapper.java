package com.meerim.server.util;

import com.meerim.common.data.Dragon;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.HeaderColumnNameMappingStrategyBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

public class CSVMapper {
    public List<Dragon> deserialize(String filename) {
        List<Dragon> dragons = null;
        try {
            dragons = new CsvToBeanBuilder<Dragon>(new FileReader(filename))
                    .withType(Dragon.class)
                    .build()
                    .parse();
            return dragons;
        } catch (FileNotFoundException e) {
            System.out.println("Couldn't read the data. Check if the file is available.");
        }
        return null;
    }
    public void serialize(String filename, List<Dragon> beans) {
        try {
            Writer writer = new FileWriter(filename);
            HeaderColumnNameMappingStrategy<Dragon> strategy = new HeaderColumnNameMappingStrategyBuilder<Dragon>().build();
            strategy.setType(Dragon.class);
            StatefulBeanToCsv<Dragon> beanToCsv = new StatefulBeanToCsvBuilder<Dragon>(writer).withMappingStrategy(strategy).build();
            beanToCsv.write(beans);
            writer.close();
        } catch (IOException | CsvDataTypeMismatchException | CsvRequiredFieldEmptyException e) {
            System.out.println("Couldn't save the data. Check if the file is available.");
        }
    }
}
