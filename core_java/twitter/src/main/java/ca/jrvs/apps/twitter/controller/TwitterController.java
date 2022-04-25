package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import ca.jrvs.apps.twitter.util.TweetUtil;
import java.util.*;
import java.util.stream.Collectors;

public class TwitterController implements Controller{
  private static final String COORD_SEP = ":";
  private static final String COMMA = ",";

  private Service service;

  //@AutoWired
  public TwitterController(Service service){ this.service = service;}

  @Override
  public Tweet postTweet(String[] args) {
    if(args.length != 3){
      throw new IllegalArgumentException(
          "USAGE: TwitterCLIApp post \"tweet_text\"\"latitude:longitude\"");
    }

    String tweet_txt = args[1];
    String [] coordinates = args[2].split(COORD_SEP);
    if (coordinates.length != 2){
      throw new IllegalArgumentException("Invalid number of coordinates. "+
          "Usage: TwitterApp \"post\" \"tweet_text\" \"latitude:longitude\" ");
    }

    Tweet tweet;

    try {
      float longitude = Float.parseFloat(coordinates[0]);
      float latitude = Float.parseFloat(coordinates[1]);
      tweet = TweetUtil.buildTweet(tweet_txt, longitude, latitude);
    }catch(Exception e) {
      throw new IllegalArgumentException("Invalid coordinates (Must be float). " +
          "Usage: TwitterApp \"post\" \"tweet_text\" \"latitude:longitude\"");
    }

    return service.postTweet(tweet);
  }

  @Override
  public Tweet showTweet(String[] args) {
    if(args.length>3) {
      throw new IllegalArgumentException("Invalid or " +
          "Missing Arguments. Usage: TwitterApp show tweet_id \"field1,fields2\"");
    }
    String id = args[1];

    if(args.length==2){
      return service.showTweet(id, null);
    }

    List<String> fieldsList = Arrays.stream(args[2].split(","))
        .map(s -> s.toLowerCase())
        .map(s -> s.replaceAll("\\s+",""))
        .collect(Collectors.toList());
    String [] fields = fieldsList.toArray(new String[fieldsList.size()]);

    return service.showTweet(id, fields);
  }

  @Override
  public List<Tweet> deleteTweet(String[] args) {
    if(args.length!=2) {
      throw new IllegalArgumentException("Invalid or " +
          "Missing Arguments. Usage: TwitterApp delete \"id1,id2,..\"");
    }

    String [] ids = args[1].split(",");

    return service.deleteTweets(ids);
  }

}
