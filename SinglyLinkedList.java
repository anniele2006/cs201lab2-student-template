import java.util.*;

public class SinglyLinkedList<E extends Comparable<E>> {
    private Node<E> head = null;
    private Node<E> tail = null;
    private int size = 0;

    private static class Node<E> {
        private E element;
        private Node<E> next;
    
        public Node(E e, Node<E> n){
            element = e;
            next = n;
        }
    
        public E getElement(){
            return element;
        }
    
        public Node<E> getNext(){
            return next;
        }
    
        public void setNext(Node<E> n){
            next = n;
        }
    }

    public SinglyLinkedList(){

    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public E first(){
        if (isEmpty()){
            return null;
        } 
        return head.getElement();
    }

    public E last(){
        if (isEmpty()){
            return null;
        }
        return tail.getElement();
    }

    public void addFirst(E e){
        head = new Node<>(e, head);

        if (isEmpty()){
            tail = head;
        }
        size++;
    }

    public void addLast(E e){
        Node<E> newest = new Node<>(e, null);
        if (isEmpty()){
            head = newest;
        } else {
            tail.setNext(newest);
        }
        tail = newest;
        size++;
    }

    public E removeFirst(){
        if (isEmpty()){
            return null;
        }

        E answer = head.getElement();
        head = head.getNext();
        size--;

        if (isEmpty()){
            tail = null;
        }
        return answer;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        Node<E> current = head;
        while (current != null) {
            sb.append(current.getElement());
            sb.append(" ");
            current = current.getNext();
        }
        return sb.toString();
    }

    // // write your codes here
    // public void swap(){
    //     // nothing to swap if empty or size is 1
    //     if (isEmpty() || size() == 1) {
    //         return;
    //     }

    //     // 5 3 2 4 1
    //     // one pass: find max, min, swap positions
    //     // second pass: find max (smaller than last max), and min (larger than last min) n swap positions
    //     // size/2 passes
    //     // 1 3 4 2 5

    //     E lowerBound = null;
    //     E upperBound = null;

    //     for (int i = 0; i < size / 2; i++) {

    //         Node<E> min = null;
    //         Node<E> max = null;

    //         Node<E> minPrev = null;
    //         Node<E> maxPrev = null;

    //         Node<E> prev = null;
    //         Node<E> current = head;

    //         // time to parse thru
    //         while (current != null) {

    //             E value = current.getElement();

    //             // check to consider only values that haven't been swapped
    //             boolean valid = (lowerBound == null || value.compareTo(lowerBound) > 0) && (upperBound == null || value.compareTo(upperBound) < 0);

    //             if (valid) {

    //                 if (min == null || value.compareTo(min.getElement()) < 0) {
    //                     min = current;
    //                     minPrev = prev;
    //                 }

    //                 if (max == null || value.compareTo(max.getElement()) > 0) {
    //                     max = current;
    //                     maxPrev = prev;
    //                 }

    //             }

    //             prev = current;
    //             current = current.getNext();
    //         }

    //         // save values before moving the nodes for the next parse
    //         lowerBound = min.getElement();
    //         upperBound = max.getElement();

    //         swapNodes(min, minPrev, max, maxPrev);
        
    //     }

    // }

    public void swap() {
        if (size <= 1) {
            return;
        }

        // keep nodes in original sequence
        ArrayList<Node<E>> original = new ArrayList<>();

        Node<E> current = head;

        while (current != null) {
            original.add(current);
            current = current.getNext();
        }

        // Make another list and sort the nodes by value by value
        ArrayList<Node<E>> sorted = new ArrayList<>(original);
        sorted.sort((a, b) -> a.getElement().compareTo(b.getElement()));

        // map each node to the one it shall swap with\
        // smallest -> largest, 2nd smallest -> 2nd largest...
        Map<Node<E>, Node<E>> partner = new HashMap<>();

        for (int i = 0; i < sorted.size(); i++) {
            partner.put(
                sorted.get(i),
                sorted.get(sorted.size() - 1 - i)
            );
        }

        /*
        * Rebuild the linked-list sequence.
        *
        * At every original position, put that node's
        * corresponding partner.
        */

        // rebuild link list at original position, put node's partner from map
        head = partner.get(original.get(0));

        Node<E> previous = head;

        for (int i = 1; i < original.size(); i++) {
            Node<E> nextNode = partner.get(original.get(i));

            previous.setNext(nextNode);
            previous = nextNode;
        }

        tail = previous;
        tail.setNext(null);
    }


    // // swap e1 and e2 spots, e1b and e2b are the nodes b4 them
    // public void swapNodes(Node<E> e1, Node<E> e1b, Node<E> e2, Node<E> e2b) {

    //     if (e1 == e2) {
    //         return;
    //     }
    //     // e1 immediately before e2
    //     if (e1.getNext() == e2) {

    //         // connect node before e1 to e2
    //         if (e1b == null) {
    //             head = e2;           // e1 was head
    //         } else {
    //             e1b.setNext(e2);
    //         }

    //         e1.setNext(e2.getNext());
    //         e2.setNext(e1);

    //         // if e2 used to be tail, e1 is now tail
    //         if (tail == e2) {
    //             tail = e1;
    //         }
    //     }

    //     // e2 immediately before e1
    //     else if (e2.getNext() == e1) {

    //         if (e2b == null) {
    //             head = e1;           // e2 was head
    //         } else {
    //             e2b.setNext(e1);
    //         }

    //         e2.setNext(e1.getNext());
    //         e1.setNext(e2);

    //         if (tail == e1) {
    //             tail = e2;
    //         }
    //     }

    //     // non-adjacent
    //     else {
    //         Node<E> e1a = e1.getNext();
    //         Node<E> e2a = e2.getNext();

    //         // point e1's previous node to e2
    //         if (e1b == null) { //e1 is head
    //             head = e2;
    //         } else {
    //             e1b.setNext(e2);
    //         }

    //         // point e2's previous node to e1
    //         if (e2b == null) {      // e2 is head
    //             head = e1;
    //         } else {
    //             e2b.setNext(e1);
    //         }

    //         e2.setNext(e1a);
    //         e1.setNext(e2a);

    //         // update tail if necessary
    //         if (tail == e1) {
    //             tail = e2;
    //         } else if (tail == e2) {
    //             tail = e1;
    //         }
    //     }

    // }



    public static void main(String[] args) {

        // Test 1: odd number of elements
        SinglyLinkedList<Integer> list1 = new SinglyLinkedList<>();
        list1.addLast(5);
        list1.addLast(3);
        list1.addLast(2);
        list1.addLast(4);
        list1.addLast(1);

        System.out.println("Test 1");
        System.out.println("Before: " + list1);
        list1.swap();
        System.out.println("After:  " + list1);
        System.out.println("Expected: 1 3 4 2 5");
        System.out.println();


        // Test 2: even number of elements
        SinglyLinkedList<Integer> list2 = new SinglyLinkedList<>();
        list2.addLast(4);
        list2.addLast(1);
        list2.addLast(3);
        list2.addLast(2);

        System.out.println("Test 2");
        System.out.println("Before: " + list2);
        list2.swap();
        System.out.println("After:  " + list2);

        // Pairwise:
        // min 1 <-> max 4
        // then min 2 <-> max 3
        // 4 1 3 2
        // -> 1 4 3 2
        // -> 1 4 2 3
        System.out.println("Expected: 1 4 2 3");
        System.out.println();


        // Test 3: two elements
        SinglyLinkedList<Integer> list3 = new SinglyLinkedList<>();
        list3.addLast(2);
        list3.addLast(1);

        System.out.println("Test 3");
        System.out.println("Before: " + list3);
        list3.swap();
        System.out.println("After:  " + list3);
        System.out.println("Expected: 1 2");
        System.out.println();


        // Test 4: one element
        SinglyLinkedList<Integer> list4 = new SinglyLinkedList<>();
        list4.addLast(10);

        System.out.println("Test 4");
        System.out.println("Before: " + list4);
        list4.swap();
        System.out.println("After:  " + list4);
        System.out.println("Expected: 10");
        System.out.println();


        // Test 5: empty list
        SinglyLinkedList<Integer> list5 = new SinglyLinkedList<>();

        System.out.println("Test 5");
        System.out.println("Before: " + list5);
        list5.swap();
        System.out.println("After:  " + list5);
        System.out.println("Expected: empty");
        System.out.println();


        // Test 6: min/max are adjacent
        SinglyLinkedList<Integer> list6 = new SinglyLinkedList<>();
        list6.addLast(3);
        list6.addLast(1);
        list6.addLast(5);
        list6.addLast(4);
        list6.addLast(2);

        System.out.println("Test 6");
        System.out.println("Before: " + list6);
        list6.swap();
        System.out.println("After:  " + list6);
        System.out.println();
    
    }
   
}

