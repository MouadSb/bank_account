package sg.kata.bank.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Account {
    private Long id;
    private Client client;
    private int balance;
    private List<Operation> operations;
}
