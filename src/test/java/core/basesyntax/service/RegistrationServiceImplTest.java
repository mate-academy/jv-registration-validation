package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static final String VALID_LOGIN = "validLogin";
    private static final String VALID_PASSWORD = "validPassword";
    private static final int VALID_AGE = 20;

    private static final String SHORT_LOGIN = "abc";
    private static final String SHORT_PASSWORD = "123";
    private static final int UNDERAGE = 17;

    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_Ok() {
        User user = new User();
        user.setAge(VALID_AGE);
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);

        User registratedUser = registrationService.register(user);

        assertNotNull(registratedUser);
        assertEquals(VALID_LOGIN,registratedUser.getLogin());
    }

    @Test
    void register_existingLogin_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(VALID_AGE);
        registrationService.register(user);

        User user2 = new User();
        user2.setLogin(VALID_LOGIN);
        user2.setPassword(VALID_PASSWORD);
        user2.setAge(VALID_AGE);
        assertThrows(RegistrationException.class,
                () -> registrationService.register(user2));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(SHORT_LOGIN);
        user.setAge(VALID_AGE);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(SHORT_PASSWORD);
        user.setAge(VALID_AGE);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_underAge_notOk() {
        User user = new User();
        user.setLogin(VALID_LOGIN);
        user.setPassword(VALID_PASSWORD);
        user.setAge(UNDERAGE);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }
}
