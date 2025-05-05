package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        User user = null;
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "User cannot be null");
    }

    @Test
    void register_shortLogin_notOK() {
        User user = new User();
        user.setLogin("abed");
        user.setPassword("UserPassword");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login must be at least 6 characters");
    }

    @Test
    void register_shortPassword_notOK() {
        User user = new User();
        user.setLogin("UserLogin");
        user.setPassword("0123");
        user.setAge(19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password must be at least 6 characters");
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("UserLogin");
        user.setPassword("UserPassword");
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Registration has failed. Age cannot be null");
    }

    @Test
    void register_negativeAge_notOK() {
        User user = new User();
        user.setLogin("UserLogin");
        user.setPassword("UserPassword");
        user.setAge(-65);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Registration has failed. Age must be positive");
    }

    @Test
    void register_under_MinAge_notOk() {
        User user = new User();
        user.setLogin("UserLogin");
        user.setPassword("UserPassword");
        user.setAge(16);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Registration has failed. Age must be over or equal to the minimum age.");
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setLogin(null);
        user.setPassword("UserPassword");
        user.setAge(27);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Registration has failed. Login cannot be null");
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("UserLogin");
        user.setPassword(null);
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Registration has failed. Password cannot be null");
    }

    @Test
    void register_MinValidAge_Ok() {
        User expected = new User();
        expected.setLogin("UserLogin");
        expected.setPassword("UserPassword");
        expected.setAge(18);
        User actual = registrationService.register(expected);
        assertEquals(expected, actual);
    }

    @Test
    void register_userAlreadyExists_NotOk() {
        User user = new User();
        user.setLogin("UserLogin");
        user.setPassword("UserPassword");
        user.setAge(20);
        Storage.people.add(user);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Registration has failed. User already exists");
    }
}
