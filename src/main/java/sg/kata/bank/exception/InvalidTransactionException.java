package sg.kata.bank.exception;

public class InvalidTransactionException extends RuntimeException {
    public InvalidTransactionException(String messageError) {
        super(messageError);
    }
}
