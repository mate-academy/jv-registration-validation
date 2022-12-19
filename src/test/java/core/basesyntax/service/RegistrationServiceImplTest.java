package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void setUp() {
        user.setAge(22);
        user.setPassword("password");
        user.setLogin("login");
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_loginIsEmpty_notOk() {
        user.setLogin(" ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordLengthLessThan6_notOk() {
        user.setPassword("hello");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_passwordIsEmpty_notOk() {
        user.setPassword(" ");
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageLessThan18_notOk() {
        user.setAge(17);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_ageMoreThan18_ok() {
        User expected = registrationService.register(user);
        assertEquals(user, expected);
    }

    @Test
    void register_age18_ok() {
        user.setAge(18);
        User expected = registrationService.register(user);
        assertEquals(user, expected);
    }

    @Test
    void register_negativeAge_notOk() {
        user.setAge(-1);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAlreadyExists_notOk() {
        assertEquals(registrationService.register(user), user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user));
    }

    @Test
    void register_userIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> registrationService.register(null));
    }
}
