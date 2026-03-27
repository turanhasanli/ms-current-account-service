package az.bank.mscurrentaccountservice.dto.client;

import az.bank.mscurrentaccountservice.dto.enums.CustomerStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerResponseDto {


    private String name;
    private String surname;
    private String fin;
    private LocalDate birthDate;
    private String email;
    private String phoneNumber;
    private CustomerStatus status;

}
