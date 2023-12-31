package com.ecommerce.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// La clase User que representa un usuario en el sistema
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long user_id;

   private String user_type;

   
   private String user_state_id;

   private String name;
   private String email;
   private String password;

}