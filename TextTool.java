
/**
 * Write a description of class TextTool here.
 *
 * @author Josh Staples
 * @version 04302022
 */
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.File;

public class TextTool
{
    private static Scanner sysin;
    
    public static void main(String args[]) {
        sysin = new Scanner(System.in);
        
        // char[] settings = getSettings();
        
        String file = "peter.txt";
        BinarySearchTree<String> bst = getText(file, false);

        
        System.out.println("Binary Search Tree:");
        ArrayList<BSTNode> inOrder = bst.inOrderTraversal();
        PriorityQueue pq = new PriorityQueue();
        for(BSTNode word : inOrder){
            System.out.println(String.format("%-7s | f: %1d", word.getData(), word.getFrequency()));
            pq.enqueue(word.getData(), word.getFrequency());
        }
        
        System.out.println("\n"
                         + "BST Statistics\n"
                         + "--------------\n"
                         + "Number of Nodes: " + bst.getNumNodes() + "\n"
                         + "Height of Tree: " + bst.getTreeHeight() + "\n"
                         + "--------------\n");
                         
        
        System.out.println("Frequency Analysis:");
                         
        while(!pq.isEmpty()) {
            PriorityQueueNode pqNode = pq.dequeue();
            System.out.println(String.format("%-7s | f: %1d", pqNode.getData(), pqNode.getPriority()));
        }
    }
    
    // gets the program settings, returns an array containing all selections
    public static char[] getSettings() {
        char[] settings = new char[4];
        System.out.print("Select Mode: [T]ext [J]ava [H]tml:  ");
        settings[0] = getValidCharInput(new char[] {'T'});
        
        System.out.print("Case Sensitive: [Y] or [N]: ");
        settings[1] = getValidCharInput(new char[] {'Y', 'N'});
        
        System.out.print("Output of results: [F]ile or [C]onsole: ");
        settings[2] = getValidCharInput(new char[] {'F', 'C'});
        
        System.out.print("Output Size: [F]ull List or [T]op Values: ");
        settings[3] = getValidCharInput(new char[] {'F', 'T'});
        
        return settings;
    }
    
    // gets a valid character from the user based on what values are in validChars
    public static char getValidCharInput(char[] validChars) {
        char input;
        do {
            input = sysin.next().toUpperCase().charAt(0); // gets an uppercase character from the user
            if(containsChar(validChars, input)) {
                break;
            } else {
                System.out.print("  Invalid input, try again: "); // if the input is invalid, reprompt the user
            }
        } while(true);
        
        return input; // return character
    }
    
    // check to see if a character appears within a character array, returns true if yes, false if no
    public static boolean containsChar(char[] charList, char searched) {
        for(int i = 0; i < charList.length; i++) {
            if(charList[i] == searched) return true;
        }
        return false;
    }
    
    // reads in all the text from a file with possible case sensitivity, returns a new Binary Search Tree with the data
    public static BinarySearchTree getText(String fileName, boolean caseSensitive) {
        File textFile = null;
        Scanner fileReader = null;
        
        // checks to see if the file is there and accessible
        try {
            textFile = new File(fileName);
            fileReader = new Scanner(textFile);
        } catch(Exception e) {
            System.out.println("The URL you entered was invalid or the file has been moved, try again.");
            System.exit(-1); // ends program as the file was invalid
        }
        
        // if either are uninitialized still, return null
        if(textFile == null || fileReader == null) {
            return null;
        }
        
        // read in all words and add them to the Binary Search Tree
        BinarySearchTree<String> bst = new BinarySearchTree<String>();
        while(fileReader.hasNext()) {
            String word;
            if(caseSensitive) { // returns word or word in all lowercase based on case sensitivity variable
                word = sanitizeWord(fileReader.next());
            } else {
                word = sanitizeWord(fileReader.next().toLowerCase());
            }
            
            BSTNode wordNode = bst.find(word); // checks to see if a word is present
            if(wordNode == null) { // if not present, add the word
                bst.add(word);
            } else { // if present, increment frequency variable
                wordNode.incrementFrequency();
            }
        }
        
        return bst; // returns the sanitized word BST
    }
    
    // "sanitizes" the words in the lists; i.e. removes any punctuation or extra spaces that may have been included in the initial split
    public static String sanitizeWord(String word) {
        String newString = "";
        for(int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            if(Character.isLetterOrDigit(currentChar) || currentChar == '\'') {
                newString += currentChar;
            }
        }
        
        return newString;
    }
}
