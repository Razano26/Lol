import app.utils.ServerJava;
import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static utils.UrlPaths.CREATE_URL;

class UseCase1_1IntegrationTest {

    public static final String SRC_TEST_RESOURCES_DATASETS = "src/test/resources/datasets/create/";
    Javalin app = new ServerJava().javalinApp(); // inject any dependencies you might have

    @Test
    void test_create_nasus() throws IOException {

        String jsonContent = new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS + "create_nasus.json")));

        JavalinTest.test(app, (server, client) -> {
            assertThat(client.post(CREATE_URL, jsonContent).code()).isEqualTo(200);
        });
    }

    @Test
    void test_create_pantheon() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS + "create_pantheon.json")));

        JavalinTest.test(app, (server, client) -> {
            assertThat(client.post(CREATE_URL, jsonContent).code()).isEqualTo(200);
        });
    }

    @Test
    void test_create_nami() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS + "create_nami.json")));

        JavalinTest.test(app, (server, client) -> {
            assertThat(client.post(CREATE_URL, jsonContent).code()).isEqualTo(200);
        });
    }

    @Test
    void test_create_KO_bulbizarre_already_in() throws IOException {
        String jsonContent = new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS + "create_nami.json")));

        JavalinTest.test(app, (server, client) -> {
            assertThat(client.post(CREATE_URL, jsonContent).code()).isEqualTo(200);
            assertThat(client.post(CREATE_URL, jsonContent).code()).isEqualTo(400);
        });
    }

    @Test
    void test_create_KO_invalid_json() throws IOException {
        String invalidLifepoints = new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS + "create_invalid_wrong_lifepoints.json")));
        String invalidType = new String(Files.readAllBytes(Paths.get(SRC_TEST_RESOURCES_DATASETS + "create_invalid_wrong_role.json")));

        JavalinTest.test(app, (server, client) -> {
            assertThat(client.post(CREATE_URL, invalidLifepoints).code()).isEqualTo(400);
            assertThat(client.post(CREATE_URL, invalidType).code()).isEqualTo(400);
        });

    }


}
