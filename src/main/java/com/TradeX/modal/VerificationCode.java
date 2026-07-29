package com.TradeX.modal;

import com.TradeX.Domain.VerificationType;
import jakarta.persistence.*;

import lombok.Data;

@Entity
@Data
public class VerificationCode {
     @Id
     @GeneratedValue(strategy=GenerationType.AUTO)
     private long id;

     private String otp;

     @OneToOne
     private User user;

     private String email;
     private String mobile;

     private VerificationType verificationType;
}
