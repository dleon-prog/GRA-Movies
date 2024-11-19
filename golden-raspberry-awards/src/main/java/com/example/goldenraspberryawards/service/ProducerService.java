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
        List<Movie> winners = movieRepository.findAll()
                .stream()
                .filter(Movie::getWinner)
                .collect(Collectors.toList());

        Map<String, List<Integer>> producerWins = new HashMap<>();
        for (Movie movie : winners) {
            for (String producer : movie.getProducers().split(" and ")) {
                producerWins.computeIfAbsent(producer.trim(), k -> new ArrayList<>()).add(movie.getYear());
            }
        }

        List<Map<String, Object>> minList = new ArrayList<>();
        List<Map<String, Object>> maxList = new ArrayList<>();

        for (Map.Entry<String, List<Integer>> entry : producerWins.entrySet()) {
            List<Integer> years = entry.getValue();
            Collections.sort(years);

            for (int i = 1; i < years.size(); i++) {
                int interval = years.get(i) - years.get(i - 1);
                Map<String, Object> result = Map.of(
                        "producer", entry.getKey(),
                        "interval", interval,
                        "previousWin", years.get(i - 1),
                        "followingWin", years.get(i)
                );

                if (minList.isEmpty() || interval < (int) minList.get(0).get("interval")) {
                    minList.clear();
                    minList.add(result);
                } else if (interval == (int) minList.get(0).get("interval")) {
                    minList.add(result);
                }

                if (maxList.isEmpty() || interval > (int) maxList.get(0).get("interval")) {
                    maxList.clear();
                    maxList.add(result);
                } else if (interval == (int) maxList.get(0).get("interval")) {
                    maxList.add(result);
                }
            }
        }

        return Map.of("min", minList, "max", maxList);
    }
}
