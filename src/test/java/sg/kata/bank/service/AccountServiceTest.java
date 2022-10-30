package sg.kata.bank.service;

import org.junit.Before;
import org.junit.Test;
import sg.kata.bank.exception.InvalidTransactionException;
import sg.kata.bank.model.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class AccountServiceTest {

    private Client client;
    private Client wrongClient;
    private Account account;
    private AccountService accountService;
    private final static int SOLDE_ACCOUNT = 200;

    @Before
    public void setUp() {
        client = new Client(1L, "Mouad", "SBAII");
        wrongClient = new Client(2L, "Dauom", "IIABS");

        account = Account.builder()
                .id(1L)
                .client(client)
                .transactions(new ArrayList<>())
                .balance(SOLDE_ACCOUNT)
                .build();
        accountService = new AccountServiceImpl();
    }

    @Test
    public void saveMoney() {
        Transaction transaction = accountService.saveMoney(account, client, 100);
        int expectedSolde = 100;

        assertEquals(Deposit.class, transaction.getClass());
        assertEquals(expectedSolde, transaction.getAmount());
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

    @Test
    public void saveMoney_throwException_whenWrongAccount() {
        InvalidTransactionException exception = assertThrows(InvalidTransactionException.class,
                () -> accountService.saveMoney(account, wrongClient, 100));

        String expectedMessage = "Wrong account.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void retrieveMoney() {
        Transaction transaction = accountService.retrieveMoney(account, client, 100);

        int expectedSolde = 100;
        assertEquals(Withdrawal.class, transaction.getClass());
        assertEquals(expectedSolde, transaction.getAmount());
        assertEquals(account, transaction.getAccount());
        assertEquals(client, transaction.getClient());
    }

    @Test
    public void retrieveMoney_throwException_whenAmountNegative() {
        InvalidTransactionException exception = assertThrows(InvalidTransactionException.class,
                () -> accountService.retrieveMoney(account, client, -100));

        String expectedMessage = "Incorrect amount for a withdrawal transaction.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void retrieveMoney_throwException_whenAmountSuperiorToSolde() {
        InvalidTransactionException exception = assertThrows(InvalidTransactionException.class,
                () -> accountService.retrieveMoney(account, client, 300));

        String expectedMessage = "Incorrect amount for a withdrawal transaction Insufficient balance.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void retrieveMoney_throwException_whenWrongAccount() {
        InvalidTransactionException exception = assertThrows(InvalidTransactionException.class,
                () -> accountService.retrieveMoney(account, wrongClient, 100));

        String expectedMessage = "Wrong account.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void retrieveAllMoney() {
        Transaction transaction = accountService.retrieveAllMoney(account, client);

        assertEquals(Withdrawal.class, transaction.getClass());
        assertEquals(SOLDE_ACCOUNT, transaction.getAmount());
        assertEquals(account, transaction.getAccount());
        assertEquals(client, transaction.getClient());
    }

    @Test
    public void retrieveAllMoney_throwException_whenWrongAccount() {
        InvalidTransactionException exception = assertThrows(InvalidTransactionException.class,
                () -> accountService.retrieveAllMoney(account, wrongClient));

        String expectedMessage = "Wrong account.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}