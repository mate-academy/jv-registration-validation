package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.dao.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class UserRegistrationTest {
    private static RegistrationServiceImpl registrationService;
    private static int ageValid;
    private static int ageNotValid;
    private static String passwordValid;
    private static String pasWorldNotValid;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        ageValid = 19;
        ageNotValid = 16;
        passwordValid = "asdfgh";
        pasWorldNotValid = "asd";
    }

    @Test
    void loginIsAlreadyRegistered_notOk() {
        User user1 = new User();
        user1.setLogin("login");
        user1.setAge(ageValid);
        user1.setPassword(passwordValid);
        User user2 = new User();
        user2.setLogin("login");
        user2.setAge(ageValid);
        user2.setPassword(passwordValid);
        registrationService.register(user1);
        try {
            registrationService.register(user2);
        } catch (RegistrationException e) {
            return;
        }
        fail("UsersDataIsNotValidException shout throw if login is already registered");
    }

    @Test
    void loginIsNewerRegistered_ok() {
        User user1 = new User();
        user1.setLogin("user");
        user1.setAge(ageValid);
        user1.setPassword(passwordValid);
        User user2 = new User();
        user2.setLogin("new user");
        user2.setAge(ageValid);
        user2.setPassword(passwordValid);
        registrationService.register(user1);
        assertEquals(registrationService.register(user2), user2);
    }

    @Test
    void ageIsLessMinimalAge_notOk() {
        User user1 = new User();
        user1.setLogin("new login");
        user1.setAge(ageNotValid);
        user1.setPassword(passwordValid);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void userIsOlderMinimalAge_ok() {
        User user1 = new User();
        user1.setLogin("minimal age");
        user1.setAge(ageValid);
        user1.setPassword(passwordValid);
        assertEquals(registrationService.register(user1), user1);
    }

    @Test
    void passwordIsLessMinimumCharacterSize_notOk() {
        User user1 = new User();
        user1.setLogin("minimal password");
        user1.setAge(ageValid);
        user1.setPassword(pasWorldNotValid);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user1);
        });
    }

    @Test
    void passwordIsLargeMinimumCharacterSize_Ok() {
        User user1 = new User();
        user1.setLogin("large password");
        user1.setAge(ageValid);
        user1.setPassword(passwordValid);
        assertEquals(registrationService.register(user1), user1);
    }

    @Test
    void register_nullLogin_notOk() {
        User user1 = new User();
        user1.setLogin(null);
        user1.setAge(ageValid);
        user1.setPassword(passwordValid);
        assertNull(registrationService.register(user1));
    }

    @Test
    void register_nullAge_notOk() {
        User user1 = new User();
        user1.setLogin("null age");
        user1.setAge(null);
        user1.setPassword(passwordValid);
        assertNull(registrationService.register(user1));
    }

    @Test
    void register_nullPassword_notOk() {
        User user1 = new User();
        user1.setLogin("null password");
        user1.setAge(ageValid);
        user1.setPassword(null);
        assertNull(registrationService.register(user1));
    }
}
