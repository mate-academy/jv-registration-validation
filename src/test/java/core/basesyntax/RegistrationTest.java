package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.RegistrationException;
import core.basesyntax.model.User;
import core.basesyntax.service.RegistrationService;
import core.basesyntax.service.RegistrationServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Feel free to remove this class and create your own.
 */
public class RegistrationTest {
    private static RegistrationService service;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private static final int MIN_AGE = 18;
    private User user;

    @BeforeAll
    static void beforeAll() {
        service = new RegistrationServiceImpl();
    }

    @BeforeEach
    void setUp() {
        user = new User("Oleksandr", "oleksandr", 21);
    }

    @Test
    void register_nullUser_notOk() {
        Assertions.assertThrows(RegistrationException.class, () -> service.register(null));
    }

    @Test
    void register_nullLogin_notOk() {
        user.setLogin(null);
        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        user.setPassword(null);
        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        user.setAge(null);
        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_userWithIdenticalLogin_notOk() {
        Storage.people.add(user);
        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_loginLessThanMinCharacters_notOk() {
        user.setLogin("Sasha");
        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_loginMinCharacters_Ok() {
        user.setLogin("Danilo");
        Assertions.assertTrue(service.register(user).getLogin().length() >= MIN_LOGIN_LENGTH);
    }

    @Test
    void register_loginMoreThanMinCharacters_Ok() {
        user.setLogin("Oleksandr1");
        Assertions.assertTrue(service.register(user).getLogin().length() >= MIN_LOGIN_LENGTH);
    }

    @Test
    void register_passwordLessThanMinCharacters_notOk() {
        user.setLogin("Oleksandr2");
        user.setPassword("12345");
        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_passwordMinCharacters_Ok() {
        user.setLogin("Oleksandr3");
        user.setPassword("123456");
        Assertions.assertTrue(service.register(user).getPassword().length() >= MIN_PASSWORD_LENGTH);
    }

    @Test
    void register_passwordWithMoreThanMinCharacters_Ok() {
        user.setLogin("Oleksandr4");
        user.setPassword("12345678");
        Assertions.assertTrue(service.register(user).getPassword().length() >= MIN_PASSWORD_LENGTH);
    }

    @Test
    void register_LessThanMinAge_notOk() {
        user.setLogin("Oleksandr5");
        user.setAge(17);
        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_MinAge_Ok() {
        user.setLogin("Oleksandr6");
        user.setAge(18);
        Assertions.assertTrue(service.register(user).getAge() >= MIN_AGE);
    }

    @Test
    void register_MoreThanMinAge_Ok() {
        user.setLogin("Oleksandr7");
        user.setAge(64);
        Assertions.assertTrue(service.register(user).getAge() >= MIN_AGE);
    }
}
