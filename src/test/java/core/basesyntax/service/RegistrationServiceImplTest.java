package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final User FIRST_VALID_TEST_USER = new User();
    private static final User SECOND_VALID_TEST_USER = new User();
    private static final User NULL_LOGIN_TEST_USER = new User();
    private static final User NULL_PASSWORD_TEST_USER = new User();
    private static final User SAME_FIRST_VALID_TEST_USER = new User();
    private static final User INVALID_LOGIN_TEST_USER = new User();
    private static final User INVALID_PASSWORD_TEST_USER = new User();
    private static final User INVALID_AGE_TEST_USER = new User();
    private static RegistrationService registrationService;

    @BeforeAll
    public static void set() {
        FIRST_VALID_TEST_USER.setLogin("Qwerty");
        FIRST_VALID_TEST_USER.setPassword("123456");
        FIRST_VALID_TEST_USER.setAge(19);

        SECOND_VALID_TEST_USER.setLogin("Abigale");
        SECOND_VALID_TEST_USER.setPassword("123456");
        SECOND_VALID_TEST_USER.setAge(30);

        NULL_LOGIN_TEST_USER.setPassword("123456");
        NULL_LOGIN_TEST_USER.setAge(18);

        NULL_PASSWORD_TEST_USER.setLogin("Qwerty");
        NULL_PASSWORD_TEST_USER.setAge(19);

        SAME_FIRST_VALID_TEST_USER.setLogin("Qwerty");
        SAME_FIRST_VALID_TEST_USER.setPassword("123456");
        SAME_FIRST_VALID_TEST_USER.setAge(19);

        INVALID_LOGIN_TEST_USER.setLogin("Bob");
        INVALID_LOGIN_TEST_USER.setPassword("123456");
        INVALID_LOGIN_TEST_USER.setAge(20);

        INVALID_PASSWORD_TEST_USER.setLogin("Bobby Brown");
        INVALID_PASSWORD_TEST_USER.setPassword("12345");
        INVALID_PASSWORD_TEST_USER.setAge(18);

        INVALID_AGE_TEST_USER.setLogin("Andrew");
        INVALID_AGE_TEST_USER.setPassword("123456");
        INVALID_AGE_TEST_USER.setAge(16);
    }

    @BeforeEach
    public void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    public void register_ValidUserRegistration_Ok() {
        registrationService.register(FIRST_VALID_TEST_USER);
        registrationService.register(SECOND_VALID_TEST_USER);
        int actual = Storage.people.size();
        assertEquals(2, actual,
                "Valid users was not added");
    }

    @Test
    public void register_EmptyLoginInput_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(NULL_LOGIN_TEST_USER);
        }, "No exception was thrown");
    }

    @Test
    public void register_EmptyPasswordInput_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(NULL_PASSWORD_TEST_USER);
        }, "No exception was thrown");
    }

    @Test
    public void register_SameLoginUser_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            Storage.people.add(FIRST_VALID_TEST_USER);
            registrationService.register(SAME_FIRST_VALID_TEST_USER);
        }, "No exception was thrown");
    }

    @Test
    public void register_IncorrectLoginRegistration_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(INVALID_LOGIN_TEST_USER);
        }, "No exception was thrown");
    }

    @Test
    public void register_IncorrectPasswordRegistration_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(INVALID_PASSWORD_TEST_USER);
        }, "No exception was thrown");
    }

    @Test
    public void register_IncorrectAgeRegistration_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(INVALID_AGE_TEST_USER);
        }, "No exception was thrown");
    }
}
