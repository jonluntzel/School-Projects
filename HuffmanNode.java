// Jon Luntzel
// 12/07/17
// CSE143
// TA: Gavin Cai
// Assignment #8
// A specific tree node class that can construct a node with some given frequency for our
// Huffman Node

public class HuffmanNode implements Comparable<HuffmanNode>{
   public int frequency;       // number of occurrences of a letter
   public int letter;          // numerical representation of a letter
   public HuffmanNode left;    // link to left node in the list
   public HuffmanNode right;   // link to right node in the list
   
   // constructs a node with given frequency and null link
   public HuffmanNode(int frequency) {
      this(frequency, null, null);
   }
   
   // constructs a node with given frequency alongside left and right node values
   public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
      this.frequency = frequency;
      this.left = left;
      this.right = right;
   }
   
   // returns true if node has a corresponding left node, and false if not
   public boolean hasLeft() {
      if (this.left != null) {
         return true;
      }
      return false;
   }
   
   // returns true if node has a corresponding right node, and false if not
   public boolean hasRight() {
      if (this.right != null) {
         return true;
      }
      return false;
   }
   
   // takes in another Huffman Node and returns a positive integer value if the node being called
   // on has a higher frequency. It returns negative if lower, and zero if they are equal.
   public int compareTo(HuffmanNode other) {
         return this.frequency - other.frequency;
   }
}


