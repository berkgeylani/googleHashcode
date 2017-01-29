package org.kekosystem.pizza;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.stream.Stream;

public class ProbabiltyCreator {

	private static boolean isFirst = true;
	private static String[][] pizzaTable;
	private static int R, C, L, H, index = 0;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		Stream<String> lines = Files.lines(Paths.get(System.getProperty("user.dir") + File.separator + "small.in"));
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
				pizzaTable = new String[R][C];
			} else {
				String[] charsPerLine = line.split("");
				pizzaTable[index - 1] = charsPerLine;
				// Alttaki for döngüsü hangi malzemeden kaç tane var çıtkısını
				// veriyor.
				for (int i = 0; i < charsPerLine.length; i++) {
					String gradient = charsPerLine[i];
					if (gradient.equals("T")) {
						if (gradientCount.containsKey('T')) {
							gradientCount.put('T', gradientCount.get('T') + 1);
						} else {
							gradientCount.put('T', 1);
						}
					} else if (gradient.equals("M")) {
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
		// prerequirities finisihed.

		// Lets get probabilities
		for (int i = 1; i <= ((gradientCount.get('T') < gradientCount.get('M')) ? gradientCount.get('T')
				: gradientCount.get('M'))/* Az olan malzemenin sayısını getir */; i++) {
			int lineCount=i-1;
			for (int j = 0; j < combination((R-1)*(C-1), lineCount); j++) {
				
			}
		}
	}
	public static int combination(int n, int k)
	{
	    return permutation(n) / (permutation(k) * permutation(n - k));
	}

	public static int permutation(int i)
	{
	    if (i == 1)
	    {
	        return 1;
	    }
	    return i * permutation(i - 1);
	}
	private static void printPizzaTable() {
		for (int i = 0; i < pizzaTable.length; i++) {
			for (int j = 0; j < pizzaTable[i].length; j++) {
				System.out.print(pizzaTable[i][j]);
			}
			System.out.println();
		}
	}
}
