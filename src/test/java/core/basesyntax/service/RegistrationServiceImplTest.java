package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User emptyUser;
    private static User user;

    @BeforeAll
    static void init() {
        registrationService = new RegistrationServiceImpl();
        emptyUser = new User();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        user.setAge(99);
        user.setPassword("userPassword");
        user.setLogin("userLogin");
    }

    @AfterEach
    void clearOldData() {
        Storage.people.clear();
    }

    @Test
    void registration_nullUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void registration_emptyUser_notOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(emptyUser);
        });
    }

    @Test
    void registration_emptyUserLogin_notOk() {
        user.setLogin(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registration_emptyUserPassword_notOk() {
        user.setPassword(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registration_emptyUserAge_notOk() {
        user.setAge(null);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registration_User_Ok() {
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void registration_reRegistrationUser_notOk() {
        registrationService.register(user);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registration_youngUser_notOk() {
        user.setAge(17);
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registration_smallPassword_notOk() {
        user.setPassword("small");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }

    @Test
    void registration_loginLengthZero_notOk() {
        user.setLogin("");
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(user);
        });
    }
}
