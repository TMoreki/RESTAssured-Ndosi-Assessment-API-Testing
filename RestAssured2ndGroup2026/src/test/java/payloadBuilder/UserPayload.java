package payloadBuilder;

import org.json.simple.JSONObject;
import org.testng.annotations.Test;

public class UserPayload {


    public static JSONObject userLoginPayload(String email, String password) {
        JSONObject userLogin = new JSONObject(); //instantiate userLogin object of type JSONObject
        userLogin.put("email", email); //putting key-value pairs in the userLogin object
        userLogin.put("password", password);

        return userLogin;
    }

    public static JSONObject registerUserPayload(String firstName, String lastName, String email, String password, String groupId) {
        JSONObject registerUser = new JSONObject();
        registerUser.put("firstName", firstName);
        registerUser.put("lastName", lastName);
        registerUser.put("email", email);
        registerUser.put("password", password);
        registerUser.put("confirmPassword", password);
        registerUser.put("groupId", groupId);

        return registerUser;
    }

    public static JSONObject createTestimonialPayload(String title, String content, int rating, boolean isPublic) {
        JSONObject payload = new JSONObject();
        payload.put("title", title);
        payload.put("content", content);
        payload.put("rating", rating);
        payload.put("isPublic", isPublic);
        return payload;
    }

    public static JSONObject updateTestimonialPayload(String title, String content, int rating) {
        JSONObject payload = new JSONObject();
        payload.put("title", title);
        payload.put("content", content);
        payload.put("rating", rating);
        return payload;
    }

}
