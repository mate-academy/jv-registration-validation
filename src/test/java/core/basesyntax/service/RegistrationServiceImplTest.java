package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {
    private static final int MIN_AGE = 18;

    private static RegistrationServiceImpl registrationService;
    private static User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
    }

    @BeforeEach
    void beforeEach() {
        user.setLogin("User");
        user.setPassword("123456");
        user.setAge(MIN_AGE);
    }

    @AfterEach
    void clearData() {
        Storage.people.clear();
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(NullPointerException.class,
                () -> registrationService.register(null));
    }

    @Test
    void register_minAge_ok() {
        user.setAge(MIN_AGE);
        boolean expected = user.equals(registrationService.register(user));
        assertTrue(expected);
    }

    @Test
    void register_LoverAgeThanMinAge_notOk() {
        user.setAge(MIN_AGE - 1);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));

    }

    @Test
    void register_minPasswordLength_ok() {
        user.setPassword("123456");
        boolean expected = user.equals(registrationService.register(user));
        assertTrue(expected);
    }

    @Test
    void register_ShorterThanMinLengthPassword_notOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_NullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_NotExistLogin_ok() {
        user.setLogin("User");
        boolean expected = user.equals(registrationService.register(user));
        assertTrue(expected);
    }

    @Test
    void register_ExistLogin_notOk() {
        registrationService.register(user);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class,
                () -> registrationService.register(user));
    }
}
