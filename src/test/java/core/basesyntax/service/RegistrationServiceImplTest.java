package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setLogin("ValidLogin");
        user.setPassword("ValidPassword");
        user.setAge(18);
    }

    @Test
    void register_validUser_Ok() {
        assertEquals(user, registrationService.register(user));
        assertTrue(Storage.people.contains(user));
    }

    @Test
    void register_validUserExistsInData_Ok() {
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullUser_notOk() {
        User user = new User();
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsEmpty_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginLengthLessThan6_notOk() {
        user.setLogin("abc");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setLogin("abcde");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsEmpty_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthLessThan6_notOk() {
        user.setPassword("abc");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
        user.setPassword("abcde");
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageLessThanMinAge_NotOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> registrationService.register(user));
    }
}
