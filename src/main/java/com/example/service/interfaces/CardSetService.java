package com.example.service.interfaces;

import com.example.model.CardSet;
import com.example.model.User;

import java.util.List;

public interface CardSetService {

    List<CardSet> getAllCardSets();

    List<CardSet> getCardSetsByUser(User user);

    CardSet saveCardSet(CardSet cardSet);

    CardSet getCardSetById(Long id);

    CardSet updateCardSet(CardSet cardSet);

    void deleteCardSetById(Long id);

    boolean isCardSetNameUnique(User user, CardSet cardSet);
}
