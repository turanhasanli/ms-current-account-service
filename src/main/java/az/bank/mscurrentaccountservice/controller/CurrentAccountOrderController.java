package az.bank.mscurrentaccountservice.controller;

import az.bank.mscurrentaccountservice.dto.CurrentAccountOrderRequestDto;
import az.bank.mscurrentaccountservice.dto.CurrentAccountRequestDto;
import az.bank.mscurrentaccountservice.service.CurrentAccountOrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/current-account-order")
@RequiredArgsConstructor
public class CurrentAccountOrderController {

    private final CurrentAccountOrderService currentAccountOrderService;


    @PostMapping
    public void createCurrentAccountOrder(@RequestBody @Valid CurrentAccountOrderRequestDto currentAccountRequestDto,
                                @RequestParam Long customerId) {
        currentAccountOrderService.createCurrentAccountOrder(currentAccountRequestDto,customerId);

    }
}
