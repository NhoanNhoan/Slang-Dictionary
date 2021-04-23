package service.ds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Trie
{
    // Define the alphabet size (26 characters for `a – z`)
    private static final int CHAR_SIZE = 128;

    private List<String> words;
    private boolean isLeaf;
    private List<Trie> children = null;

    // Constructor
    public Trie()
    {
        isLeaf = false;
        children = new ArrayList<>(Collections.nCopies(CHAR_SIZE, null));
        words = new ArrayList<>();
    }

    public Trie(HashMap<String, List<String>> dic) {
        isLeaf = false;
        children = new ArrayList<>(Collections.nCopies(CHAR_SIZE, null));
        words = new ArrayList<>();

        for (var word : dic.keySet()) {
            for (var meaning : dic.get(word)) {
                for (var key : meaning.split(" ")) {
                    insert(key, word);
                }
            }
        }
    }

    // Iterative function to insert a string into a Trie
    public void insert(String key, String keyWord)
    {
        key = key.toLowerCase();
        // start from the root node
        Trie curr = this;

        // do for each character of the key
        for (char c: key.toCharArray())
        {
            // create a new Trie node if the path does not exist
            if (curr.children.get(c) == null) {
                curr.children.set(c, new Trie());
            }

            // go to the next node
            curr = curr.children.get(c);
        }

        // mark the current node as a leaf
        curr.isLeaf = true;
        curr.words.add(keyWord);
    }

    // Iterative function to search a key in a Trie. It returns true
    // if the key is found in the Trie; otherwise, it returns false
    public List<String> search(String key)
    {
        key = key.toLowerCase();
        Trie curr = this;

        // do for each character of the key
        for (char c: key.toCharArray())
        {
            // go to the next node
            curr = curr.children.get(c);

            // if the string is invalid (reached end of a path in the Trie)
            if (curr == null) {
                return null;
            }
        }

        // return true if the current node is a leaf node and the
        // end of the string is reached
        return curr.words;
    }
}
