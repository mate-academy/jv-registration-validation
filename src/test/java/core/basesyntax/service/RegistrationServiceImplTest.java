package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.InvalidUserDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN = "dimasmuk10@gmail.com";
    private static final int DEFAULT_AGE = 23;
    private static final String DEFAULT_PASSWORD = "1234qwerty";
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setAge(DEFAULT_AGE);
        user.setPassword(DEFAULT_PASSWORD);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_userLoginOnNull_notOk() {
        user.setLogin(null);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user), "Login can't be empty!");
    }

    @Test
    void register_userLoginHasTheSameLogin_notOk() {
        registrationService.register(user);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user),
                "Method should throw InvalidInputException if user with this login exists");
    }

    @Test
    void register_userPasswordIsNull_notOk() {
        user.setPassword(null);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user), "Password can't be empty!");
    }

    @Test
    void register_userPasswordIsEmpty_notOk() {
        user.setPassword("");
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user), "Password can't be empty!");
    }

    @Test
    void register_userPasswordLengthLessMin_notOk() {
        user.setPassword("12");
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user), "Password length can't be"
                        + "less then 6!");
    }

    @Test
    void register_userAgeOnNull_notOk() {
        user.setAge(null);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user), "Your age can't be null");
    }

    @Test
    void register_userAgeOlderThan18_notOk() {
        user.setAge(11);
        assertThrows(InvalidUserDataException.class,
                () -> registrationService.register(user), "Your age " + user.getAge() + " less"
                        + " then allowed: 18");
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(user, registrationService.register(user),
                "User must be registered");
    }
}
