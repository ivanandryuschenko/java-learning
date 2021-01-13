import exceptions.TerminalException;
import interfaces.Terminal;
import interfaces.TerminalView;

public class TerminalApp {
    private final Terminal terminal = new TerminalImpl();
    private final TerminalView view = new ConsoleTerminalViewImpl();;

    public void start() {
        while (true) {
            try {
                if (terminal.getLocked()) {
                    terminal.unlock(view.inputPin());
                } else {
                    switch (view.selectOperation()) {
                        case 0:
                            terminal.lock();
                            break;
                        case 1:
                            view.showBalance(terminal.getBalance());
                            break;
                        case 2:
                            terminal.getMoney(view.inputCashForGet());
                            break;
                        case 3:
                            terminal.putMoney(view.inputCashForPut());
                            break;
                    }
                }
            } catch (TerminalException e) {
                view.showError(e);
            }
        }
    }

    public static void main(String[] args) {
        TerminalApp app = new TerminalApp();
        app.start();
    }
}
