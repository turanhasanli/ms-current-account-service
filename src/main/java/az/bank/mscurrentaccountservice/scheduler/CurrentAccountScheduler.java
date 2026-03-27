package az.bank.mscurrentaccountservice.scheduler;

import az.bank.mscurrentaccountservice.service.CurrentAccountService;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentAccountScheduler {

    private final CurrentAccountService currentAccountService;

    @Scheduled(cron = "${scheduler.currentAccount.cron}")
    @SchedulerLock(name = "updateExpiredCurrentAccounts", lockAtLeastFor = "PT20S", lockAtMostFor = "PT5M")
    public void updateExpiredCards() {
        currentAccountService.accountExpire();

    }

}