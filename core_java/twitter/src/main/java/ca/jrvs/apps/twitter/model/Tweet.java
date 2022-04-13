package ca.jrvs.apps.twitter.model;


import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "created_at",
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
  public String created_at;
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
  public int retweet_count;
  @JsonProperty("favorite_count")
  public int favorite_count;
  @JsonProperty("favorited")
  public boolean favorited;
  @JsonProperty("retweeted")
  public boolean retweeted;

  @JsonGetter
  public String getCreated_at() {
    return created_at;
  }
  @JsonSetter
  public void setCreated_at(String created_at) {
    this.created_at = created_at;
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
  public void setEntities(Entities entites) {
    this.entities = entites;
  }
  @JsonGetter
  public Coordinates getCoordinates() {
    return coordinates;
  }
  @JsonSetter
  public void setCoordinates(Coordinates coordinates) {
    this.coordinates = coordinates;
  }
  @JsonGetter
  public int getRetweet_count() {
    return retweet_count;
  }
  @JsonSetter
  public void setRetweet_count(int retweet_count) {
    this.retweet_count = retweet_count;
  }
  @JsonGetter
  public int getFavorite_count() {
    return favorite_count;
  }
  @JsonSetter
  public void setFavorite_count(int favorite_count) {
    this.favorite_count = favorite_count;
  }
  @JsonGetter
  public boolean getFavorited() {
    return favorited;
  }
  @JsonSetter
  public void setFavorited(boolean favorited) {
    this.favorited = favorited;
  }
  @JsonGetter
  public boolean getRetweeted() {
    return retweeted;
  }
  @JsonSetter
  public void setRetweeted(boolean retweeted) {
    this.retweeted = retweeted;
  }

}


