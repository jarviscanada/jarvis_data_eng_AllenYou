package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TweetUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TwitterDaoIntTest {

  public String hashTag = "#test";
  float longitude = 10.01f;
  float latitude = -10.01f;
  TwitterDao twitterDao;
  String text = "@This is a test for testing purposes "+ hashTag + " " + System.currentTimeMillis();

  @Before
  public void setUp() throws Exception {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");

    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey,consumerSecret,accessToken,tokenSecret);
    this.twitterDao = new TwitterDao(httpHelper);
  }

  @Test
  public void create()throws Exception{

    Tweet postTweet = TweetUtil.buildTweet(text, longitude, latitude);
    Tweet tweet = twitterDao.create(postTweet);
    System.out.println(JsonUtil.toJson(tweet,true, true));

    assertEquals(text, tweet.getText());
    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates().getCoordinates().length);
    assertEquals(longitude, tweet.getCoordinates().getCoordinates()[0], 1e-8);
    assertEquals(latitude, tweet.getCoordinates().getCoordinates()[1], 1e-8);
    assertTrue(hashTag.contains(tweet.getEntities().getHashtags().get(0).getText()));
  }

  @Test
  public void findById() {
    String id = "1519214290930130945";
    String expectedText = "@This is a test for testing purposes #test 1651043885219";
    Tweet tweet = twitterDao.findById(id);

    assertEquals(expectedText, tweet.getText());
    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates().getCoordinates().length);
    assertEquals(longitude, tweet.getCoordinates().getCoordinates()[0], 1e-8);
    assertEquals(latitude, tweet.getCoordinates().getCoordinates()[1], 1e-8);
    assertTrue(hashTag.contains(tweet.getEntities().getHashtags().get(0).getText()));
  }

  @Test
  public void deleteById() {
    Tweet postTweet = twitterDao.create(TweetUtil.buildTweet(text+" COPY", longitude, latitude));
    Tweet tweet = twitterDao.deleteById(postTweet.getId_str());

    assertEquals(text+" COPY", tweet.getText());
    assertNotNull(tweet.getCoordinates());
    assertEquals(2, tweet.getCoordinates().getCoordinates().length);
    assertEquals(longitude, tweet.getCoordinates().getCoordinates()[0],1e-8);
    assertEquals(latitude, tweet.getCoordinates().getCoordinates()[1], 1e-8);
    assertTrue(hashTag.contains(tweet.getEntities().getHashtags().get(0).getText()));
  }
}