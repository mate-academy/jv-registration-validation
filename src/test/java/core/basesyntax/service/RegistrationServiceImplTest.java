package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exseption.InvalidUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String FIRST_LOGIN = "first_person";
    private static final String SECOND_LOGIN = "second_person";
    private static final String LOGIN_IN_DB = "registered_user";
    private static final String VALID_PASSWORD = "12345678";
    private static final String INVALID_PASSWORD = "12345";
    private static final Integer AGE_OVER_MIN = 22;
    private static final Integer AGE_UNDER_MIN = 17;
    private static final Integer MIN_AGE = 18;
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(InvalidUserException.class, () -> registrationService.register(null));
    }

    @Test
    void register_validUser_ok() {
        User user = new User(FIRST_LOGIN, VALID_PASSWORD, AGE_OVER_MIN);
        User actual = registrationService.register(user);
        assertEquals(Storage.people.size(), 1);
        assertEquals(Storage.people.get(0), user);
    }

    @Test
    void register_loginNull_notOk() {
        User user = new User(null, VALID_PASSWORD, AGE_OVER_MIN);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passportNull_notOk() {
        User user = new User(FIRST_LOGIN, null, AGE_OVER_MIN);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageNull_notOk() {
        User user = new User(FIRST_LOGIN, VALID_PASSWORD, null);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidPassport_notOk() {
        User user = new User(FIRST_LOGIN, INVALID_PASSWORD, AGE_OVER_MIN);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageUnderMin_notOk() {
        User user = new User(FIRST_LOGIN, VALID_PASSWORD, AGE_UNDER_MIN);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
    }

    @Test
    void register_minAge_ok() {
        User user = new User(FIRST_LOGIN, VALID_PASSWORD, MIN_AGE);
        registrationService.register(user);
        assertEquals(Storage.people.size(), 1);
        assertEquals(Storage.people.get(0), user);
    }

    @Test
    void register_twoDifferentUsers_ok() {
        User firstUser = new User(FIRST_LOGIN, VALID_PASSWORD, MIN_AGE);
        User secondUser = new User(SECOND_LOGIN, VALID_PASSWORD, AGE_OVER_MIN);
        registrationService.register(firstUser);
        registrationService.register(secondUser);
        assertEquals(Storage.people.size(), 2);
        assertEquals(Storage.people.get(0), firstUser);
        assertEquals(Storage.people.get(1), secondUser);
    }

    @Test
    void register_existedLogin_notOk() {
        User expectedUser = new User(LOGIN_IN_DB, VALID_PASSWORD, AGE_OVER_MIN);
        Storage.people.add(expectedUser);
        User user = new User(LOGIN_IN_DB, VALID_PASSWORD, MIN_AGE);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        assertEquals(Storage.people.size(), 1);
        assertEquals(Storage.people.get(0), expectedUser);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
