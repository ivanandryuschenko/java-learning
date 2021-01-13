package interfaces;

import exceptions.AccountInLockedException;
import exceptions.IncorrectPinException;

public interface PinValidator {
    void validate(int pin) throws IncorrectPinException, AccountInLockedException;
}
