package az.bank.mscurrentaccountservice.controller;

import az.bank.mscurrentaccountservice.dto.*;
import az.bank.mscurrentaccountservice.enums.CurrentAccountStatus;
import az.bank.mscurrentaccountservice.service.CurrentAccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")

public class CurrentAccountController {

    private final CurrentAccountService currentAccountService;


    @PutMapping("{customerId}/update")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update current account status", description = "Update current account status")
    public void updateCurrentAccountStatus(@PathVariable Long customerId, @RequestParam String accountNumber,
                                           @RequestParam CurrentAccountStatus accountStatus) {
        currentAccountService.updateCurrentAccountStatus(accountNumber, customerId, accountStatus);
    }

    @GetMapping("/customer/{customerId}")
    @Operation(summary = "Get current account by customer id", description = "Get current account by customer id")
    public List<CurrentAccountResponseDto> getAccountsByCustomerId(@PathVariable Long customerId) {
        return currentAccountService.getAccountsByCustomerId(customerId);

    }
    @PostMapping("/increase-balance")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Increase current account balance", description = "Increase current account balance")
    public void increaseBalance(
            @RequestBody IncreaseBalanceRequest request) {
        currentAccountService.increaseBalance(request.getAccountNumber(),
                request.getAmount()
        );

    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete current account by id", description = "Delete current account by id")
    public void deleteCurrentAccountById(@PathVariable Long id) {
        currentAccountService.deleteCurrentAccountById(id);
    }


}



