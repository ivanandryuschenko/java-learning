package interfaces;

import exceptions.TerminalException;

public interface TerminalView {
    int inputPin();
    int selectOperation();
    void showBalance(double balance);
    double inputCashForGet();
    double inputCashForPut();
    void showError(TerminalException e);
}
