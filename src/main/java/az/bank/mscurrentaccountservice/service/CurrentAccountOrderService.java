package az.bank.mscurrentaccountservice.service;


import az.bank.mscurrentaccountservice.client.CustomerClient;
import az.bank.mscurrentaccountservice.dto.CurrentAccountOrderRequestDto;
import az.bank.mscurrentaccountservice.dto.client.CustomerResponseDto;
import az.bank.mscurrentaccountservice.dto.enums.CustomerStatus;
import az.bank.mscurrentaccountservice.entity.CurrentAccountEntity;
import az.bank.mscurrentaccountservice.entity.CurrentAccountOrderEntity;
import az.bank.mscurrentaccountservice.enums.AccountType;
import az.bank.mscurrentaccountservice.enums.CurrentAccountStatus;
import az.bank.mscurrentaccountservice.enums.OrderStatus;
import az.bank.mscurrentaccountservice.exceptions.AccountLimitExceededException;
import az.bank.mscurrentaccountservice.exceptions.AgeRestrictionException;
import az.bank.mscurrentaccountservice.exceptions.CustomerBlockedException;
import az.bank.mscurrentaccountservice.exceptions.NotFoundException;
import az.bank.mscurrentaccountservice.repository.CurrentAccountOrderRepository;
import az.bank.mscurrentaccountservice.repository.CurrentAccountRepository;
import az.bank.mscurrentaccountservice.util.IbanGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CurrentAccountOrderService {
    private final CurrentAccountOrderRepository currentAccountOrderRepository;
    private final CurrentAccountRepository currentAccountRepository;
    private final CustomerClient customerClient;
    private final IbanGenerator ibanGenerator;
    private final CurrentAccountService currentAccountService;


    @Value("${account.max-accounts-per-customer}")
    private int maxAccounts;


    public void createCurrentAccountOrder(CurrentAccountOrderRequestDto request, Long customerId) {
        CustomerResponseDto customer = customerClient.getCustomerById(customerId);
        if (customer == null) {
            throw new NotFoundException("Customer not found");
        }

        CurrentAccountOrderEntity currentAccountOrder = new CurrentAccountOrderEntity();
        currentAccountOrder.setAccountType(request.getAccountType());
        currentAccountOrder.setStatus(OrderStatus.PENDING);
        currentAccountOrder.setCurrency(request.getCurrency());
        currentAccountOrder.setRejectReason("Current account order pending");
        currentAccountOrder.setDescription(String.format("Order for %s account", request.getAccountType()));
        currentAccountOrder.setCreatedAt(LocalDateTime.now());
        currentAccountOrder.setUpdatedAt(LocalDateTime.now());
        currentAccountOrder.setCustomerId(customerId);

        if (customer.getBirthDate().isAfter(LocalDate.now().minusYears(18))) {
            currentAccountOrder.setStatus(OrderStatus.REJECTED);
            currentAccountOrder.setRejectReason("Customer must be at least 18 years old to create a card.");
            currentAccountOrderRepository.saveAndFlush(currentAccountOrder);
            throw new AgeRestrictionException("Customer must be at least 18 years old");
        }

        if (customer.getStatus() != CustomerStatus.ACTIVE) {
            currentAccountOrder.setStatus(OrderStatus.REJECTED);
            currentAccountOrder.setRejectReason("Customer is blocked and cannot create a card.");
            currentAccountOrderRepository.saveAndFlush(currentAccountOrder);
            throw new CustomerBlockedException("Customer is blocked");
        }

        long currentAccountCount = currentAccountRepository
                .countByCustomerIdAndStatus(customerId, CurrentAccountStatus.ACTIVE);

        if (currentAccountCount >= maxAccounts) {
            currentAccountOrder.setStatus(OrderStatus.REJECTED);
            currentAccountOrder.setRejectReason("Customer has reached the maximum number of accounts.");
            currentAccountOrderRepository.saveAndFlush(currentAccountOrder);
            throw new AccountLimitExceededException
                    ("Customer already has the maximum number of accounts.(" + maxAccounts + ")");
        }
        CurrentAccountEntity account = new CurrentAccountEntity();

        try {
            account = currentAccountService.createCurrentAccount(request, customerId, customer);
        } catch (Exception e) {
            currentAccountOrder.setStatus(OrderStatus.REJECTED);
            currentAccountOrder.setRejectReason("Internal server error occurred while creating the account. Please try again later.");
            currentAccountRepository.saveAndFlush(account);
            throw new AccountLimitExceededException(
                    "Internal server error occurred while creating the account. Please try again later."
            );

        }


        currentAccountOrder.setCurrentAccount(account);
        currentAccountOrderRepository.save(currentAccountOrder);


    }
}

