package app.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        if (championsDistribution == null) {
            return false;
        }
        Set<String> uniqueChampions = championsDistribution.stream()
                .map(ChampionDistribution::getChampionName)
                .collect(Collectors.toSet());
        return uniqueChampions.size() != championsDistribution.size();
    }

    public boolean containsChampion(String championName) {
        return championsDistribution.stream()
                .anyMatch(cd -> cd.getChampionName().equalsIgnoreCase(championName));
    }
}