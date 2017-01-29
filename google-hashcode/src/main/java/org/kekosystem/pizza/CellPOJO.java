package org.kekosystem.pizza;

public class CellPOJO {
	private int x;
	private int y;
	private char gradient;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public char getGradient() {
		return gradient;
	}
	public void setGradient(char gradient) {
		this.gradient = gradient;
	}
	public CellPOJO(int x, int y, char gradient) {
		this.x = x;
		this.y = y;
		this.gradient = gradient;
	}
	@Override
	public String toString() {
		return "Cell("+getX()+","+getY()+")-("+getGradient()+")";
	}
}
