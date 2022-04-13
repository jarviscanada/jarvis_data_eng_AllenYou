package ca.jrvs.apps.twitter.model;


import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
    "coordinates",
    "type"
})

public class Coordinates {
  @JsonProperty("coordinates")
  public float[] coordinates;
  @JsonProperty("type")
  public String type;

  @JsonGetter
  public float[] getCoordinates() {
    return coordinates;
  }
  @JsonSetter
  public void setCoordinates(float[] coordinates) {
    this.coordinates = coordinates;
  }
  @JsonGetter
  public String getType() {
    return type;
  }
  @JsonSetter
  public void setType(String type) {
    this.type = type;
  }
}
