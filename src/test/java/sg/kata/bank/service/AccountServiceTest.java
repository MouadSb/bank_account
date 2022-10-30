package sg.kata.bank.service;

import org.junit.Before;
import org.junit.Test;
import sg.kata.bank.exception.InvalidTransactionException;
import sg.kata.bank.model.Account;
import sg.kata.bank.model.Client;
import sg.kata.bank.model.Deposit;
import sg.kata.bank.model.Transaction;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AccountServiceTest {

    private Client client;
    private Account account;
    private AccountService accountService;

    @Before
    public void setUp() {
        client = new Client(1L, "Mouad", "SBAII");

        account = Account.builder()
                .id(1L)
                .client(client)
                .transactions(new ArrayList<>())
                .build();
        accountService = new AccountServiceImpl();
    }

    @Test
    public void saveMoney() {
        Transaction transaction = accountService.saveMoney(account, client, 100);

        assertEquals(Deposit.class, transaction.getClass());
        assertEquals(100, transaction.getAmount());
        assertEquals(account, transaction.getAccount());
        assertEquals(client, transaction.getClient());
    }

    @Test
    public void saveMoney_throwException_whenAmountNegative() {
        InvalidTransactionException exception = assertThrows(InvalidTransactionException.class,
                () -> accountService.saveMoney(account, client, -100));

        String expectedMessage = "Incorrect amount for a deposit transaction.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}