// Jon Luntzel
// 10/5/17
// CSE143
// TA: Gavin Cai
// Assignment #1
// LetterInventory constructs an inventory that stores the counts of each letter in a given 
// string, and lets the user add letters or find the sum and difference between inventories

public class LetterInventory {
   //base capacity for inventories
   public final static int COUNTERS = 26;
   
   private int[] inventory;
   private char letter;
   private int size;
   
   // pre: takes in a specific phrase for a parameter
   // post: constructs an 'inventory' that will be used to store counts of each letter
   public LetterInventory(String data) { 
      data = data.toUpperCase();
      inventory = new int[COUNTERS];
      for (int i = 0; i < data.length(); i++) {
         letter = data.charAt(i);
         if (letter >= 'A' && letter <= 'Z') {
            inventory[letter - 'A']++;
            size++;
         }
      }
   }
   
   // pre: takes in a letter
   // post: makes sure the parameter is a letter, otherwise throwing an illegal argument exception
   // returns the number of said letter in the specific phrase
   public int get(char letter) { 
      letter = Character.toUpperCase(letter);
      if (letter < 'A' || letter > 'Z') {
         throw new IllegalArgumentException();
      }
      return inventory[letter - 'A'];
   }
   
   // pre: takes in a letter and value
   // post: throws an illegal argument exception if the value is negative, or a letter was not
   // passed. Sets the value at the index corresponding to the letter
   public void set(char letter, int value) {
      letter = Character.toUpperCase(letter);
      if (value < 0 || letter < 'A' || letter > 'Z') {
         throw new IllegalArgumentException();
      }
      size += value - inventory[letter - 'A'];
      inventory[letter - 'A'] = value;
      
   }
   
   // returns the size
   public int size() {
      return size;
   }
   
   // returns whether the inventory is empty or not
   public boolean isEmpty() {
      return size == 0;
   }
   
   // returns an alphabetical string representation of letters in the specific phrase
   public String toString() {
      String s = "[";
      for (int i = 0; i < inventory.length; i++) {
         for (int j = 0; j < inventory[i]; j++) {
            s += Character.toLowerCase((char)('A' + i));
         }
      }
      return s + "]";
   }
   
   // pre: takes in an inventory to add to the initial letter inventory
   // post: adds the values at each index and places them in a sum inventory to return
   public LetterInventory add(LetterInventory other) { 
      LetterInventory summation = new LetterInventory("");
      summation.size = other.size + size;
      for (int i = 0; i < COUNTERS; i++) {
         summation.inventory[i] = inventory[i] + other.inventory[i];
      }
      return summation;
   }
   
   // pre: takes in an inventory to subtract from the intial letter inventory
   // post: subtracts the values at each index (returning a null value if the result is negative)
   // and places them in a difference inventory to return
   public LetterInventory subtract(LetterInventory other) { 
      LetterInventory diff = new LetterInventory("");
      for (int i = 0; i < COUNTERS; i++) {
         if (inventory[i] - other.inventory[i] < 0) {
            return null;
         }
         diff.size += inventory[i] - other.inventory[i];
         diff.inventory[i] = inventory[i] - other.inventory[i];
      }
      return diff;
   }
}

