package org.kekosystem.pizza;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Stream;

public class ProbabiltyCreator {

	private static boolean isFirst = true;
//	private static char[][] pizzaTable;
	private static int R, C, L, H, index = 0;
	private static List<List<CellPOJO>> pizzaTableAsList = new ArrayList<List<CellPOJO>>();

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
//				pizzaTable = new char[R][C];
			} else {
				pizzaTableAsList.add(new ArrayList<CellPOJO>());
				char[] charsPerLine = line.toCharArray();
				//index -1 = pizza nın satırı
//				pizzaTable[index - 1] = charsPerLine;
				// Alttaki for döngüsü hangi malzemeden kaç tane var çıtkısını
				// veriyor.
				for (int i = 0; i < charsPerLine.length; i++) {
					char gradient = charsPerLine[i];
					pizzaTableAsList.get(index-1).add(new CellPOJO(2*(index-1),2*i,gradient));
					if (gradient=='T') {
						if (gradientCount.containsKey('T')) {
							gradientCount.put('T', gradientCount.get('T') + 1);
						} else {
							gradientCount.put('T', 1);
						}
					} else if (gradient=='M') {
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
//		 prerequirities finisihed.
		for (List<CellPOJO> row : pizzaTableAsList) {
			for (CellPOJO cell : row) {
				System.out.print(cell.toString()+"\t");
			}
			System.out.println();
		}
		
		
		// Lets get probabilities
		/*
		 * Öncelikle birnici ışın için bütün ihtimalleri aldığımız algoritmayı yazacağız.
		 * 3*5(3 satır 5 sütun olan bir matris)elemanları
		 * 0,0	0,1 0,2
		 * 1,0	1,1	1,2
		 * string indexleri bu şekilde biz ise bunları kordinat düzleminde(x,y) olarak tutacağız.Tuttuk şimdi ise;
		 * bunları bölebileceğimiz xden1 xden 3 x den 5 yden1 yden 3 gibi çizgilerimi bulmamız gerekiyor.bulduk şimdi ise;
		 * bunları buldukya
		 * 
		 * */
		System.out.println("***************-------------***************");
		for (int i = 0; i < R-1; i++) {
			int dividerLineAtX = (i)*2+1;
			System.out.println("x bölücüsü :\t"+ dividerLineAtX);
			//burada bölünen çizgi var zaten arraylistin o indexten küçük ve büyük bölgelerini alsam yeter.
			//bunlarda 2 ye ayrılacak nodelar olacak master nodden ayrılan
			//algroritma ise her satır başındaki cell çekilip x değerine bakılacak 
			//eğer küçükse ilk slice a dahil
			//eğer büyükse ikinci slice a dahil olacak.
			//bunun yerine küççükmü büyükmü diye bakmak  yerine elimizdeki i nin
			// değeri mesela 0 ise sadece il satır dahil alıncak yani x.substring(0,1)
			//2 olsaydı 1 ve ikinci satırlar alınacak kalan satırlar 2. slice a dahil
			//yani genellerse i kadar satırı alıp birinci slice a alıcaz
			String lineId = System.currentTimeMillis()+"-x"+i;
			TableNode topSlice = new TableNode(lineId, new ArrayList<>(pizzaTableAsList.subList(0, i+1)));
			TableNode bottomSlice = new TableNode(lineId, new ArrayList<>(pizzaTableAsList.subList(i+1, pizzaTableAsList.size())));
			topSlice.printTable();
			System.out.println();
			System.out.println();
			bottomSlice.printTable();
			System.out.println();
			System.out.println("***************-------------***************");
			System.out.println();
		}
		System.out.println();
		System.out.println();
		System.out.println("***************-------------***************");
		for (int i = 0; i < C-1; i++) {
			System.out.println("y bölücüsü :\t "+ ((i)*2+1));
			List<List<CellPOJO>> leftSliceAsAL = new ArrayList<List<CellPOJO>>();
			List<List<CellPOJO>> rightSliceAsAL= new ArrayList<List<CellPOJO>>();
			for (int rowIndex = 0; rowIndex < R; rowIndex++) {
				leftSliceAsAL.add(new ArrayList<CellPOJO>(pizzaTableAsList.get(rowIndex).subList(0, i+1)));
				rightSliceAsAL.add(new ArrayList<CellPOJO>(pizzaTableAsList.get(rowIndex).subList(i+1, pizzaTableAsList.get(rowIndex).size())));
			}
			TableNode leftSlice = new TableNode(System.currentTimeMillis()+"-y"+i, leftSliceAsAL);
			TableNode rightSlice = new TableNode(System.currentTimeMillis()+"-y"+i, rightSliceAsAL);
			leftSlice.printTable();
			System.out.println();
			System.out.println();
			rightSlice.printTable();
			System.out.println();
			System.out.println("***************-------------***************");
			System.out.println();
		}
		
		
		
//		for (int i = 1; i <= ((gradientCount.get('T') < gradientCount.get('M')) ? gradientCount.get('T')
//				: gradientCount.get('M'))/* Az olan malzemenin sayısını getir */; i++) {
//			int lineCount=i-1;
//			for (int j = 0; j < combination((R-1)*(C-1), lineCount); j++) {
//				
//			}
//		}
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
//	private static void printPizzaTable() {
//		for (int i = 0; i < pizzaTable.length; i++) {
//			for (int j = 0; j < pizzaTable[i].length; j++) {
//				System.out.print(pizzaTable[i][j]);
//			}
//			System.out.println();
//		}
//	}
}
