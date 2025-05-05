package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static final int ACCEPTABLE_AGE = 18;
    private static final String DEFAULT_LOGIN = "qwertyuio";
    private static final String DEFAULT_PASSWORD = "1234567";
    private static RegistrationService registrationService;

    @BeforeAll
    static void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void registerNullUser_notOk() {
        User newUser = null;
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerNullLogin_notOk() {
        User newUser = new User(null, DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerNullPassword_notOk() {
        User newUser = new User(DEFAULT_LOGIN, null, ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerNullAge_notOk() {
        User newUser = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerEmptyLogin_notOk() {
        User newUser = new User("", DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerUpperCaseLogin_notOk() {
        User newUser = new User("PLANET", DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerCamelCaseLogin_notOk() {
        User newUser = new User("CamelCaseLogin", DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerLowerCaseLogin_ok() {
        User newUser = new User("lowercaselogin", DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        User actual = registrationService.register(newUser);
        assertEquals(newUser, actual);
    }

    @Test
    void registerLoginLength3_notOk() {
        User newUser = new User("123", DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerLoginLength5_notOk() {
        User newUser = new User("12345", DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }
    
    @Test
    void registerPasswordLength0_notOk() {
        User newUser = new User("defaultlogin1", "", ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerPasswordLength3_notOk() {
        User newUser = new User("defaultlogin2", "123", ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerPasswordLength5_notOk() {
        User newUser = new User("defaultLogin3", "12345", ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerAge10_notOk() {
        User newUser = new User("defaultlogin4", DEFAULT_PASSWORD, 10);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerAge17_notOk() {
        User newUser = new User("defaultlogin5", DEFAULT_PASSWORD, 17);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerExistLogin_notOk() {
        User newUser = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        Storage.people.add(newUser);
        User sameLoginUser = new User(DEFAULT_LOGIN, DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(sameLoginUser));
    }

    @Test
    void registerValidUser_ok() {
        User newUser = new User("defaultlogin5", DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        User actual = registrationService.register(newUser);
        assertEquals(newUser, actual);
    }

    @AfterAll
    static void tearDown() {
        Storage.people.clear();
    }
}
