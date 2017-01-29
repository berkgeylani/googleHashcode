package org.kekosystem.pizza;

import java.util.ArrayList;
import java.util.List;

public class TableNode {
	private String lineId;
	private List<List<CellPOJO>> pizzaSlice= new ArrayList<List<CellPOJO>>();// yourList will be 2D arraylist.
	private static int R, C;

	public String getLineId() {
		return lineId;
	}
	public void setLineId(String lineId) {
		this.lineId = lineId;
	}
	public List<List<CellPOJO>> getPizzaSlice() {
		return pizzaSlice;
	}
	public void setPizzaSlice(List<List<CellPOJO>> pizzaSlice) {
		this.pizzaSlice = pizzaSlice;
	}
	
	public void printSliceDividerLines(){
		for (int i = 0; i < R-1; i++) {
			System.out.println("x bölücüsü :\t"+ ((i)*2+1));
		}
		for (int i = 0; i < C-1; i++) {
			System.out.println("y bölücüsü :\t "+ ((i)*2+1));
		}
	}
	
	public TableNode(String lineId, List<List<CellPOJO>> pizzaSlice) {
		super();
		this.lineId = lineId;
		this.pizzaSlice = pizzaSlice;
	}
	private CellPOJO getTopRightCornerCell() {
		return getPizzaSlice().get(0).get(0);
	}
	public void printTable() {
		for (List<CellPOJO> row : getPizzaSlice()) {
			for (CellPOJO cell : row) {
				System.out.print(cell.toString()+"\t");
			}
			System.out.println();
		}
	}
}
