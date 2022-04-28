package ca.jrvs.apps.twitter.controller;

import ca.jrvs.apps.twitter.model.Coordinates;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.service.Service;
import oauth.signpost.exception.OAuthException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterControllerUnitTest {
  @Mock
  Service service;

  @InjectMocks
  TwitterController controller;

  String hashTag = "#testdays";
  String text = "Currently testing Controller unit tests! "+ hashTag;
  float lon = 10.01f;
  float lat = 10.01f;

  @Before
  public void setUp() throws Exception {
    controller = new TwitterController(service);
  }

  @Test
  public void postTweet() throws IOException, OAuthException {
    Tweet tweet = new Tweet();

    Coordinates coordinates = new Coordinates();
    float[] coords = {lon, lat};
    coordinates.setCoordinates(coords);
    tweet.setCoordinates(coordinates);
    tweet.setText(text);

    String [] missingArgs = {"post", text};
    String [] invalidLatArgs = {"post", text, "AAA:10.01"};
    String [] missingLatArgs = {"post", text, "10.01"};

    when(service.postTweet(any())).thenReturn(tweet);

    try{
      controller.postTweet(missingArgs);
      fail();
    }catch(IllegalArgumentException e )
    {
      assertEquals("USAGE: TwitterCLIApp post \"tweet_text\"\"latitude:longitude\"", e.getMessage());
    }
    try{
      controller.postTweet(invalidLatArgs);
      fail();

    }catch(IllegalArgumentException e )
    {
      assertEquals("Invalid coordinates (Must be float). Usage: TwitterApp \"post\" \"tweet_text\" \"latitude:longitude\"",e.getMessage());
    }
    try{
      controller.postTweet(missingLatArgs);
      fail();
    }catch (IllegalArgumentException e ){
      assertEquals("Invalid number of coordinates. Usage: TwitterApp \"post\" \"tweet_text\" \"latitude:longitude\" ",e.getMessage());
    }

    String newText = text + System.currentTimeMillis();
    String[] validArgs = {"post", "Currently testing Controller unit tests! #testdays", "10.01:10.01"};

    float lon = 10.01f;
    float lat = 10.01f;
    Tweet validTweet = controller.postTweet(validArgs);

    assertEquals(validArgs[1], validTweet.getText());
    assertEquals(lon, validTweet.getCoordinates().getCoordinates()[0],1e-8);
    assertEquals(lat, validTweet.getCoordinates().getCoordinates()[1], 1e-8);


  }

  @Test
  public void showTweet() {
    Tweet tweet = new Tweet();

    Coordinates coordinates = new Coordinates();
    float[] coords = {lon, lat};
    coordinates.setCoordinates(coords);
    tweet.setCoordinates(coordinates);
    tweet.setText(text);
    tweet.setId_str("1519214290930130945");
    String [] invalidArgs = {"show","1519214290930130945" , "someText", "someArgument"};

    when(service.showTweet(any(), any())).thenReturn(tweet);

    try{
      controller.showTweet(invalidArgs);
      fail();
    }catch(IllegalArgumentException e){
      assertEquals("Invalid or Missing Arguments. Usage: TwitterApp show tweet_id \"field1,fields2\"",e.getMessage());
    }

    String [] validArgs = {"show", "1519214290930130945", "id,text,coordinates"};
    Tweet validTweet = controller.showTweet(validArgs);

    String validText = "Currently testing Controller unit tests! #testdays";
    float lon = 10.01f;
    float lat = 10.01f;

    assertEquals(validText, validTweet.getText());
    assertEquals(lon, validTweet.getCoordinates().getCoordinates()[0], 1e-8);
    assertEquals(lat, validTweet.getCoordinates().getCoordinates()[1], 1e-8);


  }

  @Test
  public void deleteTweet() throws OAuthException, IOException {
    Tweet tweet = new Tweet();

    Coordinates coordinates = new Coordinates();
    float[] coords = {lon, lat};
    coordinates.setCoordinates(coords);
    tweet.setCoordinates(coordinates);
    tweet.setText(text);
    tweet.setId_str("1519214290930130945");

    List<Tweet> tweets = new ArrayList<>();
    tweets.add(tweet);

    String [] missingArgs = {"delete"};

    when(service.deleteTweets(any())).thenReturn(tweets);

    try {
      controller.deleteTweet(missingArgs);
      fail();
    }catch (IllegalArgumentException e){
      assertEquals("Invalid or Missing Arguments. Usage: TwitterApp delete \"id1,id2,..\"", e.getMessage());
    }




    String [] validArgs = {"delete", "1519214290930130945"};
    List<Tweet> deleteTweets = controller.deleteTweet(validArgs);


    for(Tweet validTweet: deleteTweets)
    {
      assertEquals("Currently testing Controller unit tests! #testdays" ,deleteTweets.get(0).getText());
      assertEquals(lon, validTweet.getCoordinates().getCoordinates()[0], 1e-8);
      assertEquals(lat, validTweet.getCoordinates().getCoordinates()[1], 1e-8);

    }


  }
}
