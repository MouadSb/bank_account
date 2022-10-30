package sg.kata.bank.service;

import sg.kata.bank.exception.InvalidTransactionException;
import sg.kata.bank.model.Account;
import sg.kata.bank.model.Client;
import sg.kata.bank.model.Transaction;

public interface AccountService {

    Transaction saveMoney(Account account, Client client, int amount) throws InvalidTransactionException;

    Transaction retrieveMoney(Account account, Client client, int amount) throws InvalidTransactionException;

    Transaction retrieveAllMoney(Account account, Client client);
}
