package com.TradeX.service;

import com.TradeX.modal.TwoFactorOTP;
import org.springframework.security.core.userdetails.User;

public class TwoFactorServiceImpl implements TwoFactorOtpService {
    @Override
    public TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt) {
        return null;
    }

    @Override
    public TwoFactorOTP findByUser(Long userId) {
        return null;
    }

    @Override
    public TwoFactorOTP findByID(String id) {
        return null;
    }

    @Override
    public boolean verifyTwoFactorOtp(TwoFactorOTP twoFactorOtp, String otp) {
        return false;
    }

    @Override
    public void deleteTwoFactorOtp(TwoFactorOTP twoFactorOtp) {

    }
}
