package com.TradeX.service;

import com.TradeX.modal.TwoFactorOTP;
import com.TradeX.repository.TwoFactorOtpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TwoFactorServiceImpl implements TwoFactorOtpService {

    @Autowired
    private TwoFactorOtpRepository twoFactorOtpRepository;

    @Override
    public TwoFactorOTP createTwoFactorOtp(User user, String otp, String jwt) {
        UUID uuid = UUID.randomUUID();
        String id = uuid.toString();
        TwoFactorOTP twoFactorOtp = new TwoFactorOTP();
        twoFactorOtp.setId(id);
        twoFactorOtp.setOtp(otp);
        twoFactorOtp.setJwt(jwt);
        twoFactorOtp.setUser(user);
         return twoFactorOtpRepository.save(twoFactorOtp);




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
