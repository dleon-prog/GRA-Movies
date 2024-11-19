package com.example.goldenraspberryawards;

import com.example.goldenraspberryawards.entity.Movie;
import com.example.goldenraspberryawards.repository.MovieRepository;
import com.example.goldenraspberryawards.utils.CsvLoader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class GoldenRaspberryAwardsApplication implements CommandLineRunner {

    private final MovieRepository movieRepository;
    private final CsvLoader csvLoader;

    public GoldenRaspberryAwardsApplication(MovieRepository movieRepository, CsvLoader csvLoader) {
        this.movieRepository = movieRepository;
        this.csvLoader = csvLoader;
    }

    public static void main(String[] args) {
        SpringApplication.run(GoldenRaspberryAwardsApplication.class, args);
    }

    @Override
    public void run(String... args) {
        List<Movie> movies = csvLoader.loadMovies();
        movieRepository.saveAll(movies);
    }
}
