package ca.jrvs.practice.codingChallenge;


public class Fibonacci {

  /**
   * O(n)
   * @param n
   * @return
   */
  public static int fibRecursive(int n){

    if( n == 0 )
      return 0;
    if( n == 1 )
      return 1;

    return fibRecursive (n-1) + fibRecursive(n-2);
  }

  /**
   * O(n)
   * @param n
   * @return
   */

  public static int fibDynamic(int n){
    int fib[] = new int[n+2];

    fib[0] = 0;
    fib[1] = 1;

    for (int i = 2; i < n-1; i++)
    {
      fib[i] = fib[i-1] + fib[i-2];
    }

    return fib[n];
  }
}

