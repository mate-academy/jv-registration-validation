package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String REALLY_STRONG_PASSWORD = "123456789";
    private static final int REALLY_COOL_AGE = 30;
    private static final User PREPARED_FOR_CHECKING_LOGIN_USER_1 =
            new User("Michael", REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static final User PREPARED_FOR_CHECKING_LOGIN_USER_2 =
            new User("Patrick", REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static final User PREPARED_FOR_CHECKING_LOGIN_USER_3 =
            new User("Jackson", REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static final User NULL_USER = null;
    private static final User INVALID_PASSWORD_USER_1 = new User("Olivia", "12345", REALLY_COOL_AGE);
    private static final User INVALID_PASSWORD_USER_2 = new User("Charlotte", "", REALLY_COOL_AGE);
    private static final User VALID_PASSWORD_USER_1 = new User("Sophia", "123456", REALLY_COOL_AGE);
    private static final User VALID_PASSWORD_USER_2 = new User("Isabella", "1234567", REALLY_COOL_AGE);
    private static final User NULL_PASSWORD_USER = new User("William", null, REALLY_COOL_AGE);
    private static final User INVALID_AGE_USER_1 = new User("Benjamin", REALLY_STRONG_PASSWORD, 16);
    private static final User INVALID_AGE_USER_2 = new User("Theodore", REALLY_STRONG_PASSWORD, 0);
    private static final User INVALID_AGE_USER_3 = new User("Oliver", REALLY_STRONG_PASSWORD, -7);
    private static final User VALID_AGE_USER_1 = new User("Sebastian", REALLY_STRONG_PASSWORD, 19);
    private static final User VALID_AGE_USER_2 = new User("Samuel", REALLY_STRONG_PASSWORD, 18);
    private static final User NULL_LOGIN_USER =
            new User(null, REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static final User INVALID_LOGIN_USER_1 =
            new User("Carl", REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static final User INVALID_LOGIN_USER_2 =
            new User("", REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static final User VALID_LOGIN_USER_1 = new
            User("Polina", REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static final User VALID_LOGIN_USER_2 = new
            User("Tetiana", REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static final User SAME_NAME_WITH_USER_1 = new
            User("Michael", REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static final User SAME_NAME_WITH_USER_2 = new
            User("Patrick", REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static final User SAME_NAME_WITH_USER_3 = new
            User("Jackson", REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static RegistrationService registrationService;

    @BeforeAll
    static void registrationServiceInitialization() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_invalidPasswordUsers_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(INVALID_PASSWORD_USER_1);
            registrationService.register(INVALID_PASSWORD_USER_2);
        });
    }

    @Test
    void register_validPasswordUsers_ok() {
        User actual1 = registrationService.register(VALID_PASSWORD_USER_1);
        User actual2 = registrationService.register(VALID_PASSWORD_USER_2);
        assertEquals(VALID_PASSWORD_USER_1, actual1);
        assertEquals(VALID_PASSWORD_USER_2, actual2);
    }

    @Test
    void register_nullPasswordUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(NULL_PASSWORD_USER);
        });
    }

    @Test
    void register_invalidAgeUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(INVALID_AGE_USER_1);
            registrationService.register(INVALID_AGE_USER_2);
            registrationService.register(INVALID_AGE_USER_3);
        });
    }

    @Test
    void register_validAgeUsers_ok() {
        User actual1 = registrationService.register(VALID_AGE_USER_1);
        User actual2 = registrationService.register(VALID_AGE_USER_2);
        assertEquals(VALID_AGE_USER_1, actual1);
        assertEquals(VALID_AGE_USER_2, actual2);
    }

    @Test
    void register_nullLoginUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(NULL_LOGIN_USER);
        });
    }

    @Test
    void register_invalidLoginUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(INVALID_LOGIN_USER_1);
            registrationService.register(INVALID_LOGIN_USER_2);
        });
    }

    @Test
    void register_validLoginUsers_ok() {
        User actual1 = registrationService.register(VALID_LOGIN_USER_1);
        User actual2 = registrationService.register(VALID_LOGIN_USER_2);
        assertEquals(VALID_LOGIN_USER_1, actual1);
        assertEquals(VALID_LOGIN_USER_2, actual2);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(NULL_USER);
        });
    }

    @Test
    void register_sameNameWithUser1_notOk() {
        Storage.people.add(PREPARED_FOR_CHECKING_LOGIN_USER_1);
        Storage.people.add(PREPARED_FOR_CHECKING_LOGIN_USER_2);
        Storage.people.add(PREPARED_FOR_CHECKING_LOGIN_USER_3);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(SAME_NAME_WITH_USER_1);
            registrationService.register(SAME_NAME_WITH_USER_2);
            registrationService.register(SAME_NAME_WITH_USER_3);
        });
    }
}
