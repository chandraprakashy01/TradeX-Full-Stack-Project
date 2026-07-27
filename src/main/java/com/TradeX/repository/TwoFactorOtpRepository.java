package com.TradeX.repository;

import com.TradeX.modal.TwoFactorOTP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwoFactorOtpRepository extends JpaRepository<TwoFactorOTP,String> {
    TwoFactorOTP findByUserId(long userId);

}
