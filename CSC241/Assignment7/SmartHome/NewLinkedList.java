package SmartHome;

// TODO: Extra Work
//  Add more methods:
//      remove():   extracts a node from linked list. removeFirst(), removeLast()
//      size():     returns size of linked list
//      get(index): returns a given indexed node
//  Enhancement: doubly linked list

public class NewLinkedList {
    Node headPtr;

    class Node {
        Node next;
        Object data;        // Choose any datatype

        Node(Object data) {
            this.data = data;
            next = null;
        }

        public Node getNext() { return next;}
        public Object getData() { return data;}

        public void setNext(Node next) { this.next = next; }
        public void setData(Object data) { this.data = data;}
    }

    NewLinkedList () {
        headPtr = null;
    }

    public void traverse() {
        Node p = headPtr;

        while (p != null) {
            p = p.getNext();
        }
    }

    public void insertFirst(Object data) {
        Node newNode = new Node(data);
        newNode.setNext(headPtr);
        headPtr = newNode;
    }

    public void insertLast(Object data) {
        Node newNode = new Node(data);
        if(headPtr == null) { headPtr = newNode; }
        else {
            Node p = headPtr;
            while (p.getNext() != null) {p = p.getNext(); }
            p.setNext(newNode);
        }
    }

    public void deleteFirst() {
        if(headPtr != null) headPtr = headPtr.getNext();
    }

    public void deleteLast() {
        if(headPtr == null) return;
        if(headPtr.getNext() == null) {
            headPtr = null;
            return;
        }
        Node p = headPtr;
        while (p.getNext().getNext() != null) p = p.getNext();
        p.setNext(null);
    }
}
