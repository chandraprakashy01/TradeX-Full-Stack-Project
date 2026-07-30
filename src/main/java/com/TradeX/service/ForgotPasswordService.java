package com.TradeX.service;

import com.TradeX.Domain.VerificationType;
import com.TradeX.modal.ForgotPasswordToken;
import com.TradeX.modal.User;

public interface ForgotPasswordService {

ForgotPasswordToken createToken(User user,
                                String  id,
                                String otp,
                                VerificationType verificationType,
                                String sendTo);
ForgotPasswordToken findById(String id);

ForgotPasswordToken findByUser(long userId);

void deleteToken( ForgotPasswordToken token);
}
