package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("fujitsu");
        user.setAge(33);
        user.setPassword("RollingInTheDeep");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_isEmptyLogin_NotOK() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_isBlankLogin_NotOk() {
        user.setLogin("     ");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_isAlreadyExistLogin_NotOk() {
        registrationService.register(user);
        User newUser = new User();
        newUser.setLogin("fujitsu");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_nullAge_notOK() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_isLessThanMinimumAge_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_isEmptyPassword_NotOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_isBlankPassword_NotOk() {
        user.setPassword("      ");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_isLessThanMinimumPasswordLength_NotOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_acceptNewUserRegistration_OK() {
        User registerUser = registrationService.register(user);
        assertEquals(user, registerUser);
    }
}
