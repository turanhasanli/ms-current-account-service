package az.bank.mscurrentaccountservice.mapper;


import az.bank.mscurrentaccountservice.dto.CurrentAccountOrderResponseDto;
import az.bank.mscurrentaccountservice.entity.CurrentAccountOrderEntity;
import org.springframework.stereotype.Component;

@Component
public class CurrentAccountOrderMapper {
    public static CurrentAccountOrderResponseDto mapToCurrentAccountOrderResponseDto(CurrentAccountOrderEntity currentAccountOrder) {

        CurrentAccountOrderResponseDto currentAccountOrderResponseDto = new CurrentAccountOrderResponseDto();
       currentAccountOrder.setAccountType(currentAccountOrder.getAccountType());
       currentAccountOrder.setCurrency(currentAccountOrder.getCurrency());
       currentAccountOrder.setStatus(currentAccountOrder.getStatus());
       currentAccountOrder.setRejectReason(currentAccountOrder.getRejectReason());
       currentAccountOrder.setId(currentAccountOrder.getId());
       currentAccountOrder.setCreatedAt(currentAccountOrder.getCreatedAt());

        return currentAccountOrderResponseDto;
    }
}