package refactor;

public class TractorImpl implements Tractor {

	private Position position = new Position(0, 0);
	private Orientation orientation = Orientation.NORTH;
	private Field field = new Field(5, 5);

	private void moveForwards() {
		position = orientation.move(position);
		if (field.contains(position) == false) {
			throw new TractorInDitchException();
		}
	}

	private void turnClockwise() {
		orientation = orientation.turn();
	}

	@Override
	public void move(String command) {
		if (command == "F") {
			moveForwards();
		} else if (command == "T") {
			turnClockwise();
		}
	}

	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public Orientation getOrientation() {
		return orientation;
	}

	@Override
	public Field getField() {
		return field;
	}
}
