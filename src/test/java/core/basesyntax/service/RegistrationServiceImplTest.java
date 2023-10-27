package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.cases.of.exceptions.ExceptionDuringRegistration;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int CORRECT_AGE = 20;
    private static final int INCORRECT_AGE = 15;
    private static final int MIN_AGE = 18;
    private static final String CORRECT_PASSWORD = "password123";
    private static final String CORRECT_LOGIN = "testUser2";
    private static final String INCORRECT_LOGIN_PASSWORD = "short";
    private final RegistrationServiceImpl registrationService = new RegistrationServiceImpl();
    private User expected;

    @BeforeEach
    void setUp() {
        expected = new User();
        Storage.people.clear();
    }

    @Test
    void register_ValidUser() {
        expected.setLogin(CORRECT_LOGIN);
        expected.setPassword(CORRECT_PASSWORD);
        expected.setAge(CORRECT_AGE);

        User registeredUser = registrationService.register(expected);

        assertNotNull(registeredUser);
    }

    @Test
    void register_NotNullUser() {
        assertThrows(ExceptionDuringRegistration.class, ()
                -> registrationService.register(null));
    }

    @Test
    void register_shortLogin_NotOk() {
        // Given
        expected.setLogin(INCORRECT_LOGIN_PASSWORD);
        expected.setPassword(CORRECT_PASSWORD);
        expected.setAge(CORRECT_AGE);

        assertThrows(ExceptionDuringRegistration.class, ()
                -> registrationService.register(expected));
    }

    @Test
    void register_UnderAgeUser_NotOk() {
        expected.setLogin(CORRECT_LOGIN);
        expected.setPassword(CORRECT_PASSWORD);
        expected.setAge(INCORRECT_AGE);

        assertThrows(ExceptionDuringRegistration.class, ()
                -> registrationService.register(expected));
    }

    @Test
    void register_DuplicateUser_NotOk() {
        expected.setLogin(CORRECT_LOGIN);
        expected.setPassword(CORRECT_PASSWORD);
        expected.setAge(CORRECT_AGE);

        registrationService.register(expected);

        User user = new User();
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);

        assertThrows(ExceptionDuringRegistration.class, ()
                -> registrationService.register(user));
    }

    @Test
    void register_ValidUserWithMinAge_NotOk() {
        expected.setLogin("newUser");
        expected.setPassword(CORRECT_PASSWORD);
        expected.setAge(MIN_AGE);

        User registeredUser = registrationService.register(expected);

        assertNotNull(registeredUser);
    }
}
