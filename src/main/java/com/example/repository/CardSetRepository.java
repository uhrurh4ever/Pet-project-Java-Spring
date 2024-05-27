package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.CardSet;

public interface CardSetRepository extends JpaRepository<CardSet,Long> {
  
}
