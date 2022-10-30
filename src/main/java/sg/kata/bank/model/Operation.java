package sg.kata.bank.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@SuperBuilder
public abstract class Operation {
    private Account account;
    private Client client;
    private int amount;
    private LocalDateTime date;
    private int balance;

    @Override
    public String toString() {
        return "{" +
                "operation = " + this.getClass().getSimpleName() +
                ", date = " + date.format(DateTimeFormatter.ofPattern("d/MM/uuuu HH:mm:ss")) +
                ", amount = " + amount +
                ", balance = " + balance +
                '}';
    }

}
