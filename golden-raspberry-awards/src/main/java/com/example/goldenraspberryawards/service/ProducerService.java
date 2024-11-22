package com.example.goldenraspberryawards.service;

import com.example.goldenraspberryawards.entity.Movie;
import com.example.goldenraspberryawards.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProducerService {

    private final MovieRepository movieRepository;

    public ProducerService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public Map<String, List<Map<String, Object>>> getProducersIntervals() {
        List<Movie> winnerMovies = movieRepository.findByWinnerTrue();
        Map<String, List<Integer>> producerWins = groupWinnersByProducer(winnerMovies);

        List<Map<String, Object>> minIntervals = new ArrayList<>();
        List<Map<String, Object>> maxIntervals = new ArrayList<>();

        calculateIntervals(producerWins, minIntervals, maxIntervals);

        return Map.of("min", minIntervals, "max", maxIntervals);
    }

    private Map<String, List<Integer>> groupWinnersByProducer(List<Movie> winnerMovies) {
        return winnerMovies.stream()
                .flatMap(movie -> Arrays.stream(movie.getProducers().split("(,| and )"))
                        .map(String::trim)
                        .map(producer -> Map.entry(producer, movie.getYear())))
                .collect(Collectors.groupingBy(
                        Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                ));
    }

    private void calculateIntervals(Map<String, List<Integer>> producerWins, 
                                    List<Map<String, Object>> minIntervals, 
                                    List<Map<String, Object>> maxIntervals) {
        producerWins.forEach((producer, years) -> {
            List<Integer> sortedYears = sortYears(years);

            calculateProducerIntervals(producer, sortedYears, minIntervals, maxIntervals);
        });
    }

    private List<Integer> sortYears(List<Integer> years) {
        return years.stream()
                .sorted()
                .toList();
    }

    private void calculateProducerIntervals(String producer, List<Integer> sortedYears,
                                            List<Map<String, Object>> minIntervals, 
                                            List<Map<String, Object>> maxIntervals) {
        sortedYears.stream()
                .reduce((previousYear, currentYear) -> {
                    int interval = currentYear - previousYear;

                    updateIntervalList(minIntervals, producer, previousYear, currentYear, interval, true);
                    updateIntervalList(maxIntervals, producer, previousYear, currentYear, interval, false);

                    return currentYear;
                });
    }

    private void updateIntervalList(List<Map<String, Object>> intervals, 
                                    String producer, int previousYear, 
                                    int currentYear, int interval, boolean isMin) {
        if (shouldReplaceIntervals(intervals, interval, isMin)) {
            intervals.clear();
        }

        if (intervals.isEmpty() || isSameInterval(intervals, interval)) {
            intervals.add(createIntervalMap(producer, previousYear, currentYear, interval));
        }
    }

    private boolean shouldReplaceIntervals(List<Map<String, Object>> intervals, int interval, boolean isMin) {
        if (intervals.isEmpty()) return true;

        int currentInterval = (int) intervals.get(0).get("interval");
        return isMin ? interval < currentInterval : interval > currentInterval;
    }

    private boolean isSameInterval(List<Map<String, Object>> intervals, int interval) {
        return !intervals.isEmpty() && (int) intervals.get(0).get("interval") == interval;
    }

    private Map<String, Object> createIntervalMap(String producer, int previousYear, 
                                                  int currentYear, int interval) {
        return Map.of(
                "producer", producer,
                "interval", interval,
                "previousWin", previousYear,
                "followingWin", currentYear
        );
    }
}
