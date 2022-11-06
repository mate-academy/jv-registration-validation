package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */

public class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static StorageDao storageDao;
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private User user;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();
        storageDao = new StorageDaoImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User();
    }

    @Test
    void register_theSameLogin_notOk() {
        user.setLogin("vasylenko");
        user.setAge(20);
        user.setPassword("123456");
        storageDao.add(user);
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "RunTimeException should be thrown "
                        + "if user with such login already is in the Storage");
    }

    @Test
    void register_ageLessThanMinAge_notOk() {
        user.setLogin("polyakov");
        user.setAge(16);
        user.setPassword("qwerty");
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "RunTimeException should be thrown if user is less than "
                + MIN_AGE + " years old");
    }

    @Test
    void register_passwordLessThanMinPasswordLength_notOk() {
        user.setLogin("ukraine");
        user.setAge(35);
        user.setPassword("123");
        assertThrows(RuntimeException.class, () -> registrationService.register(user),
                "RunTimeException should be thrown if password is less than "
                + MIN_PASSWORD_LENGTH + " characters");
    }

    @Test
    void register_nullLogin_notOk() {
        user.setAge(27);
        user.setPassword("abcdef");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user), "Login can't be null");
    }

    @Test
    void register_nullAge_notOk() {
        user.setLogin("Hello");
        user.setPassword("a1b2c3");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user), "Age can't be null");
    }

    @Test
    void register_nullPassword_notOk() {
        user.setLogin("Hello");
        user.setAge(50);
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user), "Password can't be null");
    }

    @Test
    void register_nullUser_notOk() {
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user), "All fields must be filled!");
    }

    @Test
    void register_validUser_Ok() {
        user.setLogin("user123");
        user.setAge(29);
        user.setPassword("AlfaBeta");
        registrationService.register(user);
        assertEquals(user, storageDao.get("user123"));
    }

    @Test
    void register_correctReturnedUser_Ok() {
        user.setLogin("mate");
        user.setAge(29);
        user.setPassword("789456");
        assertEquals(user, registrationService.register(user));
    }

    @Test
    void register_minAge_Ok() {
        user.setLogin("user");
        user.setAge(18);
        user.setPassword("11041985");
        registrationService.register(user);
        assertEquals(user, storageDao.get("user"));
    }

    @Test
    void register_negativeAge_notOk() {
        user.setLogin("HelloWorld");
        user.setAge(-24);
        user.setPassword("abracadabra");
        assertThrows(RuntimeException.class, () ->
                registrationService.register(user), "Age can't be negative");
    }
}
