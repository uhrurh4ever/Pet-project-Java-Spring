package com.example.service.impl;

import java.util.Collection;

import com.example.model.CardSet;
import org.springframework.stereotype.Service;

import com.example.exceptions.CardNotFoundException;
import com.example.exceptions.CardSetNotFoundException;
import com.example.model.Card;
import com.example.model.CardSet;
import com.example.repository.CardSetRepository;
import com.example.service.interfaces.CardSetService;

import lombok.Getter;
import lombok.Setter;
import com.example.repository.CardRepository;

@Getter
@Setter
@Service
public class CardSetServiceImpl implements CardSetService {

    private CardSetRepository cardSetRepository;

    private CardRepository cardRepository;

    public CardSetServiceImpl(CardSetRepository cardSetRepository, CardRepository cardRepository) {
        this.cardSetRepository = cardSetRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public Collection<CardSet> getAllCardSets() {
        return cardSetRepository.findAll();
    }

    @Override
    public CardSet saveCardSet(CardSet cardSet) {
        return cardSetRepository.save(cardSet);
    }

    @Override
    public CardSet getCardSetById(Long id) throws CardSetNotFoundException {
        return cardSetRepository.findById(id)
                .orElseThrow(() -> new CardSetNotFoundException(id));
    }

    @Override
    public CardSet updateCardSet(CardSet cardSet) throws CardSetNotFoundException {
        CardSet existingCardSet = cardSetRepository.findById(cardSet.getId())
                .orElseThrow(() -> new CardSetNotFoundException(cardSet.getId()));
        return cardSetRepository.save(existingCardSet);
    }

    @Override
    public void deleteCardSetById(Long id) {
        cardSetRepository.deleteById(id);
    }

    @Override
    public void addCardToCardSet(Long cardSetId, Long cardId) throws CardNotFoundException, CardSetNotFoundException {
        CardSet cardSet = cardSetRepository.findById(cardSetId)
                .orElseThrow(() -> new CardSetNotFoundException(cardSetId));
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));

        Collection<Card> cards = cardSet.getCards();
        cards.add(card);
        cardSet.setCards(cards);

        cardSetRepository.save(cardSet);
    }
    
        /**
     * Удаление карточки из набора карточек
     *
     * @param cardSetId Идентификатор набора карточек
     * @param cardId Идентификатор карточки
     * @throws CardNotFoundException Ошибка, возникающая при попытке удалить несуществующую карточку
     * @throws CardSetNotFoundException Ошибка, возникающая при попытке удалить карточку из несуществующего набора карточек
     */
    @Override
    public void removeCardFromCardSet(Long cardSetId, Long cardId) throws CardSetNotFoundException, CardNotFoundException {
      CardSet cardSet = cardSetRepository.findById(cardSetId)
                .orElseThrow(() -> new CardSetNotFoundException(cardSetId));
      Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new CardNotFoundException(cardId));
        cardRepository.delete(card);
        cardSetRepository.save(cardSet);
    }
  }

