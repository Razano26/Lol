package app.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Ability {
    private String abilityName;
    private int damage;

    @JsonCreator
    public Ability(@JsonProperty("ability") String abilityName, @JsonProperty("damage") int damage) {
        this.abilityName = abilityName;
        this.damage = damage;
    }

    public String getAbilityName() {
        return abilityName;
    }

    public void setAbilityName(String abilityName) {
        this.abilityName = abilityName;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}