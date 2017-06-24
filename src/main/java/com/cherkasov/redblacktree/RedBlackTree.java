package com.cherkasov.redblacktree;

/**
 * Created by hawk on 19.03.2017.
 */


/**
 * Implements a red-black tree.
 * Note that all "matching" is based on the compareTo method.
 *
 * @author Mark Allen Weiss
 */
public class RedBlackTree {
    /**
     * Construct the tree.
     */
    public RedBlackTree() {

        header = new Node(Integer.MIN_VALUE);
        header.left = header.right = EMPTY;
    }

    /**
     * Compare item and t.element, using compareTo, with
     * caveat that if t is header, then item is always larger.
     * This routine is called if is possible that t is header.
     * If it is not possible for t to be header, use compareTo directly.
     */
    private final int compare(int item, Node t) {

        if (t == header) return 1;
        else return item - t.element;
    }

    /**
     * Insert into the tree.
     *
     * @param item the item to insert.
     * @throws //DuplicateItemException if item is already present.
     */
    public void insert(int item) {

        current = parent = grand = header;
        EMPTY.element = item;

        while (compare(item, current) != 0) {
            great = grand;
            grand = parent;
            parent = current;
            current = compare(item, current) < 0 ? current.left : current.right;

            // Check if two red children; fix if so
            if (current.left.color == Color.RED && current.right.color == Color.RED) reorient(item);
        }

        // Insertion fails if already present
        if (current != EMPTY) return; //todo check
        //throw new DuplicateItemException( item.toString( ) );
        current = new Node(item, EMPTY, EMPTY);

        // Attach to parent
        if (compare(item, parent) < 0) parent.left = current;
        else parent.right = current;
        reorient(item);
    }

    /**
     * Remove from the tree.
     *
     * @param x the item to remove.
     * @throws UnsupportedOperationException if called.
     */
    public void remove(int x) {

        throw new UnsupportedOperationException();
    }

    /**
     * Find the smallest item  the tree.
     *
     * @return the smallest item or null if empty.
     */
    public int findMin() {

        if (isEmpty()) return 0;

        Node itr = header.right;

        while (itr.left != EMPTY) itr = itr.left;

        return itr.element;
    }

    /**
     * Find the largest item in the tree.
     *
     * @return the largest item or null if empty.
     */
    public int findMax() {

        if (isEmpty()) return 0;

        Node itr = header.right;

        while (itr.right != EMPTY) itr = itr.right;

        return itr.element;
    }

    /**
     * Find an item in the tree.
     *
     * @param x the item to search for.
     * @return the matching item or null if not found.
     */
    public int find(int x) {

        EMPTY.element = x;
        current = header.right;

        for (; ; ) {
            if (x - current.element < 0) current = current.left;
            else if (x - current.element > 0) current = current.right;
            else if (current != EMPTY) return current.element;
            else return 0;
        }
    }

    /**
     * Make the tree logically empty.
     */
    public void clear() {

        header.right = EMPTY;
    }

    /**
     * Print all items.
     */
    public void printTree() {

        printTree(header.right);
    }

    /**
     * Internal method to print a subtree in sorted order.
     *
     * @param t the node that roots the tree.
     */
    private void printTree(Node t) {

        if (t != EMPTY) {
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    /**
     * Test if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {

        return header.right == EMPTY;
    }

    /**
     * Internal routine that is called during an insertion
     * if a node has two red children. Performs flip and rotations.
     *
     * @param item the item being inserted.
     */
    protected void reorient(int item) {
        // Do the color flip
        current.color = Color.RED;
        current.left.color = Color.BLACK;
        current.right.color = Color.BLACK;

        if (parent.color == Color.RED)   // Have to rotate
        {
            grand.color = Color.RED;
            if ((compare(item, grand) < 0) != (compare(item, parent) < 0))
                parent = rotate(item, grand);  // Start dbl rotate
            current = rotate(item, great);
            current.color = Color.BLACK;
        }
        header.right.color = Color.BLACK; // Make root black
    }

    /**
     * Internal routine that performs a single or double rotation.
     * Because the result is attached to the parent, there are four cases.
     * Called by reorient.
     *
     * @param item   the item in reorient.
     * @param parent the parent of the root of the rotated subtree.
     * @return the root of the rotated subtree.
     */
    private Node rotate(int item, Node parent) {

        if (compare(item, parent) < 0)
            return parent.left = compare(item, parent.left) < 0 ? rotateWithLeftNode(parent.left) :  // LL
                    rotateWithRightNode(parent.left);  // LR
        else return parent.right = compare(item, parent.right) < 0 ? rotateWithLeftNode(parent.right) :  // RL
                rotateWithRightNode(parent.right);  // RR
    }

    /**
     * Rotate binary tree node with left child.
     */
    private static Node rotateWithLeftNode(Node k2) {

        Node k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        return k1;
    }

    /**
     * Rotate binary tree node with right child.
     */
    private static Node rotateWithRightNode(Node k1) {

        Node k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        return k2;
    }

    @Override
    public String toString() {

        return "RedBlackTree{" + "current=" + current + ", parent=" + parent + ", grand=" + grand + ", great=" + great + ", header=" + header + '}';
    }

    public static class Node {
        // Constructors
        Node(int theElement) {

            this(theElement, null, null);
        }

        Node(int theElement, Node lt, Node rt) {

            element = theElement;
            left = lt;
            right = rt;
            color = Color.BLACK;
        }

        private int element;    // The data in the node
        private Node left;       // Left child
        private Node right;      // Right child
        private Color color;      // Color



        @Override
        public String toString() {

            return "Node{" + "element=" + Integer.toString(element) + ", left=" + left.element + ", right=" + right.element + ", color=" + color + '}';
        }
    }

    public static enum Color {
        BLACK, RED
    }

    private Node header;
    private static final Node EMPTY;

    static         // Static initializer for nullNode
    {
        EMPTY = new Node(0);
        EMPTY.left = EMPTY.right = EMPTY;
    }

    // private static final int BLACK = 1;    // Black must be 1
    // private static final int RED = 0;

    // Used in insert routine and its helpers
    protected Node current;
    private Node parent;
    private Node grand;
    private Node great;



}
// Test program
  /*  public static void main( String [ ] args ) {
        RedBlackTree t = new RedBlackTree( );
        final int NUMS = 400000;
        final int GAP  =  35461;

        System.out.println( "Checking... (no more output means success)" );

        for( int i = GAP; i != 0; i = ( i + GAP ) % NUMS )
            t.insert( new Integer( i ) );

        if( ((Integer)(t.findMin( ))).intValue( ) != 1 ||
                ((Integer)(t.findMax( ))).intValue( ) != NUMS - 1 )
            System.out.println( "FindMin or FindMax error!" );

        for( int i = 1; i < NUMS; i++ )
            if( ((Integer)(t.find( new Integer( i ) ))).intValue( ) != i )
                System.out.println( "Find error1!" );
    }
}*/


/**
 * Exception class for duplicate item errors
 * in search tree insertions.
 *
 * @author Mark Allen Weiss
 * <p>
 * Construct this exception object.
 * <p>
 * Construct this exception object.
 * @param message the error message.
 */
/* class DuplicateItemException extends RuntimeException
{
    *//**
 * Construct this exception object.
 *//*
    public DuplicateItemException( )
    {
        super( );
    }
    *//**
 * Construct this exception object.
 * @param message the error message.
 *//*
    public DuplicateItemException( String message )
    {
        super( message );
    }
}*/
