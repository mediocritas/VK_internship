package api.auth;

import config.Config;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class RequestSpecWithAuthTokens {

    public static RequestSpecification getRequestSpecWithAuthToken() {
        return given()
                .param("application_key", Config.APPLICATION_KEY)
                .param("access_token", Config.ACCESS_TOKEN);
    }
}