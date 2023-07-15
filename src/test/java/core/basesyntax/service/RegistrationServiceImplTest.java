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
    private static final User preparedForCheckingLoginUser1 =
            new User("Michael", REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static final User preparedForCheckingLoginUser2 =
            new User("Patrick", REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static final User preparedForCheckingLoginUser3 =
            new User("Jackson", REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static final User nullUser = null;
    private static final User invalidPasswordUser1 = new User("Olivia", "12345", REALLY_COOL_AGE);
    private static final User invalidPasswordUser2 = new User("Charlotte", "", REALLY_COOL_AGE);
    private static final User validPasswordUser1 = new User("Sophia", "123456", REALLY_COOL_AGE);
    private static final User validPasswordUser2 = new User("Isabella", "1234567", REALLY_COOL_AGE);
    private static final User nullPasswordUser = new User("William", null, REALLY_COOL_AGE);
    private static final User invalidAgeUser1 = new User("Benjamin", REALLY_STRONG_PASSWORD, 16);
    private static final User invalidAgeUser2 = new User("Theodore", REALLY_STRONG_PASSWORD, 0);
    private static final User invalidAgeUser3 = new User("Oliver", REALLY_STRONG_PASSWORD, -7);
    private static final User validAgeUser1 = new User("Sebastian", REALLY_STRONG_PASSWORD, 19);
    private static final User validAgeUser2 = new User("Samuel", REALLY_STRONG_PASSWORD, 18);
    private static final User nullLoginUser =
            new User(null, REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static final User invalidLoginUser1 =
            new User("Carl", REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static final User invalidLoginUser2 =
            new User("", REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static final User validLoginUser1 = new
            User("Polina", REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static final User validLoginUser2 = new
            User("Tetiana", REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static final User sameNameWithUser1 = new
            User("Michael", REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static final User sameNameWithUser2 = new
            User("Patrick", REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static final User sameNameWithUser3 = new
            User("Jackson", REALLY_STRONG_PASSWORD, REALLY_COOL_AGE);
    private static RegistrationService registrationService;

    @BeforeAll
    static void registrationServiceInitialization() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_invalidPasswordUsers_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(invalidPasswordUser1);
            registrationService.register(invalidPasswordUser2);
        });
    }

    @Test
    void register_validPasswordUsers_ok() {
        User actual1 = registrationService.register(validPasswordUser1);
        User actual2 = registrationService.register(validPasswordUser2);
        assertEquals(validPasswordUser1, actual1);
        assertEquals(validPasswordUser2, actual2);
    }

    @Test
    void register_nullPasswordUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(nullPasswordUser);
        });
    }

    @Test
    void register_invalidAgeUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(invalidAgeUser1);
            registrationService.register(invalidAgeUser2);
            registrationService.register(invalidAgeUser3);
        });
    }

    @Test
    void register_validAgeUsers_ok() {
        User actual1 = registrationService.register(validAgeUser1);
        User actual2 = registrationService.register(validAgeUser2);
        assertEquals(validAgeUser1, actual1);
        assertEquals(validAgeUser2, actual2);
    }

    @Test
    void register_nullLoginUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(nullLoginUser);
        });
    }

    @Test
    void register_invalidLoginUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(invalidLoginUser1);
            registrationService.register(invalidLoginUser2);
        });
    }

    @Test
    void register_validLoginUsers_ok() {
        User actual1 = registrationService.register(validLoginUser1);
        User actual2 = registrationService.register(validLoginUser2);
        assertEquals(validLoginUser1, actual1);
        assertEquals(validLoginUser2, actual2);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(nullUser);
        });
    }

    @Test
    void register_sameNameWithUser1_notOk() {
        Storage.people.add(preparedForCheckingLoginUser1);
        Storage.people.add(preparedForCheckingLoginUser2);
        Storage.people.add(preparedForCheckingLoginUser3);
        assertThrows(InvalidDataException.class, () -> {
            registrationService.register(sameNameWithUser1);
            registrationService.register(sameNameWithUser2);
            registrationService.register(sameNameWithUser3);
        });
    }
}
