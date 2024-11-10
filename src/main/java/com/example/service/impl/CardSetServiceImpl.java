package com.example.service.impl;

import com.example.model.Card;
import com.example.model.CardSet;
import com.example.model.User;
import com.example.repository.CardSetRepository;
import com.example.service.interfaces.CardSetService;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Service
public class CardSetServiceImpl implements CardSetService {

    private final CardSetRepository cardSetRepository;

    @Override
    public List<CardSet> getAllCardSets() {
        return cardSetRepository.findAll();
    }

    @Override
    public List<CardSet> getCardSetsByUser(User user) {
        return cardSetRepository.findByUser(user);
    }

    @Override
    public CardSet saveCardSet(CardSet cardSet) {
        return cardSetRepository.save(cardSet);
    }

    @Override
    public CardSet getCardSetById(Long id) {
        return cardSetRepository.findById(id).get();
    }

    @Override
    public CardSet updateCardSet(CardSet cardSet) {
        return cardSetRepository.save(cardSet);
    }

    @Override
    public void deleteCardSetById(Long id) {
        cardSetRepository.deleteById(id);
    }

    @Override
    public boolean isCardSetNameUnique(User user, CardSet cardSet) {
        List<CardSet> existingCardSets = getCardSetsByUser(user);
        return existingCardSets.stream()
                .noneMatch(existingCardSet -> !existingCardSet.getId().equals(cardSet.getId())
                        && existingCardSet.getName().equals(cardSet.getName()));
    }

    @Override
    public CardSet addCardToCardSet(CardSet cardSet, Card card) {
        if (!cardSet.getCards().contains(card)) {
            cardSet.getCards().add(card);
            card.getCardSets().add(cardSet);
        }
        return cardSetRepository.save(cardSet);
    }

    @Override
    public void deleteCardFromCardSet(Long id) {
        
      
    }

    
}
