package com.example.service.impl;

import org.springframework.stereotype.Service;
import com.example.model.Card;
import com.example.repository.CardRepository;
import com.example.service.interfaces.CardService;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
public class CardServiceImpl implements CardService {

    private CardRepository cardRepository;

    public CardServiceImpl(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    @Override
    public Card saveCard(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public Card getCardById(Long id) {
        return cardRepository.findById(id).get();
    }

    @Override
    public Card updateCard(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public void deleteCardById(Long id) {
        cardRepository.deleteById(id);
    }
}

