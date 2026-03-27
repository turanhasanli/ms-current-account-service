package az.bank.mscurrentaccountservice.dto;


import az.bank.mscurrentaccountservice.enums.AccountType;
import az.bank.mscurrentaccountservice.enums.Currency;
import az.bank.mscurrentaccountservice.enums.CurrentAccountStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class CurrentAccountResponseDto {

    private String accountNumber;
    private CurrentAccountStatus status;
    private LocalDate expiryDate;
    private BigDecimal balance;
    private Currency currency;
    private LocalDateTime activatedDate;
    private Long customerId;
}
