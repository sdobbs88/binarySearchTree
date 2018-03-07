/**
 * Class: CSCI2410 Data Structures and Algorithms
 * Instructor: Y. Daniel Liang
 * Description: Modified a current version of a binary search tree to add
 * and displays the in order, pre order and post order lists depending on a 
 * action event of a user pressing a button. 
 * Due: 10/24/2016
 *
 * @author Shaun C. Dobbs
 * @version 1.0
 *
 * I pledge by honor that I have completed the programming assignment
 * independently. I have not copied the code from a student or any source. I
 * have not given my code to any student. * Sign here: Shaun C. Dobbs
 */
import java.util.Collection;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class Exercise25_13 extends Application {

    @Override // Override the start method in the Application class
    public void start(Stage primaryStage) {
        BST<Integer> tree = new BST<>(); // Create a tree

        BorderPane pane = new BorderPane();
        BTView view = new BTView(tree); // Create a View
        pane.setCenter(view);

        TextField tfKey = new TextField();
        tfKey.setPrefColumnCount(3);
        tfKey.setAlignment(Pos.BASELINE_RIGHT);
        Button btInsert = new Button("Insert");
        Button btDelete = new Button("Delete");
        Button btInorder = new Button("Show Inorder");
        Button btPreorder = new Button("Show Preorder");
        Button btPostorder = new Button("Show Postorder");
        Label lblResult = new Label();
        HBox hBox = new HBox(5);
        HBox hBox0 = new HBox(5);
        hBox.getChildren().addAll(new Label("Enter a key: "),
                tfKey, btInsert, btDelete, btInorder, btPreorder, btPostorder);
        hBox0.getChildren().addAll(lblResult);
        hBox.setAlignment(Pos.CENTER);
        hBox0.setAlignment(Pos.CENTER);
        pane.setBottom(hBox);
        pane.setTop(hBox0);

        btInsert.setOnAction(e -> {
            int key = Integer.parseInt(tfKey.getText());
            if (tree.search(key)) { // key is in the tree already
                view.displayTree();
                view.setStatus(key + " is already in the tree");
            } else {
                tree.insert(key); // Insert a new key
                view.displayTree();
                view.setStatus(key + " is inserted in the tree");
                tfKey.setText("");
            }
        });

        btDelete.setOnAction(e -> {
            int key = Integer.parseInt(tfKey.getText());
            if (!tree.search(key)) { // key is not in the tree
                view.displayTree();
                view.setStatus(key + " is not in the tree");
            } else {
                tree.delete(key); // Delete a key
                view.displayTree();
                view.setStatus(key + " is deleted from the tree");
                tfKey.setText("");
            }
        });

        //Sort the list by inOrder
        btInorder.setOnAction(e -> {
            String inOrder = "";
            inOrder = tree.inOrderStrBuilder(inOrder, tree.root);
            
            lblResult.setText(inOrder);
            
            
        });
        
        btPreorder.setOnAction(e -> {
            String preOrder = "";
            preOrder = tree.preOrderStrBuilder(preOrder, tree.root);
            
            lblResult.setText(preOrder);

        });
        
        btPostorder.setOnAction(e -> {
            String postOrder = "";
            postOrder = tree.postOrderStrBuilder(postOrder, tree.root);
            
            lblResult.setText(postOrder);

        });
        
        
        // Create a scene and place the pane in the stage
        Scene scene = new Scene(pane, 550, 250);
        primaryStage.setTitle("Exercise 25_13"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    /**
     * The main method is only needed for the IDE with limited JavaFX support.
     * Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}

class BST<E extends Comparable<E>> implements Tree<E> {
    
    String inOrderResult = "";
    String preOrderResult = "";
    String postOrderResult = "";

    protected TreeNode<E> root;
    protected int size = 0;

    /**
     * Create a default binary tree
     */
    public BST() {
    }
    

    /**
     * Create a binary tree from an array of objects
     */
    public BST(E[] objects) {
        for (int i = 0; i < objects.length; i++) {
            add(objects[i]);
        }
    }

    @Override
    /**
     * Returns true if the element is in the tree
     */
    public boolean search(E e) {
        TreeNode<E> current = root; // Start from the root

        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                current = current.left;
            } else if (e.compareTo(current.element) > 0) {
                current = current.right;
            } else // element matches current.element
            {
                return true; // Element is found
            }
        }

        return false;
    }

    @Override
    /**
     * Insert element o into the binary tree Return true if the element is
     * inserted successfully
     */
    public boolean insert(E e) {
        if (root == null) {
            root = createNewNode(e); // Create a new root
        } else {
            // Locate the parent node
            TreeNode<E> parent = null;
            TreeNode<E> current = root;
            while (current != null) {
                if (e.compareTo(current.element) < 0) {
                    parent = current;
                    current = current.left;
                } else if (e.compareTo(current.element) > 0) {
                    parent = current;
                    current = current.right;
                } else {
                    return false; // Duplicate node not inserted
                }
            }
            // Create the new node and attach it to the parent node
            if (e.compareTo(parent.element) < 0) {
                parent.left = createNewNode(e);
            } else {
                parent.right = createNewNode(e);
            }
        }

        size++;
        return true; // Element inserted successfully
    }

    protected TreeNode<E> createNewNode(E e) {
        return new TreeNode<>(e);
    }

    @Override
    /**
     * Inorder traversal from the root
     */
    public void inorder() {
        inorder(root);
    }
    protected String inOrderStrBuilder(String str, TreeNode<E> root){  
        inOrderResult = "";
        str = inorder(root);
        return str;
    }

    /**
     * Inorder traversal from a subtree
     */
    protected String inorder(TreeNode<E> root) {
        String empty = "Binary Tree has no elements";
        if (root == null) {
            return empty;
        }
        inorder(root.left);
        inOrderResult = inOrderResult + root.element.toString() + " ";
        inorder(root.right);
        
        return inOrderResult;
    }

    @Override
    /**
     * Postorder traversal from the root
     */
    public void postorder() {
        postorder(root);
    }
    
    //postOrderStrBuilder
    
    protected String postOrderStrBuilder(String str, TreeNode<E> root){  
        postOrderResult = "";
        str = postorder(root);
        return str;
    }
    

    /**
     * Postorder traversal from a subtree
     */
    protected String postorder(TreeNode<E> root) {
        String empty = "Binary Tree has no elements";
        if (root == null) {
            return empty;
        }
        postorder(root.left);
        postorder(root.right);
        postOrderResult = postOrderResult + root.element.toString() + " ";
        
        return postOrderResult;
    }

    @Override
    /**
     * Preorder traversal from the root
     */
    public void preorder() {
        preorder(root);
    }
    
    protected String preOrderStrBuilder(String str, TreeNode<E> root){  
        preOrderResult = "";
        str = preorder(root);
        return str;
    }

    /**
     * Preorder traversal from a subtree
     */
    protected String preorder(TreeNode<E> root) {
        String empty = "Binary Tree has no elements";
        if (root == null) {
            return empty;
        }
        preOrderResult = preOrderResult + root.element.toString() + " ";
        preorder(root.left);
        preorder(root.right);
        
        return preOrderResult;
    }

    /**
     * This inner class is static, because it does not access any instance
     * members defined in its outer class
     */
    public static class TreeNode<E> {

        protected E element;
        protected TreeNode<E> left;
        protected TreeNode<E> right;

        public TreeNode(E e) {
            element = e;
        }
    }

    @Override
    /**
     * Get the number of nodes in the tree
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the root of the tree
     */
    public TreeNode<E> getRoot() {
        return root;
    }

    /**
     * Returns a path from the root leading to the specified element
     */
    public java.util.ArrayList<TreeNode<E>> path(E e) {
        java.util.ArrayList<TreeNode<E>> list
                = new java.util.ArrayList<>();
        TreeNode<E> current = root; // Start from the root

        while (current != null) {
            list.add(current); // Add the node to the list
            if (e.compareTo(current.element) < 0) {
                current = current.left;
            } else if (e.compareTo(current.element) > 0) {
                current = current.right;
            } else {
                break;
            }
        }

        return list; // Return an array list of nodes
    }

    @Override
    /**
     * Delete an element from the binary tree. Return true if the element is
     * deleted successfully Return false if the element is not in the tree
     */
    public boolean delete(E e) {
        // Locate the node to be deleted and also locate its parent node
        TreeNode<E> parent = null;
        TreeNode<E> current = root;
        while (current != null) {
            if (e.compareTo(current.element) < 0) {
                parent = current;
                current = current.left;
            } else if (e.compareTo(current.element) > 0) {
                parent = current;
                current = current.right;
            } else {
                break; // Element is in the tree pointed at by current
            }
        }

        if (current == null) {
            return false; // Element is not in the tree
        }
        // Case 1: current has no left child
        if (current.left == null) {
            // Connect the parent with the right child of the current node
            if (parent == null) {
                root = current.right;
            } else if (e.compareTo(parent.element) < 0) {
                parent.left = current.right;
            } else {
                parent.right = current.right;
            }
        } else {
            // Case 2: The current node has a left child
            // Locate the rightmost node in the left subtree of
            // the current node and also its parent
            TreeNode<E> parentOfRightMost = current;
            TreeNode<E> rightMost = current.left;

            while (rightMost.right != null) {
                parentOfRightMost = rightMost;
                rightMost = rightMost.right; // Keep going to the right
            }

            // Replace the element in current by the element in rightMost
            current.element = rightMost.element;

            // Eliminate rightmost node
            if (parentOfRightMost.right == rightMost) {
                parentOfRightMost.right = rightMost.left;
            } else // Special case: parentOfRightMost == current
            {
                parentOfRightMost.left = rightMost.left;
            }
        }

        size--;
        return true; // Element deleted successfully
    }

    @Override
    /**
     * Obtain an iterator. Use inorder.
     */
    public java.util.Iterator<E> iterator() {
        return new InorderIterator();
    }

    // Inner class InorderIterator
    private class InorderIterator implements java.util.Iterator<E> {
        // Store the elements in a list

        private java.util.ArrayList<E> list
                = new java.util.ArrayList<>();
        private int current = 0; // Point to the current element in list

        public InorderIterator() {
            inorder(); // Traverse binary tree and store elements in list
        }

        /**
         * Inorder traversal from the root
         */
        private void inorder() {
            inorder(root);
        }

        /**
         * Inorder traversal from a subtree
         */
        private void inorder(TreeNode<E> root) {
            if (root == null) {
                return;
            }
            inorder(root.left);
            list.add(root.element);
            inorder(root.right);
        }

        @Override
        /**
         * More elements for traversing?
         */
        public boolean hasNext() {
            if (current < list.size()) {
                return true;
            }

            return false;
        }

        @Override
        /**
         * Get the current element and move to the next
         */
        public E next() {
            return list.get(current++);
        }

        @Override
        /**
         * Remove the current element
         */
        public void remove() {
            delete(list.get(current)); // Delete the current element
            list.clear(); // Clear the list
            inorder(); // Rebuild the list
        }
    }

    @Override
    /**
     * Remove all elements from the tree
     */
    public void clear() {
        root = null;
        size = 0;
    }
}

