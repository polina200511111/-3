package com.example.demo.repository;

import com.example.demo.model.Преподаватель;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ПреподавательRepository extends JpaRepository<Преподаватель, Long> {
    List<Преподаватель> findByФиоContainingIgnoreCase(String фио);
}