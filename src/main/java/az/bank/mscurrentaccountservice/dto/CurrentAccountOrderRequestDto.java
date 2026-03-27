package az.bank.mscurrentaccountservice.dto;
import az.bank.mscurrentaccountservice.enums.AccountType;
import az.bank.mscurrentaccountservice.enums.Currency;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CurrentAccountOrderRequestDto {
    @NotNull(message = "Account type is required")
    private AccountType accountType;

    @NotNull(message = "Currency is required")
    private Currency currency;

}
