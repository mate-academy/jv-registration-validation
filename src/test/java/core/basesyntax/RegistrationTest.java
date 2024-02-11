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
    void register_passwordLessThanMinCharacters_notOk() {
        user.setLogin("Oleksandr1");
        user.setPassword("12345");
        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_LessThanMinAge_notOk() {
        user.setLogin("Oleksandr2");
        user.setAge(17);
        Assertions.assertThrows(RegistrationException.class, () -> service.register(user));
    }

    @Test
    void register_normalValues_Ok() {
        Assertions.assertTrue(service.register(new User("Danilo", "oleksandr", 21))
                .getLogin().length() >= MIN_LOGIN_LENGTH);
        Assertions.assertTrue(service.register(new User("Oleksandr3", "oleksandr", 21))
                .getLogin().length() >= MIN_LOGIN_LENGTH);
        Assertions.assertTrue(service.register(new User("Oleksandr4", "123456", 21))
                .getPassword().length() >= MIN_PASSWORD_LENGTH);
        Assertions.assertTrue(service.register(new User("Oleksandr5", "12345678", 21))
                .getPassword().length() >= MIN_PASSWORD_LENGTH);
        Assertions.assertTrue(service.register(new User("Oleksandr6", "oleksandr", 18))
                .getAge() >= MIN_AGE);
        Assertions.assertTrue(service.register(new User("Oleksandr7", "oleksandr", 64))
                .getAge() >= MIN_AGE);
    }
}
