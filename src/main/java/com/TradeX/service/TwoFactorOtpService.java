package com.TradeX.service;

import com.TradeX.modal.TwoFactorOTP;
import org.springframework.security.core.userdetails.User;

public interface TwoFactorOtpService {

TwoFactorOTP createTwoFactorOtp(User user , String otp, String jwt);

TwoFactorOTP findByUser(Long userId);

TwoFactorOTP findByID(String  id);

boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp , String otp);

static void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp);



}
