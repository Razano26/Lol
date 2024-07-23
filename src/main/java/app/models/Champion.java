package app.models;

import app.enums.Roles;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Champion {
    private String championName;
    private Roles championType;
    private int lifePoints;
    private List<Ability> abilities;

    @JsonCreator
    public Champion(@JsonProperty("championName") String championName,
                    @JsonProperty("role") Roles championType,
                    @JsonProperty("lifePoints") int lifePoints,
                    @JsonProperty("abilities") List<Ability> abilities) {
        setChampionName(championName);
        this.championType = championType;
        this.lifePoints = lifePoints;
        this.abilities = abilities;
    }

    // Getters and Setters
    public String getChampionName() {
        return championName;
    }

    public void setChampionName(String championName) {
        if (championName == null || championName.trim().isEmpty()) {
            throw new IllegalArgumentException("Champion name cannot be null or empty");
        }
        this.championName = capitalizeFirstLetter(championName);
    }

    public Roles getChampionType() {
        return championType;
    }

    public void setChampionType(Roles championType) {
        this.championType = championType;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(Object lifePoints) {
        if (lifePoints instanceof Integer) {
            this.lifePoints = (Integer) lifePoints;
        } else if (lifePoints instanceof String) {
            try {
                this.lifePoints = Integer.parseInt((String) lifePoints);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Life points must be an integer or a string that can be parsed as an integer.");
            }
        } else {
            throw new IllegalArgumentException("Life points must be of type Integer or String.");
        }
    }

    public void updateLifePoints(Object lifePoints) {
        if (lifePoints instanceof Integer) {
            this.lifePoints = (Integer) lifePoints;
        } else if (lifePoints instanceof String) {
            try {
                this.lifePoints = Integer.parseInt((String) lifePoints);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Life points must be an integer or a string that can be parsed as an integer.");
            }
        } else {
            throw new IllegalArgumentException("Life points must be of type Integer or String.");
        }
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    // Capitalize the first letter of the champion name
    private String capitalizeFirstLetter(String championName) {
        return championName.substring(0, 1).toUpperCase() + championName.substring(1);
    }

    public void update(Champion updatedChampion) {
        if (updatedChampion.getChampionName() != null) {
            setChampionName(updatedChampion.getChampionName());
        }
        if (updatedChampion.getChampionType() != null) {
            setChampionType(updatedChampion.getChampionType());
        }
        if (updatedChampion.getLifePoints() != 0) {
            updateLifePoints(updatedChampion.getLifePoints());
        }
        // add new abilities
        if (updatedChampion.getAbilities() != null) {
            abilities.addAll(updatedChampion.getAbilities());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Champion) || obj == null) {
            return false;
        }

        Champion champion = (Champion) obj;
        return Objects.equals(championName, champion.championName);
    }
}
