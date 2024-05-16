package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import core.basesyntax.service.ValidationException;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelloWorldTest {
    private RegistrationServiceImpl registrationService;
    private User user = new User();
    private String login = "admin" + new Random().nextInt(6, 99999);
    private String password = "admin" + new Random().nextInt(6, 99999);
    private int userAge = new Random().nextInt(18, 99);

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void check_correct_login() {
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(userAge);

        assertEquals(registrationService.register(user), user);
    }

    @Test
    public void check_setid_after_registration_ok() {
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(userAge);
        registrationService.register(user);

        assertNotNull(user.getId());
    }

    @Test
    public void check_toshort_password_notOk() {
        String toShortPassword = "1";
        user.setLogin(login);
        user.setPassword(toShortPassword);

        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    public void check_not_valid_age_ok() {
        int failAge = 17;
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(failAge);

        assertThrows(ValidationException.class, () -> registrationService.register(user));
    }

    @Test
    public void check_two_users_notequals() {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("password1");
        user1.setAge(25);

        User user2 = new User();
        user2.setLogin("user2"); // Изменили login для второго пользователя
        user2.setPassword("password1");
        user2.setAge(25);

        assertNotEquals(user1, user2);
    }

    @Test
    public void check_user_hashcode() {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("password1");
        user1.setAge(25);

        User user2 = new User();
        user2.setLogin("user1");
        user2.setPassword("password1");
        user2.setAge(25);

        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test
    public void check_user_hashcode_with_notequal_objects() {
        User user1 = new User();
        user1.setLogin("user1");
        user1.setPassword("password1");
        user1.setAge(25);

        User user2 = new User();
        user2.setLogin("user1");
        user2.setPassword("password1");
        user2.setAge(26);

        assertNotEquals(user1.hashCode(), user2.hashCode());
    }
}
