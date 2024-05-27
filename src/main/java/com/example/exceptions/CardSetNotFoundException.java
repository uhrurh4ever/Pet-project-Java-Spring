package com.example.exceptions;

public class CardSetNotFoundException extends Exception {

  private Long cardSetId;

  public CardSetNotFoundException(Long cardSetId) {
      super("CardSet not found with ID: " + cardSetId);
      this.cardSetId = cardSetId;
  }

  public Long getCardSetId() {
      return cardSetId;
  }
}
