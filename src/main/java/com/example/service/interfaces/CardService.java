package com.example.service.interfaces;

import com.example.model.Card;

public interface CardService  {

  Card saveCard(Card card);

  Card getCardById(Long id);

  Card updateCard(Card card);

  void deleteCardById(Long id);

}
