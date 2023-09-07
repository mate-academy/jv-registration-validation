package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationService registrationService;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationService();
    }

    @AfterEach
    void tearDown() {
        Storage.PEOPLE.clear();
    }

    @Test
    void register_allParamsValid_ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setAge(18);
        user.setPassword("123456790");
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> registrationService.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setAge(18);
        user.setPassword("123456790");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setAge(null);
        user.setPassword("123456790");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setAge(18);
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidLogin_notOk() {
        User user = new User();
        user.setLogin("a");
        user.setAge(18);
        user.setPassword("123456790");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_emptyLogin_notOk() {
        User user = new User();
        user.setLogin("");
        user.setAge(18);
        user.setPassword("123456790");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setAge(12);
        user.setPassword("123456790");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativedAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setAge(-10);
        user.setPassword("123456790");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_invalidPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setAge(18);
        user.setPassword("1234");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAlreadyExist_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setAge(18);
        user.setPassword("12345678");
        registrationService.register(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