class BTView extends Pane {

    private BST<Integer> tree = new BST<>();
    private double radius = 15; // Tree node radius
    private double vGap = 50; // Gap between two levels in a tree

    BTView(BST<Integer> tree) {
        this.tree = tree;
        setStatus("Tree is empty");
    }

    public void setStatus(String msg) {
        getChildren().add(new Text(20, 20, msg));
    }

    public void displayTree() {
        this.getChildren().clear(); // Clear the pane
        if (tree.getRoot() != null) {
            // Display tree recursively   
            displayTree(tree.getRoot(), getWidth() / 2, vGap,
                    getWidth() / 4);
        }
    }

    /**
     * Display a subtree rooted at position (x, y)
     */
    private void displayTree(BST.TreeNode<Integer> root,
            double x, double y, double hGap) {
        if (root.left != null) {
            // Draw a line to the left node
            getChildren().add(new Line(x - hGap, y + vGap, x, y));
            // Draw the left subtree recursively
            displayTree(root.left, x - hGap, y + vGap, hGap / 2);
        }

        if (root.right != null) {
            // Draw a line to the right node
            getChildren().add(new Line(x + hGap, y + vGap, x, y));
            // Draw the right subtree recursively
            displayTree(root.right, x + hGap, y + vGap, hGap / 2);
        }

        // Display a node
        Circle circle = new Circle(x, y, radius);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        getChildren().addAll(circle,
                new Text(x - 4, y + 4, root.element + ""));
    }
}

