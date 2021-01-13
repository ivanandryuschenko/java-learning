package interfaces;

import exceptions.IncorrectAmountException;
import exceptions.InsufficientFundsException;

public interface TerminalServer {
    double getBalance();
    void getMoney(double value) throws IncorrectAmountException, InsufficientFundsException;
    void putMoney(double value) throws IncorrectAmountException;
}
