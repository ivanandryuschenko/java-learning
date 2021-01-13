import exceptions.*;
import interfaces.TerminalView;

import java.util.Scanner;

public class ConsoleTerminalViewImpl implements TerminalView {
    @Override
    public int inputPin() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите ПИН-код (4 цифры)");
            System.out.print("> ");
            String pinStr = scanner.nextLine();
            if (pinStr.length() == 4) {
                try {
                    int pin = Integer.parseInt(pinStr);
                    return pin;
                } catch (NumberFormatException e) {
                    System.out.println("Введен недопустимый символ");
                }
            } else {
                System.out.println("Неверная длина ПИН-кода");
            }
        }
    }

    @Override
    public int selectOperation() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Выберите операцию (номер):");
            System.out.println("1) Проверить баланс");
            System.out.println("2) Снять наличные");
            System.out.println("3) Внести наличные");
            System.out.println("0) Выход");
            System.out.print("> ");
            try {
                int operation = Integer.parseInt(scanner.nextLine());
                if (operation >= 0 && operation <= 3) {
                    return operation;
                } else {
                    System.out.println("Неверная операция");
                }
            } catch (NumberFormatException e) {
                System.out.println("Введен недопустимый символ");
            }
        }
    }

    @Override
    public void showBalance(double balance) {
        System.out.println("Ваш баланс: " + String.format("%.2f Р", balance));
    }

    @Override
    public double inputCashForGet() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите сумму для выдачи: ");
            System.out.print("> ");
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введен недопустимый символ");
            }
        }
    }

    @Override
    public double inputCashForPut() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите сумму для внесения: ");
            System.out.print("> ");
            try {
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Введен недопустимый символ");
            }
        }
    }

    @Override
    public void showError(TerminalException e) {
        if (e instanceof IncorrectPinException) {
            System.out.println("Введен неправильный пин-код!");
        } else if (e instanceof AccountInLockedException) {
            System.out.println("Ваш аккаунт временно заблокирован! До конца блокировки осталось: "
                    + (((AccountInLockedException)e).getLockedTime() / 1000) + " c.");
        } else if (e instanceof InsufficientFundsException) {
            System.out.println("Недостаточно средств на счете!");
        } else if (e instanceof IncorrectAmountException) {
            System.out.println("Указана неверная сумма! " + e.getMessage());
        }
    }
}
