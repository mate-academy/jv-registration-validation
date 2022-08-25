package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    private static RegistrationService registrationServiceImpl;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationServiceImpl = new RegistrationServiceImpl();
    }

    @BeforeEach
    void beforeEach() {
        user = new User();
        user.setLogin("User");
        user.setPassword("123456");
        user.setAge(MIN_AGE);
    }

    @AfterEach
    void clearData() {
        Storage.people.clear();
    }

    @Test
    void register_minAge_ok() {
        user.setAge(MIN_AGE);
        boolean expected = user.equals(registrationServiceImpl.register(user));
        assertTrue(expected);
    }

    @Test
    void register_loverAgeThanMinAge_notOk() {
        user.setAge(MIN_AGE - 1);
        assertThrows(RuntimeException.class, () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        assertThrows(RuntimeException.class,
                () -> registrationServiceImpl.register(user));

    }

    @Test
    void register_minPasswordLength_ok() {
        user.setPassword("123456");
        User actual = registrationServiceImpl.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_shorterThanMinLengthPassword_notOk() {
        user.setPassword("12345");
        assertThrows(RuntimeException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        assertThrows(RuntimeException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_notExistLogin_ok() {
        user.setLogin("User");
        User actual = registrationServiceImpl.register(user);
        assertEquals(user, actual);
    }

    @Test
    void register_existLogin_notOk() {
        registrationServiceImpl.register(user);
        assertThrows(RuntimeException.class,
                () -> registrationServiceImpl.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        assertThrows(RuntimeException.class,
                () -> registrationServiceImpl.register(user));
    }
}
