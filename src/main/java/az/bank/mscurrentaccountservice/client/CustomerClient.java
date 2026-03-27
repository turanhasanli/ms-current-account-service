package az.bank.mscurrentaccountservice.client;
import az.bank.mscurrentaccountservice.dto.client.CustomerResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "customer-service",
        url = "http://localhost:8080/api/v1/customers")


public interface CustomerClient {

    @GetMapping("/id/{id}")
    CustomerResponseDto getCustomerById(@PathVariable Long id);



}
