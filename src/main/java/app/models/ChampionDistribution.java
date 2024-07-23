package app.models;

import app.enums.Lanes;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChampionDistribution {
    private String championName;
    private Lanes lane;

    @JsonCreator
    public ChampionDistribution(
            @JsonProperty("championName") String championName,
            @JsonProperty("lane") Lanes lane
    ) {
        this.championName = championName;
        this.lane = lane;
    }

    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        this.championName = championName;
    }

    public Lanes getLane() {
        return lane;
    }

    public void setLane(Lanes lane) {
        this.lane = lane;
    }
}