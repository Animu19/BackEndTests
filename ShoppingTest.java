package org.example.Lesson4;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ShoppingTest extends AbstractTest {

    ResponseSpecification responseSpecification = null;
    RequestSpecification requestSpecification = null;


    @BeforeEach
    @DisplayName("Базовые проверки на все тесты")
    void beforeTest() {
        responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(5000L))
                .expectHeader("Connection", "keep-alive")
                .log(LogDetail.HEADERS)
                .build();

        requestSpecification = new RequestSpecBuilder()
                .addQueryParam("apiKey", getApiKey())
                .addQueryParam("hash", getHash())
                .build();
    }


    @Test
    void getListShopTest() {
        given()
                .spec(requestSpecification)
                .contentType("application/json")
                .when()
                .get(getBaseUrl() + "mealplanner/geekbrains/shopping-list")
                .then()
                .assertThat()
                .spec(responseSpecification);
    }

    String id;

    @Test
    void addShopingTest() {
        JsonAddShoping jsonAddShoping = new JsonAddShoping("1 package baking powder", "Baking", true);
        String body = jsonAddShoping.json(jsonAddShoping);
        id = given()
                .spec(requestSpecification)
                .body(body)
                .when()
                .post(getBaseUrl() + "mealplanner/geekbrains/shopping-list/items")
                .then()
                .assertThat()
                .spec(responseSpecification)
                .log().all()
                .extract()
                .jsonPath()
                .get("id")
                .toString();
    }

    @Test
    void deletShopping() {
        given()
                .spec(requestSpecification)
                .delete(getBaseUrl() + "mealplanner/geekbrains/shopping-list/items/" + id)
                .then()
                .assertThat()
                .spec(responseSpecification)
                .log().body();
    }


}
