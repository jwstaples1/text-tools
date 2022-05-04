
/**
 * Write a description of class BinarySearchTree here.
 *
 * @author Josh Staples
 * @version 05032022
 */
import java.util.ArrayList;
public class BinarySearchTree<E> {
    private BSTNode root;
    private int treeHeight;
    private int numNodes;
    
    public BinarySearchTree() {
        root = null;
        treeHeight = 0;
        numNodes = 0;
    }
    
    public void add(E data) {
        BSTNode newNode = new BSTNode(data);
        
        if(root == null) {
            root = newNode;    
            return;
        }
        
        BSTNode currentNode = root;
        numNodes++; // updates node counter
        int tempHeight = 0;
        while(true) {
            int comparisonFactor = newNode.compareTo(currentNode); // checks to see if the new data is larger or smaller
            if(comparisonFactor < 0) { // if its smaller
                if(!currentNode.hasLeft()) { // if current node doesn't have a left node, make it the new left
                    currentNode.setLeft(newNode);
                    newNode.setParent(currentNode);
                    treeHeight = (treeHeight < tempHeight) ? tempHeight : treeHeight; // updates max tree height
                    return;
                }
                // if node does exist, repeat with that node as currentNode
                currentNode = currentNode.getLeft();
                tempHeight++;
            } else if(comparisonFactor > 0) { // if its larger
                if(!currentNode.hasRight()) { // if current node doesnt have a right node, make it the new right
                    currentNode.setRight(newNode);
                    newNode.setParent(currentNode);
                    treeHeight = (treeHeight < tempHeight) ? tempHeight : treeHeight; // updates max tree height
                    return;
                }
                // if node does exist, repeat process with that as current node
                currentNode = currentNode.getRight();
                tempHeight++;
            } else { // if its the same, increment frequency value of that node
                currentNode.incrementFrequency();
                return;
            }
        }
    }
    
    // checks to see if an element is in the Binary Search Tree, if yes return true, if no return false.
    public BSTNode find(E data) {
        if(root == null) {
            return null;    
        }
        
        BSTNode currentNode = root;
        while(true) { // infinite loop, break sequences within
            int comparisonFactor = ((Comparable) data).compareTo((Comparable) currentNode.getData());
            if(comparisonFactor < 0) { // if data is smaller than current node's data
                if(currentNode.hasLeft()) { // if the current node has a left, go to the left and repeat
                    currentNode = currentNode.getLeft();
                } else { // otherwise the bst can't contain it
                    return null;
                }
            } else if(comparisonFactor > 0) { // if data is larger than current node's data
                if(currentNode.hasRight()) { // if the current node has a right, go to the right and repeat
                    currentNode = currentNode.getRight();
                } else { // otherwise bst can't contain it
                    return null;
                }
            } else { // if they're the same, it does contain it, return true
                return currentNode;
            }
        }
    }
    
    // Executes an In-Order Traversal and returns an HashMap mapped as <element: frequency>
    public ArrayList<BSTNode> inOrderTraversal() {
        ArrayList<BSTNode> data = new ArrayList<BSTNode>();
        
        BSTNode currentNode = root;
        do {
            if(currentNode.hasLeft()) { // if there is a node to the left, traverse to the left
                if(!data.contains(currentNode.getLeft())) { // if the data isn't already in the arraylist, add it
                    currentNode = currentNode.getLeft();
                    continue; // repeat
                }
            }
            
            if(!data.contains(currentNode)) { // add the current node to the data arraylist if it doesnt already exist
                data.add(currentNode);    
            }
            
            if(currentNode.hasRight()) { // if there is a node to the right, traverse to the right
                if(!data.contains(currentNode.getRight())) { // if the data isnt already in the arraylist, add it
                    currentNode = currentNode.getRight();
                    continue; // repeat
                }
            }
            
            currentNode = currentNode.getParent(); // if all conditions are skipped, repeat with the parent
        } while(currentNode != null); // end when the currentNode is null (parent of root)
        
        return data; // returns the arraylist
    } // end inOrderTraversal()
    
    // getters
    
    public BSTNode getRoot() {
        return root;
    }
    
    public int getTreeHeight() {
        return treeHeight;
    }
    
    public int getNumNodes() {
        return numNodes;
    }
} 

class BSTNode<E> implements Comparable<BSTNode<E>> {
    private E data;
    private int frequency;
    private BSTNode leftNode;
    private BSTNode rightNode;
    private BSTNode parent;
    
    public BSTNode(E data) {
        this.data = data;
        frequency = 1;
        leftNode = rightNode = parent = null;
    }
    
    // getters
    
    public E getData() {
        return data;
    }
    
    public int getFrequency() {
        return frequency;
    }
    
    public BSTNode getLeft() {
        return leftNode;
    }
    
    public BSTNode getRight() {
        return rightNode;
    }
    
    public BSTNode getParent() {
        return parent;
    }
    
    public boolean hasLeft() {
        return leftNode != null;
    }
    
    public boolean hasRight() {
        return rightNode != null;
    }
    
    public boolean hasParent() {
        return parent != null;
    }
    
    // setter
    
    public void setData(E data) {
        this.data = data;
    }
    
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    
    public void setLeft(BSTNode leftNode) {
        this.leftNode = leftNode;
    }
    
    public void setRight(BSTNode rightNode) {
        this.rightNode = rightNode;
    }
    
    public void setParent(BSTNode parent) {
        this.parent = parent;
    }
    
    // increases the frequency by 1
    public void incrementFrequency() {
        frequency++;
    }
    
    // clones the current node, returns a new one
    public BSTNode clone() {
        BSTNode newNode = new BSTNode(this.data);
        newNode.setFrequency(frequency);
        newNode.setRight(rightNode);
        newNode.setLeft(leftNode);
        newNode.setParent(parent);
        
        return newNode;
    }
    
    // overrides comparable methods
    @Override
    public int compareTo(BSTNode other) {
        return ((Comparable) data).compareTo((Comparable) other.getData());
    }
}
