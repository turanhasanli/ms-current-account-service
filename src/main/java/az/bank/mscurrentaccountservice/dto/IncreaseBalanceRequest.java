package az.bank.mscurrentaccountservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class IncreaseBalanceRequest {
    private String accountNumber;
    private BigDecimal amount;
}
