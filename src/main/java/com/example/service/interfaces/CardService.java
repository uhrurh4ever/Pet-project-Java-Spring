package com.example.service.interfaces;

import com.example.model.CardSet;
import com.example.model.User;

import java.util.List;


public interface CardService {
    List<CardSet> getAllCardSets();

    List<CardSet> getCardSetByUser(User user);

    CardSet getCardSetById(Long id);
}
