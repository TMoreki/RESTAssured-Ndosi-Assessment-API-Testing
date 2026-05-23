package testimonialTests;

import com.github.javafaker.Faker;
import io.qameta.allure.*;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;
import requestBuilder.AdminRequestBuilder;
import requestBuilder.TestimonialRequestBuilder;
import requestBuilder.UserRequestBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;

import static org.hamcrest.CoreMatchers.equalTo;

@Epic("Testimonial API")
@Feature("Testimonial Management")
public class TestimonialTests {

    static String firstName;
    static String lastName;
    static String email;
    static String password;
    static String groupId;

    static Faker faker = new Faker();

    @BeforeClass
    public static void setUpData() {
        firstName = faker.name().firstName();
        lastName = faker.name().lastName();
        email = "Group2" + faker.internet().emailAddress();
        password = "7654321!";
        groupId = "5328c91e-fc40-11f0-8e00-5000e6331276";
    }

    @Test(priority = 1)
    @Story("User Registration")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Register a new user with generated fake data and verify 201 Created response")
    public void userRegistrationTest() {
        Response response = UserRequestBuilder.registerUser(firstName, lastName, email, password, groupId);
        response.then().log().all();

        Allure.addAttachment("Request Body", "application/json",
                "{ \"firstName\": \"" + firstName + "\", \"lastName\": \"" + lastName + "\", \"email\": \"" + email + "\", \"groupId\": \"" + groupId + "\" }");
        Allure.addAttachment("Response Body", "application/json", response.getBody().asString());
        Allure.addAttachment("Status Code", String.valueOf(response.getStatusCode()));

        Assert.assertEquals(response.getStatusCode(), 201);
    }

    @Test(priority = 2)
    @Story("Admin Authentication")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Admin logs in and retrieves a bearer token for subsequent authorized requests")
    public void adminLoginTest() {
        Response response = AdminRequestBuilder.adminLogin();
        response.then().log().all();

        Allure.addAttachment("Response Body", "application/json", response.getBody().asString());
        Allure.addAttachment("Status Code", String.valueOf(response.getStatusCode()));

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    @Story("User Approval")
    @Severity(SeverityLevel.NORMAL)
    @Description("Admin approves the registered user so they can log in and use the system")
    public void userApprovalTest() {
        Response response = AdminRequestBuilder.approveUser();
        response.then().log().all();

        Allure.addAttachment("Approved User ID", UserRequestBuilder.registeredUserId);
        Allure.addAttachment("Response Body", "application/json", response.getBody().asString());

        response.then()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test(priority = 4)
    @Story("User Authentication")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Registered and approved user logs in and receives a valid token")
    public void userLoginTest() {
        Response response = UserRequestBuilder.loginUser(email, password);
        response.then().log().all();

        Allure.addAttachment("Login Email", email);
        Allure.addAttachment("Response Body", "application/json", response.getBody().asString());

        response.then()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test(priority = 5)
    @Story("Create Testimonial")
    @Severity(SeverityLevel.NORMAL)
    @Description("Create a new testimonial with title, content, rating and public flag; verify 201 and capture ID")
    public void createTestimonialTest() {
        Response response = TestimonialRequestBuilder.createTestimonial("string", "string", 5, true);
        response.then().log().all();

        Allure.addAttachment("Response Body", "application/json", response.getBody().asString());
        Allure.addAttachment("Captured Testimonial ID", TestimonialRequestBuilder.testimonialId);

        System.out.println("Testimonial ID captured: " + TestimonialRequestBuilder.testimonialId);
        Assert.assertEquals(response.getStatusCode(), 201);
    }

    @Test(priority = 6)
    @Story("Update Testimonial")
    @Severity(SeverityLevel.NORMAL)
    @Description("Update the previously created testimonial's title, content and rating; verify 200 success")
    public void updateTestimonialTest() {
        Response response = TestimonialRequestBuilder.updateTestimonial("Updated Title", "Updated testimonial content", 5);
        response.then().log().all();

        Allure.addAttachment("Updated Testimonial ID", TestimonialRequestBuilder.testimonialId);
        Allure.addAttachment("Response Body", "application/json", response.getBody().asString());

        response.then()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test(priority = 7)
    @Story("Delete Testimonial")
    @Severity(SeverityLevel.NORMAL)
    @Description("Delete the testimonial by ID and verify 200 success response")
    public void deleteTestimonialTest() {
        Response response = TestimonialRequestBuilder.deleteTestimonial();
        response.then().log().all();

        Allure.addAttachment("Deleted Testimonial ID", TestimonialRequestBuilder.testimonialId);
        Allure.addAttachment("Response Body", "application/json", response.getBody().asString());

        response.then()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }
}
