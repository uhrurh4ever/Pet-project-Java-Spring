package com.example.exceptions;

public class CardNotFoundException extends Exception {

  private Long cardId;

  public CardNotFoundException(Long cardId) {
      super("Card not found with ID: " + cardId);
      this.cardId = cardId;
  }

  public Long getCardId() {
      return cardId;
  }
}