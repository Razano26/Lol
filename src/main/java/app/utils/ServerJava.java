package app.utils;

import app.models.Champion;
import app.models.Team;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ServerJava {

    private final Javalin app;
    private static final Logger logger = LoggerFactory.getLogger(ServerJava.class);
    private final Map<String, Champion> champions = new HashMap<>();
    private final Map<String, Team> teams = new HashMap<>();

    public ServerJava() {
        // TODO : compléter les endpoints pour que les tests passent au vert !
        app = Javalin.create()
                .get("/api/status", ctx -> {
                    logger.debug("Status handler triggered", ctx);
                    ctx.status(200);
                    ctx.json("Hello World");
                })
                // USE CASE 1.1 - Création de champion
                .post("/api/create", this::createChampion)
                // USE CASE 1.2 - Modification de champion
                .post("/api/modify", this::modifyChampion)
                // USE CASE 2 - Remplissage de l'équipe
                .post("/api/team", this::createTeam)
                // USE CASE 3 - Lancement de la partie
                .get("/api/begin", ctx -> {
                })
                // USE CASE 4 - Rechercher les champions par lane
                .get("/api/prediction", ctx -> {
                })

        ;
    }

    private void createChampion(Context ctx) {
        try {
            Champion champion = ctx.bodyAsClass(Champion.class);
            String championName = champion.getChampionName().toLowerCase();
            if (champions.containsKey(championName)) {
                ctx.status(400).json("Champion already exists");
            } else if (champion.getLifePoints() < 100 || champion.getLifePoints() > 1500) {
                ctx.status(400).json("Life points must be between 100 and 150");
            } else {
                champions.put(championName, champion);
                ctx.status(200).json("Champion created successfully");
            }
        } catch (IllegalArgumentException e) {
            ctx.status(400).json("Invalid data: " + e.getMessage());
        } catch (Exception e) {
            ctx.status(400).json("Invalid JSON: " + e.getMessage());
        }
    }

    private void modifyChampion(Context ctx) {
        try {
            Champion modifiedChampion = ctx.bodyAsClass(Champion.class);
            String championName = modifiedChampion.getChampionName().toLowerCase();
            logger.info("Modifying champion: " + championName);

            if (!champions.containsKey(championName)) {
                ctx.status(404).json("Champion not found");
                logger.info("Champion not found: " + championName);
                return;
            }

            logger.info("Champion found: " + championName);

            Champion champion = champions.get(championName);
            champion.update(modifiedChampion);
            ctx.status(200).json("Champion modified successfully");
            logger.info("Champion modified successfully: " + championName);
        } catch (IllegalArgumentException e) {
            ctx.status(400).json("Invalid data: " + e.getMessage());
            logger.error("Invalid data: " + e.getMessage());
        } catch (Exception e) {
            ctx.status(400).json("Invalid JSON: " + e.getMessage());
            logger.error("Invalid JSON: " + e.getMessage());
        }
    }

    private void createTeam(Context ctx) {
        try {
            Team team = ctx.bodyAsClass(Team.class);
            String teamName = team.getTeamName().toLowerCase();
            if (teams.containsKey(teamName)) {
                ctx.status(400).json("Team already exists");
            } else if (!team.isValidSize()) {
                ctx.status(400).json("Team must have at most 5 champions");
            } else if (team.hasDuplicates()) {
                ctx.status(400).json("Team must have unique champions");
            } else {
                teams.put(teamName, team);
                ctx.status(200).json("Team created successfully");
            }
        } catch (IllegalArgumentException e) {
            ctx.status(400).json("Invalid data: " + e.getMessage());
        } catch (Exception e) {
            ctx.status(400).json("Invalid JSON: " + e.getMessage());
        }
    }

    public Javalin javalinApp() {
        return app;
    }
}
