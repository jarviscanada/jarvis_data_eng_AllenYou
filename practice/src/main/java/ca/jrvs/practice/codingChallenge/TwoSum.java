package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Map;

/**
 * https://www.notion.so/jarvisdev/Two-Sum-598987159aac46778bfa617805820190
 * Two Sum problem
 *
 */

public class TwoSum {

  /**
   * Big O : n^2
   * @param nums
   * @param target
   * @return
   */

  public int[] twoSum(int[] nums, int target) {
    int  result [] = new int[2];

    for(int i = 0; i < nums.length; i++) {
      for(int j = i+1 ; j <  nums.length; j++){
        if(nums[i] + nums[j] == target){
          result [0] = i;
          result [1] = j;
          break;
        }
      }
    }

    return result;
  }

  /**
   * Big O : n
   * @param nums
   * @param target
   * @return
   */

  public int[] twoSumHashMap(int[] nums, int target){
    int[] result = new int [2];
    Map<Integer,Integer> map = new HashMap<Integer,Integer>();

    for(int i = 0; i < nums.length ; i++){
      int remainder = target - nums[i];

      if(map.containsKey(remainder)){
        result[0] = i;
        result[1] = map.get(remainder);
      }
      map.put(nums[i],i);
    }
    return result;
  }

}
