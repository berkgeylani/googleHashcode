package org.kekosystem.pizza;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class TableNode {
	private String lineId;
	private List<List<CellPOJO>> pizzaSlice = new ArrayList<List<CellPOJO>>();
	private int R = 0, C = 0;
	private TableNode parentNode;
	private Hashtable<String, TableNode[]> sliceTable = new Hashtable<>();
	private int depth;
	private static Set<String> milisList = new HashSet<>();
	
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

	public void printSliceDividerLines() {
		for (int i = 0; i < R - 1; i++) {
			System.out.println("x bölücüsü :\t" + ((i) * 2 + 1));
		}
		for (int i = 0; i < C - 1; i++) {
			System.out.println("y bölücüsü :\t " + ((i) * 2 + 1));
		}
	}

	public Hashtable<String, TableNode[]> getSubSliceTable() {
		return sliceTable;
	}

	public void setSliceTable(Hashtable<String, TableNode[]> sliceTable) {
		this.sliceTable = sliceTable;
	}

	public TableNode(String lineId, List<List<CellPOJO>> pizzaSlice, TableNode parentNode, boolean willTryToFindChild) {
		this.parentNode = parentNode;
		this.depth = (parentNode == null) ? 1 : parentNode.getDepth() + 1;
		this.lineId = lineId;
		this.pizzaSlice = pizzaSlice;
		this.R = pizzaSlice.size();
		if (!pizzaSlice.isEmpty()) {
			this.C = pizzaSlice.get(0).size();
		}
		// this.printTable();
		if (willTryToFindChild)
			findProbabilities();
		// findProbabilities will find possible partition of pizza.
	}

	public int getDepth() {
		return depth;
	}

	public TableNode getParentNode() {
		return parentNode;
	}

	public void setParentNode(TableNode parentNode) {
		this.parentNode = parentNode;
	}

	private CellPOJO getTopLeftCornerCell() {
		return getPizzaSlice().get(0).get(0);
	}

	/**
	 * T,M,(Lx,Ly),(Hx,Hy)
	 */
	public void printTable() {
//		System.out.println();
//		System.out.println(R+","+C);
		System.out.print(this.lineId + "-");
		System.out.print(this.getGredientStatistics().get('T'));
		System.out.print("," + this.getGredientStatistics().get('M') + ",");
		CellPOJO topLeftCornerCell = this.getTopLeftCornerCell();
		System.out.print((topLeftCornerCell.getX()/2 ) + "," + (topLeftCornerCell.getY()/2 ) + ",");
		System.out.print(((topLeftCornerCell.getX()/2) + (R-1) ) + "," + ((topLeftCornerCell.getY()/2 ) + (C-1)));
		System.out.println();
//		 for (List<CellPOJO> row : getPizzaSlice()) {
//		 //satir
//		 for (CellPOJO cell : row) {
//		 //sutun
//		 System.out.print(cell.toString() + "\t");
//		 }
//		 System.out.println();
//		 }
	}

	/**
	 * T,M,(Lx,Ly),(Hx,Hy)
	 */
	public void printTableOneTree() {
		System.out.println();
		System.out.print(this.lineId + ",");
		System.out.print(this.getGredientStatistics().get('T'));
		System.out.print("," + this.getGredientStatistics().get('M') + ",");
		CellPOJO topLeftCornerCell = this.getTopLeftCornerCell();
		System.out.print((topLeftCornerCell.getX() / 2) + "," + (topLeftCornerCell.getY() / 2) + ",");
		System.out.print(((topLeftCornerCell.getX() / 2) + C - 1) + "," + ((topLeftCornerCell.getY() / 2) + R - 1));
		System.out.println();
		// for (List<CellPOJO> row : getPizzaSlice()) {
		// //satir
		// for (CellPOJO cell : row) {
		// //sutun
		// System.out.print(cell.toString() + "\t");
		// }
		// System.out.println();
		// }
	}

	public Hashtable<String, TableNode[]> findProbabilities() {
		TableNode[] slices;
		for (int i = 0; i < R - 1; i++) {
			String inheritedMilis = System.currentTimeMillis() + "";
			if (!getLineId().equals("000")) {
				inheritedMilis = getLineId().split("-")[0];
			} else {
				milisList.add(inheritedMilis);
			}
			slices = new TableNode[2];
			int dividerLineAtX = (i) * 2 + 1 + getTopLeftCornerCell().getX();
			// System.out.println("x bölücüsü :\t" + dividerLineAtX);
			// burada bölünen çizgi var zaten arraylistin o indexten küçük
			// ve
			// büyük bölgelerini alsam yeter.
			// bunlarda 2 ye ayrılacak nodelar olacak master nodden ayrılan
			// algroritma ise her satır başındaki cell çekilip x değerine
			// bakılacak
			// eğer küçükse ilk slice a dahil
			// eğer büyükse ikinci slice a dahil olacak.
			// bunun yerine küççükmü büyükmü diye bakmak yerine elimizdeki i
			// nin
			// değeri mesela 0 ise sadece il satır dahil alıncak yani
			// x.substring(0,1)
			// 2 olsaydı 1 ve ikinci satırlar alınacak kalan satırlar 2.
			// slice a
			// dahil
			// yani genellerse i kadar satırı alıp birinci slice a alıcaz
			String inheritedLineId = "";
			if (!this.getLineId().equals("000")) {
				String[] lineId2 = this.getLineId().split("-");
				inheritedLineId = lineId2[2];
			}
			String lineIdForX = inheritedMilis + "-" + (getDepth() + 1) + "-" + inheritedLineId + "x" + dividerLineAtX;
			TableNode topSlice = new TableNode(lineIdForX+"*1", new ArrayList<>(getPizzaSlice().subList(0, i + 1)), this,
					false);
			TableNode bottomSlice = new TableNode(lineIdForX+"*2",
					new ArrayList<>(getPizzaSlice().subList(i + 1, getPizzaSlice().size())), this, false);
			Hashtable<Character, Integer> gredientStatistic = topSlice.getGredientStatistics();
			if (topSlice.isValidSlice() && bottomSlice.isValidSlice()) {
				topSlice = new TableNode(lineIdForX+"*1,", new ArrayList<>(getPizzaSlice().subList(0, i + 1)), this, true);
				bottomSlice = new TableNode(lineIdForX+"*2,",
						new ArrayList<>(getPizzaSlice().subList(i + 1, getPizzaSlice().size())), this, true);
				slices[0] = topSlice;
				slices[1] = bottomSlice;
//				System.out.println("***********************" + (this.depth + 1) + "**************************");
//				System.out.println("top");
				topSlice.printTable();
//				System.out.println("bottom");
				bottomSlice.printTable();
				sliceTable.put(lineIdForX, slices);
			}
		}
		for (int i = 0; i < C - 1; i++) {
			String inheritedMilis = System.currentTimeMillis() + "";
			if (!getLineId().equals("000")) {
				inheritedMilis = getLineId().split("-")[0];
			} else {
				milisList.add(inheritedMilis);
			}
			int dividerLineAtY = (i) * 2 + 1 + getTopLeftCornerCell().getY();
			slices = new TableNode[2];
			// System.out.println("y bölücüsü :\t " + ((i) * 2 + 1));
			List<List<CellPOJO>> leftSliceAsAL = new ArrayList<List<CellPOJO>>();
			List<List<CellPOJO>> rightSliceAsAL = new ArrayList<List<CellPOJO>>();
			for (int rowIndex = 0; rowIndex < R; rowIndex++) {
				leftSliceAsAL.add(new ArrayList<CellPOJO>(getPizzaSlice().get(rowIndex).subList(0, i + 1)));
				rightSliceAsAL.add(new ArrayList<CellPOJO>(
						getPizzaSlice().get(rowIndex).subList(i + 1, getPizzaSlice().get(rowIndex).size())));
			}
			String inheritedLineId = "";
			if (!this.getLineId().equals("000")) {
				String[] lineId2 = this.getLineId().split("-");
				inheritedLineId = lineId2[2];
			}
			String lineIdForY = inheritedMilis + "-" + (getDepth() + 1) + "-" + inheritedLineId + "y" + dividerLineAtY;
			TableNode leftSlice = new TableNode(lineIdForY, leftSliceAsAL, this, false);
			TableNode rightSlice = new TableNode(lineIdForY, rightSliceAsAL, this, false);
			Hashtable<Character, Integer> gredientStatistic = leftSlice.getGredientStatistics();
			if (leftSlice.isValidSlice() && rightSlice.isValidSlice()) {
				leftSlice = new TableNode(lineIdForY+"*1,", leftSliceAsAL, this, true);
				rightSlice = new TableNode(lineIdForY+"*2,", rightSliceAsAL, this, true);
//				System.out.println("***********************" + (this.depth + 1) + "**************************");
//				System.out.println("left");
				leftSlice.printTable();
//				System.out.println("right");
				rightSlice.printTable();
				slices[0] = leftSlice;
				slices[1] = rightSlice;
				sliceTable.put(lineIdForY, slices);
			}
		}
		return sliceTable;
	}

	public static Set<String> getMilisList() {
		return milisList;
	}

	public static void setMilisList(Set<String> milisList) {
		TableNode.milisList = milisList;
	}

	public Hashtable<Character, Integer> getGredientStatistics() {
		Hashtable<Character, Integer> gradientCount = new Hashtable<>();
		for (int i = 0; i < getPizzaSlice().size(); i++) {
			for (int j = 0; j < getPizzaSlice().get(i).size(); j++) {
				char gradient = getPizzaSlice().get(i).get(j).getGradient();
				if (gradient == 'T') {
					if (gradientCount.containsKey('T')) {
						gradientCount.put('T', gradientCount.get('T') + 1);
					} else {
						gradientCount.put('T', 1);
					}
				}
				if (gradient == 'M') {
					if (gradientCount.containsKey('M')) {
						gradientCount.put('M', gradientCount.get('M') + 1);
					} else {
						gradientCount.put('M', 1);
					}
				}
			}
		}
		return gradientCount;
	}

	private boolean isValidSlice() {
		Hashtable<Character, Integer> gredientStatistic = getGredientStatistics();
		return gredientStatistic.containsKey('T') && gredientStatistic.containsKey('M')
				&& gredientStatistic.get('M') >= ProbabiltyCreator.L
				&& gredientStatistic.get('T') >= ProbabiltyCreator.L;
	}
}
