package app.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Team {
    private String teamName;
    private List<ChampionDistribution> championsDistribution;

    @JsonCreator
    public Team(
            @JsonProperty("teamName") String teamName,
            @JsonProperty("championsDistribution") List<ChampionDistribution> championsDistribution
    ) {
        this.teamName = teamName;
        this.championsDistribution = championsDistribution;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<ChampionDistribution> getChampionsDistribution() {
        return championsDistribution;
    }

    public void setChampionsDistribution(List<ChampionDistribution> championsDistribution) {
        this.championsDistribution = championsDistribution;
    }

    public boolean isValidSize() {
        return championsDistribution.size() <= 5;
    }

    public boolean hasDuplicates() {
        Set<String> championNames = new HashSet<>();
        for (ChampionDistribution cd : championsDistribution) {
            if (!championNames.add(cd.getChampionName().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean containsChampion(String championName) {
        return championsDistribution.stream()
                .anyMatch(cd -> cd.getChampionName().equalsIgnoreCase(championName));
    }
}