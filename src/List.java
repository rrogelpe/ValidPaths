/* File name : List.java */
/**
 * This program represents a linked list ADT. The list stores nodes comprising  
 * a graph and has been modified to find the number of non-looping paths from 
 * the source to the sink node as well as the paths themselves.
 * 
 * @author Rodrigo Rogel-Perez
 * @version 1.0
 * @since 2020-11-16
 */
public class List {

   private Node head;
   private Node tail;
   private int size;
   private String allPaths;

   /**
    * Class constructor.
    */
   public List() {
      this.head = null;
      this.tail = null;
      this.size = 0;
      this.allPaths = "";
   }

   /**
    * Adds specified node to list.
    *
    * @param vertex Node to be added to this list
    */
   public void addLast(Node vertex) {
      if (this.size == 0) {
         this.head = vertex;
         this.tail = vertex;
      } else {
         this.tail.setNext(vertex);
         this.tail = vertex;
      }

      this.size++;
   }

   /**
    * Removes specified node from the list.
    *
    * @param vertex Node to be removed from this list
    * @return
    */
   public boolean remove(Node vertex) {
      if (isEmpty()) {
         System.out.println("Error: Can not remove from empty list!");
         return false;
      }
      Node previous = this.head;
      Node succ = this.head.getNext();
      // Look the list until for a node with a matching id
      while (succ != null && succ.getID() != vertex.getID()) {
         previous = succ;
         succ = succ.getNext();
      }
      // End of list was reached and no matching id was found 
      if (succ == null) {
         System.out.println("Non-Existing Node Exception!");
         return false;
      } else {
         previous.setNext(succ.getNext());
         succ.setNext(null);
         this.size--;
      }

      return true;
   }
   /**
    * Removes node at specified index from the list.
    * 
    * @param index Position of node to be removed from this list.
    * @return 
    */
   public boolean remove(int index) {
      if (isEmpty()) {
         System.out.println("Error: Can not remove from empty list!");
         return false;
      }
      else if (index > getSize()) {
         System.out.println("Specified index is greater than list size!");
         return false;
      }
      else if (index <= 0) {
         System.out.println("Specified index cannot be less than one!");
         return false;
      }
      else if (index == 1) {
         Node tmp = this.head;
         this.head = tmp.getNext();
         tmp.setNext(null);
         
      } else {
         Node prev = this.head;
         Node succ = this.head.getNext();
         int counter = 2;
         // Look the list until for a node with a matching id
         while (counter < index) {
            prev = succ;
            succ = succ.getNext();
            counter++;
         }
         prev.setNext(succ.getNext());
         succ.setNext(null);
      }
      
      this.size--;

      return true;
   }
   
   /**
    * Deletes all nodes in the list.
    */
   public void removeAll() {
      while(!isEmpty()) {
         this.remove(1);
      }
      this.allPaths = "";
   }

   /**
    * Gets the node in the specified position.
    *
    * @param index Position of node to be fetched
    * @return Node at specified index
    */
   public Node get(int index) {
      if (isEmpty()) {
         System.out.println("Empty List Exception!");
         return null;
      } else if (index > getSize()) {
         System.out.println("Specified index is greater than list size!");
         return null;
      } else if (index <= 0) {
         System.out.println("Specified index cannot be less than one!");
         return null;
      } else {
         Node curr = this.head;
         int count = 1;

         while (count != index) {
            curr = curr.getNext();
            count++;
         }
         return curr;
      }
   }

   /**
    * Gets the size of the list.
    *
    * @return Size of this list as an integer
    */
   public int getSize() {
      return this.size;
   }

   /**
    * Indicates whether the list is empty.
    *
    * @return Boolean
    */
   public boolean isEmpty() {
      return this.size == 0;
   }

   /**
    * Gets the head node of the list.
    *
    * @return This list's head node
    */
   public Node getHead() {
      return this.head;
   }

   /**
    * Finds all non-looping paths between all possible pairs of nodes.
    */
   public void findAllPaths() {
      if (isEmpty()) {
         System.out.println("No paths in Empty List!");
      } else {
         Node source = this.head;
         // Loop through the list, each node is a source node
         while (source != null) {
            Node sink = this.head;
            // Inner loop, each node is a sink node
            while (sink != null) {
               System.out.println("DFS(Source = " + source.getID() + ", Sink = " + sink.getID() + ")");
               this.allPaths += "DFS(Source = " + source.getID() + ", Sink = " + sink.getID() + ")\n";
               dfs(source, sink); // Depth First Search algorithm
               sink = sink.getNext();
            }
            source = source.getNext();
         }
      }
   }

   /**
    * Implementation of "Depth First Search" algorithm to find all non-looping
    * paths from the source node to the sink node.
    *
    * @param source Starting node
    * @param sink Destination node
    */
   private void dfs(Node source, Node sink) {
      // Array to keep track of which vertices have been visited
      boolean hasVisited[] = new boolean[getSize()];
      // Queue to store visited vertices
      Deque pathQ = new Deque(getSize() + 1);
      // Add 'root' vertex. This is the starting vertex
      pathQ.addLast(source.getID());
      // Find neighbors of root vertex to kick-off DFS
      int count = visitNeighbors(source, sink, hasVisited, pathQ);

      if (count == 0) {
         System.out.println("No path from " + pathQ.peek()
            + " to " + (sink.getID()));
         this.allPaths += "No path from " + pathQ.peek()
            + " to " + (sink.getID()) + "\n";
      }
   }

   /**
    *
    * @param source Starting node
    * @param sink Destination node
    * @param hasVisited Array to keep track of visited nodes
    * @param pathQ Deque to store current path
    *
    * @return integer representing the number of non-looping paths
    */
   private int visitNeighbors(Node source, Node sink, boolean[] hasVisited,
      Deque pathQ) {

      int count = 0; // number of paths countera
      hasVisited[source.getID() - 1] = true; // Mark visited vertex

      // A path was found. Print the path
      if (source.getID() == sink.getID() && pathQ.getSize() > 1) {
         System.out.println("Path: " + pathQ.toString());
         this.allPaths += "Path: " + pathQ.toString() + "\n";
         return 1;
      }

      // Explore the adjacent vertices to the current vertex.
      // This is done recursively until the destination vertex is found or until
      // all vertices within the starting vertex's reach have been explored.
      for (int i = 0; i < getSize(); i++) {
         if (source.isAdjacentTo(new Node(i + 1))) {
            // Vertex has not been visited
            if (!hasVisited[i]) {
               pathQ.addLast(i + 1);
               count += visitNeighbors(get(i + 1), sink, hasVisited, pathQ);

               pathQ.popLast(); // Undo current path
            } else {
               // This conditional makes it possible to indentify paths comprised 
               // of the same two vertices
               if (sink.getID() == (i + 1)) {
                  pathQ.addLast(i + 1);
                  count += visitNeighbors(get(i + 1), sink, hasVisited, pathQ);

                  pathQ.popLast();
               }
            }
         }
      }
      // Mark as 'not visited' to find another path to the same destination
      hasVisited[source.getID() - 1] = false;

      return count;
   }

   /**
    * Get the string representation of all non-looping paths.
    *
    * @return String containing all non-looping paths
    */
   public String allPathsStr() {
      return this.allPaths;
   }
} // End of List()
