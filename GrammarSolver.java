// Jon Luntzel
// 11/2/17
// CSE143
// TA: Gavin Cai
// Assignment #5
// Accesses grammar rules that are represented in Backus-Naur Form. This program supplements a
// separate program that handles user input, and GrammarSolver lets the user generate random
// elements of the grammar. It does so by manipulating the grammar rules with various methods.
import java.util.*;

public class GrammarSolver {
   SortedMap<String, String[]> grammars;
   
   // Takes in a list of strings as a parameter that represents a grammar. Stores the grammar in a
   // map with nonterminals as the key and rules as the value. If the grammar list is empty, or if
   // there is more than one entry in the grammar for the same nonterminal, it throws an
   // IllegalArgumentException. Does not modify the list.
   public GrammarSolver(List<String> grammar) {
      if (grammar.isEmpty()) {
         throw new IllegalArgumentException();
      }
      grammars = new TreeMap<String, String[]>();
      for (String sequence : grammar) {
         String[] backusForm = sequence.split("::=");
         String[] rules = backusForm[1].split("[|]");
         if (grammars.containsKey(backusForm[0])) {
            throw new IllegalArgumentException();
         }
         grammars.put(backusForm[0], rules);
      }
   }
   
   // Returns whether or not the string symbol passed as a parameter is a nonterminal in the
   // grammar. If it does contain the symbol, it returns true, and if the grammar does not,the
   // method returns false.
   public boolean grammarContains(String symbol) {
      return grammars.containsKey(symbol);
   }
   
   // Takes in a string symbol and an integer for the number of occurrences this method randomly
   // generates words or sentences of based on the nonterminal symbol. It should randomly pick
   // the nonterminal's rule without weighting probability. Throws an IllegalArgumentException
   // when the grammar does not contain the symbol, or the times is less than 0.
   public String[] generate(String symbol, int times) {
      if (!grammarContains(symbol) || times < 0) {
         throw new IllegalArgumentException();
      }
      String[] generated = new String[times];
      Random r = new Random();
      for (int i = 0; i < times; i++) {
         generated[i] = generate(symbol, r).trim();
      }
      return generated;
   }
   
   // Private helper method for generate. Takes in a string symbol as a parameter.
   // Creates and returns a string phrase by accessing the grammar.
   private String generate(String symbol, Random r) {
      String phrase = "";
      String[] rules = grammars.get(symbol);
      String[] rule = rules[r.nextInt(rules.length)].trim().split("[ \t]+");
      for (int i = 0; i < rule.length; i++) {
         //checks to see if the rule is a nonterminal, and applies recursion if so
         if(grammarContains(rule[i])) {
            phrase += generate(rule[i], r);
         } else {
            phrase += rule[i] + " ";
         }
      }
      return phrase;
   }
   
   // Returns a string that displays all the nonterminal symbols from the given grammar. They
   // appear in a sorted list separated by commas and encased in brackets.
   public String getSymbols() {
      return grammars.keySet().toString();
   }
}

