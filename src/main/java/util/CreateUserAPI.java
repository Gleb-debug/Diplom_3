package util;

import io.qameta.allure.Param;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.datafaker.Faker;
import pages.dto.CreateTestUser;


import static io.qameta.allure.model.Parameter.Mode.HIDDEN;
import static io.restassured.RestAssured.given;
import static java.net.HttpURLConnection.HTTP_OK;

public class CreateUserAPI {
    public static final String BASE_URL = "https://stellarburgers.nomoreparties.site/api/";
    public static final String REGISTER_PATH = "auth/register";
    public static final String DELETE_PATH = "auth/user";
    public static final String AUTH_PATH = "auth/login";
    private static final Faker faker = new Faker();

    // Генерируем нового пользователя
    @Step("Генерируем нового пользователя")
    public static CreateTestUser randomUser() {
        return new CreateTestUser(
                faker.name().toString(),
                faker.internet().password(),
                faker.internet().emailAddress()
        );
    }

    @Step("Создаем пользователя и извлекаем токен")
    public static String createUser(CreateTestUser user) {

        ValidatableResponse response = given()
                .log().all()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(user)
                .when()
                .post(REGISTER_PATH)
                .then()
                .log().all()
                .statusCode(HTTP_OK);
        return response.extract().path("accessToken");
    }

    //Логинимся пользователем и получаем токен
    @Step("Логинимся пользователем и получаем токен")
    public static String logInAndGetToken(CreateTestUser user) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(BASE_URL)
                .body(user)
                .when()
                .post(AUTH_PATH)
                .then()
                .extract()
                .path("accessToken");
    }

    @Step("Удаляем тестового пользователя")
    public static ValidatableResponse deleteUser(@Param(mode = HIDDEN) String accessToken) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", accessToken)
                .when()
                .delete(BASE_URL + DELETE_PATH)
                .then()
                .log().all();
    }
}
