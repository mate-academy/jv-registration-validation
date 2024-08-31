package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
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
        user.setLogin("Example1");
        user.setPassword("Password1");
        user.setAge(30);
    }

    @Test
    void register_existingLogin_NotOk() {
        User newUser = new User();
        newUser.setLogin("Example1");
        newUser.setPassword("DifferentPass");
        newUser.setAge(25);
        Storage.people.add(newUser);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(newUser));
    }

    @Test
    void register_passwordTooShort_NotOk() {
        user.setPassword("short");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_validUser_Ok() {
        User newUser = new User();
        newUser.setLogin("someLogin2");
        newUser.setPassword("Valid12345");
        newUser.setAge(28);
        User registeredUser = registrationService.register(newUser);
        assertNotNull(registeredUser);
        assertEquals(newUser.getLogin(), registeredUser.getLogin());
        assertEquals(newUser.getPassword(), registeredUser.getPassword());
        assertEquals(newUser.getAge(), registeredUser.getAge());
    }

    @Test
    void register_ageTooYoung_NotOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_tooShortLogin_NotOk() {
        user.setLogin("Pasha");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_nullUser_NotOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(null));
    }

    @Test
    void register_loginContainWhitespace_NotOk() {
        user.setLogin("Pasha Otroda");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_nullLogin_NotOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_nullPassword_NotOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_nullAge_NotOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_negativeAge_NotOk() {
        user.setAge(-28);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }
}
