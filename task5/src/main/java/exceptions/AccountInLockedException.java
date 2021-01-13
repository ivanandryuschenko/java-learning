package exceptions;

public class AccountInLockedException extends TerminalException {
    private long lockedTime;

    public AccountInLockedException(long lockedTime) {
        this.lockedTime = lockedTime;
    }

    public long getLockedTime() {
        return lockedTime;
    }
}
