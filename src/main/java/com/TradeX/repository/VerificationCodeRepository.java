package com.TradeX.repository;

import com.TradeX.modal.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode,Long> {

    public VerificationCode findByUserId(Long userId) ;
}
