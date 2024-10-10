import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.restassured.RestAssured.given;

public class APITest {

    private static final Logger logger = LoggerFactory.getLogger(APITest.class);

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "https://api.ok.ru";  // Укажи базовый URL API
        logger.info("Base URI установлен: " + RestAssured.baseURI);
    }

    @Test
    public void testGetFriends() {
        logger.info("Тест: получение списка друзей");

        String applicationKey = "CLJENOLGDIHBABABA"; // Укажи ключ приложения
        String accessToken = "-n-GugZFeFHvYIdmhXXZj0Jbp5bFPEGc2A4u2ZXZbu64AEYTdJMkJ2Y5MNpKLvgKurctFoCNjcX4SxqHIhSQ"; // Токен доступа

        given()
                .param("application_key", applicationKey)
                .param("method", "friends.get")
                .param("access_token", accessToken)
                .when()
                .get("/fb.do")
                .then()
                .log().all()  // Логирование запроса и ответа
                .statusCode(200);

        logger.info("Тест завершён.");
    }
}
