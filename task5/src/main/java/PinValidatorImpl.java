import exceptions.AccountInLockedException;
import exceptions.IncorrectPinException;
import interfaces.PinValidator;

import java.util.Date;

public class PinValidatorImpl implements PinValidator {
    public static final int MAX_ATTEMPT_COUNT = 3;
    public static final int ACCOUNT_LOCK_TIME = 10000;
    private final int PIN = 1234;

    private int attemptCount;
    private Date lockTime;

    @Override
    public void validate(int pin) throws IncorrectPinException, AccountInLockedException {
        if (attemptCount >= MAX_ATTEMPT_COUNT) {
            long locked = new Date().getTime() - lockTime.getTime();
            if (locked < ACCOUNT_LOCK_TIME) {
                throw new AccountInLockedException(ACCOUNT_LOCK_TIME - locked);
            }
        }

        if (PIN != pin) {
            if (++attemptCount >= MAX_ATTEMPT_COUNT) {
                lockTime = new Date();
            }
            throw new IncorrectPinException();
        }

        attemptCount = 0;
    }
}
