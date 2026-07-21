package com.TradeX.modal;

import com.TradeX.Domain.VerificationType;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class TowFactorAuth {
    private boolean isEnabled = false;
    private VerificationType sendTo;
}
