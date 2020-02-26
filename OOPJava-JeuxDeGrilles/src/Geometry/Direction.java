package Geometry;

public enum Direction {
	EST(0, 1), NORDEST(-1, 1), NORD(-1, 0), NORDOUEST(-1, -1), OUEST(0, -1), SUDOUEST(1, -1), SUD(1, 0), SUDEST(1, 1);

	private final int dx, dy;

	Direction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public int getDx() {
		return dx;
	}

	public int getDy() {
		return dy;
	}

	/**
	 * Retourne une direction inverse à la direction courante.
	 * 
	 * @return la direction opposée
	 */
	public Direction inverser() {
		return Direction.values()[(this.ordinal() + 4) % 8];
	}
}
