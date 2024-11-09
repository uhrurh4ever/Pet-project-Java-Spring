package com.example.repository;

import com.example.model.CardSet;
import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ListResourceBundle;

@Repository
public interface CardSetRepository extends JpaRepository<CardSet,Long> {
    List<CardSet> findByUser(User user);
}
