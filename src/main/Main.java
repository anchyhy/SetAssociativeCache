package main;

import java.util.InputMismatchException;
import java.util.Scanner;

import main.com.TheTradeDesk.cache.Cache;
import main.com.TheTradeDesk.cache.CacheImpl;
import main.com.TheTradeDesk.cache.ReplacementMethodNames;
import main.com.TheTradeDesk.replacementalgorithm.LRU;

public class Main {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int setCount = 0;
		int blockCount = 0;
		ReplacementMethodNames replacementMethod = ReplacementMethodNames.LRU;
		System.out.println("Hi! This is a N-way Set Associate Cache! You are required to provide several arguments to proceed!");

		while(true) {
			System.out.println("The number of sets (1 - 2,147,483,647):");
			try {
				setCount = scanner.nextInt();
				if (setCount <= 0) {
					System.err.println("Please enter a positive number");
					continue;
				}
				break;
			} catch (InputMismatchException ex) {
				System.err.println("Please enter a valid number(1 - 2,147,483,647)");
				scanner.next();
			}
		}
		clearScanner(scanner);
		
		while (true) {
			System.out.println("The number of blocks per set (1 - 2,147,483,647):");
			try {
				blockCount = scanner.nextInt();
				if (blockCount <= 0) {
					System.err.println("Please enter a positive number");
					continue;
				}
				break;
			} catch (InputMismatchException ex) {
				System.err.println("Please enter a valid number(1 - 2,147,483,647)");
				scanner.next();;
			}
		}
		clearScanner(scanner);
		
		while (true) {
			System.out.println("The replacement algorithm, LRU or MRU:");
			String algorithm = scanner.next().toUpperCase();
			if (!algorithm.equals("LRU") && !algorithm.equals("MRU")) {
				System.err.println("Please enter LRU or MRU as the replacement algorithm!");
				continue;
			}
			switch(algorithm.toUpperCase()) {
				case "LRU":
					replacementMethod = ReplacementMethodNames.LRU;
					break;
				case "MRU":
					replacementMethod = ReplacementMethodNames.MRU;
					break;
			}
			break;
		}
		clearScanner(scanner);
		
		Cache<String, String> cache = new CacheImpl<>(setCount, blockCount, replacementMethod);
		System.out.println("Congrats! You 've created the cache!");
		printRules();
		while (true) {
			System.out.println("INPUT > ");
			String str = scanner.nextLine();
			String strArr[] = str.split(" ");
			
			if (strArr.length == 1 && str.equals("q")) {
				scanner.close();
				break;
			} else if (strArr.length == 1 && str.equals("print")) {
				System.out.println("Current cache: ");
				cache.print();
			} else if (strArr.length == 2 && strArr[0].equals("get")) {
				System.out.println(cache.get(strArr[1]));
			} else if (strArr.length == 3 && strArr[0].equals("put")) {
				cache.put(strArr[1], strArr[2]);
				System.out.println("Current cache:");
				cache.print();
			} else {
				System.err.println("Please follow the rules below: ");
				printRules();
			}
		}
	}
	
	private static void clearScanner(Scanner scanner) {
		if (scanner.hasNextLine()) {
			scanner.nextLine();
		}
	}
	
	private static void printRules() {
		System.out.println("================================================");
		System.out.println("|" + centerString("Operation", 20) + "|" + centerString("Arg1", 20) + "|" + centerString("Arg2", 20) + "|");
		System.out.println("--------------------------------------------------------");
		System.out.println("|" + centerString("put", 20) + "|" + centerString("key", 20) + "|" + centerString("value", 20) + "|");
		System.out.println("|" + centerString("get", 20) + "|" + centerString("key", 20) + "|" + centerString("", 20) + "|");
		System.out.println("|" + centerString("print", 20) + "|" + centerString("", 20) + "|" + centerString("", 20) + "|");
		System.out.println("|" + centerString("q", 20) + "|" + centerString("", 20) + "|" + centerString("", 20) + "|");
		System.out.println("================================================");
		System.out.println("======== Seperate elements with spaces ==========");
		System.out.println("================================================");
	}
	
	private static String centerString (String s, int width) {
	    int padSize = width - s.length();
	    int padStart = s.length() + padSize / 2;

	    s = String.format("%" + padStart + "s", s);
	    s = String.format("%-" + width  + "s", s);
	    return s;
	}
	
	/*public static void main(String[] args) {
		Cache<Integer, Integer> cache = new CacheImpl<>(1, 3, ReplacementMethodNames.MRU);
		cache.put(1, 2);
		cache.put(2, 111);
		cache.put(122, 29191);
		cache.put(111, 299999);
		cache.print();
		System.out.println(cache.get(2).toString());
	}*/
}
