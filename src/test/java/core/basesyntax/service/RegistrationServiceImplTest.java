package core.basesyntax.service;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class RegistrationServiceImplTest {

    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        Storage.people.clear();
    }

    private User createUser(String login, String password, Integer age) {
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(age);
        return user;
    }


    @Test
    void register_validUser_ok() {
        User user = createUser("Yehoria", "YehoriaPasswords", 18);

        User actual  = registrationService.register(user);

        assertEquals(user, actual);
    }

    @Test
    void register_existingLogin_notOk() {
        User user = createUser("Yehoria", "YehoriaPasswords", 20);
        registrationService.register(user);

        User another = createUser("Yehoria", "another", 25);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(another));
    }

    @Test
    void register_isNot18_notOk() {
        User user = createUser("Yehoria", "YehoriaPasswords", 17);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_is18_ok() {
        User user = createUser("Yehoria", "YehoriaPasswords", 18);
        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_shortPasswords_notOk() {
        User user = createUser("Yehoria", "Yeh", 18);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthSix_ok() {
        User user = createUser("Yehoria", "pass12", 20);

        assertDoesNotThrow(() -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = createUser("login", "password", 20);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginLengthSix_ok() {
        User user = createUser("login1", "password", 20);

        assertDoesNotThrow(() -> registrationService.register(user));
    }
}