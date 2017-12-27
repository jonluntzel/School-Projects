// Jon Luntzel
// 11/30/17
// CSE143
// TA: Gavin Cai
// Assignment #7
// QuestionTree simulates 20 questions and builds a tree that contains a series of yes or no
// questions to discriminate between answers. The computer adds possible answers each time it is
// answered

import java.io.*;
import java.util.*;

public class QuestionTree {
   private QuestionNode root;
   private Scanner console;
   
   //Constructs a question tree with data "computer", alongside a scanner console
   public QuestionTree() {
      root = new QuestionNode("computer");
      console = new Scanner(System.in);
   }
   
   //Passed a Scanner as a parameter linked to the file. Rebuilds the present tree with
   //the information in the file
   public void read(Scanner input) {
      root = reads(input);
   }
   
   //Helper method that takes in a scanner as a parameter and replaces the present tree with
   //a new tree based on file information
   private QuestionNode reads(Scanner input) {
      String questionOrAnswer = input.nextLine();
      QuestionNode newRoot;
      if (questionOrAnswer.equals("Q:")) {
         newRoot = new QuestionNode(input.nextLine());
         newRoot.left = reads(input);
         newRoot.right = reads(input);
      }
      else {
         newRoot = new QuestionNode(input.nextLine());
      }
      return newRoot;
   }
   
   //Stores the present tree in an output file by taking in a PrintStream as a parameter.
   //The given PrintStream will be open for writing                                       
   public void write(PrintStream output) {
      write(root, output);
   }
   
   //Assists the write method by taking in the question node and a PrintStream as a parameter
   //in order to write to the PrintStream
   private void write(QuestionNode curr, PrintStream output) {
      if (curr.left == null && curr.right == null) {
         output.println("A:");
      } else {
         output.println("Q:");
         write( curr.left, output);
         write(curr.right, output);
      }
      output.println(curr.data);
   }
   
   //Asks the user yes or no questions until the computer correctly guesses their object, or
   //until the computer fails, in which case it grows the tree to include a discriminating
   //question for their object
   public void askQuestions() {
      root = askQuestions(root);
   }
   
   //Helper method that takes in a question node and allows the computer to find out if it contains
   //the correct answer and grow the tree if it does not
   private QuestionNode askQuestions(QuestionNode curr) {
      if (curr.left == null && curr.right == null) {
         if (!yesTo("Would your object happen to be " + curr.data + "?")) {
            System.out.print("What is the name of your object? ");
            String object = console.nextLine().trim();
            QuestionNode temp = curr;
            System.out.println("Please give me a yes/no question that");
            System.out.println("distinguishes between your object");
            System.out.println("and mine--> ");
            QuestionNode newBranch = new QuestionNode(console.nextLine().trim());
            if (!yesTo("And what is the answer for your object?")) {
               newBranch.right = new QuestionNode(object); 
               newBranch.left = temp; 
            } else {
               newBranch.left = new QuestionNode(object); 
               newBranch.right = temp;
            }
            curr = newBranch;
         } else {
            System.out.println("Great, I got it right!");
         }
      } else {
         if (!yesTo(curr.data)) {
            curr.right = askQuestions(curr.right);
         } else {
            curr.left = askQuestions(curr.left);
           }
       }
       return curr;
   }
   
   // post: asks the user a question, forcing an answer of "y " or "n";
   //       returns true if the answer was yes, returns false otherwise
   public boolean yesTo(String prompt) {
      System.out.print(prompt + " (y/n)? ");
      String response = console.nextLine().trim().toLowerCase();
      while (!response.equals("y") && !response.equals("n")) {
         System.out.println("Please answer y or n.");
         System.out.print(prompt + " (y/n)? ");
         response = console.nextLine().trim().toLowerCase();
      }
      return response.equals("y");
   }
}