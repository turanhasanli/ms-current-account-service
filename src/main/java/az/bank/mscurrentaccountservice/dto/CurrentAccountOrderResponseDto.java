package az.bank.mscurrentaccountservice.dto;

import az.bank.mscurrentaccountservice.entity.CurrentAccountEntity;
import az.bank.mscurrentaccountservice.enums.AccountType;
import az.bank.mscurrentaccountservice.enums.Currency;
import az.bank.mscurrentaccountservice.enums.OrderStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CurrentAccountOrderResponseDto {

    private AccountType accountType;
    private String accountNumber;
    private OrderStatus status;
    private String rejectReason;

    private CurrentAccountResponseDto currentAccount;
    private LocalDateTime createdAt;

}
