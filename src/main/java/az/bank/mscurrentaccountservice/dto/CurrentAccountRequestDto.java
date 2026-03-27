package az.bank.mscurrentaccountservice.dto;


import az.bank.mscurrentaccountservice.enums.AccountType;
import az.bank.mscurrentaccountservice.enums.Currency;
import az.bank.mscurrentaccountservice.enums.CurrentAccountStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CurrentAccountRequestDto {

    @NotNull(message = "Account holder name is required")
    private String accountHolderName;

    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @NotNull(message = "Currency is required")
    private Currency currency;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    private CurrentAccountStatus status;
    private LocalDate expiryDate;
}
