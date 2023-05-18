package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String DEFAULT_LOGIN_FOR_TESTS = "validLogin";
    private static final String DEFAULT_PASSWORD_FOR_TESTS = "validPassword";
    private static final int DEFAULT_AGE_FOR_TESTS = 18;
    private static RegistrationServiceImpl registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin(DEFAULT_LOGIN_FOR_TESTS);
        user.setPassword(DEFAULT_PASSWORD_FOR_TESTS);
        user.setAge(DEFAULT_AGE_FOR_TESTS);
    }

    @Test
    void userExistsAndLoginAndPasswordAreValid_Ok() {
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user, registeredUser);
        assertTrue(Storage.people.contains(registeredUser));
    }

    @Test
    void userDoesNotExist_NotOk() {
        User actual = new User();
        assertFalse(Storage.people.contains(actual));
    }

    @Test
    void loginIsNull_NotOk() {
        user.setLogin(null);
        assertRegistrationException(user, "Incorrect login or password, please try again");
    }

    @Test
    void loginIsEmpty_NotOk() {
        user.setLogin("");
        assertRegistrationException(user, "Incorrect login or password, please try again");
    }

    @Test
    void loginLengthIsOne_NotOk() {
        user.setLogin("s");
        assertRegistrationException(user, "The length of the login cannot be less "
                + "than the minimal");
    }

    @Test
    void loginLengthIsTwo_NotOk() {
        user.setLogin("sh");
        assertRegistrationException(user, "The length of the login cannot be less "
                + "than the minimal");
    }

    @Test
    void loginLengthIsThree_NotOk() {
        user.setLogin("sho");
        assertRegistrationException(user, "The length of the login cannot be less "
                + "than the minimal");
    }

    @Test
    void loginLengthIsFour_NotOk() {
        user.setLogin("shor");
        assertRegistrationException(user, "The length of the login cannot be less "
                + "than the minimal");
    }

    @Test
    void loginIsLessThanMinimalLength_NotOk() {
        user.setLogin("short");
        assertRegistrationException(user, "The length of the login cannot be less "
                + "than the minimal");
    }

    @Test
    void loginLengthIsSix_Ok() {
        user.setLogin("normal");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void loginLengthIsEight_Ok() {
        user.setLogin("isNormal");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void passwordIsNull_NotOk() {
        user.setPassword(null);
        assertRegistrationException(user, "Incorrect login or password, please try again");
    }

    @Test
    void passwordIsEmpty_NotOk() {
        user.setPassword("");
        assertRegistrationException(user, "Incorrect login or password, please try again");
    }

    @Test
    void passwordLengthIsOne_NotOk() {
        user.setPassword("s");
        assertRegistrationException(user, "The length of the password cannot be less "
                + "than the minimal");
    }

    @Test
    void passwordLengthIsTwo_NotOk() {
        user.setPassword("sh");
        assertRegistrationException(user, "The length of the password cannot be less "
                + "than the minimal");
    }

    @Test
    void passwordLengthIsThree_NotOk() {
        user.setPassword("sho");
        assertRegistrationException(user, "The length of the password cannot be less "
                + "than the minimal");
    }

    @Test
    void passwordLengthIsFour_NotOk() {
        user.setPassword("shor");
        assertRegistrationException(user, "The length of the password cannot be less "
                + "than the minimal");
    }

    @Test
    void passwordLengthIsFive_NotOk() {
        user.setPassword("short");
        assertRegistrationException(user, "The length of the password cannot be less "
                + "than the minimal");
    }

    @Test
    void passwordLengthIsSix_Ok() {
        user.setPassword("normal");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void passwordLengthIsEight_Ok() {
        user.setPassword("isNormal");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void ageIsLessThanMinimal_NotOk() {
        user.setAge(17);
        assertRegistrationException(user, "The user's age is less "
                + "than the minimal age allowed for registration");
    }

    @Test
    void ageIsNegative_NotOk() {
        user.setAge(-33);
        assertRegistrationException(user, "The user's age cannot be negative");
    }

    @Test
    void ageIsZero_NotOk() {
        user.setAge(0);
        assertRegistrationException(user, "The user's age cannot be zero");
    }

    @Test
    void ageAllowed_Ok() {
        user.setAge(33);
        assertEquals(user, registrationService.register(user));
    }

    private void assertRegistrationException(User user, String errorMessage) {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user), errorMessage);
    }
}
