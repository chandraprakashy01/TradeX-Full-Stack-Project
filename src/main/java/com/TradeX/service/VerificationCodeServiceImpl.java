package com.TradeX.service;

import com.TradeX.Domain.VerificationType;
import com.TradeX.modal.User;
import com.TradeX.modal.VerificationCode;
import com.TradeX.repository.VerificationCodeRepository;
import com.TradeX.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class VerificationCodeServiceImpl implements VerificationCodeService {
    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Override
    public VerificationCode sendverificationCode(User user, VerificationType verificationType) {

        VerificationCode verificationCode1 = new VerificationCode();

        verificationCode1.setOtp(OtpUtils.generatedOTP());

        verificationCode1.setVerificationType(verificationType);

        verificationCode1.setUser(user);


        return verificationCodeRepository.save(verificationCode1);

    }

    @Override
    public VerificationCode getVerificationCodeById(long id) throws Exception {

        Optional<VerificationCode> verificationCode =
                verificationCodeRepository.findById(id);

        if(verificationCode.isPresent()){
            return verificationCode.get();
        }

        throw new Exception("verification code not found");
    }

    @Override
    public VerificationCode getVerificationCodeByUser(long userId) {
        return verificationCodeRepository.findByUserId(userId);
    }

    @Override
    public Void deleteVerificationCodeById(VerificationCode verificationCode) {
        verificationCodeRepository.delete(verificationCode);
        return null;
    }
}
