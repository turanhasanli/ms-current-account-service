package az.bank.mscurrentaccountservice.entity;
import az.bank.mscurrentaccountservice.enums.AccountType;
import az.bank.mscurrentaccountservice.enums.Currency;
import az.bank.mscurrentaccountservice.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "current_account_order")
public class CurrentAccountOrderEntity extends BaseEntity{


    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private Long customerId;
    private String rejectReason;
    private String description;




    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "current_account_id")
    private CurrentAccountEntity currentAccount;





}
