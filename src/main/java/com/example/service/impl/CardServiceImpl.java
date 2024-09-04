package com.example.service.impl;

import com.example.model.CardSet;
import com.example.model.User;
import com.example.repository.CardRepository;
import com.example.service.interfaces.CardService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
@Setter
public class CardServiceImpl implements CardService {

    CardRepository cardRepository;

    @Override
    public List<CardSet> getAllCardSets() {
        return cardRepository.findAll();
    }

    @Override
    public List<CardSet> getCardSetByUser(User user) {
        return cardRepository.findByUser(user);
    }

    @Override
    public CardSet getCardSetById(Long id) {
        return cardRepository.findById(id).get();
    }

}
