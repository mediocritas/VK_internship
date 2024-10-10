package tests;

import api.errors.ErrorMessage;
import api.group.counters.GroupCountersRequest;
import api.group.counters.GroupCountersResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.Config;
import counters.CounterType;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class GroupCountersTest {

    public static final String GROUP_ID = "70000008283655";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = Config.BASE_URL;
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    private GroupCountersResponseBody readExpectedResponseFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        File file = new File("src/test/resources/expected_counters.json");
        return objectMapper.readValue(file, GroupCountersResponseBody.class);
    }

    @Test
    public void testAllCounters() throws Exception {
        log.info("Отправка запроса для получения всех счетчиков с параметром");
        Response response = GroupCountersRequest.sendCountersRequest(GROUP_ID, CounterType.values());

        ObjectMapper objectMapper = new ObjectMapper();
        GroupCountersResponseBody actualCountersResponse = objectMapper.readValue(response.asString(), GroupCountersResponseBody.class);

        GroupCountersResponseBody expectedCountersResponse = readExpectedResponseFromJson();

        Assertions.assertEquals(expectedCountersResponse, actualCountersResponse, "Ответы не совпадают с ожидаемыми");
    }

    @Test
    public void testGroupCounterRequestWithOnlyOneCounter() throws Exception {
        log.info("Отправка запроса для получения счетчика MEMBERS");
        Response response = GroupCountersRequest.sendCountersRequest(GROUP_ID, CounterType.MEMBERS);

        ObjectMapper objectMapper = new ObjectMapper();
        GroupCountersResponseBody actualCountersResponse = objectMapper.readValue(response.asString(), GroupCountersResponseBody.class);

        GroupCountersResponseBody fullExpectedCountersResponse = readExpectedResponseFromJson();

        GroupCountersResponseBody expectedCountersResponse = GroupCountersResponseBody.builder()
                .counters(GroupCountersResponseBody.Counters.builder()
                        .members(fullExpectedCountersResponse.getCounters().getMembers())
                        .build())
                .build();

        assertEquals(expectedCountersResponse.getCounters().getMembers(), actualCountersResponse.getCounters().getMembers(),
                "MEMBERS counter mismatch");
    }


    @Test
    public void testThreeCounterTypes() throws Exception {
        log.info("Отправка запроса с тремя типами каунтеров: MEMBERS, MODERATORS, PINNED_TOPICS");
        Response response = GroupCountersRequest.sendCountersRequest(GROUP_ID,
                CounterType.MEMBERS, CounterType.MODERATORS, CounterType.PINNED_TOPICS);

        ObjectMapper objectMapper = new ObjectMapper();
        GroupCountersResponseBody actualCountersResponse = objectMapper.readValue(response.asString(), GroupCountersResponseBody.class);

        GroupCountersResponseBody fullExpectedCountersResponse = readExpectedResponseFromJson();

        GroupCountersResponseBody expectedCountersResponse = GroupCountersResponseBody.builder()
                .counters(GroupCountersResponseBody.Counters.builder()
                        .members(fullExpectedCountersResponse.getCounters().getMembers())
                        .moderators(fullExpectedCountersResponse.getCounters().getModerators())
                        .pinnedTopics(fullExpectedCountersResponse.getCounters().getPinnedTopics())
                        .build())
                .build();

        assertEquals(expectedCountersResponse, actualCountersResponse, "Ответы не совпадают с ожидаемыми для трёх типов счётчиков");
    }


    @Test
    public void testInvalidGroupId() throws Exception {
        String invalidGroupId = "invalid_test_id";
        log.info("Отправка запроса с неверным group_id");
        Response response = GroupCountersRequest.sendCountersRequest(invalidGroupId, CounterType.MEMBERS);

        ObjectMapper objectMapper = new ObjectMapper();
        ErrorMessage actualErrorMessage = objectMapper.readValue(response.asString(), ErrorMessage.class);

        ErrorMessage expectedErrorMessage =  ErrorMessage.createExpectedError(160, "PARAM_GROUP_ID : Invalid group_id [invalid_id]");

        assertEquals(expectedErrorMessage, actualErrorMessage, "Ожидалось сообщение об ошибке, но оно не совпадает");
    }

    @Test
    public void testEmptyGroupId() throws Exception {
        String emptyGroupId = "";
        log.info("Отправка запроса с пустым group_id");
        Response response = GroupCountersRequest.sendCountersRequest(emptyGroupId, CounterType.MEMBERS);

        ObjectMapper objectMapper = new ObjectMapper();
        ErrorMessage actualErrorMessage = objectMapper.readValue(response.asString(), ErrorMessage.class);

        ErrorMessage expectedErrorMessage =  ErrorMessage.createExpectedError(100, "PARAM : Missing required parameter group_id");

        assertEquals(expectedErrorMessage, actualErrorMessage, "Ожидалось сообщение об ошибке, но оно не совпадает");
    }

    @Test
    public void testNoGroupId() throws Exception {
        log.info("Отправка запроса без параметра group_id");
        Response response = RestAssured.given()
                .param("method", "group.getCounters")
                .param("application_key", Config.APPLICATION_KEY)
                .param("access_token", Config.ACCESS_TOKEN)
                .param("counterTypes", CounterType.MEMBERS.name())
                .when()
                .get()
                .then()
                .extract().response();

        ObjectMapper objectMapper = new ObjectMapper();
        ErrorMessage actualErrorMessage = objectMapper.readValue(response.asString(), ErrorMessage.class);

        ErrorMessage expectedErrorMessage =  ErrorMessage.createExpectedError(100, "PARAM : Missing required parameter group_id");

        assertEquals(expectedErrorMessage, actualErrorMessage, "Ожидалось сообщение об ошибке, но оно не совпадает");
    }

    @Test
    public void testInvalidCounterType() throws Exception {
        String invalidCounterType = "INVALID_COUNTER";
        log.info("Отправка запроса с несуществующим counterType");
        Response response = RestAssured.given()
                .param("method", "group.getCounters")
                .param("application_key", Config.APPLICATION_KEY)
                .param("access_token", Config.ACCESS_TOKEN)
                .param("group_id", GROUP_ID)
                .param("counterTypes", invalidCounterType)
                .when()
                .get()
                .then()
                .extract().response();

        ObjectMapper objectMapper = new ObjectMapper();
        ErrorMessage actualErrorMessage = objectMapper.readValue(response.asString(), ErrorMessage.class);

        ErrorMessage expectedErrorMessage =  ErrorMessage.createExpectedError(100, "PARAM : Invalid parameter counterTypes value  : [INVALID_COUNTER]");

        assertEquals(expectedErrorMessage, actualErrorMessage, "Ожидалось сообщение об ошибке, но оно не совпадает");
    }

    @Test
    public void testNoCounterTypes() throws Exception {
        log.info("Отправка запроса без параметра counterTypes");
        Response response = RestAssured.given()
                .param("method", "group.getCounters")
                .param("application_key", Config.APPLICATION_KEY)
                .param("access_token", Config.ACCESS_TOKEN)
                .param("group_id", GROUP_ID)
                .when()
                .get()
                .then()
                .extract().response();

        ObjectMapper objectMapper = new ObjectMapper();
        ErrorMessage actualErrorMessage = objectMapper.readValue(response.asString(), ErrorMessage.class);

        ErrorMessage expectedErrorMessage = ErrorMessage.createExpectedError(100, "PARAM : Missing required parameter counterTypes");

        assertEquals(expectedErrorMessage, actualErrorMessage, "Ожидалось сообщение об ошибке, но оно не совпадает");
    }
}

