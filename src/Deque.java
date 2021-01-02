/* File name : Deque.java */
/**
 * This program is an array implementation of a Deque ADT.
 * Integers can be added or removed from either end of the structure.
 *
 * @author Rodrigo Rogel-Perez
 * @version 1.0
 * @since 2020-11-17
 */
public class Deque {

   private final int[] arrayQ;
   private int size; // Counts # of elements in the Deque
   private int head; // Points to the front-most element
   private int tail; // Points to the back-most element

   /**
    * Class constructor.
    *
    * @param maxSize Maximum Deque capacity
    */
   public Deque(int maxSize) {
      this.arrayQ = new int[maxSize];
      this.size = 0;
      this.head = -1;
      this.tail = 0;
   }

   /**
    * Adds the passed element to the front of the Deque.
    *
    * @param elem Element to be added
    */
   public void addFirst(int elem) {
      // If Deque is full, item is not inserted and user is notifed
      if (getSize() == this.arrayQ.length) {
         System.out.println("Deque is full. Item was not inserted");
         return;
      }

      this.size++;
      // Increment and 'wrap' index around array
      this.head = (this.head + 1) % this.arrayQ.length;

      this.arrayQ[this.head] = elem;
   }

   /**
    * Adds the passed element to the back of the Deque.
    *
    * @param elem Element to be added
    */
   public void addLast(int elem) {
      // If Deque is full, item is not inserted and user is notifed
      if (getSize() == this.arrayQ.length) {
         System.out.println("Deque is full. Item was not inserted");
         return;
      }

      this.size++;
      // Take mod of tail position to "wrap" around the array
      this.tail %= this.arrayQ.length;

      this.arrayQ[this.tail++] = elem; // Insert element to array
   }

   /**
    * Removes the last element in the Deque.
    *
    * @return This Deque's last element.
    */
   public int popLast() {
      // If Deque is empty, provide helpful message to user and terminate program
      if (isEmpty()) {
         throw new ArrayIndexOutOfBoundsException("Empty Deque Exception");
      }

      this.size--;
      // Take mod of tail position to "wrap" around the array
      this.tail = (this.tail - 1 + arrayQ.length) % arrayQ.length;

      return arrayQ[this.tail];
   }

   /**
    * Removes the first element in the Deque.
    *
    * @return This Deque's first element
    */
   public int popFirst() {
      // If Deque is empty, provide helpful message to user and terminate program
      if (isEmpty()) {
         throw new ArrayIndexOutOfBoundsException("Empty Deque Exception");
      }

      this.size--;

      this.head++;
      // Take mod of head index to "wrap" around the array
      this.head %= this.arrayQ.length;

      return arrayQ[this.head];
   }

   /**
    * Returns, but does not remove this Deque's front-most element.
    *
    * @return This Deque's front-most element
    */
   public int peek() {
      if (isEmpty()) {
         throw new ArrayIndexOutOfBoundsException("Empty Deque Exception");
      }

      return this.arrayQ[(this.head + 1) % this.arrayQ.length];
   }

   /**
    * Gets the number of elements in the Deque.
    *
    * @return Number of elements in this Deque
    */
   public int getSize() {
      return this.size;
   }

   /**
    * Indicates whether the Deque is empty i.e. contains no elements.
    *
    * @return True if Deque contains no elements, false otherwise
    */
   public boolean isEmpty() {
      return getSize() == 0;
   }

   /**
    * Gets string representation of the elements in this Deque in the order of
    * first to last.
    *
    * @return A string representation of this Deque's elements
    */
   @Override
   public String toString() {
      
      String strQ = "";
      int start = this.head + 1;
      
      // Loop through the array, adding each element to a string 
      for (int i = start; i < (getSize() + start); i++) {
         strQ += this.arrayQ[i % this.arrayQ.length];
      }
     
      return strQ;
   }
} // End of Deque()
