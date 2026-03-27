package az.bank.mscurrentaccountservice.exceptions;

public class CustomerBlockedException extends RuntimeException {
    public CustomerBlockedException(String message) {
        super(message);
    }
}
