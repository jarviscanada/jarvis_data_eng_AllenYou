package ca.jrvs.apps.twitter.service;

import ca.jrvs.apps.twitter.dao.CrdDao;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceUnitTest {
  @Mock
  CrdDao dao;

  @InjectMocks
  TwitterService service;

  String text = "test with loc223";
  String text2 = "allen__you test " + System.currentTimeMillis();
  float longitude = 10.1f;
  float latitude = -10.1f;

  String tweetJsonStr = "{\n"
      + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2019\",\n"
      + "   \"id\":1097607853932564480,\n"
      + "   \"id_str\":\"1097607853932564480\",\n"
      + "   \"text\":\"test with loc223\",\n"
      + "   \"entities\":{\n"
      + "      \"hashtags\":[],"
      + "      \"user_mentions\":[]"
      + "   },\n"
      + "   \"coordinates\":null,"
      + "   \"retweet_count\":0,\n"
      + "   \"favorite_count\":0,\n"
      + "   \"favorited\":false,\n"
      + "   \"retweeted\":false\n"
      + "}";

  @Before
  public void setUp() throws Exception {
    service = new TwitterService(dao);
  }

  @Test
  public void postTweet() throws Exception {
    //test failed request
    String invalidText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed fringilla facilisis ipsum." +
        " Nunc lacinia lobortis lectus, vel luctus nulla tristique non. Nullam tempor hendrerit eros, eu imperdiet " +
        "nulla convallis eget. Etiam vitae sapien eu sem vestibulum varius. Integer cursus cras amet." +
        "consequat massa quis"; //290 characters (10 over limit)
    float badLat = 200.1f;
    float badLong = -200.1f;

    Tweet badTxtTweet =  TweetUtil.buildTweet(invalidText, longitude, latitude);
    Tweet badLongTweet =  TweetUtil.buildTweet(text, badLong, latitude);
    Tweet badLatTweet =  TweetUtil.buildTweet(text, longitude, badLat);

    service.postTweet(TweetUtil.buildTweet(text, longitude, latitude));
    //invalid text test
    try{
      service.postTweet(badTxtTweet);
      fail();
    }
    catch(IllegalArgumentException e){
      final String expected = "Tweet must be 280 characters or less.";
      assertEquals(expected, e.getMessage());
    }

    //invalid longitude test
    try{
      service.postTweet(badLongTweet);
      fail();
    } catch(IllegalArgumentException e){
      final String expected = "Coordinates are out of range.";
      assertEquals(expected, e.getMessage());
    }

    //invalid latitude test
    try{
      service.postTweet(badLatTweet);
      fail();
    }
    catch(IllegalArgumentException e){
      final String expected = "Coordinates are out of range.";
      assertEquals(expected, e.getMessage());
    }

    Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);
    doReturn(expectedTweet).when(dao).create(any());
    Tweet tweet = service.postTweet(TweetUtil.buildTweet(text, longitude, latitude));

    assertEquals(text, tweet.getText());
    assertEquals(null, tweet.getCoordinates());
  }

  @Test
  public void testShowTweet() throws IOException {
    String id = "1445814382668091394";
    String invalidId = "ABC1234ABCDEFG%&*#JKLMN";
    String[] fields = {
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
    String[] invalidFields = {
        "created_@",
        "idd",
        "id_strr",
        "favorite_countt"
    };

    //invalid id testing
    try{
      service.showTweet(invalidId, fields);
      fail();
    }
    catch(IllegalArgumentException e){
      final String expected = "ID must be numerical characters.";
      assertEquals(expected, e.getMessage());
    }

    //invalid field testing
    try{
      service.showTweet(id, invalidFields);
      fail();
    }
    catch(IllegalArgumentException e){
      final String expected = "Invalid or Missing Fields(s): created_@ idd id_strr favorite_countt ";
      assertEquals(expected, e.getMessage());
    }

    Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);
    doReturn(expectedTweet).when(dao).findById(any());
    Tweet tweet = service.showTweet(id, fields);

    assertEquals(text, tweet.getText());
    assertEquals(null, tweet.getCoordinates());
  }

  @Test
  public void testDeleteById() throws IOException {
    String[] ids = {"1445454948389429253", "1445455964530823179"};
    String[] invalidIds = {"ABC1234ABCDEFG%&*#JKLMN", "1445814382668091394"};

    //invalid id testing
    try{
      service.deleteTweets(invalidIds);
      fail();
    }
    catch(IllegalArgumentException e){
      final String expected = "ID must be numerical characters.";
      assertEquals(expected, e.getMessage());
    }

    Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);
    doReturn(expectedTweet).when(dao).deleteById(any());
    List<Tweet> tweets = service.deleteTweets(ids);
    for(Tweet tweet : tweets) {
      assertTrue((text.equals(tweet.getText())) || text2.equals(tweet.getText()));
      assertEquals(null, tweet.getCoordinates());
    }
  }
}