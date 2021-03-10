package refactor;

public interface Tractor {
    void move(String command);
    Position getPosition();
    Orientation getOrientation();
    Field getField();
}
