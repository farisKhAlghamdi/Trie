import java.util.ArrayList;
import java.util.Scanner;

public class Trie {
	
	private TrieNode root;
	
	private int ALPHABET_SIZE = 26;
	
	public Trie() {
		root = new TrieNode();
	}
	
	// is the trie empty
	public boolean isEmpty() {
		return isEmpty(root);
	}
	
	// is a specific node empty
	public boolean isEmpty(TrieNode root) {
		// for each node
        for (TrieNode n: root.children)
        	// if a letter found
            if (n != null)
                return false;
        // no letter found
        return true;
    }
	
	public void clear() {
		for (int i = 0; i < ALPHABET_SIZE; i++)
			root.children[i] = null;
	}
	
	public void insert(String word) {
		
		TrieNode current = root;
		
		// for each character in the word
		for (char ch: word.toCharArray()) {
			int index = ch - 'A';
			if (current.children[index] == null)
				current.children[index] = new TrieNode();
			current = current.children[index];
		}
		
		// we reached the last character in the word
		current.isEndOfWord = true;
	}
	
	public void delete(String word) {
		root = delete(root, word, 0);
	}
	
	private TrieNode delete(TrieNode root, String word, int depth) {
		
		// if we reached an end:
		if (root == null)
			return null;
		
		// If we reached the last character:
		else if (word.length() == depth) {
			
			root.isEndOfWord = false;
			
			if (isEmpty(root))
                root = null;
			
			return root;
		}
		
		// If we still traversing the word:
		else {
			int index = word.charAt(depth) - 'A';
			root.children[index] = delete(root.children[index], word, depth + 1);
			
			// if after deleting the node has no children, delete the node
			if (isEmpty(root) && root.isEndOfWord == false)
	            root = null;
		}
		
		return root;
		
	}
	
	public boolean contains(String word) {
		
		TrieNode current = root;
		
		for (char ch: word.toCharArray()) {
			int index = ch - 'A';
			if (current.children[index] == null)
				return false;
			current = current.children[index];
		}
			return current.isEndOfWord;
	}
	
	public boolean isPrefix(String prefix) {
		
		TrieNode current = root;
		
		for (char ch: prefix.toCharArray()) {
			int index = ch - 'A';
			if (current.children[index] == null)
				return false;
			current = current.children[index];
		}
		
		// if the node corresponding to the last letter in the prefix has no children
		// ,then it is not a prefix
		// otherwise it is a prefix
		return !isEmpty(current);
	}
	
	public String[] allWordsPrefix(String prefix) {
		
		TrieNode current = root;
		
		for (char ch: prefix.toCharArray()) {
			int index = ch - 'A';
			if (current.children[index] == null)
				return null;
			current = current.children[index];
		}
		
		// the current here is the node corresponding to the last letter in the prefix
		// if prefix = "PO", the current is the node in the index corresponding to the letter "O"
		ArrayList<String> list = allWordsPrefix(current, prefix);
		String[] arr = new String[list.size()];
		return list.toArray(arr);
		
		
	}
	
	private ArrayList<String> allWordsPrefix(TrieNode root ,String prefix) {
		
		if (isEmpty(root))
			// return an empty list if an end reached
			return new ArrayList<String>();
		
		else {
			ArrayList<String> list = new ArrayList<String>();
			for (int i = 0; i < ALPHABET_SIZE; i++) {
				char ch = (char)(i+65);
				
				// if a letter exists
				if (root.children[i] != null) {
					if (root.children[i].isEndOfWord)
						// add a word with that prefix
						list.add(prefix + ch);
					list.addAll(allWordsPrefix(root.children[i], prefix + ch));
				}
			}
			return list;
		}
		
	}
	
	public int size() {
		return size(root);
	}
	
	private int size(TrieNode root) {
		if (isEmpty(root))
			return 0;
		else {
			int sum = 0;
			for (TrieNode n: root.children)
				if (n != null)
					sum += 1 + size(n);
			return sum;
		}
	}
	
	

	public static void main(String[] args) {
		
		String keys[] = {"POT", "POTS", "POST", "OPT", "OPTS", "TOP", "TOPS"};
		
		Trie trie = new Trie();
		
		System.out.println(trie.contains("POT"));
		System.out.println(trie.contains("POTS"));
		System.out.println(trie.contains("PO"));
		System.out.println(trie.contains("POO"));
		System.out.println(trie.size());
		
		for (String s: trie.allWordsPrefix("P"))
			System.out.println(s);
		
		trie.delete("POST");
		trie.delete("POT");
		trie.delete("POTS");
		trie.delete("TOP");
		System.out.println(trie.contains("POT"));
		System.out.println(trie.contains("POTS"));
		System.out.println(trie.contains("PO"));
		System.out.println(trie.contains("POO"));
		System.out.println(trie.size());
		
	}

}

class TrieNode {
	TrieNode[] children = new TrieNode[26];
	boolean isEndOfWord;
}
