package ca.jrvs.practice.codingChallenge;

import static org.junit.Assert.*;

import org.junit.Test;

public class OddEvenTest {

  @Test
  public void OddAndEvenTest(){
    OddEven oddeven = new OddEven();

    assertEquals("odd", oddeven.oddEvenMod(1));
  }
}