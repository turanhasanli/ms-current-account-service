package az.bank.mscurrentaccountservice.controller;


import az.bank.mscurrentaccountservice.dto.AccountCreditRequest;
import az.bank.mscurrentaccountservice.dto.AccountDebitRequest;

import az.bank.mscurrentaccountservice.service.CurrentAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts/internal")
@RequiredArgsConstructor
public class CurrentAccountInternalController {

    private final CurrentAccountService accountService;

    @PutMapping("/debit")
    public void debit(@RequestBody AccountDebitRequest request) {
        accountService.debit(request);
    }


    @PutMapping("/credit")
    public void credit(@RequestBody AccountCreditRequest request) {
        accountService.credit(request);
    }

    @GetMapping("/{accountNumber}/currency")
    public String getCurrency(@PathVariable String accountNumber) {
        return accountService.getCurrency(accountNumber);
    }

}