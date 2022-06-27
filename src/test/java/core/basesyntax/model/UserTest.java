package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserTest {
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
    }

    @Test
    void getId_Ok() {
        Long testId = 123L;
        testUser.setId(testId);
        Long actual = testUser.getId();
        assertEquals(testId, actual, "Error getting user Id");
    }

    @Test
    void getLogin_Ok() {
        String testLogin = "Paul";
        testUser.setLogin(testLogin);
        String actual = testUser.getLogin();
        assertEquals(testLogin, actual, "Error getting user Login");
    }

    @Test
    void getPassword_Ok() {
        String testPassword = "Paul";
        testUser.setPassword(testPassword);
        String actual = testUser.getPassword();
        assertEquals(testPassword, actual, "Error getting user Password");
    }

    @Test
    void getAge_Ok() {
        Integer testAge = 34;
        testUser.setAge(testAge);
        Integer actual = testUser.getAge();
        assertEquals(testAge, actual, "Error getting user Age");
    }
}
