package az.bank.mscurrentaccountservice.util;
import az.bank.mscurrentaccountservice.enums.AccountType;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Random;

@Component
public class IbanGenerator {

    public String generate() {

        Random random = new Random();

        StringBuilder iban = new StringBuilder("AZ");

        for (int i = 0; i < 20; i++) {
            iban.append(random.nextInt(10));
        }

        return iban.toString();
    }
    public LocalDate generateExpiryDate(AccountType accountType){
        LocalDate today= LocalDate.now();
        switch (accountType) {
            case CURRENT:
                return today.plusYears(3);
            case SAVING:
                return today.plusYears(5);
            default:
                throw new IllegalArgumentException("Unknown card type: " + AccountType.CURRENT);
        }
    }
}
