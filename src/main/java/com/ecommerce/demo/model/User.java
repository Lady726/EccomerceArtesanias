package com.ecommerce.demo.model;

import java.time.LocalDateTime;

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

   private int failedLoginAttempts;
   private LocalDateTime lockTime;
   private static final int MAX_FAILED_ATTEMPTS = 5; // Número máximo de intentos fallidos
    private static final int LOCK_DURATION_MINUTES = 30;

   public boolean isAccountLocked() {
      if (lockTime == null) {
         return false;
      }
      return lockTime.isAfter(LocalDateTime.now());
   }

   public void incrementFailedAttempts() {
      failedLoginAttempts++;
      if (failedLoginAttempts >= MAX_FAILED_ATTEMPTS) {
         lockTime = LocalDateTime.now().plusMinutes(LOCK_DURATION_MINUTES);
      }
   }

   public void resetFailedAttempts() {
      failedLoginAttempts = 0;
      lockTime = null;
   }

   public boolean isLockTimeExpired() {
      return lockTime != null && lockTime.isBefore(LocalDateTime.now());
   }
}