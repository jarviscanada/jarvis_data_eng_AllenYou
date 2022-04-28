package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.TwitterService;
import oauth.signpost.exception.OAuthException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class TwitterControllerIntTest {

  public TwitterController controller;

  @Before
  public void setUp() throws Exception {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey,consumerSecret,accessToken,tokenSecret);
    controller = new TwitterController(new TwitterService(new TwitterDao(httpHelper)));
  }

  @Test
  public void postTweet() throws OAuthException, IOException {


    String [] missingArgs = {"post", "testertext"};
    String [] invalidLatArgs = {"post", "testertext", "AAA:10"};
    String [] missingLatArgs = {"post", "textertex", "10"};

    try{
      controller.postTweet(missingArgs);
      fail();
    }catch(IllegalArgumentException e)
    {
      assertEquals("USAGE: TwitterCLIApp post \"tweet_text\"\"latitude:longitude\"", e.getMessage());
    }
    try{
      controller.postTweet(invalidLatArgs);
      fail();

    }catch(IllegalArgumentException e)
    {
      assertEquals("Invalid coordinates (Must be float). Usage: TwitterApp \"post\" \"tweet_text\" \"latitude:longitude\"",e.getMessage());
    }
    try{
      controller.postTweet(missingLatArgs);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("Invalid number of coordinates. Usage: TwitterApp \"post\" \"tweet_text\" \"latitude:longitude\" ",e.getMessage());
    }
    String hashTag = "#testok";
    String text = "Testing a test using this text "+ hashTag + " " + System.currentTimeMillis();

    String[] validArgs = {"post", text, "10:10"};

    float lon = 10f;
    float lat = 10f;
    Tweet tweet = controller.postTweet(validArgs);

    assertEquals(validArgs[1], tweet.getText());
    assertEquals(lon, tweet.getCoordinates().getCoordinates()[0],1e-8);
    assertEquals(lat, tweet.getCoordinates().getCoordinates()[1], 1e-8);
  }

  @Test
  public void showTweet() {
    String [] invalidArgs = {"show","1234567890" , "someText", "someArgument"};

    try{
      controller.showTweet(invalidArgs);
      fail();
    }catch(IllegalArgumentException e){
      assertEquals("Invalid or Missing Arguments. Usage: TwitterApp show tweet_id \"field1,fields2\"",e.getMessage());
    }

    String [] validArgs = {"show", "1519214290930130945", "id,text,coordinates"};
    Tweet tweet = controller.showTweet(validArgs);

    String text = "@This is a test for testing purposes #test 1651043885219";
    float lon = 10.01f;
    float lat = -10.01f;

    assertEquals(text, tweet.getText());
    assertEquals(lon, tweet.getCoordinates().getCoordinates()[0], 1e-8);
    assertEquals(lat, tweet.getCoordinates().getCoordinates()[1], 1e-8);


  }

  @Test
  public void deleteTweet() throws OAuthException, IOException {
    String [] missingArgs= {"delete"};
    try {
      controller.deleteTweet(missingArgs);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("Invalid or Missing Arguments. Usage: TwitterApp delete \"id1,id2,..\"", e.getMessage());
    }
    String text = "@This is a test for testing purposes. Testing Controller! #test" + System.currentTimeMillis();
    float lat = 10.01f;
    float lon = 10.01f;

    String[] tweet1Args = {"post", text, "10.01:10.01"};
    String[] tweet2Args = {"post", text + "COPY", "10.01:10.01"};
    Tweet tweet1 = controller.postTweet(tweet1Args);
    Tweet tweet2 = controller.postTweet(tweet2Args);

    String[] ids = {tweet1.getId_str(), tweet2.getId_str()};

    List<Tweet> deleteTweets = controller.deleteTweet(ids);

    for(Tweet tweet: deleteTweets)
    {
      assertTrue(((text).equals(tweet.getText())) || (text+"COPY").equals(tweet.getText()));
      assertEquals(lon, tweet.getCoordinates().getCoordinates()[0], 1e-8);
      assertEquals(lat, tweet.getCoordinates().getCoordinates()[1], 1e-8);

    }

  }
}
