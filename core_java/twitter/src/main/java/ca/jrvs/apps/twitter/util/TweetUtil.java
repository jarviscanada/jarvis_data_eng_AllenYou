package ca.jrvs.apps.twitter.util;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetUtil {

  public static Tweet buildTweet(String text, float longitude, float latitude){
    Tweet tweet = new Tweet();
    Coordinates coordinates = new Coordinates();
    float[] coordArr = {longitude, latitude};

    coordinates.setCoordinates(coordArr);
    coordinates.setType("Point");

    tweet.setText(text);
    tweet.setCoordinates(coordinates);

    return tweet;
  }

}
