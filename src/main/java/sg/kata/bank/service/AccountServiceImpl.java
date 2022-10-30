package sg.kata.bank.service;

import sg.kata.bank.exception.InvalidOperationException;
import sg.kata.bank.model.*;

import java.time.LocalDateTime;
import java.util.List;

public class AccountServiceImpl implements AccountService {

    /**
     * A bank client makes a deposit in an account
     *
     * @param account The account in which the customer deposits the amount
     * @param client  Client who want to make a deposit
     * @param amount  Amount to deposit
     * @return Operation Deposit with all informations
     * @throws InvalidOperationException Throw an exception if the amount is negative
     *                                   or the account doesn't belongs to client
     */
    @Override
    public Operation saveMoney(Account account, Client client, int amount) throws InvalidOperationException {
        this.verifyAccountBelongsToClient(account, client);

        if (amount < 0) {
            throw new InvalidOperationException("Incorrect amount for a deposit transaction.");
        }

        account.setBalance(account.getBalance() + amount);

        Deposit newTransaction = Deposit.builder()
                .client(client)
                .account(account)
                .amount(amount)
                .date(LocalDateTime.now())
                .balance(account.getBalance())
                .build();

        account.getOperations().add(newTransaction);
        return newTransaction;
    }

    /**
     * A bank client retrieve some of his savings
     *
     * @param account The account in which the customer retreive some savings
     * @param client  Client who want to make a withdrawal
     * @param amount  Amount to withdrawal
     * @return Operation Withdrawal with all informations
     * @throws InvalidOperationException Throw an exception if the amount is negative
     *                                   or if the amount is heigher then the account solde
     *                                   or the account doesn't belongs to client
     */
    @Override
    public Operation retrieveMoney(Account account, Client client, int amount) throws InvalidOperationException {
        this.verifyAccountBelongsToClient(account, client);

        if (amount < 0) {
            throw new InvalidOperationException("Incorrect amount for a withdrawal transaction.");
        }
        if (amount > account.getBalance()) {
            throw new InvalidOperationException("Incorrect amount for a withdrawal transaction Insufficient balance.");
        }

        account.setBalance(account.getBalance() - amount);

        Withdrawal newTransaction = Withdrawal.builder()
                .client(client)
                .account(account)
                .amount(amount)
                .date(LocalDateTime.now())
                .balance(account.getBalance())
                .build();

        account.getOperations().add(newTransaction);
        return newTransaction;
    }

    /**
     * A bank client retrieve all of his savings
     *
     * @param account The account in which the client retreive all savings
     * @param client  Client who want to make a withdrawal
     * @return Operation Withdrawal with all informations
     * @throws InvalidOperationException Throw an exception if the account doesn't belongs to client
     */
    @Override
    public Operation retrieveAllMoney(Account account, Client client) throws InvalidOperationException {
        this.verifyAccountBelongsToClient(account, client);

        Withdrawal newTransaction = Withdrawal.builder()
                .client(client)
                .account(account)
                .amount(account.getBalance())
                .date(LocalDateTime.now())
                .balance(0)
                .build();
        account.setBalance(0);

        account.getOperations().add(newTransaction);
        return newTransaction;
    }

    /**
     * A bank client check his operations
     *
     * @param account The account in which the client retreive all savings
     * @param client  Client who want to make a withdrawal
     * @return list of operations
     * @throws InvalidOperationException Throw an exception if the account doesn't belongs to client
     */
    @Override
    public List<Operation> checkMyOperations(Account account, Client client) throws InvalidOperationException {
        this.verifyAccountBelongsToClient(account, client);
        return account.getOperations();
    }

    private void verifyAccountBelongsToClient(Account account, Client client) {
        if (!account.getClient().getId().equals(client.getId())) {
            throw new InvalidOperationException("Wrong account.");
        }
    }
}
