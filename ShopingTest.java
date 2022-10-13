package org.example.Lesson4;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ShopingTest extends AbstractTest {

    ResponseSpecification responseSpecification = null;
    RequestSpecification requestSpecification = null;


    @BeforeEach
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
    @DisplayName("Проверка, что статус кода равен:'200' ")
    void ComplexSerchStatusCodeTest() {
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
    @DisplayName("Добавление плана и его удаление ")
    void CompTest() {
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

    @AfterEach
    void tearDown() {
        given()
                .spec(requestSpecification)
                .delete(getBaseUrl() + "mealplanner/geekbrains/shopping-list/items/" + id)
                .then()
                .assertThat()
                .spec(responseSpecification)
                .log().body();
    }


}
