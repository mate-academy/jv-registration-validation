package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private final RegistrationService registrationService = new RegistrationServiceImpl();
    private User firstUser;
    private User secondUser;
    private List<User> people = new ArrayList<>();

    @BeforeEach
    void setUp() {
        firstUser = new User();
        firstUser.setLogin("testLogin");
        secondUser = new User();
        secondUser.setLogin("testLogin");
    }

    @Test
    void userWithSuchLogin() {
        User user1 = new User();
        user1.setLogin("User1234");

        boolean userExists = false;
        for (User user : people) {
            if (user1.getLogin().equals(user.getLogin())) {
                userExists = true;
                break;
            }
        }

        assertFalse(userExists, "User with such login already exists");
    }

    @Test
    void userLoginLeastThan6_Ok() {
        firstUser.setLogin("123456");
        String login = firstUser.getLogin();
        assertTrue(login.length() >= 6, "user's login is at least 6 characters");
    }

    @Test
    void userPassvordLeastThan6_Ok() {
        firstUser.setPassword("1234567");
        String password = firstUser.getPassword();
        assertTrue(password.length() >= 6, "user's password is at least 6 characters");
    }

    @Test
    void userAgeLeastThan10_Ok() {
        firstUser.setAge(19);
        int age = firstUser.getAge();
        assertTrue(age >= 18, "user's age is at least 18 years old");
    }

    @Test
    void passwordIsNotEmpty() {
        firstUser.setPassword("123456");
        String password = firstUser.getPassword();
        assertFalse(password.isEmpty(), "user's password should not be empty");
    }

    @Test
    void loginIsNotEmpty() {
        firstUser.setLogin("testLogin");
        String login = firstUser.getLogin();
        assertFalse(login.isEmpty(), "user's login should not be empty");
    }

    @Test
    void ageIsNotEmpty() {
        firstUser.setAge(null);
        String age = firstUser.getLogin();
        assertFalse(age.isEmpty(), "user's age should not be empty");
    }

}
