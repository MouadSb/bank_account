package sg.kata.bank.service;

import sg.kata.bank.exception.InvalidTransactionException;
import sg.kata.bank.model.*;

public class AccountServiceImpl implements AccountService {

    /**
     * A bank client makes a deposit in an account
     *
     * @param account The account in which the customer deposits the amount
     * @param client  Client who want to make a deposit
     * @param amount  Amount to deposit
     * @return Transaction Deposit with all informations
     * @throws InvalidTransactionException Throw an exception if the amount is negative
     */
    @Override
    public Transaction saveMoney(Account account, Client client, int amount) throws InvalidTransactionException {
        this.verifyAccountBelongsToClient(account, client);

        if (amount < 0) {
            throw new InvalidTransactionException("Incorrect amount for a deposit transaction.");
        }

        Deposit newTransaction = Deposit.builder()
                .client(client)
                .account(account)
                .amount(amount)
                .build();

        account.getTransactions().add(newTransaction);
        account.setBalance(account.getBalance() + amount);
        return newTransaction;
    }

    /**
     * A bank client retrieve some of his savings
     *
     * @param account The account in which the customer retreive some savings
     * @param client  Client who want to make a withdrawal
     * @param amount  Amount to withdrawal
     * @return Transaction Withdrawal with all informations
     * @throws InvalidTransactionException Throw an exception if the amount is negative
     *                                     or if the amount is heigher then the account solde
     */
    @Override
    public Transaction retrieveMoney(Account account, Client client, int amount) throws InvalidTransactionException {
        this.verifyAccountBelongsToClient(account, client);

        if (amount < 0) {
            throw new InvalidTransactionException("Incorrect amount for a withdrawal transaction.");
        }
        if (amount > account.getBalance()) {
            throw new InvalidTransactionException("Incorrect amount for a withdrawal transaction Insufficient balance.");
        }

        Withdrawal newTransaction = Withdrawal.builder()
                .client(client)
                .account(account)
                .amount(amount)
                .build();

        account.getTransactions().add(newTransaction);
        account.setBalance(account.getBalance() - amount);
        return newTransaction;
    }

    /**
     * A bank client retrieve all of his savings
     *
     * @param account The account in which the customer retreive all savings
     * @param client  Client who want to make a withdrawal
     * @return Transaction Withdrawal with all informations
     */
    @Override
    public Transaction retrieveAllMoney(Account account, Client client) {
        this.verifyAccountBelongsToClient(account, client);
        Withdrawal newTransaction = Withdrawal.builder()
                .client(client)
                .account(account)
                .amount(account.getBalance())
                .build();

        account.getTransactions().add(newTransaction);
        account.setBalance(0);
        return newTransaction;
    }

    private void verifyAccountBelongsToClient(Account account, Client client) {
        if (!account.getClient().getId().equals(client.getId())) {
            throw new InvalidTransactionException("Wrong account.");
        }
    }
}
