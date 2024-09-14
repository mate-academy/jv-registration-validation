package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private static final int ACCEPTABLE_AGE = 18;
    private static final String DEFAULT_LOGIN = "qwertyuio";
    private static final String DEFAULT_PASSWORD = "1234567";
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
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
        User newUser = User.of(null, DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerNullPassword_notOk() {
        User newUser = User.of(DEFAULT_LOGIN, null, ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerNullAge_notOk() {
        User newUser = User.of(DEFAULT_LOGIN, DEFAULT_PASSWORD, null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerEmptyLogin_notOk() {
        User newUser = User.of("", DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerUpperCaseLogin_notOk() {
        User newUser = User.of("PLANET", DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerCamelCaseLogin_notOk() {
        User newUser = User.of("CamelCaseLogin", DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerLowerCaseLogin_ok() {
        User newUser = User.of("lowercaselogin", DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        User actual = registrationService.register(newUser);
        assertEquals(newUser, actual);
    }

    @Test
    void registerLoginLength3_notOk() {
        User newUser = User.of("123", DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerLoginLength6_ok() {
        User newUser = User.of("planet", DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        User actual = registrationService.register(newUser);
        assertEquals(newUser, actual);
    }

    @Test
    void registerPasswordLength0_notOk() {
        User newUser = User.of("defaultlogin1", "", ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerPasswordLength3_notOk() {
        User newUser = User.of("defaultlogin2", "123", ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerPasswordLength6_ok() {
        User newUser = User.of("defaultlogin3", DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        User actual = registrationService.register(newUser);
        assertEquals(newUser, actual);
    }

    @Test
    void registerAge10_notOk() {
        User newUser = User.of("defaultlogin4", DEFAULT_PASSWORD, 10);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerAge17_notOk() {
        User newUser = User.of("defaultlogin5", DEFAULT_PASSWORD, 17);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void registerAge18_ok() {
        User newUser = User.of("defaultlogin6", DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        User actual = registrationService.register(newUser);
        assertEquals(newUser, actual);
    }

    @Test
    void registerExistLogin_notOk() {
        User newUser = User.of(DEFAULT_LOGIN, DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        registrationService.register(newUser);
        User sameLoginUser = User.of(DEFAULT_LOGIN, DEFAULT_PASSWORD, ACCEPTABLE_AGE);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(sameLoginUser));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
