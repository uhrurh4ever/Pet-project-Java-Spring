package com.example.model;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table (name = "Note")
public class Note {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "note_id")
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private String topic;

  @Column(unique = true,nullable = false)
  private String name;

  @Column(name = "created_date")
  private LocalDateTime createdDate;

  @Column(name = "last_modified_date")
  private LocalDateTime lastModifiedDate;
  
  private String text;
  private String tags;

public Note(User user, String topic){
  this.user = user;
  this.topic = topic;
}

}
