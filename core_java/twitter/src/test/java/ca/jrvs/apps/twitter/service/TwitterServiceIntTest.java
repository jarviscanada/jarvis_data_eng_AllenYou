package ca.jrvs.apps.twitter.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import ca.jrvs.apps.twitter.dao.TwitterDao;
import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.TweetUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.internal.matchers.Equals;

public class TwitterServiceIntTest {
  public TwitterService service;
  String hashtag ="#test";
  String text = "Test text " + hashtag;
  float longitude = 10.1f;
  float latitude  = 10.1f;

  @Before
  public void setUp() throws Exception {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    System.out.println(consumerKey+"|"+consumerSecret+"|"+accessToken+"|"+tokenSecret);
    //Create components
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
    service = new TwitterService(new TwitterDao(httpHelper));
  }

  @Test
  public void PostTweetTest() throws JsonProcessingException {
    //Invalid text over 280 characters (285)
    String invalidText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed fringilla facilisis ipsum." +
        " Nunc lacinia lobortis lectus, vel luctus nulla tristique non. Nullam tempor hendrerit eros, eu imperdiet " +
        "nulla convallis eget. Etiam vitae sapien eu sem vestibulum varius. Integer cursus cras amet.";
    float badLat = 200.1f;
    float badLong = -100.1f;

    Tweet badTxtTweet =  TweetUtil.buildTweet(invalidText, longitude, latitude);
    Tweet badLongTweet =  TweetUtil.buildTweet(text, badLong, latitude);
    Tweet badLatTweet =  TweetUtil.buildTweet(text, longitude, badLat);

    //invalid text test
    try{
      service.postTweet(badTxtTweet);
      fail();
    }catch(IllegalArgumentException e) {
      assertEquals("Tweet must be 280 characters or less.", e.getMessage());
    }
    //invalid longitude test
    try{
      service.postTweet(badLongTweet);
      fail();
    }
    catch(IllegalArgumentException e){
      assertEquals("Coordinates are out of range.", e.getMessage());
    }

    //invalid latitude test
    try{
      service.postTweet(badLatTweet);
      fail();
    }
    catch(IllegalArgumentException e){
      assertEquals("Coordinates are out of range.", e.getMessage());
    }
    String newText = text+" "+System.currentTimeMillis();
    Tweet postTweet = TweetUtil.buildTweet(newText, longitude, latitude);
    Tweet tweet = service.postTweet(postTweet);

    //testing valid tweet
    assertEquals(newText, tweet.getText());
    int epsilon =(int) Math.abs(longitude - tweet.getCoordinates().getCoordinates()[0]); //required for float testing
    assertEquals(longitude, tweet.getCoordinates().getCoordinates()[0], epsilon);
    assertEquals(latitude, tweet.getCoordinates().getCoordinates()[1], epsilon);
  }

}
