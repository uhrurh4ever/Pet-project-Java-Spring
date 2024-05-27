package com.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Role")
public class Role {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   
   private Long id;
   private String name;


   public Role(String name) {
      super();
      this.name = name;
   }

}