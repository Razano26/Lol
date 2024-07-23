import app.utils.ServerJava;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.Response;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.LoadData.createChampionsAtInit;
import static utils.UrlPaths.CREATE_TEAM_URL;

class UseCase2IntegrationTest {

    Javalin app = new ServerJava().javalinApp(); // inject any dependencies you might have

    private static final String SRC_TEST_RESOURCES_DATASETS = "src/test/resources/datasets/teams/";

    @Test
    void test_team_blue() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS + "create_blue.json")));

        JavalinTest.test(app, (server, client) -> {
            createChampionsAtInit(client);
            Response response = client.post(CREATE_TEAM_URL, jsonContent);
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    void test_team_red() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS + "create_red.json")));

        JavalinTest.test(app, (server, client) -> {
            createChampionsAtInit(client);
            Response response = client.post(CREATE_TEAM_URL, jsonContent);
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    void test_both_teams() throws IOException {
        String blueTeam = new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS + "create_blue.json")));
        String redTeam = new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS + "create_red.json")));

        JavalinTest.test(app, (server, client) -> {
            createChampionsAtInit(client);
            assertThat(client.post(CREATE_TEAM_URL, blueTeam).code()).isEqualTo(200);
            assertThat(client.post(CREATE_TEAM_URL, redTeam).code()).isEqualTo(200);

        });
    }

    @Test
    void test_duplicates_in_team() throws IOException {
        String duplicatesInTeam = new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS + "create_duplicates_in.json")));

        JavalinTest.test(app, (server, client) -> {
            createChampionsAtInit(client);
            assertThat(client.post(CREATE_TEAM_URL, duplicatesInTeam).code()).isEqualTo(400);
        });
    }

    @Test
    void test_duplicates_between_team() throws IOException {
        String blueTeam = new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS + "create_blue.json")));
        String duplicatesBetweenTeam = new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS + "create_duplicates_between.json")));

        JavalinTest.test(app, (server, client) -> {
            createChampionsAtInit(client);
            client.post(CREATE_TEAM_URL, blueTeam);
            assertThat(client.post(CREATE_TEAM_URL, duplicatesBetweenTeam).code()).isEqualTo(400);
        });
    }

    @Test
    void test_incomplete() throws IOException {
        String incomplete = new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS + "create_incomplete.json")));

        JavalinTest.test(app, (server, client) -> {
            createChampionsAtInit(client);
            assertThat(client.post(CREATE_TEAM_URL, incomplete).code()).isEqualTo(200);
        });
    }

    @Test
    void test_too_big() throws IOException {
        String tooBig = new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS + "create_too_big.json")));

        JavalinTest.test(app, (server, client) -> {
            createChampionsAtInit(client);
            assertThat(client.post(CREATE_TEAM_URL, tooBig).code()).isEqualTo(400);
        });
    }

    @Test
    void test_unknown() throws IOException {
        String unknown = new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS + "create_unknown.json")));

        JavalinTest.test(app, (server, client) -> {
            createChampionsAtInit(client);
            assertThat(client.post(CREATE_TEAM_URL, unknown).code()).isEqualTo(400);
        });
    }
}
