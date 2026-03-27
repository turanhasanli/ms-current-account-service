package az.bank.mscurrentaccountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class AccountCreditRequest {

    private String accountNumber;
    private BigDecimal amount;
}