package utils;

import io.javalin.testtools.HttpClient;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static utils.UrlPaths.CREATE_TEAM_URL;
import static utils.UrlPaths.CREATE_URL;

public class LoadData {

    public static final String SRC_TEST_RESOURCES_DATASETS_CREATE = "src/test/resources/datasets/create/";
    public static final String SRC_TEST_RESOURCES_DATASETS_TEAMS = "src/test/resources/datasets/teams/";

    public static void createChampionsAtInit(HttpClient httpClient) throws IOException {
        httpClient.post(CREATE_URL, new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS_CREATE + "create_nasus.json"))));
        httpClient.post(CREATE_URL, new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS_CREATE + "create_pantheon.json"))));
        httpClient.post(CREATE_URL, new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS_CREATE + "create_nami.json"))));
        httpClient.post(CREATE_URL, new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS_CREATE + "create_champ4.json"))));
        httpClient.post(CREATE_URL, new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS_CREATE + "create_champ5.json"))));
        httpClient.post(CREATE_URL, new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS_CREATE + "create_champ6.json"))));
        httpClient.post(CREATE_URL, new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS_CREATE + "create_champ7.json"))));
        httpClient.post(CREATE_URL, new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS_CREATE + "create_champ8.json"))));
        httpClient.post(CREATE_URL, new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS_CREATE + "create_champ9.json"))));
        httpClient.post(CREATE_URL, new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS_CREATE + "create_champ10.json"))));
    }

    public static void createTeamsAtInit(HttpClient httpClient) throws IOException {
        httpClient.post(CREATE_TEAM_URL, new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS_TEAMS + "create_blue.json"))));
        httpClient.post(CREATE_TEAM_URL, new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS_TEAMS + "create_red.json"))));
    }

    public static void createOneTeam(HttpClient httpClient) throws IOException {
        httpClient.post(CREATE_TEAM_URL, new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS_TEAMS + "create_blue.json"))));
    }

    public static void createInequalTeams(HttpClient httpClient) throws IOException {
        httpClient.post(CREATE_TEAM_URL, new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS_TEAMS + "create_blue.json"))));
        httpClient.post(CREATE_TEAM_URL, new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS_TEAMS + "create_incomplete.json"))));
    }
}
