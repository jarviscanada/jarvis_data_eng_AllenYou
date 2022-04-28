package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "name",
    "indices",
    "screen_name",
    "id",
    "id_str"
})
public class UserMention {

  @JsonProperty("name")
  public String name;
  @JsonProperty("indices")
  public int [] indices;
  @JsonProperty("screen_name")
  public String screenName;
  @JsonProperty("id")
  public long id;
  @JsonProperty("id_str")
  public String id_str;

  @JsonGetter
  public String getName() {
    return name;
  }

  @JsonSetter
  public void setName(String name) {
    this.name = name;
  }

  @JsonGetter
  public int[] getIndices() {
    return indices;
  }

  public void setIndices(int[] indices) {
    this.indices = indices;
  }

  @JsonGetter
  public String getScreenName() {
    return screenName;
  }

  @JsonSetter
  public void setScreenName(String screenName) {
    this.screenName = screenName;
  }

  @JsonGetter
  public long getId() {
    return id;
  }

  @JsonSetter
  public void setId(long id) {
    this.id = id;
  }

  @JsonGetter
  public String getId_str() {
    return id_str;
  }

  @JsonSetter
  public void setId_str(String id_str) {
    this.id_str = id_str;
  }
}