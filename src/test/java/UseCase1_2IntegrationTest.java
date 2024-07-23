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
import static utils.UrlPaths.MODIFY_URL;

class UseCase1_2IntegrationTest {

    public static final String SRC_TEST_RESOURCES_DATASETS = "src/test/resources/datasets/modify/";
    Javalin app = new ServerJava().javalinApp(); // inject any dependencies you might have

    @Test
    void test_modify_lifePoints() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS + "modify_nasus_lifepoints.json")));

        JavalinTest.test(app, (server, client) -> {
            createChampionsAtInit(client);
            Response response = client.post(MODIFY_URL, jsonContent);
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    void test_modify_add_power() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS + "add_pantheon_ability.json")));

        JavalinTest.test(app, (server, client) -> {
            createChampionsAtInit(client);
            Response response = client.post(MODIFY_URL, jsonContent);
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    void test_modify_unexisting_champ() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS + "modify_unexisting_champ.json")));

        JavalinTest.test(app, (server, client) -> {
            createChampionsAtInit(client);
            Response response = client.post(MODIFY_URL, jsonContent);
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    void test_modify_invalid_json() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS + "modify_invalid_json.json")));

        JavalinTest.test(app, (server, client) -> {
            createChampionsAtInit(client);
            Response response = client.post(MODIFY_URL, jsonContent);
            assertThat(response.code()).isEqualTo(400);
        });
    }
}
