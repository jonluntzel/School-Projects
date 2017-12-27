// Jon Luntzel
// 11/30/17
// CSE143
// TA: Gavin Cai
// Assignment #8
// A specific tree node class that can construct a node with some given data for our Huffman Node

public class HuffmanNode implements Comparable<HuffmanCode>(){
   public int frequency;       // data stored in this node
   public HuffmanNode left;  // link to left node in the list
   public HuffmanNode right;  // link to right node in the list
   
   //constructs a node with given data and null link
   public HuffmanNode(String data) {
      this.data = data;
      left = null;
      right = null;
   }
   
   public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
      this.frequency = frequency;
      this.left = left;
      this.right = right;
    } 
   
   public int compareTo(HuffmanNode other) {
        if (frequency == other.frequency) {
            return frequency - other.frequency;
        }
   }
}

