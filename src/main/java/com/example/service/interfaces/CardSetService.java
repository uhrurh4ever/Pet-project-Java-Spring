package com.example.service.interfaces;

import java.util.Collection;

import com.example.model.CardSet;
import com.example.model.CardSet;

public interface CardSetService  {
  
  Collection<CardSet> getAllCardSets();

  CardSet saveCardSet(CardSet cardSet);

  CardSet getCardSetById(Long id) throws Exception;

  CardSet updateCardSet(CardSet cardSet) throws Exception;

  void deleteCardSetById(Long id);

  void addCardToCardSet(Long cardSetId, Long cardId) throws Exception;

  void removeCardFromCardSet(Long cardSetId, Long cardId)  throws Exception;

  
  
}
