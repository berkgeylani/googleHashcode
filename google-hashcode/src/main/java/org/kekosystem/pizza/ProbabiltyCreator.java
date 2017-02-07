package org.kekosystem.pizza;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProbabiltyCreator {

	private static final String RESULT_PATH = System.getProperty("user.dir") + File.separator + "testResult.txt";
	private static boolean isFirst = true;
	// private static char[][] pizzaTable;
	public static int R, C, L, H, index = 0;
	private static List<List<CellPOJO>> pizzaTableAsList = new ArrayList<List<CellPOJO>>();

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Stream<String> lines = Files.lines(Paths.get(System.getProperty("user.dir") + File.separator + "small2.in"));
		Hashtable<Character, Integer> gradientCount = new Hashtable<>();
		lines.forEach((line) -> {
			if (isFirst) {
				isFirst = false;
				String[] attributes = line.split(" ");
				System.out.println("Satır sayısı :\t" + attributes[0]);
				System.out.println("Sutun sayısı :\t" + attributes[1]);
				System.out.println("Bir parçba başına en az bulunacak malzeme sayısı :\t" + attributes[2]);
				System.out.println("Bir dilimde bulunucak max hücre :\t" + attributes[3]);
				R = new Integer(attributes[0]).intValue();
				C = new Integer(attributes[1]).intValue();
				L = new Integer(attributes[2]).intValue();
				H = new Integer(attributes[3]).intValue();
				// pizzaTable = new char[R][C];
			} else {
				pizzaTableAsList.add(new ArrayList<CellPOJO>());
				char[] charsPerLine = line.toCharArray();
				// index -1 = pizza nın satırı
				// pizzaTable[index - 1] = charsPerLine;
				// Alttaki for döngüsü hangi malzemeden kaç tane var çıtkısını
				// veriyor.
				for (int i = 0; i < charsPerLine.length; i++) {
					char gradient = charsPerLine[i];
					pizzaTableAsList.get(index - 1).add(new CellPOJO(2 * (index - 1), 2 * i, gradient));
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
			index++;
		});
		System.setOut(new PrintStream(new File(System.getProperty("user.dir") + File.separator + "testResult.txt")));
		TableNode mainNode = new TableNode("000", pizzaTableAsList, null, true);
		// prerequirities finisihed.
		// for (List<CellPOJO> row : pizzaTableAsList) {
		// for (CellPOJO cell : row) {
		// System.out.print(cell.toString() + "\t");
		// }
		// System.out.println();
		// }
		
		List<String> willBeWriteList = Files.lines(Paths.get(RESULT_PATH)).filter(map -> new Integer(map.split("-")[3].split(",")[0]).intValue()+new Integer(map.split("-")[3].split(",")[1]).intValue() <= H).collect(Collectors.toList());
		willBeWriteList.sort(new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				 String x1 = o1.split("-")[0];
		            String x2 = o2.split("-")[0];
		            int sComp = x1.compareTo(x2);

		            if (sComp != 0) {
		               return sComp;
		            } else {
		               String x3 = o1.split("-")[1];
		               String x4 = o2.split("-")[1];
		               return x3.compareTo(x4);
		            }
			}
		});
		Files.write(Paths.get(RESULT_PATH), willBeWriteList);
		//readFile();
	}

	private static void readFile() throws IOException {
		// System.setOut(new PrintStream(new
		// FileOutputStream(FileDescriptor.out)));
		// System.setOut(new PrintStream(new File(System.getProperty("user.dir")
		// + File.separator + "testResultFiltered.txt")));
		List<String> lines = Files.readAllLines(Paths.get(RESULT_PATH));
		
		Set<String> result = new HashSet<>();
		lines.forEach((line)->{
			if(!result.contains(line)){
				String[] splitted = line.split("-");
				String treeId = splitted[0],
					   info   = splitted[3];
			}
		});
		
	}

	private static void printSLices(TableNode mainNode) {
		mainNode.getSubSliceTable().forEach((k, v) -> {
			System.out.println("\n____________________" + k + "____________________\n");
			for (int i = 0; i < v.length; i++) {
				System.out.println("**********************************************");
				v[i].printTable();
			}
		});
	}

	private Queue<TableNode> queue = new LinkedList<TableNode>();

	public void breadth(TableNode root) {
		if (root == null)
			return;
		queue.clear();
		queue.add(root);
		while (!queue.isEmpty()) {
			TableNode node = queue.remove();
			node.printTable();
			if (!node.getSubSliceTable().isEmpty()) {
				for (String key : node.getSubSliceTable().keySet()) {
					TableNode[] tableNodes = node.getSubSliceTable().get(key);
					queue.add(tableNodes[0]);
					queue.add(tableNodes[1]);
				}
			}
		}

	}

}
