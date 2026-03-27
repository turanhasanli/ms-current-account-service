package az.bank.mscurrentaccountservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CurrentAccountBalanceResponse {
    private String status;
    private String accountNumber;
    private String accountHolderName;
    private String currency;
    private String transactionDate;
    private BigDecimal oldBalance;
    private BigDecimal newBalance;
}
