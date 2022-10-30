package sg.kata.bank.service;

import org.junit.Before;
import org.junit.Test;
import sg.kata.bank.exception.InvalidOperationException;
import sg.kata.bank.model.*;

import java.util.ArrayList;
import java.util.List;

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
                .operations(new ArrayList<>())
                .balance(SOLDE_ACCOUNT)
                .build();
        accountService = new AccountServiceImpl();
    }

    @Test
    public void saveMoney() {
        Operation operation = accountService.saveMoney(account, client, 100);
        int expectedSolde = 100;

        assertEquals(Deposit.class, operation.getClass());
        assertEquals(expectedSolde, operation.getAmount());
        assertEquals(account, operation.getAccount());
        assertEquals(client, operation.getClient());
    }

    @Test
    public void saveMoney_throwException_whenAmountNegative() {
        InvalidOperationException exception = assertThrows(InvalidOperationException.class,
                () -> accountService.saveMoney(account, client, -100));

        String expectedMessage = "Incorrect amount for a deposit transaction.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void saveMoney_throwException_whenWrongAccount() {
        InvalidOperationException exception = assertThrows(InvalidOperationException.class,
                () -> accountService.saveMoney(account, wrongClient, 100));

        String expectedMessage = "Wrong account.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void retrieveMoney() {
        Operation operation = accountService.retrieveMoney(account, client, 100);

        int expectedSolde = 100;
        assertEquals(Withdrawal.class, operation.getClass());
        assertEquals(expectedSolde, operation.getAmount());
        assertEquals(account, operation.getAccount());
        assertEquals(client, operation.getClient());
    }

    @Test
    public void retrieveMoney_throwException_whenAmountNegative() {
        InvalidOperationException exception = assertThrows(InvalidOperationException.class,
                () -> accountService.retrieveMoney(account, client, -100));

        String expectedMessage = "Incorrect amount for a withdrawal transaction.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void retrieveMoney_throwException_whenAmountSuperiorToSolde() {
        InvalidOperationException exception = assertThrows(InvalidOperationException.class,
                () -> accountService.retrieveMoney(account, client, 300));

        String expectedMessage = "Incorrect amount for a withdrawal transaction Insufficient balance.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void retrieveMoney_throwException_whenWrongAccount() {
        InvalidOperationException exception = assertThrows(InvalidOperationException.class,
                () -> accountService.retrieveMoney(account, wrongClient, 100));

        String expectedMessage = "Wrong account.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void retrieveAllMoney() {
        Operation operation = accountService.retrieveAllMoney(account, client);

        assertEquals(Withdrawal.class, operation.getClass());
        assertEquals(SOLDE_ACCOUNT, operation.getAmount());
        assertEquals(account, operation.getAccount());
        assertEquals(client, operation.getClient());
    }

    @Test
    public void retrieveAllMoney_throwException_whenWrongAccount() {
        InvalidOperationException exception = assertThrows(InvalidOperationException.class,
                () -> accountService.retrieveAllMoney(account, wrongClient));

        String expectedMessage = "Wrong account.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }

    @Test
    public void checkMyOperations() {
        accountService.saveMoney(account, client, 100);
        accountService.saveMoney(account, client, 500);
        accountService.retrieveMoney(account, client, 100);
        accountService.retrieveAllMoney(account, client);

        List<Operation> operationList = accountService.checkMyOperations(account, client);
        operationList.forEach(transaction -> System.out.println(transaction.toString()));

        int totalOperations = 4;
        assertEquals(totalOperations, operationList.size());
    }


    @Test
    public void checkMyOperations_throwException_whenWrongAccount() {
        InvalidOperationException exception = assertThrows(InvalidOperationException.class,
                () -> accountService.checkMyOperations(account, wrongClient));

        String expectedMessage = "Wrong account.";
        String actualMessage = exception.getMessage();

        assertEquals(expectedMessage, actualMessage);
    }
}