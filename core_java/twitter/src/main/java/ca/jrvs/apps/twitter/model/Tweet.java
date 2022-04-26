package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
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
})
public class Tweet {

  @JsonProperty("created_at")
  public String createdAt;
  @JsonProperty("id")
  public long id;
  @JsonProperty("id_str")
  public String id_str;
  @JsonProperty("text")
  public String text;
  @JsonProperty("entities")
  public Entities entities;
  @JsonProperty("coordinates")
  public Coordinates coordinates;
  @JsonProperty("retweet_count")
  public int retweetCount;
  @JsonProperty("favorite_count")
  public int favoriteCount;
  @JsonProperty("favorited")
  public boolean favorited;
  @JsonProperty("retweeted")
  public boolean retweeted;

  @JsonGetter
  public String getCreatedAt() {
    return createdAt;
  }

  @JsonSetter
  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
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

  @JsonGetter
  public String getText() {
    return text;
  }

  @JsonSetter
  public void setText(String text) {
    this.text = text;
  }

  @JsonGetter
  public Entities getEntities() {
    return entities;
  }

  @JsonSetter
  public void setEntities(Entities entities) {
    this.entities = entities;
  }

  @JsonGetter
  public Coordinates getCoordinates(){
    return coordinates;
  }

  @JsonSetter
  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }

  @JsonGetter
  public int getRetweetCount() {
    return retweetCount;
  }

  @JsonSetter
  public void setRetweetCount(int retweetCount) {
    this.retweetCount = retweetCount;
  }

  @JsonGetter
  public int getFavoriteCount() {
    return favoriteCount;
  }

  @JsonSetter
  public void setFavoriteCount(int favoriteCount) {
    this.favoriteCount = favoriteCount;
  }

  @JsonGetter
  public boolean isFavorited() {
    return favorited;
  }

  @JsonSetter
  public void setFavorited(boolean favorited) {
    this.favorited = favorited;
  }

  @JsonGetter
  public boolean isRetweeted() {
    return retweeted;
  }

  @JsonSetter
  public void setRetweeted(boolean retweeted) {
    this.retweeted = retweeted;
  }

}