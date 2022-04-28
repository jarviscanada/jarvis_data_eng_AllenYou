package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import ca.jrvs.apps.twitter.util.TweetUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TwitterDaoUnitTest {
  @Mock
  HttpHelper mockHelper;

  @InjectMocks
  TwitterDao dao;

  String tweetJsonStr = "{\n"
      + "   \"created_at\":\"Mon Feb 18 21:24:39 +0000 2022\",\n"
      + "   \"id\":11111,\n"
      + "   \"id_str\":\"11111\",\n"
      + "   \"text\":\"test with loc223\",\n"
      + "   \"entities\":{\n"
      + "      \"hashtags\":[],"
      + "      \"user_mentions\":[]"
      + "   },\n"
      + "   \"coordinates\": {"
      + "           \"coordinates\" : [ 10.01, -10.01 ],\n"
      + "           \"type\" : \"Point\"\n"
      + "   },\n"
      + "   \"retweet_count\":0,\n"
      + "   \"favorite_count\":0,\n"
      + "   \"favorited\":false,\n"
      + "   \"retweeted\":false\n"
      + "}";

  @Test
  public void postTweet() throws Exception {
    //test failed request
    String hashtag = "#sample";
    String text = "@test tweet "+ hashtag + " " + System.currentTimeMillis();
    float longitude = 10.1f;
    float latitude = -10.1f;

    when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
    try {
      dao.create(TweetUtil.buildTweet(text, longitude, latitude));
    }
    catch(RuntimeException e){
      assertTrue(true);
    }

    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);
    //mock parseResponseBody
    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
    Tweet tweet = spyDao.create(TweetUtil.buildTweet(text, longitude, latitude));
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
    assertNotNull(tweet.getCoordinates());
  }

  @Test
  public void showTweet() throws Exception {
    String id = "111111111111111";
    when(mockHelper.httpGet(isNotNull())).thenThrow(new RuntimeException("mock"));
    try {
      dao.findById(id);
    }
    catch(RuntimeException e){
      assertTrue(true);
    }

    when(mockHelper.httpGet(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);
    //mock parseResponseBody
    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
    Tweet tweet = spyDao.findById(id);
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }

  @Test
  public void deleteTweet() throws Exception {
    String id = "111111111111111";
    when(mockHelper.httpPost(isNotNull())).thenThrow(new RuntimeException("mock"));
    try {
      dao.deleteById(id);
    }
    catch(RuntimeException e){
      assertTrue(true);
    }

    when(mockHelper.httpPost(isNotNull())).thenReturn(null);
    TwitterDao spyDao = Mockito.spy(dao);
    Tweet expectedTweet = JsonUtil.toObjectFromJson(tweetJsonStr, Tweet.class);
    //mock parseResponseBody
    doReturn(expectedTweet).when(spyDao).parseResponseBody(any(), anyInt());
    Tweet tweet = spyDao.deleteById(id);
    assertNotNull(tweet);
    assertNotNull(tweet.getText());
  }
}