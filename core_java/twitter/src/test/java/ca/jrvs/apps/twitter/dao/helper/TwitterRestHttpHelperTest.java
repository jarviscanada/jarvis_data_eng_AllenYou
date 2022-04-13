package ca.jrvs.apps.twitter.dao.helper;

import ca.jrvs.apps.twitter.dao.helper.HttpHelper;
import ca.jrvs.apps.twitter.dao.helper.TwitterHttpHelper;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.*;

import java.net.URI;

public class TwitterRestHttpHelperTest {

  @Test
  public void httpPost() throws Exception {
    String consumerKey = System.getenv("consumerKey");
    String consumerSecret = System.getenv("consumerSecret");
    String accessToken = System.getenv("accessToken");
    String tokenSecret = System.getenv("tokenSecret");
    System.out.println(consumerKey+"|"+consumerSecret+"|"+accessToken+"|"+tokenSecret);
    //Create components
    HttpHelper httpHelper = new TwitterHttpHelper(consumerKey, consumerSecret, accessToken, tokenSecret);
    HttpResponse response = httpHelper.httpPost(new URI(
        "https://api.twitter.com/1.1/statuses/update.json?status=Hello"
    ));
    System.out.println(EntityUtils.toString(response.getEntity()));
  }
}
