package refactor;

public enum Orientation {
	NORTH {
		public Orientation turn() { return EAST; }
		public Position move(Position p) { return p.sum(new Position(0, 1)); }
	}, EAST {
		public Orientation turn() { return SOUTH; }
		public Position move(Position p) { return p.sum(new Position(1, 0)); }
	}, SOUTH {
		public Orientation turn() { return WEST; }
		public Position move(Position p) { return p.sum(new Position(0, -1)); }
	}, WEST {
		public Orientation turn() { return NORTH; }
		public Position move(Position p) { return p.sum(new Position(-1, 0)); }
	};

	Orientation turn() {
		return NORTH;
	}

	Position move(Position p) {
		return p.sum(new Position(0, 1));
	}
}
