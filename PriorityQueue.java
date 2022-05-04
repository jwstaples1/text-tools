
/**
 * Write a description of class PriorityQueue here.
 *
 * @author Josh Staples
 * @version 05032022
 */
public class PriorityQueue<E>
{
    private PriorityQueueNode head;
    private int numEntries;
    
    public PriorityQueue() {
        head = null;
        numEntries = 0;
    }
    
    public void enqueue(E data, int priority) {
        numEntries++;
        PriorityQueueNode newNode = new PriorityQueueNode(data, priority);
        
        if(head == null) {
            head = newNode;
            return;
        }
        
        PriorityQueueNode currentNode = head;
        
        do {
            int comparisonFactor = newNode.compareTo(currentNode);
            switch(comparisonFactor) {
                case -1:
                    if(!currentNode.hasNext()) {
                        currentNode.setNext(newNode);
                        return;
                    }
                    currentNode = currentNode.getNext();
                    break;
                case 0:
                    while(currentNode.hasNext()) {
                        PriorityQueueNode nextNode = currentNode.getNext();
                        if(newNode.compareTo(nextNode) != 0) {
                            PriorityQueueNode currNext = currentNode.getNext();
                            currentNode.setNext(newNode);
                            newNode.setNext(currNext);
                            return;
                        }
                        currentNode = currentNode.getNext();
                    }
                    currentNode.setNext(newNode);
                    return;
                case 1:
                    PriorityQueueNode prevNode = currentNode.getPrevious();
                    newNode.setPrevious(prevNode);
                    newNode.setNext(currentNode);
                    if(prevNode == null) head = newNode;
                    return;
            }
        } while(currentNode != null);
    }
    
    public PriorityQueueNode dequeue() {
        if(!isEmpty()) {
            PriorityQueueNode headNode = head;
            head = head.getNext();
            numEntries--;
            return headNode;
        }
        return null;
    }
    
    public boolean isEmpty() {
        return numEntries == 0;
    }
} 

class PriorityQueueNode<E> implements Comparable<PriorityQueueNode<E>> {
    private E data;
    private int priority;
    private PriorityQueueNode previous;
    private PriorityQueueNode next;
    
    public PriorityQueueNode(E data, int priority) {
        this.data = data;
        this.priority = priority;
        previous = null;
        next = null;
    }
    
    // getters
    
    public E getData() {
        return data;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public PriorityQueueNode getPrevious() {
        return previous;
    }
    
    public PriorityQueueNode getNext() {
        return next;
    }
    
    public boolean hasNext() {
        return next != null;
    }
    
    public boolean hasPrevious() {
        return previous != null;
    }
    
    // setters
    
    public void setData(E data) {
        this.data = data;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    public void setPrevious(PriorityQueueNode previous) {
        if(this.previous != previous) {
            this.previous = previous;
            if(previous != null) {
                previous.setNext(this);
            }
        }
    }
    
    public void setNext(PriorityQueueNode next) {
        if(this.next != next) {
            this.next = next;
            if(next != null) {
                next.setPrevious(this);
            } 
        }
    }
    
    // overrides
    @Override
    public int compareTo(PriorityQueueNode other) {
        if(priority < other.getPriority()) {
            return -1;
        } else if(priority > other.getPriority()) {
            return 1;
        } else {
            return 0;
        }
    }
}
