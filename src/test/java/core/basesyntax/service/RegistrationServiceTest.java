package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceTest {
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        user = new User();
        user.setLogin("usersLogin.com");
        user.setPassword("usersPassword");
        user.setAge(18);
    }

    @Test
    void register_login_isOK() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_gotNullUser_notOk() {
        user = null;
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsNull_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsEmpty_notOk() {
        user.setLogin("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsBlank_notOk() {
        user.setLogin("     ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_password_isOK() {
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_passwordIsNull_NotOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsEmpty_NotOk() {
        user.setPassword("");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsBlank_NotOk() {
        user.setPassword("     ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageNull_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-1);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageAboveMinUsersAge_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_age_ok() {
        user.setAge(19);
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
