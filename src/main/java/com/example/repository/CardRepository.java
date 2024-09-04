package com.example.repository;

import com.example.model.CardSet;
import com.example.model.Note;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<CardSet,Long> {
    List<CardSet> findByUser(User user);
}
