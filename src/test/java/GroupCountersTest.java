import config.Config;
import counters.CounterTypes;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import request.GroupCountersRequest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class GroupCountersTest {

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = Config.BASE_URL;  // Используем BASE_URL из Config
    }

    @ParameterizedTest
    @EnumSource(CounterTypes.class)  // Тест будет выполнен для каждого элемента CounterTypes
    public void testCounterTypes(CounterTypes counterType) {
        // Создание подписи для запроса
        String sig = GroupCountersRequest.createSignature(
                Config.GROUP_ID,
                counterType.name(),
                Config.APPLICATION_KEY,
                Config.SESSION_SECRET_KEY
        );

        // Отправка запроса
        Response response = given()
                .param("method", "group.getCounters")
                .param("application_key", Config.APPLICATION_KEY)  // Используем APPLICATION_KEY из Config
                .param("access_token", Config.ACCESS_TOKEN)  // Используем ACCESS_TOKEN из Config
                .param("group_id", Config.GROUP_ID)  // Используем GROUP_ID из Config
                .param("counterTypes", counterType.name())
                .param("sig", sig)
                .when()
                .get()
                .then()
                .log().all()
                .statusCode(200)
                .body("result.counters." + counterType.name(), notNullValue())  // Проверяем, что значение не null
                .extract().response();

        System.out.println("Response for " + counterType.name() + ": " + response.asString());
    }
}
