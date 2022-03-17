package ca.jrvs.practice.codingChallenge;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * https://www.notion.so/jarvisdev/How-to-compare-two-maps-e780d65415264f5d9e011ff53cc9fa98
 */
public class TwoMapCompare {

  /**
   * Big O :
   * @param m1
   * @param m2
   * @param <K>
   * @param <V>
   * @return
   */
  public <K,V> boolean compareTwoMaps(Map<K,V> m1, Map<K,V> m2){

    return m1.equals(m2);
  }

  /**
   * Big O :
   * @param m1
   * @param m2
   * @param <K>
   * @param <V>
   * @return
   */
  public <K,V> boolean compareTwoMapsImp(Map<K,V> m1, Map<K,V> m2){
    if(m1.size() != m2.size())
      return false;

    Iterator<K> iter = m1.keySet().iterator();

    K curr = iter.next();
    while(iter.hasNext()){
      if(!m1.containsKey(curr) && !m2.containsKey(curr))
        if(!m1.containsValue(m1.get(curr)) && !m2.containsValue(m2.get(curr)))
          if(m1.get(curr) != m2.get(curr))
            return false;
    }

    return true;
  }

}
