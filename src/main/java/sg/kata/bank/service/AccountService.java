package sg.kata.bank.service;

import sg.kata.bank.exception.InvalidOperationException;
import sg.kata.bank.model.Account;
import sg.kata.bank.model.Client;
import sg.kata.bank.model.Operation;

import java.util.List;

public interface AccountService {

    Operation saveMoney(Account account, Client client, int amount) throws InvalidOperationException;

    Operation retrieveMoney(Account account, Client client, int amount) throws InvalidOperationException;

    Operation retrieveAllMoney(Account account, Client client) throws InvalidOperationException;

    List<Operation> checkMyOperations(Account account, Client client) throws InvalidOperationException;
}
