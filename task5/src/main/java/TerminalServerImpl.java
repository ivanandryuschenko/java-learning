import exceptions.IncorrectAmountException;
import exceptions.InsufficientFundsException;
import interfaces.TerminalServer;

public class TerminalServerImpl implements TerminalServer {
    private double balance = 1000.0;

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void getMoney(double value) throws IncorrectAmountException, InsufficientFundsException {
        if (value <= 0) {
            throw new IncorrectAmountException();
        }
        if (value > balance) {
            throw new InsufficientFundsException();
        }
        balance -= value;
    }

    @Override
    public void putMoney(double value) throws IncorrectAmountException {
        if (value <= 0) {
            throw new IncorrectAmountException();
        }
        balance += value;
    }
}
