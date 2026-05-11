package requestBuilder;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import payloadBuilder.UserPayload;

import static commons.Paths.BASE_URL;
import static io.restassured.RestAssured.given;

public class TestimonialRequestBuilder {

    public static String testimonialId;

    public static Response createTestimonial(String title, String content, int rating, boolean isPublic) {
        String apiPath = "/APIDEV/testimonials";

        Response response = given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .header("accept", "*/*")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + AdminRequestBuilder.adminToken)
                .body(UserPayload.createTestimonialPayload(title, content, rating, isPublic))
            .when()
                .post()
            .then()
                .extract().response();

        testimonialId = response.jsonPath().getString("data.Id");
        return response;
    }

    public static Response updateTestimonial(String title, String content, int rating) {
        String apiPath = "/APIDEV/testimonials/" + testimonialId;

        return given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .header("accept", "*/*")
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + AdminRequestBuilder.adminToken)
                .body(UserPayload.updateTestimonialPayload(title, content, rating))
            .when()
                .put()
            .then()
                .extract().response();
    }

    public static Response deleteTestimonial() {
        String apiPath = "/APIDEV/testimonials/" + testimonialId;

        return given()
                .baseUri(BASE_URL)
                .basePath(apiPath)
                .header("accept", "*/*")
                .header("Authorization", "Bearer " + AdminRequestBuilder.adminToken)
            .when()
                .delete()
            .then()
                .extract().response();
    }

}
