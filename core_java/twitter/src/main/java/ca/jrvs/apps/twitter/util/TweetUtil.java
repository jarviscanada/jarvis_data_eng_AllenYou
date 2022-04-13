package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetUtil {

  public static Tweet buildTweet(String text, double longitude, double latitude){
    Tweet tweet = new Tweet();


    Coordinates coord = new Coordinates();
    float coordArr[] = {(float)longitude,(float)latitude};

    coord.setType(text);

  }

}
