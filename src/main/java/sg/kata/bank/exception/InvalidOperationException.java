package sg.kata.bank.exception;

public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException(String messageError) {
        super(messageError);
    }
}