interface Tree<E> extends java.util.Collection<E> {

    /**
     * Return true if the element is in the tree
     */
    public boolean search(E e);

    /**
     * Insert element o into the binary tree Return true if the element is
     * inserted successfully
     */
    public boolean insert(E e);

    /**
     * Delete the specified element from the tree Return true if the element is
     * deleted successfully
     */
    public boolean delete(E e);

    /**
     * Get the number of nodes in the tree
     */
    public int getSize();

    /**
     * Inorder traversal from the root
     */
    public default void inorder() {
    }

    /**
     * Postorder traversal from the root
     */
    public default void postorder() {
    }

    /**
     * Preorder traversal from the root
     */
    public default void preorder() {
    }

    @Override
    /**
     * Return true if the tree is empty
     */
    public default boolean isEmpty() {
        return size() == 0;
    }

    ;
 
  @Override
    public default boolean contains(Object e) {
        return search((E) e);
    }

    @Override
    public default boolean add(E e) {
        return insert(e);
    }

    @Override
    public default boolean remove(Object e) {
        return delete((E) e);
    }

    @Override
    public default int size() {
        return getSize();
    }

    @Override
    public default boolean containsAll(Collection<?> c) {
        // Left as an exercise
        return false;
    }

    @Override
    public default boolean addAll(Collection<? extends E> c) {
        // Left as an exercise
        return false;
    }

    @Override
    public default boolean removeAll(Collection<?> c) {
        // Left as an exercise
        return false;
    }

    @Override
    public default boolean retainAll(Collection<?> c) {
        // Left as an exercise
        return false;
    }

    @Override
    public default Object[] toArray() {
        // Left as an exercise
        return null;
    }

    @Override
    public default <T> T[] toArray(T[] array) {
        // Left as an exercise
        return null;
    }
}
