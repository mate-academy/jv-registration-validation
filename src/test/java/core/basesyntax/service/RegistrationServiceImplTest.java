package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final String VALID_LOGIN = "abcdefg";
    private static final String VALID_PASSWORD = "123456";
    private static final String LOGIN_LESS_THAN_6_CHARACTERS = "abcde";
    private static final String PASSWORD_LESS_THAN_6_CHARACTERS = "12345";
    private static final int AGE_LESS_THAN_MIN = 10;
    private static final int MIN_AGE = 18;
    private static final int MIN_LENGTH = 6;
    private static RegistrationService registrationService;
    private User validUser = new User(VALID_LOGIN, VALID_PASSWORD, MIN_AGE);

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () ->
                        registrationService.register(null),
                "If user is Null - Registration Exception should be thrown");
    }

    @Test
    void register_nullLogin_notOk() {
        validUser.setLogin(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser),
                "If user's login is Null - Registration Exception should be thrown");

    }

    @Test
    void register_nullPassword_notOk() {
        validUser.setPassword(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser),
                "If user's password is Null - Registration Exception should be thrown");
    }

    @Test
    void register_nullAge_notOk() {
        validUser.setAge(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser),
                "If user's age is Null - Registration Exception should be thrown");
    }

    @Test
    void register_minLogin_notOk() {
        validUser.setLogin(LOGIN_LESS_THAN_6_CHARACTERS);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser),
                "If user's login length is less than " + MIN_LENGTH
                        + " Registration Exception should be thrown");
    }

    @Test
    void register_minPassword_notOk() {
        validUser.setPassword(PASSWORD_LESS_THAN_6_CHARACTERS);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(validUser),
                "If user's password length is less than " + MIN_LENGTH
                        + " Registration Exception should be thrown");
    }

    @Test
    void register_minAge_notOk() {
        validUser.setAge(AGE_LESS_THAN_MIN);
        assertThrows(RegistrationException.class,() ->
                registrationService.register(validUser),
                "If user's age is less than " + MIN_AGE
                        + " Registration Exception should be thrown");
    }

    @Test
    void register_existingUser_notOk() {
        Storage.people.add(validUser);
        assertThrows(RegistrationException.class,() ->
                registrationService.register(validUser),
                "If user already exist in storage Registration Exception should be thrown");

    }

    @Test
    void register_validLogin_ok() {
        validUser.setLogin(VALID_LOGIN);
        registrationService.register(validUser);
        assertTrue(Storage.people.contains(validUser));
    }

    @Test
    void register_validPassword_ok() {
        validUser.setPassword(VALID_PASSWORD);
        registrationService.register(validUser);
        assertTrue(Storage.people.contains(validUser));
    }

    @Test
    void register_validAge_ok() {
        validUser.setAge(MIN_AGE);
        registrationService.register(validUser);
        assertTrue(Storage.people.contains(validUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
