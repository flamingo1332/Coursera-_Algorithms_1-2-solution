/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import java.util.Iterator;
import java.util.NoSuchElementException;


public class Deque<Item> implements Iterable<Item> {
    // construct an empty deque
    private Node<Item> first;
    private Node<Item> last;
    private int n;

    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    public Deque() {
        this.first = null;
        this.last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    public void validate(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null argument");
        }
    }

    public void validate() {
        if (n == 0) {
            throw new NoSuchElementException("empty list!");
        }
    }

    // add the item to the front
    public void addFirst(Item item) {
        validate(item);
        // if first time adding...
        if (n == 0) {
            first.item = item;
            first.next = last;
            first.prev = null;
            n++;
        }

        else if (n == 1) {
            last.item = first.item;
            last.next = null;
            last.prev = first;

            first = new Node<Item>();
            first.item = item;
            first.next = last;
            first.prev = null;
            n++;
        }

        else {
            Node<Item> oldFirst = first;
            oldFirst.prev = first;

            first = new Node<Item>();
            first.item = item;
            first.next = oldFirst;
            first.prev = null;
            n++;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        validate(item);
        if (n == 0) {
            first.item = item;
            first.next = last;
            first.prev = null;
            n++;
        }
        else if (n == 1) {
            last.item = item;
            last.next = null;
            last.prev = first;
            n++;
        }
        else {
            Node<Item> oldlast = last;
            last = new Node<Item>();
            last.item = item;
            last.next = null;
            last.prev = oldlast;
            oldlast.next = last;
            n++;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        validate();
        Item item = first.item;
        if (n == 1) {
            first = null;
            n--;
        }
        else if (n == 2) {
            first.item = last.item;
            first.next = last;
            first.prev = null;
            last = null;
            n--;
        }
        else {
            first = first.next;
            n--;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        validate();
        if (n == 1) {
            Item item = first.item;
            first = null;
            n--;
            return item;
        }
        else if (n == 2) {
            Item item = last.item;
            last = null;
            n--;
            return item;
        }
        else {
            Item item = last.item;
            last = last.prev;
            n--;
            return item;
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator(first);
    }
    private class LinkedIterator implements Iterator<Item> {
        private Node<Item> current;
        public LinkedIterator(Node<Item> first) {
            current = first;
        }
        public boolean hasNext() {
            return current != null;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> ints = new Deque<>();
        System.out.println(ints.isEmpty());
        System.out.println(ints.size());
        ints.addFirst(4);
        ints.addFirst(3);
        ints.addFirst(2);
        ints.addFirst(1);
        ints.addLast(5);
        ints.addLast(6);
        System.out.println(ints.size());
        ints.removeFirst();
        ints.removeLast();


    }

}
