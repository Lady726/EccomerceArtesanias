package com.ecommerce.demo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


// La clase UserState que representa el estado de un usuario en el sistema
@Entity
@Table(name = "users_state")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserState {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_state_id")

  private Long user_state_id; // Identificador único del estado del usuario

  @Column(name = "state")
  private String state;

}
