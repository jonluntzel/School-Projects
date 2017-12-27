// Jon Luntzel
// 12/07/17
// CSE143
// TA: Gavin Cai
// Assignment #8
// A HuffmanTree class that can build a 'Huffman tree', which uses a coding tree to create an
// efficient encoding of values that are placed inside it. The class can build a tree from a given
// structure of values, and it can build a tree from a given file. HuffmanTree also can read from
// an input stream and write the information into an output.

import java.io.*;
import java.util.*;

public class HuffmanTree {
   private HuffmanNode root;
   
   // Constructor that takes in an array of frequency values, or values denoting a character's
   // number of appearances. It then builds the initial Huffman tree
   public HuffmanTree(int[] count) {
      int length = count.length;
      Queue<HuffmanNode> letters = new PriorityQueue<HuffmanNode>();
      for (int i = 0; i < length; i++) {
         if (count[i] > 0) {
            HuffmanNode node = new HuffmanNode(count[i]);
            node.letter = i;
            letters.add(node);
         }
      }
      HuffmanNode eof = new HuffmanNode(1);
      eof.letter = length;
      letters.add(eof);
      root = HuffmanTree(letters);
   }
   
   // Helper class that takes in a queue of characters and their frequencies to build and
   // return a tree
   private HuffmanNode HuffmanTree(Queue<HuffmanNode> letters) {
      if (letters.size() == 1) {
         return letters.remove();
      }
      HuffmanNode left = letters.remove();
      HuffmanNode right = letters.remove();
      HuffmanNode sum = new HuffmanNode(right.frequency + left.frequency, left, right);
      letters.add(sum);
      return HuffmanTree(letters);
   }
   
   // Constructor that takes in a scanner and rebuilds a tree from the associated file (assumes
   // the tree is stored in standard format)
   public HuffmanTree(Scanner input) {
      int firstLetter = Integer.parseInt(input.nextLine());
      String firstCode = input.nextLine();
      root = new HuffmanNode(-1);
      root = HuffmanTree(input, root, firstLetter, firstCode);
   }
   
   // Helper method that takes in a scanner, a HuffmanNode, an int value for a letter, and a
   // string denoting the order in which the tree would visit them
   private HuffmanNode HuffmanTree(Scanner input, HuffmanNode node, int letter, String code) {
      if (code.length() == 0) {
         node.letter = letter;
         if (input.hasNextLine()) {
            int newLetter = Integer.parseInt(input.nextLine());
            String newCode = input.nextLine();
            root = HuffmanTree(input, root, newLetter, newCode);
         }
      }
      if (code.length() != 0 && code.charAt(0) == '0') {
         if (!node.hasLeft()) {
            node.left = new HuffmanNode(-1);
         }
         node.left = HuffmanTree(input, node.left, letter, code.substring(1, code.length()));
      } else if (code.length() != 0 && code.charAt(0) == '1') {
         if (!node.hasRight()) {
            node.right = new HuffmanNode(-1);
         }
         node.right = HuffmanTree(input, node.right, letter, code.substring(1, code.length()));
      }
      return node;
   }
   
   // Takes in a PrintStream and uses it to write a tree into a file in standard format
   public void write(PrintStream output) {
      write(root, output, "");
   }
   
   // Helper method that takes in a HuffmanNode, a PrintStream, and a String to build a
   // tree in standard traversal order
   private void write(HuffmanNode node, PrintStream output, String s) {
      if (node.left == null && node.right == null) {
         output.println(node.letter);
         output.println(s);
      } else {
         write(node.left, output, s + "0");
         write(node.right, output, s + "1");
      }
   }
   
   // Takes in an input stream to read bits from, an output to write characters into,
   // and the end of file integer as a sign to stop reading and writing once it appears.
   // Assumes the input stream has a legal encoding of characters
   public void decode(BitInputStream input, PrintStream output, int eof) {
      HuffmanNode node = root;
      while (node.letter != eof) {
         while (node.left != null && node.right != null) {
            int bitValue = input.readBit();
            if (bitValue == 1) {
               node = node.right;
            } else {
               node = node.left;
            }
         }
         if (node.letter != eof) {
            output.write(node.letter);
            node = root;
         }
      }
   }
}