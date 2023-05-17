import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	static Trie trie;

	public static void main(String[] args) throws FileNotFoundException {
		
		System.out.println("TRIE PROJECT");
		System.out.println("1) Create an empty trie");
		System.out.println("2) Create a trie with initial letters");
		System.out.println("3) Insert a word");
		System.out.println("4) Delete a word");
		System.out.println("5) List all words that begin with a prefix");
		System.out.println("6) Size of the trie");
		System.out.println("7) End");
		
		
		int n = extractNumber();
		
		while (n != 7) {
			
			switch (n) {
			case 1: trie = new Trie(); System.out.println("Done"); break;
			case 2: createTrieWithLetters(); break;
			case 3: insertWord(); break;
			case 4: deleteWord(); break;
			case 5: listWordsWithPrefix(); break;
			case 6: printSize(); break;
			case 7: System.exit(0);
			}
			
			n = extractNumber();
		}
		
	}
	
	public static ArrayList<String> permutation(String str) { 
	    return permutation("", str);
	}

	private static ArrayList<String> permutation(String prefix, String str) {
	    int n = str.length();
	    
	    if (n == 0) {
	    	ArrayList<String> list = new ArrayList<String>(1);
	    	list.add(prefix);
	    	return list;
	    }
	    	
	    else {
	    	ArrayList<String> list = new ArrayList<String>();
	    	for (int i = 0; i < n; i++)
	    		list.addAll(permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n)));
	    	return list;
	    }  
	}
	
	public static int extractNumber() {
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter your choice:");
		String s = input.next();
		
		while (true) {
			if (s.length() == 1)
				if (Character.isDigit(s.charAt(0)))
					if (Integer.parseInt(s) >= 1 && Integer.parseInt(s) <= 7)
						break;
			
			System.out.println("Error. Enter just one number from 1 to 7");
			System.out.print("Enter your choice:");
			s = input.next();
		}
		
		return Integer.parseInt(s);
	}
	
	public static void createTrieWithLetters() throws FileNotFoundException {
		Scanner input = new Scanner(System.in);
		System.out.print("Enter your list of letters> ");
		String letters = input.nextLine().strip().replace(" ", "");
		
		trie = new Trie();
		
		for (String str: permutation(letters)) {
			Scanner dic = new Scanner(new File("dic.txt"));
			do {
				String word = dic.next();
				if (str.equals(word))
					trie.insert(str);
				else if (str.startsWith(word))
					trie.insert(word);
			} while (dic.hasNext());
		}
		System.out.println("Done");
	}
	
	public static void insertWord() {
		Scanner input = new Scanner(System.in);
		
		if (trie != null) {
			System.out.print("Enter a word to insert> ");
			String word = input.next().strip();
			trie.insert(word);
			System.out.println("Done");
		}
		else
			System.out.println("Create a trie first");
	}
	
	public static void deleteWord() {
		Scanner input = new Scanner(System.in);
		
		if (trie != null) {
			System.out.print("Enter a word to delete> ");
			String word = input.next().strip();
			trie.delete(word);
			System.out.println("Done");
		}
		else
			System.out.println("Create a trie first");
	}
	
	public static void listWordsWithPrefix() {
		Scanner input = new Scanner(System.in);
		if (trie != null) {
			System.out.print("Enter a prefix> ");
			String prefix = input.next().strip();
			
			System.out.print("Found the following words: ");
			for (String s: trie.allWordsPrefix(prefix))
				System.out.print(s + ", ");
			System.out.println();
		}
		else
			System.out.println("Create a trie first");
	}
	
	public static void printSize() {
		if (trie != null)
			System.out.println("The size of the trie is: " + trie.size());
		else
			System.out.println("Create a trie first");
	}

}
