package az.bank.mscurrentaccountservice.repository;

import az.bank.mscurrentaccountservice.entity.CurrentAccountEntity;
import az.bank.mscurrentaccountservice.enums.CurrentAccountStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CurrentAccountRepository extends JpaRepository<CurrentAccountEntity, Long> {


    long countByCustomerIdAndStatus(Long customerId, CurrentAccountStatus status);

    CurrentAccountEntity findByCustomerIdAndAccountNumber(Long customerId, String accountNumber);

    List<CurrentAccountEntity> findByCustomerId(Long customerId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM CurrentAccountEntity c WHERE c.accountNumber = :accountNumber")
    Optional<CurrentAccountEntity> findByAccountNumberForUpdate(String accountNumber);

    Optional<CurrentAccountEntity> findByAccountNumber(String accountNumber);

    @EntityGraph(attributePaths = {"customer"})
    List<CurrentAccountEntity>
    findAllByExpiryDateLessThanEqualAndStatusNot(LocalDate date, CurrentAccountStatus status);






}

