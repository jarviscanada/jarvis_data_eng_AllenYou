package ca.jrvs.practice.codingChallenge;

/**
 * ticket: https://www.notion.so/jarvisdev/Sample-Check-if-a-number-is-even-or-odd-0b90765d47f9417d83168bbafba9ff6e
 */
public class OddEven {

  /**
   * Big-O: O(1)
   * Explanation : Singular operation
   */
  public String oddEvenMod(int i){
    return i % 2 == 0 ? "even" : "odd" ;
  }

  /**
   * Big-O: O(1)
   * Explanation : Singular operation
   */
  public String oddEvenBit(int i){
    return ((i & 1) == 0) ? "even" : "odd" ;
  }
}
