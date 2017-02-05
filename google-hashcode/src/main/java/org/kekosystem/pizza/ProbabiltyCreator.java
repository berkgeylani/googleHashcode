package org.kekosystem.pizza;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
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

		readFile();
	}

	private static void readFile() throws IOException {
//		System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
//		System.setOut(new PrintStream(new File(System.getProperty("user.dir") + File.separator + "testResultFiltered.txt")));
		int dosya_ismi = 0;
		List<String> lines = Files.readAllLines(Paths.get(RESULT_PATH));
		for (String milisPerTree : TableNode.getMilisList()) {
			System.out.println(milisPerTree + "---------");
			dosya_ismi++;
			System.setOut(new PrintStream(new File(System.getProperty("user.dir") + File.separator + "testResult"+dosya_ismi+".txt")));
			
			int depth_cursor = -1,
				ctrl = 0;
			
			ArrayList<Integer> borders = new ArrayList<>();
			ArrayList<String> slices_per_depths = new ArrayList<>();
			
			Collections.sort(lines);
			for (String line : lines) {
				if(!line.split("-")[0].equals(milisPerTree))	continue;
				
				if(depth_cursor != Integer.parseInt(line.split("-")[1])){
					depth_cursor = Integer.parseInt(line.split("-")[1]);
					borders.add(ctrl);
				}
				
				//max_depth = Integer.parseInt(lines.get(0).split("-")[1]);
				String[] miniTableInfo = line.split("-")[3].split(",");
				
				if (line.contains(milisPerTree)
						&& Integer.parseInt(miniTableInfo[0]) + Integer.parseInt(miniTableInfo[1]) <= H) {
					
					ctrl++;
					System.out.println(line);	
					
					//bir kişinin çocuklarını yazdırmak amaç
					//elimde bir node var şu anda ve onunda elinde  böylbir şey yazıyor y5*1y3*1
					//o zaman çocuklarda splitin 2 inci elemanını aratırız
//					if(line.split("-")[1].equals("8")) {
//						String[] sliceInfo = line.split("-")[3].split(",");
//						sliceInfo[sliceInfo.length-1];
//					}					
				}
				
				
			}
			
			//en küçük depthlilerden dolaşmaya başla.
			int smaller_depth = borders.get(borders.size() - 1);
			//ArrayList<String> result_slices = new ArrayList<>();
			Set<String> blacklist = new HashSet<>();
			String blacklist_adayi = null; 
			
			for(int i = borders.size() - 1; i >= 0; i--){
				int low_bound = borders.get(i),
					high_bound = (i == borders.size() - 1) ? slices_per_depths.size() : borders.get(i+1);
				
				for(; low_bound<high_bound; low_bound++){
					
					if(i != borders.size()){
						String[] blacklist_parcalari = slices_per_depths.get(low_bound).split("-")[1].split(",");
						String[] blacklist_parcalari_valid = null;
						System.arraycopy(blacklist_parcalari, 0, blacklist_parcalari_valid, 0, blacklist_parcalari.length-2);
						
						if(blacklist.contains(blacklist_parcalari_valid))
							slices_per_depths.remove(low_bound);
						
					}else{
						String[] blacklist_parcalari = slices_per_depths.get(low_bound).split("-")[1].split(",");
						String[] blacklist_parcalari_valid = null;
						System.arraycopy(blacklist_parcalari, 0, blacklist_parcalari_valid, 0, blacklist_parcalari.length-2);
						blacklist.add(Arrays.toString(blacklist_parcalari_valid));
					}
				}//endfor
			}//endfor
		
			for(String slice : slices_per_depths){
				System.out.println(slice);
			}
			
		}
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
