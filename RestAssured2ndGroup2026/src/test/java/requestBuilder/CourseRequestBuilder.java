package requestBuilder;

import io.restassured.response.Response;

import static commons.Paths.BASE_URL;
import static io.restassured.RestAssured.given;

public class  CourseRequestBuilder {

    public static Response getCourses() {
        String apiPath = "/APIDEV/courses";

        return given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .header("accept", "*/*")
            .when()
                .get()
            .then()
                .extract().response();
    }

    public static Response getCoursesByFilter(String level, String search) {
        String apiPath = "/APIDEV/courses";

        return given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .header("accept", "*/*")
                .queryParam("level", level)
                .queryParam("search", search)
            .when()
                .get()
            .then()
                .extract().response();
    }

}
