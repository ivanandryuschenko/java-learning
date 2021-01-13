package interfaces;

import exceptions.*;

public interface Terminal {
    void unlock(int pin) throws IncorrectPinException, AccountInLockedException;
    void lock();
    boolean getLocked();
    double getBalance() throws TerminalLockedException;
    void getMoney(double value) throws IncorrectAmountException, InsufficientFundsException, TerminalLockedException;
    void putMoney(double value) throws IncorrectAmountException, TerminalLockedException;
}
