package app.utils;

import app.models.Champion;
import app.models.Game;
import app.models.Team;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class ServerJava {

    private final Javalin app;
    private static final Logger logger = LoggerFactory.getLogger(ServerJava.class);
    private final Map<String, Champion> champions = new HashMap<>();
    private final Map<String, Team> teams = new HashMap<>();
    private final Game game = new Game();

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
                .get("/api/begin", this::beginGame)
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
                logger.info("Champion already exists: " + championName);
            } else if (champion.getLifePoints() < 100 || champion.getLifePoints() > 1500) {
                ctx.status(400).json("Life points must be between 100 and 150");
                logger.info("Life points must be between 100 and 150: " + championName);
            } else {
                champions.put(championName, champion);
                ctx.status(200).json("Champion created successfully");
                logger.info("Champion created successfully: " + championName);
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
            logger.info("Creating team: " + teamName);
            synchronized (teams) {
                if (teams.containsKey(teamName)) {
                    ctx.status(400).json("Team already exists");
                    logger.error("Team already exists: " + teamName);
                } else if (!team.isValidSize()) {
                    ctx.status(400).json("Team must have at most 5 champions");
                    logger.error("Team must have at most 5 champions: " + teamName);
                } else if (team.hasDuplicates()) {
                    ctx.status(400).json("Team must have unique champions");
                    logger.error("Team must have unique champions: " + teamName);
                } else if (hasDuplicatesAcrossTeams(team)) {
                    ctx.status(400).json("Champion cannot be in both teams");
                    logger.error("Champion cannot be in both teams: " + teamName);
                } else if (!allChampionsExist(team)) {
                    ctx.status(400).json("One or more champions do not exist");
                    logger.error("One or more champions do not exist in team: " + teamName);
                } else {
                    teams.put(teamName, team);
                    if ("red".equals(teamName)) {
                        game.setRedTeam(team);
                    } else if ("blue".equals(teamName)) {
                        game.setBlueTeam(team);
                    }
                    ctx.status(200).json("Team created successfully");
                    logger.info("Team created successfully: " + teamName);
                }
            }
        } catch (IllegalArgumentException e) {
            ctx.status(400).json("Invalid data: " + e.getMessage());
            logger.error("Invalid data: ", e);
        } catch (Exception e) {
            ctx.status(400).json("Invalid JSON: " + e.getMessage());
            logger.error("Invalid JSON: ", e);
        }
    }

    private boolean hasDuplicatesAcrossTeams(Team newTeam) {
        return teams.values().stream()
                .flatMap(team -> team.getChampionsDistribution().stream())
                .anyMatch(cd -> newTeam.containsChampion(cd.getChampionName()));
    }

    private boolean allChampionsExist(Team team) {
        for (String championName : team.getChampionsDistribution().stream()
                .map(cd -> cd.getChampionName().toLowerCase())
                .collect(Collectors.toList())) {
            if (!champions.containsKey(championName)) {
                logger.debug("Champion not found: " + championName);
                return false;
            }
        }
        return true;
    }


    private void beginGame(Context ctx) {
        synchronized (game) {
            if (game.getBlueTeam() == null || game.getRedTeam() == null) {
                ctx.status(400).json("Both teams must be set before starting the game");
                logger.error("Both teams must be set before starting the game");
                return;
            }
            if (game.getBlueTeam().getChampionsDistribution().size() != 5 ||
                    game.getRedTeam().getChampionsDistribution().size() != 5) {
                ctx.status(400).json("Both teams must have exactly 5 champions");
                logger.error("Both teams must have exactly 5 champions");
                return;
            }
            game.setStarted(true);
            ctx.status(200).json("Bienvenue sur la Faille de l'Invocateur !");
            logger.info("Game started");
        }
    }

    public Javalin javalinApp() {
        return app;
    }
}
