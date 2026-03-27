package az.bank.mscurrentaccountservice.mapper;


import az.bank.mscurrentaccountservice.dto.CurrentAccountResponseDto;
import az.bank.mscurrentaccountservice.entity.CurrentAccountEntity;
import org.springframework.stereotype.Component;

@Component
public class CurrentAccountMapper {

    public CurrentAccountResponseDto mapToCurrentAccountResponseDto(CurrentAccountEntity entity) {
        CurrentAccountResponseDto currentAccountResponseDto = new CurrentAccountResponseDto();
        currentAccountResponseDto.setAccountNumber(entity.getAccountNumber());
        currentAccountResponseDto.setStatus(entity.getStatus());
        currentAccountResponseDto.setExpiryDate(entity.getExpiryDate());
        currentAccountResponseDto.setBalance(entity.getBalance());
        currentAccountResponseDto.setCurrency(entity.getCurrency());
        currentAccountResponseDto.setActivatedDate(entity.getActivatedDate());
        currentAccountResponseDto.setCustomerId(entity.getCustomerId());

        return currentAccountResponseDto;
    }

}
