package api.group.counters;

import static io.restassured.RestAssured.given;

public class GroupCountersRequest {
    public static Response sendCountersRequest(CounterTypes counterType, String groupId) {
        return given()
                .param("method", "group.getCounters")
                .param("application_key", Config.APPLICATION_KEY)
                .param("access_token", Config.ACCESS_TOKEN)
                .param("group_id", groupId)  // Параметризованное значение groupId
                .param("counterTypes", counterType.name())  // Параметризованное значение counterTypes
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().response();
    }
}
