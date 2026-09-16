package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_ok() {
        User user = createValidUser();
        user.setLogin("validLogin");
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
        assertEquals(user, registeredUser);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () ->
                registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = createValidUser();
        user.setLogin(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = createValidUser();
        user.setLogin("abcde");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_minValidLoginLength_ok() {
        User user = createValidUser();
        user.setLogin("login6");
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
    }

    @Test
    void register_exsistingUserLogin_notOk() {
        User firstUser = createValidUser();
        firstUser.setLogin("existingLogin");
        registrationService.register(firstUser);
        User secondUser = createValidUser();
        secondUser.setLogin("existingLogin");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(secondUser));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = createValidUser();
        user.setPassword(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = createValidUser();
        user.setPassword("12345");
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_minValidPasswordLength_ok() {
        User user = createValidUser();
        user.setLogin("validPasswordUser");
        user.setPassword("123456");
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
    }

    @Test
    void register_nullAge_notOk() {
        User user = createValidUser();
        user.setAge(null);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_underAgeUser_notOk() {
        User user = createValidUser();
        user.setAge(17);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        User user = createValidUser();
        user.setAge(-1);
        assertThrows(RegistrationException.class, () ->
                registrationService.register(user));
    }

    @Test
    void register_minValidAge_ok() {
        User user = createValidUser();
        user.setLogin("uniqueMinAgeLogin");
        user.setAge(18);
        User registeredUser = registrationService.register(user);
        assertNotNull(registeredUser);
    }

    private User createValidUser() {
        User user = new User();
        user.setLogin("defaultLogin");
        user.setPassword("defaultPassword");
        user.setAge(25);
        return user;
    }

}
