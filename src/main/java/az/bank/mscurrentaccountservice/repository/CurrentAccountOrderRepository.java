package az.bank.mscurrentaccountservice.repository;

import az.bank.mscurrentaccountservice.entity.CurrentAccountOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrentAccountOrderRepository extends JpaRepository<CurrentAccountOrderEntity, Long> {

}
