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
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_shortLogin_notOK() {
        user.setLogin("abed");
        user.setPassword("UserPassword");
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Login must be at least 6 characters");
    }

    @Test
    void register_shortPassword_notOK() {
        user.setLogin("UserLogin");
        user.setPassword("0123");
        user.setAge(19);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Password must be at least 6 characters");
    }

    @Test
    void register_nullAge_notOk() {
        user.setLogin("UserLogin");
        user.setPassword("UserPassword");
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Registration has failed. Age cannot be null");
    }

    @Test
    void register_negativeAge_notOK() {
        user.setLogin("UserLogin");
        user.setPassword("UserPassword");
        user.setAge(-65);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Registration has failed. Age must be positive");
    }

    @Test
    void register_under_MinAge_notOk() {
        user.setLogin("UserLogin");
        user.setPassword("UserPassword");
        user.setAge(16);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Registration has failed. Age must be over or equal to the minimum age.");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        user.setPassword("UserPassword");
        user.setAge(27);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Registration has failed. Login cannot be null");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setLogin("UserLogin");
        user.setPassword(null);
        user.setAge(20);
        assertThrows(RegistrationException.class, () -> registrationService.register(user),
                "Registration has failed. Password cannot be null");
    }

    @Test
    void register_MinValidAge_Ok() {
        user.setLogin("UserLogin");
        user.setPassword("UserPassword");
        user.setAge(18);
        registrationService.register(user);
        int actual = Storage.people.size();
        assertEquals(1, actual);
    }
}
