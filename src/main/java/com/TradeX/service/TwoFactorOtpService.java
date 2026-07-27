package com.TradeX.service;

import com.TradeX.modal.TwoFactorOTP;
import com.TradeX.modal.User;
import com.TradeX.repository.TwoFactorOtpRepository;

public interface TwoFactorOtpService {

TwoFactorOTP createTwoFactorOtp(User user , String otp, String jwt);

TwoFactorOTP findByUser(Long userId);

TwoFactorOTP findByID(String  id);

boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp , String otp);

void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp) ;




}
