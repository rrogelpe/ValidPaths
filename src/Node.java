/* File name : Node.java */
/**
 * This program represents a node comprising a graph. Its attributes include a
 * unique identifier and an adjacency list which specifies nodes adjacent to
 * this node.
 *
 * @author Rodrigo Rogel-Perez
 * @version 1.0
 * @since 2020-11-16
 */
public class Node {

   private final int id;
   private final List adjacentNodes;
   private Node next;

   /**
    * Class constructor.
    *
    * @param id Unique identifier
    */
   public Node(int id) {
      this.id = id;
      this.adjacentNodes = new List();
      this.next = null;
   }

   /**
    * Assign pointer to adjacent node.
    *
    * @param next Pointer to adjacent node.
    */
   public void setNext(Node next) {
      this.next = next;
   }

   /**
    * Get adjacent node.
    *
    * @return Adjacent node.
    */
   public Node getNext() {
      return this.next;
   }

   /**
    * Gets node's unique identifier.
    *
    * @return This node's unique identifier.
    */
   public int getID() {
      return this.id;
   }

   /**
    * Adds specified node to adjacent list.
    *
    * @param vertex Node to be added to adjacency list.
    */
   public void addAdjacentNode(Node vertex) {
      this.adjacentNodes.addLast(vertex);
   }

   /**
    * Gets the first node in the adjacency list.
    *
    * @return First node in this node's adjacency list
    */
   public Node getAdjacentNodes() {
      return this.adjacentNodes.getHead();
   }

   /**
    * Indicates whether the specified node is an adjacent node.
    *
    * @param vertex A separate node.
    * @return Boolean indicating whether specified node is adjacent to this
    * node.
    */
   public boolean isAdjacentTo(Node vertex) {
      if (this.adjacentNodes.isEmpty()) {
         return false;
      } else {
         Node curr = this.getAdjacentNodes();
         // Compare specified node's ID with ID of nodes in adjacency list
         while (curr != null && curr.getID() != vertex.getID()) {
            curr = curr.getNext();
         }
         return (curr == null ? false : (curr.getID() == vertex.getID()));
      }
   }

   /**
    * Gets the number of adjacent nodes.
    *
    * @return Number of adjacent nodes to this node as an integer
    */
   public int numAdjacentNodes() {
      return this.adjacentNodes.getSize();
   }
}
