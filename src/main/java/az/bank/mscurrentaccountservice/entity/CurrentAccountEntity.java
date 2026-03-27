package az.bank.mscurrentaccountservice.entity;
import az.bank.mscurrentaccountservice.enums.AccountType;
import az.bank.mscurrentaccountservice.enums.Currency;
import az.bank.mscurrentaccountservice.enums.CurrentAccountStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "current_account")
public class CurrentAccountEntity extends BaseEntity {


    @Column(unique = true, nullable = false, length = 34)
    private String accountNumber;

    private String accountHolderName;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private CurrentAccountStatus status;

    private LocalDate expiryDate;

    @ColumnDefault("")
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private Long customerId;


    private LocalDateTime activatedDate;
    private String rejectReason;




}
