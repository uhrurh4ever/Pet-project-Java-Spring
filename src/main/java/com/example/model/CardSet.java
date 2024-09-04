package com.example.model;

import jakarta.persistence.JoinColumn;
import java.util.Collection;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Card_set")
public class CardSet {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "set_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY) 
  @JoinColumn(name = "user_id", nullable = false) 
  private User user;

  @Column(name = "topic", nullable = false)
  private Long topic;


  @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinTable(name = "card_set_cards",
          joinColumns = @JoinColumn(name = "set_id"),
          inverseJoinColumns = @JoinColumn(name = "card_id"))
  private Collection<Card> cards;
  
  private String name;
  private String description;

}
