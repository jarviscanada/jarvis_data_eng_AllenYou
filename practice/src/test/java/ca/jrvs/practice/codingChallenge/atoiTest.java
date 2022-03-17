package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class atoiTest {

  @Test
  public void atoiOne(){
    atoi at = new atoi();
    assertEquals(1,at.stringToIntegerParse("1"));
    assertEquals(1,at.stringToInteger("1"));
    assertEquals(1111,at.stringToInteger("1111"));
    assertEquals(-11,at.stringToInteger("-11"));

  }

}