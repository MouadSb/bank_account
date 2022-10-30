package sg.kata.bank.service;

import sg.kata.bank.exception.InvalidTransactionException;
import sg.kata.bank.model.Account;
import sg.kata.bank.model.Client;
import sg.kata.bank.model.Deposit;
import sg.kata.bank.model.Transaction;

public class AccountServiceImpl implements AccountService {

    /**
     * A bank client makes a deposit in an account
     * @param account The account in which the customer deposits the amount
     * @param client Client how want to make a deposit
     * @param amount Amount to deposit
     * @return Transaction with all informations
     */
    public Transaction saveMoney(Account account, Client client, int amount) throws InvalidTransactionException{
        if (amount < 0)
            throw new InvalidTransactionException("Incorrect amount for a deposit transaction.");

        Deposit newTransaction = Deposit.builder()
                .client(client)
                .account(account)
                .amount(amount)
                .build();

        account.getTransactions().add(newTransaction);
        return newTransaction;
    }
}
