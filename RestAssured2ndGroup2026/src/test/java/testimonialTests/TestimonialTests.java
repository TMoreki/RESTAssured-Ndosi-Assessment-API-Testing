package testimonialTests;

import com.github.javafaker.Faker;
import io.restassured.response.Response;
import requestBuilder.AdminRequestBuilder;
import requestBuilder.TestimonialRequestBuilder;
import requestBuilder.UserRequestBuilder;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.Assert;

import static org.hamcrest.CoreMatchers.equalTo;

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
    public void userRegistrationTest() {
        Response response = UserRequestBuilder.registerUser(firstName, lastName, email, password, groupId);
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 201);
    }

    @Test(priority = 2)
    public void adminLoginTest() {
        Response response = AdminRequestBuilder.adminLogin();
        response.then().log().all();

        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test(priority = 3)
    public void userApprovalTest() {
        AdminRequestBuilder.approveUser()
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test(priority = 4)
    public void userLoginTest() {
        UserRequestBuilder.loginUser(email, password)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test(priority = 5)
    public void createTestimonialTest() {
        Response response = TestimonialRequestBuilder.createTestimonial("string", "string", 5, true);
        response.then().log().all();
        System.out.println("Testimonial ID captured: " + TestimonialRequestBuilder.testimonialId);
        Assert.assertEquals(response.getStatusCode(), 201);
    }

    @Test(priority = 6)
    public void updateTestimonialTest() {
        TestimonialRequestBuilder.updateTestimonial("Updated Title", "Updated testimonial content", 5)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

    @Test(priority = 7)
    public void deleteTestimonialTest() {
        TestimonialRequestBuilder.deleteTestimonial()
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .body("success", equalTo(true));
    }

}
