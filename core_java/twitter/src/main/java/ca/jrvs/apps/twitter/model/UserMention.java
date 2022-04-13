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
  public int[] indices;
  @JsonProperty("screen_name")
  public String screen_name;
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
  @JsonSetter
  public void setIndices(int[] indices) {
    this.indices = indices;
  }
  @JsonGetter
  public String getScreen_name() {
    return screen_name;
  }
  @JsonSetter
  public void setScreen_name(String screen_name) {
    this.screen_name = screen_name;
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
