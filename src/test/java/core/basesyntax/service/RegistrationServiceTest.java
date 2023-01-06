package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("mate_user");
        user.setAge(18);
        user.setPassword("MyCatNameDin");
        user.setId(713298L);
    }

    @AfterEach
    void removeStorage() {
        Storage.people.clear();
    }

    @Test
    void register_userValid_Ok() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));

    }

    @Test
    void register_login_isNull_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_login_isEmpty_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_age_minimumAge_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageNull_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_passwordLength_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordUpperCaseLetters_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLowerCaseLetters_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsEmpty_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordNull_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }

    @Test
    void register_userIdIsNull_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }
}
