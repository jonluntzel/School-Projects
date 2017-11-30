// Jon Luntzel
// 1/19/17
// CSE143
// TA: John Armstrong
// Assignment #2
//this does something.

public class Guitar37 implements Guitar {
   public static final String KEYBOARD =
      "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";  // keyboard layout
   private GuitarString[] strings = new GuitarString[37];
   private int count;
   private double sampleSum;
   private boolean hasString;
   
   //Constructs a guitar with 37 strings
   public Guitar37() {
      for (int i = 0; i < 37; i++) {
         strings[i] = new GuitarString(Math.pow(2.0, ((i - 24) / 12)) * 440.0);
      }
   }
   
   public void playNote(int pitch) {
      if (pitch >= 0 || pitch <= 37) {
         strings[pitch].pluck();
      }
   }
   
   // A key is given as a parameter by the user and returns true if the key corresponds to a guitar string
   public boolean hasString(char key) {
   if (KEYBOARD.indexOf(key) >= 0 && KEYBOARD.indexOf(key) <= 37) {
      hasString = true;
      }
   else {
      hasString = false;
      }
   return hasString;
   }

   // A key is given as a parameter and the string is plucked if the key is on the guitar
   public void pluck(char key) {
      if (hasString(key) == false) {
         throw new IllegalArgumentException();
      }
      else {
         strings[KEYBOARD.indexOf(key)].pluck();
      }   
   }
   //for (int i = 0; i < 37; i++) {
         //strings[KEYBOARD.indexOf(key)].pluck();
         //play (i-24)


   //(the sum of all samples from the strings of the guitar)
   public double sample() {
      for (int i = 0; i < 37; i++) {
         sampleSum += strings[i].sample();
      }
      return sampleSum;
   }
   
   //advances time forward each time it is called
   public void tic() {
      for (int i = 0; i < 37; i++) {
         strings[i].tic();
      }
   count++;
   }
   
   //determines the current time
   public int time() {
   return count;
   }
   }
