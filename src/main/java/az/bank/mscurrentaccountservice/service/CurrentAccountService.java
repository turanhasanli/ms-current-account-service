package az.bank.mscurrentaccountservice.service;

import az.bank.mscurrentaccountservice.dto.*;
import az.bank.mscurrentaccountservice.dto.client.CustomerResponseDto;
import az.bank.mscurrentaccountservice.entity.CurrentAccountEntity;
import az.bank.mscurrentaccountservice.entity.CurrentAccountTransactionLog;
import az.bank.mscurrentaccountservice.enums.Currency;
import az.bank.mscurrentaccountservice.enums.CurrentAccountStatus;
import az.bank.mscurrentaccountservice.exceptions.NotFoundException;
import az.bank.mscurrentaccountservice.mapper.CurrentAccountMapper;
import az.bank.mscurrentaccountservice.repository.CurrentAccountRepository;
import az.bank.mscurrentaccountservice.repository.CurrentAccountTransactionLogRepository;
import az.bank.mscurrentaccountservice.util.IbanGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrentAccountService {

    private final IbanGenerator ibanGenerator;
    private final CurrentAccountRepository currentAccountRepository;
    private final CurrentAccountMapper currentAccountMapper;
    private final CurrentAccountTransactionLogRepository currentAccountTransactionLogRepository;


    public CurrentAccountEntity createCurrentAccount(CurrentAccountOrderRequestDto request, Long customerId,
                                                     CustomerResponseDto customer) {

        CurrentAccountEntity currentAccount = new CurrentAccountEntity();

        currentAccount.setAccountNumber(ibanGenerator.generate());
        currentAccount.setAccountType(request.getAccountType());
        currentAccount.setCurrency(request.getCurrency());
        currentAccount.setAccountHolderName(customer.getName() + " " + customer.getSurname());
        currentAccount.setBalance(BigDecimal.ZERO);
        currentAccount.setExpiryDate(ibanGenerator.generateExpiryDate(request.getAccountType()));
        currentAccount.setActivatedDate(LocalDateTime.now());
        currentAccount.setStatus(CurrentAccountStatus.ACTIVE);
        currentAccount.setCustomerId(customerId);
        currentAccount.setCreatedAt(LocalDateTime.now());
        currentAccount.setUpdatedAt(LocalDateTime.now());

        currentAccountRepository.save(currentAccount);
        return currentAccount;


    }

    public void updateCurrentAccountStatus(String accountNumber, Long customerId, CurrentAccountStatus status) {
        CurrentAccountEntity account = currentAccountRepository.
                findByCustomerIdAndAccountNumber(customerId, accountNumber);

        if (account == null) {
            throw new NotFoundException("Card not found");
        }

        account.setStatus(status);
        currentAccountRepository.save(account);

    }

    public List<CurrentAccountResponseDto> getAccountsByCustomerId(Long customerId) {

        if (customerId == null) {
            throw new NotFoundException("Customer not found");
        }
        List<CurrentAccountEntity> accounts = currentAccountRepository.findByCustomerId(customerId);


        if (accounts.isEmpty()) {
            throw new NotFoundException("No current accounts found for this customer");
        }


        return accounts.stream()
                .map(currentAccountMapper::mapToCurrentAccountResponseDto)
                .toList();
    }

    @Transactional
    public void increaseBalance(String accountNumber, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        CurrentAccountEntity account = currentAccountRepository.findByAccountNumberForUpdate(accountNumber)
                .orElseThrow(() -> new RuntimeException("Current Account not found"));

        BigDecimal oldBalance = account.getBalance() != null ? account.getBalance() : BigDecimal.ZERO;
        BigDecimal newBalance = oldBalance.add(amount);

        account.setBalance(newBalance);
        currentAccountRepository.save(account);


        CurrentAccountTransactionLog log = new CurrentAccountTransactionLog();
        log.setAccountNumber(accountNumber);
        log.setAmount(amount);
        log.setOldBalance(oldBalance);
        log.setNewBalance(newBalance);
        log.setTransactionDate(LocalDate.now());
        log.setCustomerId(account.getCustomerId());
        currentAccountTransactionLogRepository.save(log);


    }


    private String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 4) return "****";
        return "****" + accountNumber.substring(accountNumber.length() - 4);
    }

    public void deleteCurrentAccountById(Long id) {
        Optional<CurrentAccountEntity> customerEntity = currentAccountRepository.findById(id);

        if (customerEntity.isEmpty()) {
            throw new NotFoundException("Customer not found");
        }
        if (CurrentAccountStatus.DELETED.equals(customerEntity.get().getStatus())) {
            throw new IllegalStateException("Customer already deleted");
        }
        System.out.println(customerEntity.get().getCreatedAt());
        customerEntity.get().setStatus(CurrentAccountStatus.DELETED);
        customerEntity.get().setUpdatedAt(LocalDateTime.now());
        currentAccountRepository.save(customerEntity.get());

    }
    public void debit(AccountDebitRequest request) {

        CurrentAccountEntity acc = currentAccountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Account not found"));

        if (!"ACTIVE".equals(acc.getStatus().name())) {
            throw new RuntimeException("Account not active");
        }

        if (acc.getExpiryDate().isBefore(LocalDate.now())) {
            acc.setStatus(CurrentAccountStatus.EXPIRED);
            currentAccountRepository.save(acc);
            throw new RuntimeException("Account expired");
        }

        if (acc.getBalance().compareTo(BigDecimal.valueOf(5)) < 0) {
            throw new RuntimeException("Minimum balance 5 USD");
        }


        if (acc.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }


        acc.setBalance(
                acc.getBalance().subtract(request.getAmount())
        );

        currentAccountRepository.save(acc);
    }


    public void credit(AccountCreditRequest request) {

        CurrentAccountEntity acc = currentAccountRepository.findByAccountNumber(request.getAccountNumber())
                .orElseThrow(() -> new RuntimeException("Account not found"));


        if (!"ACTIVE".equals(acc.getStatus().name())) {
            throw new RuntimeException("Account not active");
        }


        if (acc.getExpiryDate().isBefore(LocalDate.now())) {
            acc.setStatus(CurrentAccountStatus.EXPIRED);
            currentAccountRepository.save(acc);
            throw new RuntimeException("Account expired");
        }


        acc.setBalance(
                acc.getBalance().add(request.getAmount())
        );

        currentAccountRepository.save(acc);
    }
    public String getCurrency(String accountNumber) {

        CurrentAccountEntity acc = currentAccountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new NotFoundException("Account not found"));

        return acc.getCurrency().name();
    }
    public void accountExpire() {
        List<CurrentAccountEntity> expireAccounts =currentAccountRepository.
                findAllByExpiryDateLessThanEqualAndStatusNot(LocalDate.now(), CurrentAccountStatus.EXPIRED);
        expireAccounts.forEach(account-> {
            account.setStatus(CurrentAccountStatus.EXPIRED);
            account.setUpdatedAt(LocalDateTime.now());

        });
        currentAccountRepository.saveAll(expireAccounts);
    }



}



