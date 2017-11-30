// Jon Luntzel
// 10/25/17
// CSE143
// TA: Gavin Cai
// Assignment #4
// Class HangmanManager tries to not pick a word until the computer has only one option to choose
// based on the letters that have been guessed. User input is managed in another class. The game
// state is being actively managed, keeping track of possible words based on word patterns.

import java.util.*;

public class HangmanManager {
   private Set<String> current;
   private SortedSet<Character> guessed;
   private int maximum;
   private String pattern;
   
   // This constructor takes in a collection of words, an int target word length for
   // the guess, and an int for the max number of wrong guesses that can be made. It should add
   // non-repeating words of target length to a set. Also throws an illegal argument exception if
   // the length is less than 1, or max is less than 0.
   public HangmanManager(Collection<String> dictionary, int length, int max) {
      if (length < 1 || max < 0) {
         throw new IllegalArgumentException();
      }
      pattern = "";
      maximum = max;
      current =  new TreeSet<String>();
      guessed = new TreeSet<Character>();
      for (String word : dictionary) {
         if (word.length() == length) {
            current.add(word);
         }
      }
      for (int i = 0; i < length; i++ ){
         pattern += "- ";
      }
      pattern = pattern.trim();
   }
   
   // Returns the current set of words that are plausible based on the users' guess.
   public Set<String> words() {
      return current;
   }
   
   // Returns the number of guesses remaining.
   public int guessesLeft() {
      return maximum;
   }
   
   // Returns the current set of letters that have been guessed.
   public SortedSet<Character> guesses() {
      return guessed;
   }
   
   // Returns the current pattern based on the guesses made. There are dashes where any unguessed
   // letter could be, and spaces separating the letters. Throws an illegal state exception if the
   // set of words is empty.
   public String pattern() {
      if (current.isEmpty()) {
         throw new IllegalStateException();
      }
      return pattern;
   }
   
   // Records the user's next guess. This guess eliminates many possible future sets of words and
   // uses the set corresponding to a possible pattern with the greatest number of words in it.
   // It returns the occurrences of the guessed letter in the new pattern and updates the guesses
   // remaining. Throws an illegal state exception if the number of guesses left is not at least 1,
   // or the set of words is empty. Throws an illegal argument exception if the character has
   // already been guessed.
   public int record(char guess) {
      if (guessesLeft() < 1 || current.isEmpty()) {
         throw new IllegalStateException();
      }
      if (guessed.contains(guess)) {
         throw new IllegalArgumentException();
      }
      int timesOccurred = 0;
      guessed.add(guess);
      Map<String, Set<String>> filter = new TreeMap<String, Set<String>>();
      filter = record(guess, filter);
      current = record(filter);
      for (int i = 0; i < pattern.length(); i++) {
         if (pattern.charAt(i) == guess) {
            timesOccurred++;
         }
      }
      if (!pattern.contains("" + guess)) {
         maximum--;
      }
      return timesOccurred;
   }
   
   // Private method helper that fills and returns a map with all possible patterns and their
   // corresponding words. Takes in a character and a map as parameters.
   private Map<String, Set<String>> record(char guess, Map<String, Set<String>> filterMap) {      
      filterMap = new TreeMap<String, Set<String>>();
      for (String s : current) {
         String newPattern = "";
         for (int i = 0; i < s.length(); i++) {
            if (pattern.charAt(i * 2) != '-') {
                  newPattern += " " + pattern.charAt(i * 2);
            }
            else if (s.charAt(i) == guess) {
               newPattern += " " + guess;
            }
            else {
               newPattern += " -";
            }
         }
         newPattern = newPattern.substring(1, newPattern.length());
         if (!filterMap.containsKey(newPattern)) {
         filterMap.put(newPattern, new TreeSet<String>());
         }
         filterMap.get(newPattern).add(s);
      }
      return filterMap;
   }
   
   // Private method helper that takes in a map containing possible sets of words, and finds the 
   // best group of words to return a set of them.
   private Set<String> record(Map<String, Set<String>> filterMap) {
      Set<String> bestSet = new TreeSet<String>();
      Set<String> keys = filterMap.keySet();
      String bestKey = "";
      for (String s : keys) {
         if (filterMap.get(s).size() > bestSet.size()) {
            bestSet = filterMap.get(s);
            bestKey = s;
         }
      }
      pattern = bestKey;
      return bestSet;
   }
}
