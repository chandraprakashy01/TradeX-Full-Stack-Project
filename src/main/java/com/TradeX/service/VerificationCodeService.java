package com.TradeX.service;

import com.TradeX.Domain.VerificationType;
import com.TradeX.modal.User;
import com.TradeX.modal.VerificationCode;

public interface  VerificationCodeService {

    VerificationCode sendverificationCode(User user, VerificationType verificationType);

    VerificationCode getVerificationCodeById(long id) throws Exception;

    VerificationCode getVerificationCodeByUser(long userId);

    Void deleteVerificationCodeById(VerificationCode verificationCode);




}
