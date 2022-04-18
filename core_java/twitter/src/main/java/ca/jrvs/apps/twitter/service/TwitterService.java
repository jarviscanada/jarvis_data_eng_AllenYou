package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TwitterService implements Service{

  private CrdDao dao;

  private static final int TWEET_LIMIT = 280;
  private static final float LONG_MIN = -180;
  private static final float LONG_MAX =  180;
  private static final float LAT_MIN = -90;
  private static final float LAT_MAX = 90;


  //@Autowired
  public TwitterService(CrdDao dao) { this.dao = dao;}

  @Override
  public Tweet postTweet(Tweet tweet) {
    //Business logic;
    //e.g. text length, lat/lon range, id format
    validatePostTweet(tweet);

    //create tweet via dao
    return (Tweet) dao.create(tweet);
  }

  @Override
  public Tweet showTweet(String id, String[] fields) {
    validateShowTweet(id, fields);
    return (Tweet) dao.findById(id);
  }



  @Override
  public List<Tweet> deleteTweets(String[] ids) {
    for(String id : ids){
      //Validate ID
      if(!id.matches("[0-9]+")){
        throw new IllegalArgumentException("ID must be numerical characters.");
      }
    }

    List<Tweet> deletedTweets = new ArrayList<Tweet>();
    for(String id : ids){
      deletedTweets.add((Tweet)dao.deleteById(id));
    }

    return deletedTweets;
  }

  private void validatePostTweet(Tweet tweet) {
    //text validation
    if(tweet.getText().length() >= TWEET_LIMIT)
      throw new IllegalArgumentException("Tweet must be 280 characters or less.");

    //coordinates validation
    float[] coordinates = tweet.getCoordinates().getCoordinates();
    if(coordinates[0] >= LONG_MAX || coordinates[0] <= LONG_MIN ||
    coordinates[1] >= LAT_MAX || coordinates[1] <= LAT_MIN)
      throw new IllegalArgumentException("Coordinates are out of bounds");
  }

  private void validateShowTweet(String id, String[] fields) {
    String[] validFields = {
        "created_at",
        "id",
        "id_str",
        "text",
        "entities",
        "coordinates",
        "retweet_count",
        "favorite_count",
        "favorited",
        "retweeted"
    };

    //Validate ID
    if(!id.matches("[0-9]+")){
      throw new IllegalArgumentException("ID must be numerical characters.");
    }

    if(fields!=null) {
      ArrayList<String> validFieldsList = new ArrayList(Arrays.asList(validFields));
      ArrayList<String> invalidFields = new ArrayList<String>();
      for(String field : fields){
        if(!validFieldsList.contains(field)){
          invalidFields.add(field);
        }
      }
      if(invalidFields.size()!=0){
        String exceptionMessage = "Invalid or Missing Fields(s): ";
        for(String field : invalidFields){
          exceptionMessage += field + " ";
        }
        throw new IllegalArgumentException(exceptionMessage);
      }
    }
  }
}
