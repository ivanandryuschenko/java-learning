package exceptions;

public class IncorrectAmountException extends TerminalException {
    public IncorrectAmountException() {
    }

    public IncorrectAmountException(String s) {
        super(s);
    }
}
