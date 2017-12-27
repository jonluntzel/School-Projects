// Jon Luntzel
// 11/15/17
// CSE143
// TA: Gavin Cai
// Assignment #6
// Finds word combinations that use letters from a given phrase and searches through
// a given dictionary to find said words. 
import java.util.*;

public class AnagramSolver {
   private List<String> combos;
   private List<String> dictionary;
   private Map<String, LetterInventory> inventories;
   
   // Takes in a list of words as its parameter. Constructs an anagram solver that uses
   // the list as its dictionary of possible words.
   public AnagramSolver(List<String> list) {
      dictionary = list;
      inventories = new HashMap<String, LetterInventory>();
      for (String word : list) {
         inventories.put(word, new LetterInventory(word));
      }
   }
   
   // Takes in a string for the word of interest to create all possible combinations, through
   // of words from the dictionary that are anagrams of said string. Combinations must be limited
   // to max words, where max is an integer parameter. If max is 0 then there is no limit. Throws
   // an IllegalArgumentException if max is negative. Prints all word combinations.
   public void print(String s, int max) {
      if (max < 0) {
         throw new IllegalArgumentException();
      }
      LetterInventory stringLetters = new LetterInventory(s);
      List<String> pruned = new ArrayList<String>();
      List<String> combos = new ArrayList<String>();
      for (String word : dictionary) {
         if (stringLetters.subtract(inventories.get(word)) != null) {
            pruned.add(word);
         }
      }
      print(max, stringLetters, pruned, combos);
   }
   
   // Helper method that takes in an int max, a letterinventory of letters, a pruned dictionary, 
   // and an empty list for word combinations in order to sort through a dictionary and find 
   // relevant words until a word combination is ready to print. Prints all word combinations.
   private void print(int max, LetterInventory letters, List<String> pruned, List<String> combos) {
      if (letters.isEmpty() && (max == 0 || combos.size() <= max)) {
         System.out.println(combos);
      } else {
         for (String word : pruned) {
            if (letters.subtract(inventories.get(word)) != null) {
               combos.add(word);
               print(max, letters.subtract(inventories.get(word)), pruned, combos);
               combos.remove(word);
            }
         }
      }
   }
}
