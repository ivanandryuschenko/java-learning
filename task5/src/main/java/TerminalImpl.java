import exceptions.*;
import interfaces.Terminal;

public class TerminalImpl implements Terminal {
    private final TerminalServerImpl server = new TerminalServerImpl();
    private final PinValidatorImpl pinValidator = new PinValidatorImpl();
    private final double MONEY_OPERATION_MULTIPLICITY = 100.0;
    private boolean locked = true;

    @Override
    public void unlock(int pin) throws IncorrectPinException, AccountInLockedException {
        pinValidator.validate(pin);
        locked = false;
    }

    @Override
    public void lock() {
        locked = true;
    }

    @Override
    public boolean getLocked() {
        return locked;
    }

    @Override
    public double getBalance() throws TerminalLockedException {
        if (locked) {
            throw new TerminalLockedException();
        }
        return server.getBalance();
    }

    @Override
    public void getMoney(double value) throws IncorrectAmountException, InsufficientFundsException, TerminalLockedException {
        if (locked) {
            throw new TerminalLockedException();
        }
        if (value % MONEY_OPERATION_MULTIPLICITY != 0) {
            throw new IncorrectAmountException("Сумма должна быть кратна " + String.format("%.1f", MONEY_OPERATION_MULTIPLICITY));
        }
        server.getMoney(value);
    }

    @Override
    public void putMoney(double value) throws IncorrectAmountException, TerminalLockedException {
        if (locked) {
            throw new TerminalLockedException();
        }
        if (value % MONEY_OPERATION_MULTIPLICITY != 0) {
            throw new IncorrectAmountException("Сумма должна быть кратна " + String.format("%.1f", MONEY_OPERATION_MULTIPLICITY));
        }
        server.putMoney(value);
    }
}
