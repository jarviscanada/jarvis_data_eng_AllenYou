package ca.jrvs.apps.twitter.dao;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.example.JsonParser;
import ca.jrvs.apps.twitter.model.Tweet;
import ca.jrvs.apps.twitter.util.JsonUtil;
import com.google.gdata.util.common.base.PercentEscaper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.net.*;

@Repository
public class TwitterDao implements CrdDao<Tweet, String>{

  private static final String API_BASE_URI = "https://api.twitter.com";
  private static final String POST_PATH = "/1.1/statuses/update.json";
  private static final String SHOW_PATH = "/1.1/statuses/show.json";
  private static final String DELETE_PATH = "/1.1/statuses/destroy";

  private static final String QUERY_SYM = "?";
  private static final String AMPERSAND = "&";
  private static final String EQUAL = "=";

  private static final int HTTP_OK = 200;

  final Logger logger = LoggerFactory.getLogger(TwitterDao.class);

  private HttpHelper httpHelper;

  @Autowired
  public TwitterDao(HttpHelper httpHelper) {
    this.httpHelper = httpHelper;
  }

  @Override
  public Tweet create(Tweet tweet) {
    URI uri;
    try{
      uri = getPostUri(tweet);
    }
    catch(URISyntaxException e){
      throw new IllegalArgumentException("Invalid tweet input", e);
    }
    HttpResponse response=httpHelper.httpPost(uri);

    return parseResponseBody(response,HTTP_OK);
  }

  @Override
  public Tweet findById(String s) {
    URI uri;
    try{
      uri = getShowUri(s);
    }
    catch(URISyntaxException e){
      throw new IllegalArgumentException("Invalid tweet input", e);
    }
    HttpResponse response = httpHelper.httpGet(uri);

    return parseResponseBody(response, HTTP_OK);
  }

  @Override
  public Tweet deleteById(String s) {
    URI uri;
    try{
      uri = getDeleteUri(s);
    }
    catch(URISyntaxException e){
      throw new IllegalArgumentException("Invalid tweet input", e);
    }
    HttpResponse response = httpHelper.httpPost(uri);

    return parseResponseBody(response, HTTP_OK);
  }

  Tweet parseResponseBody(HttpResponse response, Integer expectedStatusCode){
    Tweet tweet = null;

    //Check response status
    int status = response.getStatusLine().getStatusCode();
    if (status != expectedStatusCode){
      try{
        logger.debug(EntityUtils.toString(response.getEntity()));
      }
      catch(IOException e){
        logger.error("Response has no entity");
      }
      throw new RuntimeException("Unexpected HTTP status: "+status);
    }

    if(response.getEntity()==null){
      throw new RuntimeException("Empty response body");
    }

    String jsonStr;
    try{
      jsonStr = EntityUtils.toString(response.getEntity());
    }
    catch(IOException e){
      throw new RuntimeException("Failed to convert entity to String", e);
    }

    try{
      tweet = JsonUtil.toObjectFromJson(jsonStr, Tweet.class);
    }
    catch(IOException e){
      throw new RuntimeException("Unable to convert JSON str to Object", e);
    }

    return tweet;
  }

  public URI getPostUri(Tweet tweet) throws URISyntaxException {
    PercentEscaper pe = new PercentEscaper("", false);
    String longitude = String.valueOf(tweet.getCoordinates().getCoordinates()[0]);
    String latitude = String.valueOf(tweet.getCoordinates().getCoordinates()[1]);
    String text = tweet.getText();

    //https://api.twitter.com/1.1/statuses/update.json?status=Hello&long=49.0&lat=79.0
    return new URI(API_BASE_URI+POST_PATH+QUERY_SYM+"status"+EQUAL
        +pe.escape(text)+AMPERSAND+"long"+EQUAL+longitude+AMPERSAND+
        "lat"+EQUAL+latitude);
  }

  public URI getShowUri(String s) throws URISyntaxException {
    //https://api.twitter.com/1.1/statuses/show.json?id=1234567890
    return new URI(API_BASE_URI+SHOW_PATH+QUERY_SYM+"id"+EQUAL+s);
  }

  public URI getDeleteUri(String s) throws URISyntaxException {
    //https://api.twitter.com/1.1/statuses/destroy?id=1234567890
    return new URI(API_BASE_URI+DELETE_PATH+"/"+s+".json");
  }
}