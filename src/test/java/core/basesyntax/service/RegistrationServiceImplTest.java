package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exseption.InvalidUserException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @BeforeEach
    void beforeEach() {
        Storage.people.add(new User(LOGIN_IN_DB, VALID_PASSWORD, AGE_OVER_MIN));
    }

    @Test
    void register_userNull_notOk() {
        assertThrows(InvalidUserException.class, () -> registrationService.register(null));
    }

    @Test
    void register_validUser_ok() {
        User user = new User(FIRST_LOGIN, VALID_PASSWORD, AGE_OVER_MIN);
        User actual = registrationService.register(user);
        assertEquals(Storage.people.size(), 2);
        assertEquals(Storage.people.get(1), user);
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
        assertEquals(Storage.people.size(), 2);
        assertEquals(Storage.people.get(1), user);
    }

    @Test
    void register_twoDifferentUsers_ok() {
        User user1 = new User(FIRST_LOGIN, VALID_PASSWORD, MIN_AGE);
        User user2 = new User(SECOND_LOGIN, VALID_PASSWORD, AGE_OVER_MIN);
        registrationService.register(user1);
        registrationService.register(user2);
        assertEquals(Storage.people.size(), 3);
        assertEquals(Storage.people.get(1), user1);
        assertEquals(Storage.people.get(2), user2);
    }

    @Test
    void register_existedLogin_notOk() {
        User user = new User(LOGIN_IN_DB, VALID_PASSWORD, MIN_AGE);
        User expectedUser = Storage.people.get(0);
        assertThrows(InvalidUserException.class, () -> registrationService.register(user));
        assertEquals(Storage.people.size(), 1);
        assertEquals(Storage.people.get(0), expectedUser);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
