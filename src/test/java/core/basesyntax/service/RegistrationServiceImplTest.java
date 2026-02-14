package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {

    private RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        Storage.people.clear();
        registrationService = new RegistrationServiceImpl();
    }

    @Test
    void register_validUser_ok() {
        User user = createValidUser();

        User result = registrationService.register(user);

        assertNotNull(result);
        assertEquals(1, Storage.people.size());
        assertEquals(user.getLogin(), Storage.people.get(0).getLogin());
        assertNotNull(result.getId());
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = createValidUser();
        user.setLogin(null);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        User user = createValidUser();
        user.setLogin("");

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortLogin_notOk() {
        User user = createValidUser();
        user.setLogin("abcdf"); // 5 символів

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_loginMinLength_ok() {
        User user = createValidUser();
        user.setLogin("abcdef"); // 6 символів

        User result = registrationService.register(user);

        assertNotNull(result);
    }

    @Test
    void register_existingLogin_notOk() {
        User user = createValidUser();
        Storage.people.add(user); // НЕ через register()

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = createValidUser();
        user.setPassword(null);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_emptyPassword_notOk() {
        User user = createValidUser();
        user.setPassword("");

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_shortPassword_notOk() {
        User user = createValidUser();
        user.setPassword("abcdf"); // 5 символів

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_passwordMinLength_ok() {
        User user = createValidUser();
        user.setPassword("abcdef"); // 6 символів

        User result = registrationService.register(user);

        assertNotNull(result);
    }

    @Test
    void register_nullAge_notOk() {
        User user = createValidUser();
        user.setAge(null);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        User user = createValidUser();
        user.setAge(-5);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_ageBelowMin_notOk() {
        User user = createValidUser();
        user.setAge(17);

        assertThrows(RegistrationException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_ageEqualsMin_ok() {
        User user = createValidUser();
        user.setAge(18);

        User result = registrationService.register(user);

        assertNotNull(result);
    }

    @Test
    void register_ageAboveMin_ok() {
        User user = createValidUser();
        user.setAge(25);

        User result = registrationService.register(user);

        assertNotNull(result);
    }

    private User createValidUser() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123456");
        user.setAge(20);
        return user;
    }
}