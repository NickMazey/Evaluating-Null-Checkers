package nm.evaluatingnullcheckers.sample;

import javax.annotation.*;


/**
 * Simple program to ensure that each of the null checkers function
 * @author Nicholas Mazey
 *
 */
public class HelloWorld {

  /**
   * Method copied from https://fbinfer.com/docs/checker-eradicate/ to test checkers
   * @param s - A string
   * @return - The length of the string s
   */
  public static int getLength(@Nullable String s){
    return s.length();
  }
  
  public static void main(String[] args) {
    System.out.println("Hello World");
    @Nonnull String s = null;
    System.out.println(getLength(s));
    }

}
