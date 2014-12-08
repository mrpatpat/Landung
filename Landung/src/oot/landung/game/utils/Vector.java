package oot.landung.game.utils;

/**
 * Vektorhilfsklasse.
 * 
 * @author Landung
 *
 * @param <T>
 *            Datentyp der Vektorkomponenten
 */
public class Vector<T> {

	private T x;
	private T y;

	public Vector(T x, T y) {
		this.setX(x);
		this.setY(y);
	}

	public T getX() {
		return x;
	}

	public void setX(T x) {
		this.x = x;
	}

	public T getY() {
		return y;
	}

	public void setY(T y) {
		this.y = y;
	}

	public String toString() {
		return "(" + convert(x) + " " + y + ")";
	}

	private String convert(T toConvert) {
		String converted = toConvert.toString();
		switch (converted) {
		case "0":
			converted = "A";
			break;
		case "1":
			converted = "B";
			break;
		case "2":
			converted = "C";
			break;
		case "3":
			converted = "D";
			break;
		case "4":
			converted = "E";
			break;
		default:
			return null;
		}
		return converted;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((x == null) ? 0 : x.hashCode());
		result = prime * result + ((y == null) ? 0 : y.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vector other = (Vector) obj;
		if (x == null) {
			if (other.x != null)
				return false;
		} else if (!x.equals(other.x))
			return false;
		if (y == null) {
			if (other.y != null)
				return false;
		} else if (!y.equals(other.y))
			return false;
		return true;
	}
}
