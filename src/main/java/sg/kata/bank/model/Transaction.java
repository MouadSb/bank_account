package sg.kata.bank.model;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class Transaction {
    private Account account;
    private Client client;
    private int amount;
}
