package org.kekosystem.pizza;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class TableNode {
	private String lineId;
	private List<List<CellPOJO>> pizzaSlice = new ArrayList<List<CellPOJO>>();// yourList
																				// will
																				// be
																				// 2D
																				// arraylist.
	private int R = 0, C = 0;
	private Point bound_low_,bound_high_;
	
	private Hashtable<String, TableNode[]> sliceTable = new Hashtable<>();

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

	public TableNode(String lineId, List<List<CellPOJO>> pizzaSlice) {
		this.lineId = lineId;
		this.pizzaSlice = pizzaSlice;
		this.R = pizzaSlice.size();
		if (!pizzaSlice.isEmpty()) {
			C = pizzaSlice.get(0).size();
		}
		findProbabilities();
		// findProbabilities will find possible partition of pizza.
	}

	private CellPOJO getTopRightCornerCell() {
		return getPizzaSlice().get(0).get(0);
	}

	public void printTable() {
		List<List<CellPOJO>> slice = getPizzaSlice();
		
		//NOT: bound'lar 2 ile bölündü.
		bound_low_ = new Point( slice.get(0).get(0).getX()/2, slice.get(0).get(0).getY()/2 );
		bound_high_ = new Point( slice.get(slice.size()-1).get(slice.get(slice.size() - 1).size()-1).getX()/2, slice.get(slice.size()-1).get(slice.get(slice.size() - 1).size()-1).getY()/2 );
		
		Hashtable<Character, Integer> stats = getGredientStatistics();
		int T = (stats.get('T') != null) ? stats.get('T') : 0;
		int M = (stats.get('M') != null) ? stats.get('M') : 0;
		System.out.println("*" + String.join(",", new String[]{ 
				Integer.toString(T),
				Integer.toString(M),
				Integer.toString(bound_low_.x),
				Integer.toString(bound_low_.y),
				Integer.toString(bound_high_.x),
				Integer.toString(bound_high_.y)
		}));//*T,M,bLx,bLy,bHx,bHy
		
		//System.out.println("BoundL : "+bound_low_.toString());
		//System.out.println("BoundH : "+bound_high_.toString());
		
		/*for (List<CellPOJO> row : getPizzaSlice()) {
			for (CellPOJO cell : row) {
				System.out.print(cell.toString() + "\t");
			}
			System.out.println();
		}*/
	}

	public Hashtable<String, TableNode[]> findProbabilities() {
		TableNode[] slices;
		for (int i = 0; i < R - 1; i++) {
			slices = new TableNode[2];
			int dividerLineAtX = (i) * 2 + 1 + getTopRightCornerCell().getX();
			// System.out.println("x bölücüsü :\t" + dividerLineAtX);
			// burada bölünen çizgi var zaten arraylistin o indexten küçük ve
			// büyük bölgelerini alsam yeter.
			// bunlarda 2 ye ayrılacak nodelar olacak master nodden ayrılan
			// algroritma ise her satır başındaki cell çekilip x değerine
			// bakılacak
			// eğer küçükse ilk slice a dahil
			// eğer büyükse ikinci slice a dahil olacak.
			// bunun yerine küççükmü büyükmü diye bakmak yerine elimizdeki i nin
			// değeri mesela 0 ise sadece il satır dahil alıncak yani
			// x.substring(0,1)
			// 2 olsaydı 1 ve ikinci satırlar alınacak kalan satırlar 2. slice a
			// dahil
			// yani genellerse i kadar satırı alıp birinci slice a alıcaz
			String lineIdForX = System.currentTimeMillis() + "-x" + dividerLineAtX;
			TableNode topSlice = new TableNode(lineIdForX, new ArrayList<>(getPizzaSlice().subList(0, i + 1)));
			TableNode bottomSlice = new TableNode(lineIdForX,
					new ArrayList<>(getPizzaSlice().subList(i + 1, getPizzaSlice().size())));
			if (topSlice.isValidSlice() && bottomSlice.isValidSlice()) {
				slices[0] = topSlice;
				slices[1] = bottomSlice;
				sliceTable.put(lineIdForX, slices);
			}
		}
		for (int i = 0; i < C - 1; i++) {
			int dividerLineAtY = (i) * 2 + 1 + getTopRightCornerCell().getY();
			slices = new TableNode[2];
			// System.out.println("y bölücüsü :\t " + ((i) * 2 + 1));
			List<List<CellPOJO>> leftSliceAsAL = new ArrayList<List<CellPOJO>>();
			List<List<CellPOJO>> rightSliceAsAL = new ArrayList<List<CellPOJO>>();
			for (int rowIndex = 0; rowIndex < R; rowIndex++) {
				leftSliceAsAL.add(new ArrayList<CellPOJO>(getPizzaSlice().get(rowIndex).subList(0, i + 1)));
				rightSliceAsAL.add(new ArrayList<CellPOJO>(
						getPizzaSlice().get(rowIndex).subList(i + 1, getPizzaSlice().get(rowIndex).size())));
			}
			String lineIdForY = System.currentTimeMillis() + "-y" + dividerLineAtY;
			TableNode leftSlice = new TableNode(lineIdForY, leftSliceAsAL);
			TableNode rightSlice = new TableNode(lineIdForY, rightSliceAsAL);
			if (leftSlice.isValidSlice() && rightSlice.isValidSlice()) {
			slices[0] = leftSlice;
			slices[1] = rightSlice;
			sliceTable.put(lineIdForY, slices);
			}
		}
		return sliceTable;
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
				} else if (gradient == 'M') {
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
		Hashtable<Character,Integer> gredientStatistic = getGredientStatistics();
		boolean isValid=true;
		for (Character gradientAsChar : gredientStatistic.keySet()) {
			isValid = /*isValid&&*/(gredientStatistic.get(gradientAsChar)>=ProbabiltyCreator.L) ? true : false ; 
		}
		return isValid;
	}
}
