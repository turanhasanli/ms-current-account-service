package az.bank.mscurrentaccountservice.repository;

import az.bank.mscurrentaccountservice.entity.CurrentAccountTransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentAccountTransactionLogRepository extends JpaRepository<CurrentAccountTransactionLog, Long> {

}
