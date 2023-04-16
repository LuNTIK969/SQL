package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DataHelper {

    private static Faker faker = new Faker(new Locale("en"));

    private DataHelper() {
    }

    public static  AuthInfo authWithTestUser() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static String randomPass(){
        return faker.name().username();
    }

    public static String randomLogin(){
        return faker.internet().password();
    }

    public static AuthInfo generateRandomUser() {
        return new AuthInfo(randomLogin(), randomPass());
    }

    public static VerificationCode randomCode() {
        return new VerificationCode(faker.numerify("######"));
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static void assertStatus(String login) {
        String expected = "blocked";
        String actual = SQLHelper.userStatus(login);
        assertEquals(expected, actual);
    }
}
