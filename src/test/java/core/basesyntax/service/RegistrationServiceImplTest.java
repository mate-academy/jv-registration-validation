package core.basesyntax.service;

import core.basesyntax.cases.of.exceptions.ExceptionDuringRegistration;
import core.basesyntax.cases.of.exceptions.NullExceptionDuringRegistration;
import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int CORRECT_AGE = 20;
    private static final int INCORRECT_AGE = 15;
    private static final int MIN_AGE = 18;
    private static final String CORRECT_PASSWORD = "password123";
    private static final String CORRECT_LOGIN = "testUser2";
    private static final String INCORRECT_LOGIN_PASSWORD = "short";
    private static RegistrationServiceImpl newRegistrationMethod;
    private User expected;

    @BeforeEach
    void setUp() {
        newRegistrationMethod = new RegistrationServiceImpl();
        expected = new User();
        Storage.people.clear();
    }

    @Test
    void register_ValidUser() {
        expected.setLogin(CORRECT_LOGIN);
        expected.setPassword(CORRECT_PASSWORD);
        expected.setAge(CORRECT_AGE);
        User registeredUser = newRegistrationMethod.register(expected);
        Assertions.assertNotNull(registeredUser);
    }

    @Test
    void register_NotNullUser() {
        Assertions.assertThrows(NullExceptionDuringRegistration.class, ()
                -> newRegistrationMethod.register(null));
    }

    @Test
    void register_shortLogin() {
        expected.setLogin(INCORRECT_LOGIN_PASSWORD);
        expected.setPassword(CORRECT_PASSWORD);
        expected.setAge(CORRECT_AGE);
        Assertions.assertThrows(ExceptionDuringRegistration.class, ()
                -> newRegistrationMethod.register(expected));
    }

    @Test
    void register_UnderAgeUser() {
        expected.setLogin(CORRECT_LOGIN);
        expected.setPassword(CORRECT_PASSWORD);
        expected.setAge(INCORRECT_AGE);
        Assertions.assertThrows(ExceptionDuringRegistration.class, ()
                -> newRegistrationMethod.register(expected));
    }

    @Test
    void register_DuplicateUser() {
        expected.setLogin(CORRECT_LOGIN);
        expected.setPassword(CORRECT_PASSWORD);
        expected.setAge(CORRECT_AGE);
        newRegistrationMethod.register(expected);
        User user = new User();
        user.setLogin(CORRECT_LOGIN);
        user.setPassword(CORRECT_PASSWORD);
        user.setAge(CORRECT_AGE);
        Assertions.assertThrows(ExceptionDuringRegistration.class, ()
                -> newRegistrationMethod.register(user));
    }

    @Test
    void register_ValidUserWithMinAge() {
        expected.setLogin("newUser");
        expected.setPassword(CORRECT_PASSWORD);
        expected.setAge(MIN_AGE);
        User registeredUser = newRegistrationMethod.register(expected);
        Assertions.assertNotNull(registeredUser);
    }
}
