import java.util.List;
import java.util.ArrayList;

/**
 * Wrapper class of a List to represent an adjacency list in a graph. Contains iterator.
 * @author foojingting
 * @version 1.0
 * @since 2019-10-25
 */
public class AdjacencyList {
    
    private List<String> list;
    private int iterator = 0;

    public AdjacencyList() {
        list = new ArrayList<String>();
        iterator = 0;
    }

    public int length() {
        return list.size();
    }

    public boolean isEmpty() {
        return length() == 0;
    }

    /*public String get(int i) {
        return list.get(i);
    }*/

    public void add(String t) {
        list.add(t);
    }

    public boolean contains(String target) {
        setStart();
        String item = iterateNext();
        while (item != null) {
            if (item.equals(target))    // target found
                return true;

            item = iterateNext();
        }

        return false;
    }

    public void setStart() {
        iterator = 0;
    }

    public String iterateNext() {
        if (iterator == length())   // reached end of List
            return null;
        String item = list.get(iterator);
        ++iterator;
        return item;
    }
    
    public void printList(String node) {
        if (list == null) {
            System.out.println(node + " is not in graph");
        }
        else {
        	setStart();
        	String item = iterateNext();
        	System.out.print(node + ": ");
        	while (item != null) {
        		System.out.print(item);
        		item = iterateNext();
        		if (item != null)
        			System.out.print(", ");
        	}
        	System.out.println();
        }
    }
}