package api.group.counters;

import api.auth.RequestSpecWithAuthTokens;
import counters.CounterType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GroupCountersRequest {

    public static final String METHOD = "method";
    public static final String GROUP_GET_COUNTERS = "group.getCounters";
    public static final String GROUP_ID = "group_id";
    public static final String COUNTER_TYPES = "counterTypes";

    public static Response sendCountersRequest(String groupId, CounterType... counterTypes) {
        List<String> counterTypeNames = Arrays.stream(counterTypes)
                .map(Enum::name)
                .collect(Collectors.toList());
        String counterTypesParam = String.join(",", counterTypeNames);

        return RequestSpecWithAuthTokens.getRequestSpecWithAuthToken()
                .param(METHOD, GROUP_GET_COUNTERS)
                .param(GROUP_ID, groupId)
                .param(COUNTER_TYPES, counterTypesParam)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract().response();
    }
}
