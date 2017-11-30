// Jon Luntzel
// 10/19/17
// CSE143
// TA: Gavin Cai
// Assignment #3
// AssassinManager keeps track of those alive and those dead in a game of 'assassin' by using
// data from a 'kill ring' where people have targets and are targeted. If a person is killed
// they are stored in the graveyard.

import java.util.*;

public class AssassinManager {
   private AssassinNode aliveNode;
   private AssassinNode deadNode;
   
   // Takes in a list of player names and builds a kill ring made of
   // assassin nodes. If the list is empty, it will throw an illegal argument exception.
   public AssassinManager(List<String> names) {
      if (names.isEmpty()) {
         throw new IllegalArgumentException();
      }
      aliveNode = new AssassinNode(names.get(0));
      AssassinNode temp = aliveNode;
      for (int i = 1; i < names.size(); i++) {
         temp.next = new AssassinNode(names.get(i));
         temp = temp.next;
      }
      // Working with a circular list
      temp.next = aliveNode;
   }
   
   // Prints out the people's names in the kill ring. If there is only
   // one person in the kill ring it will say the person is stalking
   // theirself.
   public void printKillRing() {
      // Fenceposting for circular kill ring to have a stoppable while loop
      AssassinNode temp = aliveNode;
      System.out.println("    " + temp.name + " is stalking " + temp.next.name);
      temp = temp.next;
      while (!temp.name.equals(aliveNode.name)) {
         System.out.println("    " + temp.name + " is stalking " + temp.next.name);
         temp = temp.next;
      }
   }
   
   // Prints out the people's names in the graveyard in reverse order (most recent first).
   // Prints nothing if the graveyard is empty.
   public void printGraveyard() {
      if (deadNode != null) {
         AssassinNode temp = deadNode;
         while (temp.next != null) {
            System.out.println("    " + temp.name + " was killed by " + temp.killer);
            temp = temp.next;
         }
      }
   }
   
   // General contains method that takes in a string name and returns a boolean true value if the
   // name is in the current list that the assassinNode parameter refers to. Only used in Assassin-
   // Manager, and returns false if the name is not present.
   private boolean contains(AssassinNode state, String name) {
      if (state != null) {
         AssassinNode temp = state;
         if (name.equalsIgnoreCase(temp.name)) {
            return true;
         }
         if (temp.next != null) {
            temp = temp.next;
         }
         while(!temp.name.equals(state.name) && temp != null) {
            if (name.equalsIgnoreCase(temp.name)) {
               return true;
            }
            temp = temp.next;
         }
      }
      return false;
   }
   
   // This should return boolean true if the given name is in the current
   // kill ring and should return false otherwise.  It should ignore
   // case in comparing names.
   public boolean killRingContains(String name) {
      return contains(aliveNode, name);
   }
   
   // This should return a boolean true if the given name is in the current
   // graveyard and should return false otherwise.  It should ignore
   // case in comparing names.
   public boolean graveyardContains(String name) {
      return contains(deadNode, name);
   }
   
   // returns a boolean true if the game is over, and returns false if
   // the game is not over. The game is over if the kill ring has one person.
   public boolean gameOver() {
      return (aliveNode.next == aliveNode);
   }
   
   // returns a string of the winner's name. If the game is not over,
   // this method returns null.
   public String winner() {
      if (gameOver()) {
         return aliveNode.name;
      }
      return null;
   }
   
   // Transfers a killed player into the graveyard. Maintains the order of living players and
   // ignores casing of names.
   public void kill(String name) {
      if (!killRingContains(name)) {
         throw new IllegalArgumentException();
      }
      if (gameOver()) {
         throw new IllegalStateException();
      }
      //name = name.toLowerCase();
      AssassinNode temp = aliveNode.next;
      AssassinNode previous = aliveNode;
      while (name.equalsIgnoreCase(temp.name)) {
         //!temp.name.toLowerCase().equals(name)
         temp = temp.next;
         previous = previous.next;
      }
      if (temp.name.equals(aliveNode.name)) {
         aliveNode = aliveNode.next;
      }
      previous.next = temp.next;
      temp.killer = previous.name;
      temp.next = deadNode;
      deadNode = temp;
      
   }
}