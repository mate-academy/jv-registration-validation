package core.basesyntax.service;

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
        user.setLogin("Duck@yellow");
        user.setPassword("password007");
        user.setAge(25);
    }

    @Test
    void register_ageLessThanMin_NotOk() {
        user.setAge(11);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerPassword_LessThan() {
        user.setPassword("pas321");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerAge_isNegative() {
        user.setAge(-1);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerPassword_isClear() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerLogin_null_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void registerPassword_null_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void thelogin_already_exists() {
        user.setLogin(user.getLogin());
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
