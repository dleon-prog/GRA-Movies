package com.example.goldenraspberryawards.repository;

import com.example.goldenraspberryawards.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
