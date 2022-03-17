package ca.jrvs.practice.codingChallenge;

public class atoi {

  public static int stringToIntegerParse(String str){
    return Integer.parseInt(str);
  }

  public static int stringToInteger(String str){
    boolean negative = false;
    String subStr = "";

    if(str.charAt(0) == '-') {
      negative = true;
      subStr = str.substring(1);
    }else{
      subStr  = str.substring(0);
    }

    int result = 0;
    for(int i = subStr.length()-1, j = 1 ; i >= 0 ; i-- , j*=10)
    {
      result += (subStr.charAt(i)-48)*j;
    }

    if(negative)
      result *= -1;

    return result;

  }

}
