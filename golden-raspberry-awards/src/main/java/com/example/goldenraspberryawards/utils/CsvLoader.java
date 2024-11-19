package com.example.goldenraspberryawards.utils;

import com.example.goldenraspberryawards.entity.Movie;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVParserBuilder;
import org.springframework.stereotype.Component;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class CsvLoader {

    public List<Movie> loadMovies() {
        List<Movie> movies = new ArrayList<>();
        try (var reader = new CSVReaderBuilder(new InputStreamReader(
                getClass().getResourceAsStream("/movies.csv")))
                .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                .build()) {
            String[] line;
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                movies.add(new Movie(
                        Integer.parseInt(line[0]),
                        line[1],
                        line[2],
                        line[3],
                        "yes".equalsIgnoreCase(line[4])
                ));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar o arquivo CSV", e);
        }
        return movies;
    }
}
